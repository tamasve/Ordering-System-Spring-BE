package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.UserData;
import com.orderingsystem.OrderingSystemSpringBE.repository.UserDataRepository;
import com.orderingsystem.OrderingSystemSpringBE.security.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private UserDataRepository userDataRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService (UserDataRepository userDataRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(SignUpDTO signUpDto) {

        if (findByUsername(signUpDto.getUsername()) != null) return "User already exists!";     // Controller will send BAD Request HTTP status to FE
        if (findByUsername(signUpDto.getEmail()) != null) return "E-mail already exists!";     // Controller will send BAD Request HTTP status to FE

        UserData user = new UserData();
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setEmail(signUpDto.getEmail());
        user.setRoles("ROLE_USER");                            // user gets the basic role
        user.setActivation( createActivationCode() );
        user.setEnabled(false);                             // activation only by clicking the special link
        userDataRepository.save(user);
        emailService.sendEmail(
                user.getEmail(),
                "Activation code",
                "You have to activate your account by clicking onto this link:\n\nhttp://localhost:8080/activation/" + user.getActivation());
        return "OK";        // Controller will send OK HTTP status to FE
    }

    // Generate a random 16-char activation code
    private String createActivationCode() {
        Random random = new Random();
        char[] word = new char[16];
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }

    public String userActivation(String code) {
        UserData userData = userDataRepository.findByActivation(code);
        if (userData == null)  return "This activation code is no longer valid!";
        userData.setActivation("");
        userData.setEnabled(true);
        userDataRepository.save(userData);
        return "OK";
    }

    public UserData findByUsername(String username) {
        return userDataRepository.findByUsername(username);
    }

    public UserData findByEmail(String email) {
        return userDataRepository.findByEmail(email);
    }
}
