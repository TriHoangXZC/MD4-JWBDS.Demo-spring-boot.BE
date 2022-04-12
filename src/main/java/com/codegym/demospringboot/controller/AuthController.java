package com.codegym.demospringboot.controller;

import com.codegym.demospringboot.model.dto.JwtResponse;
import com.codegym.demospringboot.model.entity.Role;
import com.codegym.demospringboot.model.dto.SignUpForm;
import com.codegym.demospringboot.model.entity.User;
import com.codegym.demospringboot.service.JwtService;
import com.codegym.demospringboot.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtService jwtService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        //Kiểm tra username và pass có đúng hay không
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //Lưu user đang đăng nhập vào trong context của security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(currentUser.getId(), jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (!signUpForm.getPasswordForm().getPassword().equals(signUpForm.getPasswordForm().getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpForm.getUsername(), signUpForm.getPasswordForm().getPassword());
//        String password = user.getPassword();
//        String encodePassword = passwordEncoder.encode(password);//Mã hóa pass của người dùng
//        user.setPassword(encodePassword);
//        List<Role> roles = new ArrayList<>();
//        roles.add(new Role(2L, "ROLE_USER"));
//        user.setRoles(roles);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
}
