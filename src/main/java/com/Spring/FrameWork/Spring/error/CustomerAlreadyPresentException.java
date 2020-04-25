package com.Spring.FrameWork.Spring.error;

public class CustomerAlreadyPresentException extends RuntimeException {
    public CustomerAlreadyPresentException(String mobileNumber) {
        super("Customer with mobileNumber" + mobileNumber + " already exists ");
    }
}
