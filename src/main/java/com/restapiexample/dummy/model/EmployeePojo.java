package com.restapiexample.dummy.model;

import java.util.HashMap;

public class EmployeePojo {
    private String status;
    private HashMap<String,Object> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, ?> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}