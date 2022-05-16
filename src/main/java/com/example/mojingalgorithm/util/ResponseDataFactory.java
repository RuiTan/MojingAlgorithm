package com.example.mojingalgorithm.util;

public class ResponseDataFactory {
    public static ResponseData generate(Object data){
        if (data == null) {
            return new ResponseData(ResponseMsg.EMPTY, null);
        } else if (data instanceof Exception) {
            return new ResponseData(ResponseMsg.ERROR, ((Exception) data).getMessage());
        } else {
            return new ResponseData(ResponseMsg.SUCCESS, data);
        }
    }
}
