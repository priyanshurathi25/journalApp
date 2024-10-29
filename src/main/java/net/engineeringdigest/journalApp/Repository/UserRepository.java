package net.engineeringdigest.journalApp.Repository;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId > {
 User findByUserName(String username);

 void deleteByUserName(String name);
}