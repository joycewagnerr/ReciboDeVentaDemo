package com.example.recibodeventademo.data;

import java.io.Serializable;

public class Client implements Serializable {
    private String firstName;
    private String lastName;
    private int dni;

    public Client(String firstName, String lastName, int dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
    }

    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    public int getDNI() {
        return dni;
    }
}
