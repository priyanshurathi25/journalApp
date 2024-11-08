package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import net.engineeringdigest.journalApp.Enum.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(value = "journal_entries")
@Data
@NoArgsConstructor
public class journalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
private LocalDateTime date;
private Sentiment sentiment;


}
