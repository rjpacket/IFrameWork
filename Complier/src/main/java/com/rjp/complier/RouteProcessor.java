package com.rjp.complier;

import com.google.auto.service.AutoService;
import com.rjp.annotation.NeedsPermission;
import com.rjp.annotation.Route;

import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;


@AutoService(Processor.class)
public class RouteProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> routes = roundEnvironment.getElementsAnnotatedWith(Route.class);

        processRoute(routes);

        return false;
    }

    private void processRoute(Set<? extends Element> routes) {
        try {
            String packageName = "com.rjp.fastframework";
            String activityFileName = packageName + ".IProject";
            final String CLASS_SUFFIX = "$Router";
            Filer filer = processingEnv.getFiler();
            JavaFileObject javaFileObject = filer.createSourceFile(activityFileName + CLASS_SUFFIX);
            //定义writer，开始构建java文件
            Writer writer = javaFileObject.openWriter();
            //构建的类名
            String clazzName = "IProject" + CLASS_SUFFIX;

            writer.write("package " + packageName + ";\n");
            writer.write("import com.rjp.expandframework.route.bean.RouteMeta;\n");
            writer.write("import com.rjp.expandframework.route.interfaces.IRoute;\n");
            writer.write("import java.util.Map;\n");
            writer.write("public class " + clazzName + " implements IRoute {\n");
            writer.write("@Override\n");
            writer.write("public void loadRoute(Map<String, RouteMeta> groups) {\n");

            for (Element element : routes) {
                String pageClass = element.asType().toString();
                Route route = element.getAnnotation(Route.class);
                writer.write("groups.put(\"" + route.path() + "\", new RouteMeta(" + pageClass + ".class, \"" + route.path() + "\"));\n");
            }

            writer.write("}\n");
            writer.write("}\n");

            writer.close();
        }catch (Exception e){

        }
    }

    /**
     * 通过方法获取类名
     *
     * @param typeElement
     * @return
     */
    private String getActivityName(Element typeElement) {
        String packageName = getPackageName(typeElement);
        return packageName + "." + typeElement.getSimpleName().toString();
    }

    /**
     * 通过类获取包名
     *
     * @param typeElement
     * @return
     */
    private String getPackageName(Element typeElement) {
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        return packageName;
    }
}
