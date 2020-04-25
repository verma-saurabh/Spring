package com.Spring.FrameWork.Spring.error;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private int errorCode;
    private String errorMessage;

    public ErrorMessage(int errorCode, String errorMessage) {

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}