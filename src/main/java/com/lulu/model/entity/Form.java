package com.lulu.model.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    private Integer id;
    private String activityName;
    private String activityRegion;
    private LocalDateTime activityDate;
    private LocalDateTime activityTime;
    private String activityType;
    private String activityDescription;
    private String reviewStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer userId;
}

