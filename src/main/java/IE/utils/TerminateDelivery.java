package IE.utils;

import IE.model.Cart;

import java.util.ArrayList;

public class TerminateDelivery implements Runnable{
    private ArrayList<Cart> AllCarts;
    private int CartIndex;

    public TerminateDelivery(ArrayList<Cart> allCarts, int cartIndex) {
        AllCarts = allCarts;
        CartIndex = cartIndex;
    }

    @Override
    public void run()  {
        System.out.println("entered TerminateDelivery");
        AllCarts.get(CartIndex).setRemainedTimeToArrive(0);
        AllCarts.get(CartIndex).setStatus("Delivery completed");



    }

}
