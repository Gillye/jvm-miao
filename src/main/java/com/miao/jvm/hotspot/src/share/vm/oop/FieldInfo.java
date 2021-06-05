package com.miao.jvm.hotspot.src.share.vm.oop;

import lombok.Data;

import java.util.List;

/**
 * @author miaozhou
 **/
@Data
public class FieldInfo {

    private int accessFlag;
    private int nameIndex;
    private int descriptorIndex;
    private int attributesLength;
    private List<AttributeInfo> attributeInfos;
}
