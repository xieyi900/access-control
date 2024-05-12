package com.management.access.control.model;

import java.util.List;

public class AccessInfo {

    private Long userId;

    private List<String> endpoints;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public String toString() {
        return userId + String.join(",", endpoints);
    }
}
