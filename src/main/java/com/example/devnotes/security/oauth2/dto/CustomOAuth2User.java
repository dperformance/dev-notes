package com.example.devnotes.security.oauth2.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2ResponseData responseData;

    private final String role;

    public CustomOAuth2User(OAuth2ResponseData responseData,
                            String role) {
        this.responseData = responseData;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> role);
        return collection;
    }

    @Override
    public String getName() {
        return responseData.getName();
    }

    public String getUsername() {
        return responseData.getProvider()+ " " + responseData.getProviderId();
    }
}
