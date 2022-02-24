package com.example.messaging_api.service;

import com.example.messaging_api.exceptions.InformationExistException;
import com.example.messaging_api.model.Request.LoginRequest;
import com.example.messaging_api.model.Response.LoginResponse;
import com.example.messaging_api.model.User;
import com.example.messaging_api.repository.UserRepository;
import com.example.messaging_api.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User userObject) {
        if (!userRepository.existsByPhoneNumber(userObject.getPhoneNumber())){
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("User with phone number " + userObject.getPhoneNumber()+ " already exists.");
        }
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
        final String JWT = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(JWT));
    }

    public User findUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
