package IE.models;





public class Order {
    private String foodName;
    private String restaurantName;
    private float cost;
    private int numOfOrder;


    public Order(String foodName, String restaurantName, int numOfOrder, float cost) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        this.numOfOrder = numOfOrder;
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getNumOfOrder() {
        return numOfOrder;
    }

    public void setNumOfOrder(int numOfOrder) {
        this.numOfOrder = numOfOrder;
    }

    public void AddNum() {
        this.numOfOrder += 1;
    }
}
