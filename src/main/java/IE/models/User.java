package IE.models;

import java.io.File;
import java.io.IOException;
import java.util.*;


import IE.CustomSerializer.CustomCartSerializer;
import IE.CustomSerializer.CustomFoodSerializer;
import IE.CustomSerializer.CustomRestaurantSerializer;
import IE.Exceptions.DifRestaurants;
import IE.Exceptions.NoInProcessOrder;
import IE.Exceptions.NoOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class User {
    private ArrayList<Cart> cartsOfUser;
    private String fname;
    private String lname;
    private String phoneNumber;
    private String email;
    private float wallet;

    public User(String fname, String lname, String phoneNumber, String email, float wallet) {
        this.cartsOfUser = new ArrayList<>();
        this.fname = fname;
        this.lname = lname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.wallet = wallet;
    }

    public User() {
    }

    public void AddToWallet(float amount) {
        this.wallet += amount;

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

    public Cart getInProcessCart() throws NoInProcessOrder, NoOrder {
        if (cartsOfUser.size() < 1) {
            throw new NoOrder("There are no order");
        } else {
            Cart lastCart = cartsOfUser.get(cartsOfUser.size() - 1);
            if (lastCart.getStatus().equals("inProcess")) {
                return lastCart;
            } else {
                throw new NoInProcessOrder("There are no in process order");
            }
        }
    }
    public Cart getLastCart() throws NoOrder {
        if (cartsOfUser.size() < 1) {
            throw new NoOrder("There are no order");
        } else {
            return cartsOfUser.get(cartsOfUser.size() - 1);
        }
    }
    public void newCart(Cart cart) {
        cartsOfUser.add(cart);
    }

}

