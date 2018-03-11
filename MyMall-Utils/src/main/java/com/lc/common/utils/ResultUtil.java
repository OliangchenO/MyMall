package com.lc.common.utils;

import com.lc.common.pojo.Result;

public class ResultUtil<T> {

    private Result<T> result;

    public ResultUtil(){
        result=new Result<>();
        result.setSuccess(true);
        result.setMessage("success");
    }

    public Result<T> setData(T t){
        this.result.setResult(t);
        return this.result;
    }

    public Result<T> setErrorMsg(String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        return this.result;
    }
}
