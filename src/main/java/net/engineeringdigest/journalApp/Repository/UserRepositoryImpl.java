package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.User;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {
    @Autowired
    MongoTemplate mongoTemplate;
    public List<User> GetUsersForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("SantimentsAnalysis").is(true));
        return mongoTemplate.find(query,User.class);
    }
}
