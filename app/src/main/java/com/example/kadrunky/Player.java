package com.example.kadrunky;
import java.io.Serializable;

public class Player implements Serializable {
    String name;
    boolean taken;

    public Player(String name, boolean taken) {
        this.name = name;
        this.taken = taken;
    }
}
