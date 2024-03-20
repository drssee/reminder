package project.reminder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private String userId;

    @Column(name = "connected_at")
    private LocalDateTime connectedAt;

    // TODO 기타 설정들 추가
}
