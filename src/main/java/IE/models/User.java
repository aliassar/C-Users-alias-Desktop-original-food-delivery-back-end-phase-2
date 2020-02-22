package IE.models;

import java.io.File;
import java.io.IOException;
import java.util.*;


import IE.CustomSerializer.CustomCartSerializer;
import IE.CustomSerializer.CustomFoodSerializer;
import IE.CustomSerializer.CustomRestaurantSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class User {
    private ArrayList<Order> orders;
    private ArrayList<Cart> CartsOfUser;
    private String fname;
    private String lname;
    private String phoneNumber;
    private String email;
    private float wallet;

    public User(String fname, String lname, String phoneNumber, String email, float wallet) {
        this.fname = fname;
        this.lname = lname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.wallet = wallet;
    }

    public User() {
    }

    public void AddToWallet(float amount){
        this.wallet +=amount;

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public void cleanOrders() {
        this.orders.clear();
    }

    public void AddToCart(Order order) {
        this.orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

}

