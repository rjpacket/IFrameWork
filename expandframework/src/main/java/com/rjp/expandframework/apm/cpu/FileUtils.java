package com.rjp.expandframework.apm.cpu;

import android.widget.EditText;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // 判断文件是否存在
    public static void judeDirExists(File file) {

        if (file.exists()&&file.isDirectory()) {
                System.out.println("dir exists");

        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }


    public boolean topfileIsExists(String path){
        try{
            File f=new File(path);
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
    //写入到csv中
    public void writeTofile(String filepath, String content){
        String path=filepath;
        File writepath=new File(path);
        FileWriter writer=null;
        try {
            //"E:\\YallaTest\\YallaResult.csv"
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(writepath,false);
            writer.write(content);
            //\r\n表示换行
            //,表示换一格
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public String read(String path) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while((line= br.readLine()) != null) {
            sb.append(line);
        }

        System.out.println(sb.toString()); //sb包含所有文本内容
        return sb.toString();
    }
    public List readcpu(String path, EditText editText) throws IOException{
        String aa=editText.getText().toString();
        BufferedReader br = new BufferedReader(new FileReader(path));
        List list_str=new ArrayList();
        String line = null;
        while((line= br.readLine()) != null) {

            if(line.contains(aa)){
                String m=line.substring(line.indexOf("%")-2, line.indexOf("%"));
                //System.out.println(m);
                list_str.add(m);
            }

        }

       // System.out.println(sb.toString()); //sb包含所有文本内容
        return list_str;
    }

    public List readvss(String path,EditText editText) throws IOException{
        String aa=editText.getText().toString();
        BufferedReader br = new BufferedReader(new FileReader(path));
        List list_str=new ArrayList();
        String line = null;
        while((line= br.readLine()) != null) {
            if(line.contains(aa)){
                String q=line.substring(line.indexOf("K")-7, line.indexOf("K"));
                list_str.add(q);
            }

        }

        // System.out.println(sb.toString()); //sb包含所有文本内容
        return list_str;
    }

    public List readrss(String path,EditText editText) throws IOException{
        String aa=editText.getText().toString();
        BufferedReader br = new BufferedReader(new FileReader(path));
        List list_str=new ArrayList();
        String line = null;
        while((line= br.readLine()) != null) {
            if(line.contains(aa)){
                String g=line.substring(line.indexOf("K",line.indexOf("K")+1 )-6, line.indexOf("K",line.indexOf("K")+1 ));
                list_str.add(g);
            }

        }

        // System.out.println(sb.toString()); //sb包含所有文本内容
        return list_str;
    }


}