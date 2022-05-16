package com.example.mojingalgorithm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class ResponseData {

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String msg;
    @Getter
    @Setter
    private Object data;

    public ResponseData(ResponseMsg msg, Object data){
        this.code = msg.getCode();
        this.msg = msg.getMsg();
        this.data = data;
    }

}
