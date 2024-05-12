package com.management.access.control.api;

public interface WriteCallBack {

    void onSuccess(String userId);
    void onFailure(String userId, Throwable throwable);
}
