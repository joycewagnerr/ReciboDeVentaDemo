package com.example.recibodeventademo.data;

import java.io.Serializable;

public class Issuer implements Serializable {
    private String name;
    private String rif;

    public Issuer(String name, String rif) {
        this.name = name;
        this.rif = rif;
    }

    public String getName() {
        return name;
    }

    public String getRif() {
        return rif;
    }
}
