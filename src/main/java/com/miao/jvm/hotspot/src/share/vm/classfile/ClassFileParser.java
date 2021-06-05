package com.miao.jvm.hotspot.src.share.vm.classfile;

import com.miao.jvm.hotspot.src.share.tool.DataTranslate;
import com.miao.jvm.hotspot.src.share.tool.Stream;
import com.miao.jvm.hotspot.src.share.vm.oop.ConstantPool;
import com.miao.jvm.hotspot.src.share.vm.oop.InstanceKlass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author miaozhou
 **/
public class ClassFileParser {

    private static Logger logger = LoggerFactory.getLogger(ClassFileParser.class);

    public static InstanceKlass parserFile(byte[] content){
        int index = 0;
        InstanceKlass klass = new InstanceKlass();
        byte[] u2Arr = new byte[2];
        byte[] u4Arr = new byte[4];

        // 魔数 4B
        Stream.readU4Simple(content, 0, klass.getMagic());
        index += 4;

        //次版本号
        Stream.readU2Simple(content, index, klass.getMinorVersion());
        index += 2;

        //主版本号
        Stream.readU2Simple(content, index, klass.getMinorVersion());
        index += 2;

        //常量池大小
        Stream.readU2Simple(content, index, u2Arr);
        index += 2;

        klass.getConstantPool().setLength(DataTranslate.byteToUnsignedShort(u2Arr));
        klass.getConstantPool().initContainer();


        return klass;
    }
}
