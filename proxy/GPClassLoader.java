package com.gupao.lyf.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: luo
 * Date: 19-3-17
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public class GPClassLoader extends ClassLoader{
    private File classFilePath;
    public GPClassLoader(){
        String classPath = GPClassLoader.class.getResource("").getPath();
        this.classFilePath = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = GPClassLoader.class.getPackage().getName() + "."+name;
        System.out.println(className);
        if(classFilePath!=null){
           File classFile =  new File(classFilePath,name.replaceAll("\\.","/")+".class");

           if(classFile.exists()){
               FileInputStream in = null;
               ByteArrayOutputStream out = null;
               try{
                   in = new FileInputStream(classFile);
                   out = new ByteArrayOutputStream();
                   byte[] buffer = new byte[1024];
                   int len = 0;
                   while((len = in.read(buffer))!=-1) {
                       out.write(buffer,0,len);
                   }
                   return defineClass(className,out.toByteArray(),0,out.size());
               }catch (Exception e){
                   e.printStackTrace();
               }finally {
                   if(in!=null){
                       try {
                           in.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
                   if(out!=null){
                       try {
                           out.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }

               }
           }
        }
        return null;
    }
}
