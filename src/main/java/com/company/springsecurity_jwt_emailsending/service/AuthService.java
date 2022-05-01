package com.company.springsecurity_jwt_emailsending.service;

import com.company.springsecurity_jwt_emailsending.dto.LoginDto;
import com.company.springsecurity_jwt_emailsending.dto.RegisterDto;
import com.company.springsecurity_jwt_emailsending.dto.Response;
import com.company.springsecurity_jwt_emailsending.entity.User;
import com.company.springsecurity_jwt_emailsending.entity.enums.RoleName;
import com.company.springsecurity_jwt_emailsending.repository.RoleRepository;
import com.company.springsecurity_jwt_emailsending.repository.UserRepository;
import com.company.springsecurity_jwt_emailsending.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  RoleRepository roleRepository;
    private  JavaMailSender javaMailSender;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;
@Autowired
    public AuthService(UserRepository userRepository,  PasswordEncoder passwordEncoder, RoleRepository roleRepository, JavaMailSender javaMailSender, JwtProvider jwtProvider,AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
    this.jwtProvider = jwtProvider;
    this.authenticationManager = authenticationManager;
}
    public Response registerUser(RegisterDto registerDto){
        if (!userRepository.existsByEmail(registerDto.getEmail())) {
            User user=new User();
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setRoles(Collections.singleton(roleRepository.findByName(RoleName.ROLE_USER)));
            user.setEmailCode(UUID.randomUUID().toString());
            userRepository.save(user);
            sendEmail(user.getEmail(),user.getEmailCode());
            return new Response("You successfully registered !!!" +
                    " To activate your account please, verify your account.",true);
        }
            return new Response("Sorry, this email is already exists ",false);

    }
    public Boolean sendEmail(String sending_email,String code){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("abs@gmail.com");
            message.setTo(sending_email);
            message.setSubject("Verification message");
            message.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode="+code+"&email="+sending_email+"'>Verify</a>");
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public Response verifyEmail(String emailCode, String email) {

        Optional<User> user = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (user.isPresent()) {
           User user1=user.get();
           user1.setEnabled(true);
           user1.setEmailCode(null);
           userRepository.save(user1);
           return new Response("Your account has been verified !!!",true);
        }

        return new Response("This account has already verified !!!",false);

    }

    public Response login(LoginDto loginDto) {

    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        User user =(User) authentication.getPrincipal();
        String token=jwtProvider.generateToken(user.getUsername(),user.getRoles());
        return new Response("This is token",true,token);
    }catch (BadCredentialsException e)  {
    return new Response("Password or login is incorrect",false);
    }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("This email not found"));
}

}
