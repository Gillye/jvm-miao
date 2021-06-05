package com.miao.jvm.hotspot.src.share.vm.oop;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miaozhou
 **/
@Data
public class ConstantPool {

    public static final int JVM_CONSTANT_Utf8 = 1;
    public static final int JVM_CONSTANT_Unicode = 2;   /* unused */
    public static final int JVM_CONSTANT_Integer = 3;
    public static final int JVM_CONSTANT_Float = 4;
    public static final int JVM_CONSTANT_Long = 5;
    public static final int JVM_CONSTANT_Double = 6;
    public static final int JVM_CONSTANT_Class = 7;
    public static final int JVM_CONSTANT_String = 8;
    public static final int JVM_CONSTANT_Fieldref = 9;
    public static final int JVM_CONSTANT_Methodref = 10;
    public static final int JVM_CONSTANT_InterfaceMethodref = 11;
    public static final int JVM_CONSTANT_NameAndType = 12;
    public static final int JVM_CONSTANT_MethodHandle = 15; /* JSR 292 */
    public static final int JVM_CONSTANT_MethodType = 16;   /* JSR 292 */
    public static final int JVM_CONSTANT_InvokeDynamic = 18;    /* JSR 292 */
    public static final int JVM_CONSTANT_ExternalMax = 18;  /* Last tag found in classfiles */

    private Klass klass;

    private int length;

    private int[] tag;

    private Map<Integer, Object> dataMap;

    public void initContainer(){
        tag = new int[length];
        dataMap = new HashMap<>(length);
    }

    public String getClassName(int index) {
        if (0 == index || index > length) {
            return null;
        }

        /**
         * 解释：
         *  1、参数index对应的是JVM_CONSTANT_Class
         *  2、JVM_CONSTANT_Class中的信息才是类的全限定名在常量池中的索引
         */
        return (String) getDataMap().get(getDataMap().get(index));
    }

    public String getSuperClassName(int index) {
        if (0 == index || index > length) {
            return null;
        }

        /**
         * 解释：
         *  1、参数index对应的是JVM_CONSTANT_Class
         *  2、JVM_CONSTANT_Class中的信息才是类的全限定名在常量池中的索引
         */
        return (String) getDataMap().get(index);
    }

    public String getMethodName(int index) {
        if (0 == index || index > length) {
            return null;
        }

        return (String) getDataMap().get(index);
    }

}
