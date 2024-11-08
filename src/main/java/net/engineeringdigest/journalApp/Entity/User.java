package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import net.engineeringdigest.journalApp.Enum.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  private ObjectId id ;
  @Indexed(unique = true)
  @NonNull
  private String userName;
  private String Email;
  @NonNull
  private String password;
  @DBRef
  private List<journalEntry> JournalEnteries = new ArrayList<>();
private List<String >roles;
}