package net.engineeringdigest.journalApp.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class weatherResponse {
   private Current current;
   @Getter
   @Setter
    public class Current {
        @JsonProperty("temp_c")
        private double Temp_C;
        @JsonProperty("temp_f")
        private double Temp_f;
        private double feelslike_c;
        private double feelslike_f;
    }


}

