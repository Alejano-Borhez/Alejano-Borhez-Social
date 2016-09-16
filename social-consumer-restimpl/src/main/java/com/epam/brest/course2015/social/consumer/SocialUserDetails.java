package com.epam.brest.course2015.social.consumer;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        List<SocialRole> socialRoles = new ArrayList<>();
        SocialRole socialRole = new SocialRole(username);
        socialRoles.add(socialRole);
        return socialRoles;
    }

    @Override
    public String getPassword() {
        return (String) restRequest("password", String.class);
    }

    @Override
    public String getUsername() {
        return (String) restRequest("username", String.class);
    }

    @Override
    public boolean isAccountNonExpired() {
        return (boolean) restRequest("accEnabled", boolean.class);
    }

    @Override
    public boolean isAccountNonLocked() {
        return (boolean) restRequest("accNonLocked", boolean.class);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return (boolean) restRequest("credNonExpired", boolean.class);
    }

    @Override
    public boolean isEnabled() {
        return (boolean) restRequest("isEnabled", boolean.class);
    }

    private Object restRequest (String parameter, Class tClass) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("username", username);
        SocialHeaderInterceptor.setSecurityToken("token");
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .path("/security")
                .pathSegment("/" + parameter)
                .queryParams(params)
                .build();
        String restUrl = uriComponents.toUriString();
        Object result = restTemplate.getForObject(restUrl, tClass);
        return result;
    }

    private class SocialRole implements GrantedAuthority, Serializable {

        private String username;

        SocialRole(String username) {
            this.username = username;
        }

        @Override
        public String getAuthority() {
            return (String) restRequest("authorities", String.class);
        }
    }

}
