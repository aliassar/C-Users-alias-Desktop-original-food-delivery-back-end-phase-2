package IE.ManagersAndSchedulers;

import IE.models.Cart;

import java.io.IOException;
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
        AllCarts.get(CartIndex).setRemainedTimeToArive(0);
        AllCarts.get(CartIndex).setStatus("Delivery completed");



    }

}
