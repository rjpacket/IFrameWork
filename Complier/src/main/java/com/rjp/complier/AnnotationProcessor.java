package com.rjp.complier;

import com.google.auto.service.AutoService;
import com.rjp.annotation.NeedsPermission;
import com.rjp.annotation.OnNeverAskAgain;
import com.rjp.annotation.OnPermissionDenied;
import com.rjp.annotation.OnShowRationale;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementsUtils;
    private Filer filer;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        typeUtils = processingEnvironment.getTypeUtils();
        filer = processingEnvironment.getFiler();
        elementsUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(NeedsPermission.class.getCanonicalName());
        types.add(OnPermissionDenied.class.getCanonicalName());
        types.add(OnShowRationale.class.getCanonicalName());
        types.add(OnNeverAskAgain.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> needsSet = roundEnvironment.getElementsAnnotatedWith(NeedsPermission.class);
        Map<String, List<ExecutableElement>> needsMap = new HashMap<>();
        for (Element element : needsSet) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String activityName = getActivityName(executableElement);
            List<ExecutableElement> list = needsMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                needsMap.put(activityName, list);
            }
            list.add(executableElement);
            System.out.println(">>>>>>>>>>>" + executableElement.getSimpleName().toString());
        }

        Set<? extends Element> deniedSet = roundEnvironment.getElementsAnnotatedWith(OnPermissionDenied.class);
        Map<String, List<ExecutableElement>> deniedMap = new HashMap<>();
        for (Element element : deniedSet) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String activityName = getActivityName(executableElement);
            List<ExecutableElement> list = deniedMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                deniedMap.put(activityName, list);
            }
            list.add(executableElement);
        }

        Set<? extends Element> showSet = roundEnvironment.getElementsAnnotatedWith(OnShowRationale.class);
        Map<String, List<ExecutableElement>> showMap = new HashMap<>();
        for (Element element : showSet) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String activityName = getActivityName(executableElement);
            List<ExecutableElement> list = showMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                showMap.put(activityName, list);
            }
            list.add(executableElement);
        }

        Set<? extends Element> neverSet = roundEnvironment.getElementsAnnotatedWith(OnNeverAskAgain.class);
        Map<String, List<ExecutableElement>> neverMap = new HashMap<>();
        for (Element element : neverSet) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String activityName = getActivityName(executableElement);
            List<ExecutableElement> list = neverMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                neverMap.put(activityName, list);
            }
            list.add(executableElement);
        }

        //以上拿到所有的注解方法

        Set<String> keys = needsMap.keySet();
        for (String activityName : keys) {
            List<ExecutableElement> needsElements = needsMap.get(activityName);
            List<ExecutableElement> deniedElements = deniedMap.get(activityName);
            List<ExecutableElement> showElements = showMap.get(activityName);
            List<ExecutableElement> neverElements = neverMap.get(activityName);

            final String CLASS_SUFFIX = "$Permissions";
            Filer filer = processingEnv.getFiler();
            try {
                JavaFileObject javaFileObject = filer.createSourceFile(activityName + CLASS_SUFFIX);
                String packageName = getPackageName(needsElements.get(0));
                //定义writer，开始构建java文件
                Writer writer = javaFileObject.openWriter();
                //构建的类名
                String clazzName = needsElements.get(0).getEnclosingElement().getSimpleName().toString() + CLASS_SUFFIX;
                //导包
                writer.write("package " + packageName + ";\n");
                writer.write("import com.rjp.expandframework.permission.listener.RequestPermission;\n");
                writer.write("import com.rjp.expandframework.permission.listener.PermissionRequest;\n");
                writer.write("import com.rjp.expandframework.permission.listener.PermissionSetting;\n");
                writer.write("import com.rjp.expandframework.permission.PermissionUtils;\n");
                writer.write("import android.support.v7.app.AppCompatActivity;\n");
                writer.write("import android.support.v4.app.ActivityCompat;\n");
                writer.write("import android.support.annotation.NonNull;\n");
                writer.write("import android.content.Intent;\n");
                writer.write("import android.net.Uri;\n");
                writer.write("import android.provider.Settings;\n");
                writer.write("import java.lang.ref.WeakReference;\n");

                writer.write("public class " + clazzName + " implements RequestPermission<" + activityName + "> {\n");

                writer.write("private static final int REQUEST_CODE = 666;\n");
                writer.write("private static String[] PERMISSION_REQUEST;\n");

                writer.write("public void requestPermission(" + activityName + " target, String[] permissions) {\n");

                writer.write("PERMISSION_REQUEST = permissions;\n");
                writer.write("if (PermissionUtils.hasSelfPermissions(target, PERMISSION_REQUEST)) {\n");

                // 循环生成Activity每个权限申请方法
                for (ExecutableElement executableElement : needsElements) {
                    // 获取方法名
                    String methodName = executableElement.getSimpleName().toString();
                    // 调用申请权限方法
                    writer.write("target." + methodName + "();\n");
                }

                writer.write("} else if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_REQUEST)) {\n");
                if (showElements != null && showElements.size() > 0) {
                    // 循环生成Activity每个提示用户为何要开启权限方法
                    for (ExecutableElement executableElement : showElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用提示用户为何要开启权限方法
                        writer.write("target." + methodName + "(new PermissionRequestImpl(target));\n");
                    }
                }else{
                    writer.write("ActivityCompat.requestPermissions(target, PERMISSION_REQUEST, REQUEST_CODE);\n");
                }

                writer.write("} else {\n");
                writer.write("ActivityCompat.requestPermissions(target, PERMISSION_REQUEST, REQUEST_CODE);\n}\n}\n");

                // 生成onRequestPermissionsResult方法
                writer.write("public void onRequestPermissionsResult(" + activityName + " target, int requestCode, @NonNull int[] grantResults) {");
                writer.write("switch(requestCode) {\n");
                writer.write("case REQUEST_CODE:\n");
                writer.write("if (PermissionUtils.verifyPermissions(grantResults)) {\n");

                // 循环生成MainActivity每个权限申请方法
                for (ExecutableElement executableElement : needsElements) {
                    // 获取方法名
                    String methodName = executableElement.getSimpleName().toString();
                    // 调用申请权限方法
                    writer.write("target." + methodName + "();\n");
                }

                writer.write("} else if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_REQUEST)) {\n");

                if(neverElements != null && neverElements.size() > 0) {
                    // 循环生成Activity每个不再询问后的提示
                    for (ExecutableElement executableElement : neverElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用不再询问后的提示
                        writer.write("target." + methodName + "(new PermissionSettingImpl(target));\n");
                    }
                }else if(deniedElements != null && deniedElements.size() > 0){
                    // 循环生成Activity每个拒绝时的提示方法
                    for (ExecutableElement executableElement : deniedElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用拒绝时的提示方法
                        writer.write("target." + methodName + "();\n");
                    }
                }

                writer.write("} else {\n");

                if(deniedElements != null && deniedElements.size() > 0) {
                    // 循环生成MainActivity每个拒绝时的提示方法
                    for (ExecutableElement executableElement : deniedElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用拒绝时的提示方法
                        writer.write("target." + methodName + "();\n");
                    }
                }

                writer.write("}\nbreak;\ndefault:\nbreak;\n}\n}\n");

                // 生成接口实现类：PermissionRequestImpl implements PermissionRequest
                writer.write("private static final class PermissionRequestImpl implements PermissionRequest {\n");
                writer.write("private final WeakReference<" + activityName + "> weakTarget;\n");
                writer.write("private PermissionRequestImpl(" + activityName + " target) {\n");
                writer.write("this.weakTarget = new WeakReference(target);\n}\n");
                writer.write("public void proceed() {\n");
                writer.write(activityName + " target = (" + activityName + ")this.weakTarget.get();\n");
                writer.write("if (target != null) {\n");
                writer.write("ActivityCompat.requestPermissions(target, PERMISSION_REQUEST, REQUEST_CODE);\n}\n}\n}\n");

                // 生成接口实现类：PermissionSettingImpl implements PermissionSetting
                writer.write("private static final class PermissionSettingImpl implements PermissionSetting {\n");
                writer.write("private final WeakReference<" + activityName + "> weakTarget;\n");
                writer.write("private PermissionSettingImpl(" + activityName + " target) {\n");
                writer.write("this.weakTarget = new WeakReference(target);\n}\n");
                writer.write("public void setting(int settingCode) {\n");
                writer.write(activityName + " target = (" + activityName + ")this.weakTarget.get();\n");
                writer.write("if (target != null) {\n");
                writer.write("Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);\n");
                writer.write("Uri uri = Uri.fromParts(\"package\", target.getPackageName(), null);\n");
                writer.write("intent.setData(uri);\n");
                writer.write("target.startActivityForResult(intent, settingCode);\n}\n}\n}\n");

                writer.write("\n}");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }


        return false;
    }

    /**
     * 通过方法获取类名
     *
     * @param executableElement
     * @return
     */
    private String getActivityName(ExecutableElement executableElement) {
        String packageName = getPackageName(executableElement);
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        return packageName + "." + typeElement.getSimpleName().toString();
    }

    /**
     * 通过类获取包名
     *
     * @param executableElement
     * @return
     */
    private String getPackageName(ExecutableElement executableElement) {
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        return packageName;
    }
}
