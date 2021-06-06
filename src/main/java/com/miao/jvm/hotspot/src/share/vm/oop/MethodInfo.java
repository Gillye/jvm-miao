package com.miao.jvm.hotspot.src.share.vm.oop;

import lombok.Data;

import java.util.List;

/**
 * @author miaozhou
 **/
@Data
public class MethodInfo {
    private int accessFlags;
    private int nameIndex;
    private int descriptorIndex;
    private int attributesLength;
    private CodeAttributeInfo[] attributes;
    private InstanceKlass belongKlass;
    private String methodName;

    public void initAttributeContainer() {
        attributes = new CodeAttributeInfo[attributesLength];
    }

    @Override
    public String toString() {
        return "MethodInfo{ "
                + belongKlass.getConstantPool().getMethodName(nameIndex) + "#"
                + " }";
    }
}
