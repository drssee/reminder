package project.reminder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIdDto {

    // 사용자 id
    @JsonProperty("user_id")
    private String userId;
}
