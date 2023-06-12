package com.doctorplus.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.doctorplus.dao.UsersDao;
import com.doctorplus.dto.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_" + USER;

    @Autowired
    private UsersDao usersDao;
    
    @Override
    public UserDetails loadUserByUsername(final String id) {
        final User user = usersDao.getById(id);
        user.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
        return user;
    }

}
