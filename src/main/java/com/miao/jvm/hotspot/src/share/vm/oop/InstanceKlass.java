package com.miao.jvm.hotspot.src.share.vm.oop;

import lombok.Data;

/**
 * @author miaozhou
 **/
@Data
public class InstanceKlass extends Klass{

    private byte[] magic = new byte[4];
    private byte[] minorVersion = new byte[2];
    private byte[] majorVersion = new byte[2];

    private int contstantPoolLength;
    private ConstantPool constantPool;


    private int accessFlag;
    private int thisClass;
    private int superClass;


    private int interfaceLength;


    private int fieldLength;


    private int attributesLength;

    public InstanceKlass() {
        constantPool = new ConstantPool();

        constantPool.setKlass(this);
    }

}
