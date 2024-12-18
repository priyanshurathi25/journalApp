package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.api.response.weatherResponse;
import net.engineeringdigest.journalApp.services.UserService;
import net.engineeringdigest.journalApp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
 public class Usercontroller{
 @Autowired
 UserService userService;
 @Autowired
    UserRepository userRepository;
 @Autowired
 WeatherService weatherservice;

    @GetMapping("/Admin")
public List<User> GetAlluser(){
 return userService.getAll();
 }

 @PostMapping
public void CreateUser(@RequestBody User user){
userService.SaveNewEntry(user);
}

@PutMapping
public ResponseEntity<?> UpdateUser(@RequestBody User user ){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String userName = authentication.getName();
    User userindb = userService.FindByUserName(userName);
    userindb.setUserName(user.getUserName());
    userindb.setPassword(user.getPassword());
    userService.SaveNewEntry(userindb);
 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@DeleteMapping
public ResponseEntity<?> DeleteUserByUserName(){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     userRepository.deleteByUserName(authentication.getName());
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
    @GetMapping
public ResponseEntity<?> greeting(){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        weatherResponse WeatherResponse= weatherservice.getWeather("Mumbai");
        String greetings = "";
        if (WeatherResponse!=null){
greetings = " weather feels like "+WeatherResponse.getCurrent().getFeelslike_c();
        }
        return new ResponseEntity<>(" Hi " + authentication.getName() +greetings , HttpStatus.OK);
}
}
                      