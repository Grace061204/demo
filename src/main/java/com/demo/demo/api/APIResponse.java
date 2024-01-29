package com.demo.demo.api;

public class APIResponse {
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    Object data;

    String status_code;

    

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public APIResponse(String error, Object data) {
        this.error = error;
        this.data = data;
    }
}
