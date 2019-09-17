package com.rjp.expandframework.IHttp;

/**
 * author: jinpeng.ren create at 2019/8/6 11:03
 * email: jinpeng.ren@11bee.com
 */
public class BaseModel {
    private int status;

    private String message;

    private Object data;

    private boolean success;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
