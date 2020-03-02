package IE.models;

import IE.Exceptions.NoOrder;

import java.util.ArrayList;


public class User {
    private ArrayList<Cart> cartsOfUser;
    private String fname;
    private String lname;
    private String phoneNumber;
    private String email;
    private float wallet;
    private Cart inProcessCart;

    public User(String fname, String lname, String phoneNumber, String email, float wallet) {
        this.cartsOfUser = new ArrayList<>();
        inProcessCart = new Cart();
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

    public Cart getInProcessCart() {
        return inProcessCart;
    }
    public Cart getLastCart() throws NoOrder {
        if (cartsOfUser.size() < 1) {
            throw new NoOrder("There are no processed order");
        } else {
            return cartsOfUser.get(cartsOfUser.size() - 1);
        }
    }
    public void clearInProcessCart() {
        inProcessCart = new Cart();
    }
    public ArrayList<Cart> getCartsOfUser() {
        return cartsOfUser;
    }
    public void newProcessedCart(Cart cart) {
        cartsOfUser.add(cart);
    }
}

