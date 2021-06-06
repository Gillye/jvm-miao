package com.miao.jvm.hotspot.src.share.vm.oop;

import com.miao.jvm.hotspot.src.share.vm.interpreter.BytecodeStream;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miaozhou
 **/
@Data
public class CodeAttributeInfo {
    private int attrNameIndex;
    private int attrLength;
    //最大操作数栈
    private int maxStack;
    //最大局部变量数
    private int maxLocals;
    private int codeLength;

    private BytecodeStream code;

    private int exceptionTableLength;

    // 如局部变量表、操作数栈
    private int attributesCount;

    private Map<String, AttributeInfo> attributes = new HashMap<>();
}
