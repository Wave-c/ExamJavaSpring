package com.exam.ExamJavaSpring.responses;

import java.util.List;

import com.exam.ExamJavaSpring.entyties.Room;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetApplicationsResponse 
{
    Room room;
    String id;
}
