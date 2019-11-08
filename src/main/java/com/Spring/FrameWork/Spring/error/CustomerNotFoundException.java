package com.Spring.FrameWork.Spring.error;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(long id) {
        super("Customer with " + id + " id is not found ");
    }
}
