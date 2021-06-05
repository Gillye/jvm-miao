package com.miao.jvm.hotspot.src.share.vm.oop;

import lombok.Data;

/**
 * @author miaozhou
 **/
@Data
public class InterfaceInfo {

    private int constantPoolIndex;

    private String interfaceName;

    public InterfaceInfo(int index, String name) {
        this.constantPoolIndex = index;
        this.interfaceName = name;
    }
}
