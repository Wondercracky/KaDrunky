package com.example.kadrunky;

public class Question {

    String question;
    boolean kahoot;
    boolean special;
    String pris;
    boolean bonus;
    int svarNummer;
    String alternativ1;
    String alternativ2;
    String alternativ3;
    String alternativ4;
    boolean taken;
    boolean alle;

    public Question(String question, boolean kahoot, boolean special, String pris, boolean bonus, int svarNummer, String alternativ1, String alternativ2, String alternativ3 , String alternativ4, boolean taken, boolean alle) {
        this.question = question;
        this.kahoot = kahoot;
        this.special = special;
        this.pris = pris;
        this.bonus = bonus;
        this.svarNummer = svarNummer;
        this.taken = taken;
        this.alternativ1 = alternativ1;
        this.alternativ2 = alternativ2;
        this.alternativ3 = alternativ3;
        this.alternativ4 = alternativ4;
        this.alle = alle;
    }
}
