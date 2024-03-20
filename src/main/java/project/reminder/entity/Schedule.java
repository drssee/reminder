package project.reminder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 id
    @Column(name = "user_id")
    private String userId;

    // 일정
    @Column(name = "date_time",
            nullable = false)
    private LocalDateTime dateTime;

    // 사유
    @Column(name = "reason",
            nullable = false)
    private String reason;

    // 사용여부
    @Column(name = "use_yn",
            length = 1,
            nullable = false,
            columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String useYn;

    // 등록일
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    // 수정일
    @Column(name = "mod_date")
    private LocalDateTime modDate;

    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modDate = LocalDateTime.now();
    }
}
