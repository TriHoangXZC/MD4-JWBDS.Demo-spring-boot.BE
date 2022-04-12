package com.codegym.demospringboot.service.user;

import com.codegym.demospringboot.model.entity.User;
import com.codegym.demospringboot.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);
}
