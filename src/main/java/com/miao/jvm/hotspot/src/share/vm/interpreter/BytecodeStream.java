package com.miao.jvm.hotspot.src.share.vm.interpreter;

import com.miao.jvm.hotspot.src.share.vm.oop.CodeAttributeInfo;
import com.miao.jvm.hotspot.src.share.vm.oop.MethodInfo;

/**
 * @author miaozhou
 * 字节码指令
 **/
public class BytecodeStream extends BaseBytecodeStream{

    public BytecodeStream(MethodInfo belongMethod, CodeAttributeInfo belongCode) {
        this.belongMethod = belongMethod;
        this.belongCode = belongCode;
        this.length = belongCode.getCodeLength();
        this.index = 0;
        this.codes = new byte[this.length];
    }
}
