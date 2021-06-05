package com.miao.jvm.jdk;


import com.miao.jvm.hotspot.src.share.vm.classfile.BootClassLoader;
import com.miao.jvm.hotspot.src.share.vm.oop.InstanceKlass;

/**
 * @author miaozhou
 **/
public class Main {

    public static void main(String[] args) {
        startJVM();
    }

    private static void startJVM(){
        InstanceKlass mainKlass = BootClassLoader.loadMainKlass("com.miao.jvm.example.HelloWorld");

        System.out.println("1");
    }
}
