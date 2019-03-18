package com.gupao.lyf.proxy;

import com.gupao.lyf.vo.Person;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: luo
 * Date: 19-3-17
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
public class GPProxy{


    private static final String ln = "\r\n";
    public GPProxy() {
    }

    /**
     * 1 拿到被代理类的对象引用，并获取所有接口，反射获取
     * 2 JDK Proxy类重新生成一个新类，同时新类实现被代理实现的所有接口
     * 3 动态生成java代码，把新家的业务逻辑由一定的逻辑代码区调用（反射调用）
     * 4 编译生成class文件
     * 5 重新加载到JVM中运行
     *    字节码重组过程
     * ==================================
     *
     * @param classLoader
     * @param interfaces
     * @param h
     * @return
     */
    public static Object newInstance(GPClassLoader classLoader,Class<?>[] interfaces,GPInvocationHandler h){
        try{
            //1 动态生成java文件
            String proxyStr = generateSrc(interfaces);
            //2 java文件输出硬盘
            File file = writeDisk(proxyStr);
            //3 把生成的java文件编译成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null);
            Iterable iterable = manager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = compiler.getTask(null,manager,null,null,null,iterable);
            task.call();
            manager.close();
            //4 把.class文件重新加载到JVM
            Class clazz = classLoader.findClass("$Proxy0");
            //5 返回字节码重组后新的代理对象
            Constructor constructor = clazz.getConstructor(GPInvocationHandler.class);
            return constructor.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static File writeDisk(String proxyStr) {
        FileOutputStream fileOutputStream = null;
        File file;
        try {
            String filePath = GPProxy.class.getResource("").getPath();
            filePath = filePath +"$Proxy0.java";
            file = new File(filePath);
            System.out.println(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(proxyStr.getBytes());
            fileOutputStream.flush();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.gupao.lyf.proxy;"+ln);
        sb.append("import com.gupao.lyf.vo.Person;"+ln);
        sb.append("import com.gupao.lyf.proxy.GPInvocationHandler;"+ln);
        sb.append("import java.lang.reflect.Method;"+ln);
        sb.append("public class $Proxy0 implements "+interfaces[0].getName()+" { "+ln);
            // 持有handler，调用invoke方法
            sb.append("GPInvocationHandler h;"+ln);
            sb.append("public $Proxy0(GPInvocationHandler h) {"+ln);
                sb.append("this.h = h;"+ln);
            sb.append("}"+ln);
            // 实现方法
            for(Method method : interfaces[0].getMethods()){
                sb.append("public "+method.getReturnType()+" "+method.getName()+"(){"+ln);
                    sb.append("try{ "+ln);
                        sb.append("Method m = "+interfaces[0].getName()+".class.getMethod(\""+method.getName()+"\",new Class[]{});"+ln);
                        sb.append("this.h.invoke(this,m,null);"+ln);
                    sb.append("}catch(Throwable e){"+ln);
                        sb.append("e.printStackTrace();"+ln);
                    sb.append("}"+ln);
                sb.append("}"+ln);
            }

        sb.append("}"+ln);
        return sb.toString();
    }

}
