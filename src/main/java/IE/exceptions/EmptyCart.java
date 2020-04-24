package IE.exceptions;

public class EmptyCart extends Exception {
    public EmptyCart(String errorMessage){
        super(errorMessage);
    }
}
