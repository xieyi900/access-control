package com.management.access.control.api;

public interface WriteCallBack {

    void onSuccess(String userId);
    void OnFailure(String userId, Throwable throwable);
}
