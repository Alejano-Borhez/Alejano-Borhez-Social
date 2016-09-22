package com.epam.brest.course2015.social.consumer;

import com.epam.brest.course2015.social.consumer.security.SocialUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
