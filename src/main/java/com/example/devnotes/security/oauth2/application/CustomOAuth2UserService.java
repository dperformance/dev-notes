package com.example.devnotes.security.oauth2.application;

import com.example.devnotes.security.oauth2.dto.CustomOAuth2User;
import com.example.devnotes.security.oauth2.dto.GoogleResponseData;
import com.example.devnotes.security.oauth2.dto.NaverResponseData;
import com.example.devnotes.security.oauth2.dto.OAuth2ResponseData;
import com.example.devnotes.security.oauth2.entity.OAuthUser;
import com.example.devnotes.security.oauth2.repository.OAuthUserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuthUserRepository userRepository;

    public CustomOAuth2UserService(OAuthUserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

        String username = responseData.getProvider() + " " + responseData.getProviderId();
        OAuthUser existData = userRepository.findByUsername(username);
        String role = "ROLE_USER";

        if (existData == null) {
            OAuthUser user = OAuthUser.builder()
                    .username(username)
                    .email(responseData.getEmail())
                    .role(role)
                    .build();

            userRepository.save(user);
        } else {
            existData.setUsername(username);
            existData.setEmail(responseData.getEmail());

            role = existData.getRole();

            userRepository.save(existData);
        }

        return new CustomOAuth2User(responseData, role);
    }
}
