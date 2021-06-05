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

    private static int parserConstantPool(byte[] content,int index,InstanceKlass klass){
        logger.info("开始解析常量池");
        byte[] u2Arr = new byte[2];
        byte[] u4Arr = new byte[4];
        byte[] u8Arr = new byte[8];

        for(int i = 1; i < klass.getConstantPool().getLength(); i++){
            int tag = Stream.readU1Simple(content,index);
            index += 1;
            switch (tag){
                case ConstantPool.JVM_CONSTANT_Utf8: {
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Utf8;

                    //字符串长度
                    Stream.readU2Simple(content, index, u2Arr);
                    index += 2;

                    int len = DataTranslate.byteToUnsignedShort(u2Arr);

                    // 字符串内容
                    byte[] str = new byte[len];
                    Stream.readSimple(content, index, len, str);

                    index += len;

                    klass.getConstantPool().getDataMap().put(i, new String(str));
                    logger.info("\t第 " + i + " 个: 类型: utf8，值: " + klass.getConstantPool().getDataMap().get(i));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_Integer:{
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Integer;

                    Stream.readU4Simple(content,index,u4Arr);
                    index += 4;

                    klass.getConstantPool().getDataMap().put(i,DataTranslate.byteArrayToInt(u4Arr));
                    logger.info("\t第 " + i + " 个: 类型: integer，值: " + klass.getConstantPool().getDataMap().get(i));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_Float:{
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Float;

                    Stream.readU4Simple(content,index,u4Arr);
                    index += 4;

                    klass.getConstantPool().getDataMap().put(i,DataTranslate.byteArrayToInt(u4Arr));
                    logger.info("\t第 " + i + " 个: 类型: float，值: " + klass.getConstantPool().getDataMap().get(i));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_Double:{
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Double;

                    Stream.readU8Simple(content,index,u8Arr);
                    index += 8;

                    klass.getConstantPool().getDataMap().put(i,DataTranslate.bytesToDouble(u8Arr,false));
                    logger.info("\t第 " + i+ " 个: 类型: double，值: " + klass.getConstantPool().getDataMap().get(i));

                    klass.getConstantPool().getTag()[i++] = ConstantPool.JVM_CONSTANT_Double;

                    klass.getConstantPool().getDataMap().put(i,DataTranslate.bytesToDouble(u8Arr,false));
                    logger.info("\t第 " + i+ " 个: 类型: double，值: " + klass.getConstantPool().getDataMap().get(i));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_Long:{
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Long;
                    Stream.readU8Simple(content,index,u8Arr);
                    index += 8;

                    try {
                        klass.getConstantPool().getDataMap().put(i,DataTranslate.bytes2long(u8Arr));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    logger.info("\t第 " + i + " 个: 类型: long，值: " + klass.getConstantPool().getDataMap().get(i));

                    klass.getConstantPool().getTag()[i++] = ConstantPool.JVM_CONSTANT_Long;
                    try {
                        klass.getConstantPool().getDataMap().put(i++,DataTranslate.bytes2long(u8Arr));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    logger.info("\t第 " + i+ " 个: 类型: long，值: " + klass.getConstantPool().getDataMap().get(i++));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_Class:{
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Class;
                    Stream.readU2Simple(content,index,u2Arr);
                    index += 2;

                    klass.getConstantPool().getDataMap().put(i,DataTranslate.byteToUnsignedShort(u2Arr));
                    logger.info("\t第 " + i+ " 个: 类型: class，值: " + klass.getConstantPool().getDataMap().get(i));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_String:{
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_String;

                    Stream.readU2Simple(content,index,u2Arr);
                    index += 2;

                    klass.getConstantPool().getDataMap().put(i,DataTranslate.byteToUnsignedShort(u2Arr));
                    logger.info("\t第 " + i+ " 个: 类型: string，值: " + klass.getConstantPool().getDataMap().get(i));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_Fieldref:{
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Fieldref;

                    //class index 对常量池表的有效索引，必须是class_info属性
                    Stream.readU2Simple(content,index,u2Arr);
                    index += 2;
                    int classIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    //name and type index 对常量池表的有效索引，必须是nameAndType_info属性
                    Stream.readU2Simple(content,index,u2Arr);
                    index += 2;
                    int nameAndTypeIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    //为了方便,放在一起
                    klass.getConstantPool().getDataMap().put(i, classIndex << 16 | nameAndTypeIndex);
                    logger.info("\t第 " + i+ " 个: 类型: Field，值: 0x" + Integer.toHexString((int) klass.getConstantPool().getDataMap().get(i)));
                    break;
                }
                case ConstantPool.JVM_CONSTANT_Methodref: {
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_Methodref;

                    // Class_info
                    Stream.readU2Simple(content, index, u2Arr);
                    index += 2;

                    int classIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    // NameAndType info
                    Stream.readU2Simple(content, index, u2Arr);
                    index += 2;

                    int nameAndTypeIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    // 将classIndex与nameAndTypeIndex拼成一个，前十六位是classIndex，后十六位是nameAndTypeIndex
                    klass.getConstantPool().getDataMap().put(i, classIndex << 16 | nameAndTypeIndex);

                    logger.info("\t第 " + i+ " 个: 类型: Method，值: 0x" + Integer.toHexString((int) klass.getConstantPool().getDataMap().get(i)));

                    break;
                }
                case ConstantPool.JVM_CONSTANT_InterfaceMethodref: {
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_InterfaceMethodref;

                    // Class_info
                    Stream.readU2Simple(content, index, u2Arr);
                    index += 2;

                    int classIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    // NameAndType info
                    Stream.readU2Simple(content, index, u2Arr);
                    index += 2;

                    int nameAndTypeIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    // 将classIndex与nameAndTypeIndex拼成一个，前十六位是classIndex，后十六位是nameAndTypeIndex
                    klass.getConstantPool().getDataMap().put(i, classIndex << 16 | nameAndTypeIndex);

                    logger.info("\t第 " + i + " 个: 类型: InterfaceMethodref，值: 0x" + Integer.toHexString((int) klass.getConstantPool().getDataMap().get(i)));

                    break;
                }
                case ConstantPool.JVM_CONSTANT_NameAndType: {
                    klass.getConstantPool().getTag()[i] = ConstantPool.JVM_CONSTANT_NameAndType;

                    // 方法名
                    Stream.readU2Simple(content, index, u2Arr);
                    index += 2;

                    int methodNameIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    // 方法描述符
                    Stream.readU2Simple(content, index, u2Arr);
                    index += 2;

                    int methodDescriptorIndex = DataTranslate.byteToUnsignedShort(u2Arr);

                    klass.getConstantPool().getDataMap().put(i, methodNameIndex << 16 | methodDescriptorIndex);

                    logger.info("\t第 " + i+ " 个: 类型: NameAndType，值: 0x" + Integer.toHexString((int) klass.getConstantPool().getDataMap().get(i)));

                    break;
                }
            }
        }
        return index;
    }
}
