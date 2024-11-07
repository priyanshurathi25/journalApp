package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.Repository.ConfigJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class AppCache {
    public enum keys{
        WEATHER_API;
    }


    @Autowired
 private ConfigJournalRepository  configJournalRepository;
    public Map<String ,String> appCache ;


    @PostConstruct
public void init(){
    appCache = new HashMap<>();
    List<ConfigJournalAppEntity> all = configJournalRepository.findAll();
    for (ConfigJournalAppEntity configJournalAppEntity : all){
         appCache.put(configJournalAppEntity.getKey() , configJournalAppEntity.getValue());
      }
    }
}
