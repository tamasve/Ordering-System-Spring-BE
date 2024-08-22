package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.security.LoginDTO;
import com.orderingsystem.OrderingSystemSpringBE.security.SignUpDTO;
import com.orderingsystem.OrderingSystemSpringBE.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto){

        log.info("sign in attempt by {}", loginDto.getUsername());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("successful log in by {}", loginDto.getUsername());

        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) {
        // check if none of the 3 main params is void - if any of them is void >> back to registration page
        if (signUpDto.getUsername().isEmpty() || signUpDto.getPassword().isEmpty() || signUpDto.getEmail().isEmpty()) {
            log.error("Error! - Registration data contain void field");
            return new ResponseEntity<>("Registration data can not contain void fields!", HttpStatus.BAD_REQUEST);
        };
        // Otherwise register user
        String message = userService.registerUser(signUpDto);
        log.info(message);
        // any error during service activity >> back to registration page
        if (!message.equals("OK")) {
            return new ResponseEntity<>("Error during registration: " + message, HttpStatus.BAD_REQUEST);
        }
        // registration was OK >> message to Front End with info about the activation
        return new ResponseEntity<>("Registration was OK. You have to receive an e-mail about the activation.", HttpStatus.OK);
    }

    // user gets here after clicking onto the activation link
    @GetMapping("/activation/{code}")
    public ResponseEntity<?> activation(@PathVariable("code") String code) {
        log.info("code: " + code);
        String message = userService.userActivation(code);       // find user by act.code
        log.info(message);
        if (!message.equals("OK")) {           // no user found by activation code >> error message
            return new ResponseEntity<>("Error during activation: " + message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Congratulation! You've successfully activated your account!", HttpStatus.OK);
    }

}
