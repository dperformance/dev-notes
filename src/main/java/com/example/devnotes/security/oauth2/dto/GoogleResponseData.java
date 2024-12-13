package com.example.devnotes.security.oauth2.dto;

import java.util.Map;

public class GoogleResponseData implements OAuth2ResponseData{
    private final Map<String, Object> attribute;

    public GoogleResponseData(Map<String, Object> attribute) {
        this.attribute = attribute;
    }


    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
