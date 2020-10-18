package com.example.demo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{

    static final String XLASS = "Hello.xlass";
    String classFile = this.getClass().getResource("").getPath().replace("target/classes/","src/main/java/")+"\\" + XLASS;


    public static void main(String[] args) throws Exception{
        Object hello = new MyClassLoader().findClass("Hello").newInstance();
        Method method = hello.getClass().getMethod("hello");
        method.invoke(hello);
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try{
            System.out.println(this.getClass().getResource(""));
            byte[] byteRead = FileUtils.readFileToByteArray(new File(classFile));
            for(int i=0;i<byteRead.length;i++){
                byteRead[i]=(byte)(255-byteRead[i]);
            }
            return defineClass(name,byteRead,0,byteRead.length);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.findClass(name);
    }


}
