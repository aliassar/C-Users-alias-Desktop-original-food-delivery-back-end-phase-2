package IE.models;

public class Location {
    private float x;
    private float y;

    public double Distance(Location first, Location second){
        float x = Math.abs(first.getX() - second.getX()) * Math.abs(first.getX() - second.getX());
        float y = Math.abs(first.getY() - second.getY()) * Math.abs(first.getY() - second.getY());
        double result = Math.sqrt(x+y);
        return result;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


}
