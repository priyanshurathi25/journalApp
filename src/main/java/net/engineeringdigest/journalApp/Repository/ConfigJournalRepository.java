package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournalAppEntity , ObjectId> {
}
