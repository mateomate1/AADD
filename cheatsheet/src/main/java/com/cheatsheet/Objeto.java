package com.cheatsheet;

public class Objeto {

    private String dato1;
    private String dato2;

    public Objeto() {
    }

    public Objeto(String dato1, String dato2) {
        this.dato1 = dato1;
        this.dato2 = dato2;
    }

    public String getDato1() {
        return dato1;
    }

    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }

    public String getDato2() {
        return dato2;
    }

    public void setDato2(String dato2) {
        this.dato2 = dato2;
    }

    @Override
    public String toString() {
        return "Objeto [dato1=" + dato1 + ", dato2=" + dato2 + "]";
    }
}
