package com.epam.brest.course2015.social.consumer.security;

import com.epam.brest.course2015.social.consumer.SocialHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.*;

/**
 * Created by alexander_borohov on 15.9.16.
 */
@Component
public class SocialUserDetails implements UserDetails {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${rest.prefix}")
    private String restPrefix;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SocialRole> socialRoles = new HashSet<>();
        SocialRole socialRole = new SocialRole();
        socialRoles.add(socialRole);
        return socialRoles;
    }

    @Override
    public String getPassword() {
        return restRequest("password", "");
    }

    @Override
    public String getUsername() {
        return restRequest("username", "");
    }

    @Override
    public boolean isAccountNonExpired() {
        return restRequest("accEnabled", true);
    }

    @Override
    public boolean isAccountNonLocked() {
        return restRequest("accNonLocked", true);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return restRequest("credNonExpired", true);
    }

    @Override
    public boolean isEnabled() {
        return restRequest("isEnabled", true);
    }

    private <T> T restRequest (String parameter, T type) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("username", username);
        SocialHeaderInterceptor.setSecurityToken("token");
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .pathSegment("security")
                .pathSegment(parameter)
                .queryParams(params)
                .build();
        String restUrl = uriComponents.toUriString();
        return (T) restTemplate.getForObject(restUrl, type.getClass());
    }

    private class SocialRole implements GrantedAuthority {

        @Override
        public String getAuthority() {
            return restRequest("authorities", "");
        }
    }

}
