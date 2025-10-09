package com.project.videoStreaming.Users.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.SecurityConfig.JwtUtil;
import com.project.videoStreaming.SecurityConfig.config;
import com.project.videoStreaming.Users.DTO.User;
import com.project.videoStreaming.Users.Service.UserServiceImplementation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthUser {

    @Autowired
    UserServiceImplementation Service;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    config conf;

    @Autowired
    JavaMailSender eMailSender;

    @GetMapping("/status")
    public ResponseEntity<?> checkAuthStatus(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
        return ResponseEntity.ok().body(Map.of("loggedIn", true));
    } else {
        return ResponseEntity.ok().body(Map.of("loggedIn", false));
    }
}
    

    @PostMapping("/login")
    public ResponseEntity<String> LoginUser(@RequestBody User request, HttpServletRequest req) {
        try{
            authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );
            UserDetails  userDetails = userDetailsService.loadUserByUsername(request.getEmail()); 

            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occured while createAuthenticationToken", e);
            return new ResponseEntity<>("Incorrect username password", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        request.getSession().invalidate(); 
        return ResponseEntity.ok("Logged out successfully");
    }
    


    
    @PostMapping("/register/request-OTP")
    public void registerUser(@RequestBody User userRequest, HttpSession session) {
        try{
            userRequest.setPassword(encoder.encode(userRequest.getPassword()));
            session.setAttribute("pendingUser", userRequest);

            int Otp = sendOtpByMail(userRequest.getEmail());
            session.setAttribute("OTP", Otp);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    @PostMapping ("/request-OTP/{OTP}")
    private void validateOtpAndSaveUser(@PathVariable int OTP, HttpSession session, HttpServletRequest request){

        Integer getSavedOTP = (Integer) session.getAttribute("OTP");
        User getSavedUser = (User) session.getAttribute("pendingUser");
        if(getSavedOTP == null || getSavedUser == null || !getSavedOTP.equals(OTP)){
        }else{
            Service.userRegistration(getSavedUser);
            session.removeAttribute("OTP");
            session.removeAttribute("pendingUser");
        }
    }



    private int generateOTP (){
        int OTP = (int) Math.floor(Math.random()*(10000-1000+1)+1000);
        return OTP;
    }
   
    private int sendOtpByMail(String to){

        int Otp = generateOTP();

        String getText = Integer.toString(Otp);

        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(to);
        message.setSubject("OTP to Register your account.");
        message.setText("Your OTP is " + getText);
        eMailSender.send(message);
        return Otp;
    }
    
}
