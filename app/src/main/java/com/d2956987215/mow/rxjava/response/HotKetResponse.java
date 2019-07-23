package com.d2956987215.mow.rxjava.response;


import java.util.List;

public class HotKetResponse extends BaseResponse{
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
