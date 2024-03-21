package project.reminder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageDto {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("text")
    private String text;
}
