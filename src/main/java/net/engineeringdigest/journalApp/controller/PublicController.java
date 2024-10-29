package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
UserService userService;
    @GetMapping("/health-cheak")
    public String Health_cheak(){
        return "ok";
    }

    @PostMapping("/create-user")
    public void UserCeration(@RequestBody User user){
        userService.SaveNewEntry(user);
    }
}
