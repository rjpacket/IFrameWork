package com.rjp.expandframework.apm.cpu;

import android.text.TextUtils;
import android.util.Log;

import java.io.*;
import java.math.BigDecimal;

public class CpuMonitor {

    public static final String TAG = "CpuMonitor";

    private long cpuFrequency = 1000;//取样间隔 单位ms

    private double pCpu = 0.0;
    private double aCpu = 0.0;
    private double o_pCpu = 0.0;
    private double o_aCpu = 0.0;
    private volatile boolean enabled = false;
    private int currentPid;
    private OnCpuMonitorDataListener onCpuMonitorDataListener;

    Runnable cpuProcessTask = new Runnable() {
        @Override
        public void run() {
            while (enabled) {
                String usage = String.valueOf(getProcessCpuUsage(String.valueOf(currentPid)));
                if (onCpuMonitorDataListener != null) {
                    onCpuMonitorDataListener.onShowCPU(usage);
                }
                try {
                    Thread.sleep(cpuFrequency);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void setCpuFrequency(long cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    public void setOnCpuMonitorDataListener(OnCpuMonitorDataListener onCpuMonitorDataListener) {
        this.onCpuMonitorDataListener = onCpuMonitorDataListener;
    }

    public void start() {
        if (!enabled) {
            enabled = true;
            currentPid = android.os.Process.myPid();
            new Thread(cpuProcessTask).start();
        }
    }

    public void stop() {
        if (enabled) {
            enabled = false;
        }
    }


    public static void execShellGetCpuData(String packageName) {
        Process process = null;
        DataOutputStream os = null;
        BufferedReader in = null;

        try {
            process = Runtime.getRuntime().exec("sh");
            os = new DataOutputStream(process.getOutputStream());
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            os.writeBytes("top -n 1\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            String line = null;
            int index = -1;
            while ((line = in.readLine()) != null) {
                Log.d("===>", String.format("原始=%s", line));
                if (line.contains("PID") && line.contains("CPU")) {
                    String[] split = line.split("( )+");
                    int length = split.length;
                    boolean skip = true;
                    for (int i = 0; i < length; i++) {
                        Log.d("===>", String.format("当前列%s", split[i]));
                        if (skip && !"PID".equals(split[i])) {
                            skip = false;
                            continue;
                        }
                        index++;
                        if (split[i].contains("CPU")) {
                            if (split[i].contains("[")) {
                                index++;
                            }
                            break;
                        }
                    }
                } else {
                    //说明找到了
                    if (index != -1 && line.contains(packageName)) {
                        String[] titles = line.trim().split("( )+");
                        int length = titles.length;
                        for (int i = 0; i < length; i++) {
                            if (i == index) {
                                Log.d("===>", String.format("index=%s, cpu=%s", index, titles[i].replace("%", "")));
                            }
                        }
                        break;
                    }
                }
            }

            process.waitFor();
        } catch (Exception e) {

        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {

            }
        }
    }


    public double getProcessCpuUsage(String pid) {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();
            String[] toks = load.split(" ");

            double totalCpuTime1 = 0.0;
            int len = toks.length;
            for (int i = 2; i < len; i++) {
                totalCpuTime1 += Double.parseDouble(toks[i]);
            }

            RandomAccessFile reader2 = new RandomAccessFile("/proc/" + pid + "/stat", "r");
            String load2 = reader2.readLine();
            String[] toks2 = load2.split(" ");

            double processCpuTime1 = 0.0;
            double utime = Double.parseDouble(toks2[13]);
            double stime = Double.parseDouble(toks2[14]);
            double cutime = Double.parseDouble(toks2[15]);
            double cstime = Double.parseDouble(toks2[16]);

            processCpuTime1 = utime + stime + cutime + cstime;

            try {
                Thread.sleep(360);
            } catch (Exception e) {
                e.printStackTrace();
            }
            reader.seek(0);
            load = reader.readLine();
            reader.close();
            toks = load.split(" ");
            double totalCpuTime2 = 0.0;
            len = toks.length;
            for (int i = 2; i < len; i++) {
                totalCpuTime2 += Double.parseDouble(toks[i]);
            }
            reader2.seek(0);
            load2 = reader2.readLine();
            String[] toks3 = load2.split(" ");

            double processCpuTime2 = 0.0;
            utime = Double.parseDouble(toks3[13]);
            stime = Double.parseDouble(toks3[14]);
            cutime = Double.parseDouble(toks3[15]);
            cstime = Double.parseDouble(toks3[16]);

            processCpuTime2 = utime + stime + cutime + cstime;
            double usage = (processCpuTime2 - processCpuTime1) * 100.00
                    / (totalCpuTime2 - totalCpuTime1);
            BigDecimal b = new BigDecimal(usage);
            double res = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return 0.0;
    }

    private String getProcessCpuUsage1(int pid) {
        String result = "";
        String[] result1 = null;
        String[] result2 = null;
        if (pid >= 0) {

            result1 = CpuUtils.getProcessCpuAction(pid);
            if (null != result1) {
                pCpu = Double.parseDouble(result1[1])
                        + Double.parseDouble(result1[2]);
            }
            result2 = CpuUtils.getCpuAction();
            if (null != result2) {
                aCpu = 0.0;
                for (int i = 2; i < result2.length; i++) {

                    aCpu += Double.parseDouble(result2[i]);
                }
            }
            double usage = 0.0;
            if ((aCpu - o_aCpu) != 0) {
                usage = DoubleUtils.div(((pCpu - o_pCpu) * 100.00),
                        (aCpu - o_aCpu), 2);
                if (usage < 0) {
                    usage = 0;
                } else if (usage > 100) {
                    usage = 100;
                }

            }
            o_pCpu = pCpu;
            o_aCpu = aCpu;
            result = String.format("%s%%", usage);
        }
        return result;
    }

}