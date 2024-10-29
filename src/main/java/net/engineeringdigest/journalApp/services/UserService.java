package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void SaveNewEntry(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        }catch (Exception e){
          //  log.error("Error occurred for {} :",user.getUserName(),e);
            log.error("haha");
            log.info("haha");
            log.warn("haha");
            log.debug("haha");
            log.trace("haha");
        }
    }
    public void SaveUser(User user){
        userRepository.save(user);
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findbyId(ObjectId id) {
        return userRepository.findById(id);
    }

    public void DeletebyId(ObjectId id) {
        userRepository.deleteById(id);
    }

     public User FindByUserName(String userName){
return userRepository.findByUserName(userName);
}
}
