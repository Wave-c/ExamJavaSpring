package com.exam.ExamJavaSpring.entyties.communications;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoom 
{
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "room_id", nullable = false)
    private String roomId;
}
