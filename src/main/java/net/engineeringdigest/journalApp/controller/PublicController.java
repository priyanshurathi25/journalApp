package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.services.UserDetailServiceimp;
import net.engineeringdigest.journalApp.services.UserService;
import net.engineeringdigest.journalApp.utilis.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    @Autowired
UserService userService;
    @Autowired
    private UserDetailServiceimp userDetailServiceimp;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/health-cheak")
    public String Health_cheak(){
        return "ok";
    }

    @PostMapping("/signup")
    public void Signup(@RequestBody User user){
        userService.SaveNewEntry(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName() , user.getPassword()));
            UserDetails userDetails = userDetailServiceimp.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt , HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken" , e);
            return  new ResponseEntity<>("Incorrect username or password " , HttpStatus.BAD_REQUEST);
        }
    }
}
