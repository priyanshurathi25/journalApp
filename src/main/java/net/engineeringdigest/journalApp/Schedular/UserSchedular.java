package net.engineeringdigest.journalApp.Schedular;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.Enum.Sentiment;
import net.engineeringdigest.journalApp.Repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {
    @Autowired
    private  EmailServices emailServices;
    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    AppCache appCache;
     @Scheduled(cron = "0 0 9 * * SUN")
     public void fetchUsersAndSendSaMail() {
         List<User> users = userRepository.GetUsersForSA();
         for (User user : users) {
             List<journalEntry> journalEntries = user.getJournalEnteries();
             List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
             Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
             for (Sentiment sentiment : sentiments) {
                 if (sentiment != null)
                     sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
             }
             Sentiment mostFrequentSentiment = null;
             int maxCount = 0;
             for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                 if (entry.getValue() > maxCount) {
                     maxCount = entry.getValue();
                     mostFrequentSentiment = entry.getKey();
                 }
             }
             if (mostFrequentSentiment != null) {
                 emailServices.sendEmail(user.getEmail() ,"Sentiment for last 7 days " , mostFrequentSentiment.toString());
                 }
             }
         }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
appCache.init();
    }
}
