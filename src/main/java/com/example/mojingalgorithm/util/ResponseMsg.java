package com.example.mojingalgorithm.util;

import lombok.Getter;
import lombok.Setter;

public enum ResponseMsg {
    /**
     *
     */
    SUCCESS(200, "success"),
    ERROR(500,"error"),
    EMPTY(404, "empty");

    private ResponseMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String msg;
}
