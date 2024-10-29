package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.Repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {
    @Autowired
  private journalEntryRepository JournalEntryRepository;
    @Autowired
    private UserService userService;
    @Transactional
    public void SaveEntry(journalEntry JournalEntry, String userName){
try {
    User user = userService.FindByUserName(userName);
    JournalEntry.setDate(LocalDateTime.now());
    journalEntry saved =JournalEntryRepository.save(JournalEntry);
    user.getJournalEnteries().add(saved);
    userService.SaveUser(user);

}catch (Exception e){

    throw new RuntimeException("there is a exception in entry " , e );
}
        }
    public void SaveEntry(journalEntry JournalEntry){
        JournalEntryRepository.save(JournalEntry);
    }
    public List<journalEntry> getAll(){
        return JournalEntryRepository.findAll();
    }
    public Optional<journalEntry> findbyId(ObjectId id){
        return JournalEntryRepository.findById(id);
    }

    @Transactional
    public boolean DeletebyId(ObjectId id , String userName){
        boolean removed = false;
        try {
            User user = userService.FindByUserName(userName);
             removed = user.getJournalEnteries().removeIf(x -> x.getId().equals(id));
            if (removed)
                userService.SaveUser(user);
            JournalEntryRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred While deleting the entry." , e);
        }
        return removed;
    }
}
