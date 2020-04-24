package IE.model;

public class Delivery {
    private String ID;
    private Location location;
    private int velocity;
    public Delivery(String ID,Location location,int velocity){
        this.ID=ID;
        this.location=location;
        this.velocity=velocity;
    }
    public Delivery(){
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
