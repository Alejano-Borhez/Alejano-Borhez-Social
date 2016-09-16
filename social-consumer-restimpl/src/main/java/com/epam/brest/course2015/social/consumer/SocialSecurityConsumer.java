package com.epam.brest.course2015.social.consumer;

import com.epam.brest.course2015.social.service.SocialSecurityServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

/**
 * Created by alexander_borohov on 14.9.16.
 */
@Component
public class SocialSecurityConsumer implements UserDetailsService {
    @Autowired
    private SocialUserDetails userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetails.setUsername(username);
        return userDetails;
    }
}
