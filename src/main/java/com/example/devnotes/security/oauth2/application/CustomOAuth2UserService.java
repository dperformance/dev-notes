package com.example.devnotes.security.oauth2.application;

import com.example.devnotes.security.oauth2.dto.CustomOAuth2User;
import com.example.devnotes.security.oauth2.dto.GoogleResponseData;
import com.example.devnotes.security.oauth2.dto.NaverResponseData;
import com.example.devnotes.security.oauth2.dto.OAuth2ResponseData;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2ResponseData responseData = null;
        if ("naver".equals(registrationId)){
            responseData = new NaverResponseData(oAuth2User.getAttributes());
        } else if ("google".equals(registrationId)) {
            responseData = new GoogleResponseData(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String role = "ROLE_USER";
        return new CustomOAuth2User(responseData, role);
    }
}
