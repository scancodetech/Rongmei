package com.rongmei.publicdatas.operation;

public enum Operation {
    ADD(0),
    UPDATE(1),
    DELETE(2);

    private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    Operation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    }
