package pls.java;
import java.util.Random;

public class Account {
    private double balance;
    private int PIN = 0;
    public Account(double balance) {
        this.balance = balance;
    }
    public double getBalance() {
        return this.balance;
    }
    public boolean setPIN(int PIN) {
        this.PIN = PIN;
        return true;
    }
    public int getPIN() {
        return this.PIN;
    }
    public boolean deposit(double amnt) {
        this.balance += amnt;
        return true;
    }
    public boolean withdraw(double amnt) {
        if (this.balance >= amnt) {
            this.balance -= amnt;
            return true;
        } else {
            return false;
        }
    }
    public String toString() {
        return "Balance: $"+this.balance;
    }
    public static void main(String[] args) {
        System.out.println("I'm curious if this might work...");
    }
}




