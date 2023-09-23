package com.exam.ExamJavaSpring.entyties.communications;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application")
public class Application implements AutoCloseable
{
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "room_id", nullable = false)
    private String roomId;
    @Column(name = "buyer_id", nullable = false)
    private String buyerId;
    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Override
    public void close() throws Exception {}
}
