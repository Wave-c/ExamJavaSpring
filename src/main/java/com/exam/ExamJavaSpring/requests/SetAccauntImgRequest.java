package com.exam.ExamJavaSpring.requests;

import lombok.Data;

@Data
public class SetAccauntImgRequest {
    private String userName;
    private byte[] accauntImg;
}
