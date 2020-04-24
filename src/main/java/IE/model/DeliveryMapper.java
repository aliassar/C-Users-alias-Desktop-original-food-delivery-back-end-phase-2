package IE.model;

import IE.Loghme;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryMapper extends Mapper<Delivery, Integer, Integer> {
    private String ID;
    private Location location;
    private int velocity;
    private static CartMapper instance;
    public static final String COLUMNS = " ID, x, y, velocity ";
    public static final String TABLE_NAME = "deliveries";

    public DeliveryMapper() throws SQLException, IOException {

        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        st.executeUpdate(String.format(
                "CREATE TABLE %s " +
                        "(" +
                        "ID varchar(100) NOT NULL PRIMARY KEY, " +
                        "x float, " +
                        "y float, " +
                        "velocity int " +
                        ");",
                TABLE_NAME));
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Delivery> deliveries;
        deliveries = mapper.readValue(new URL("http://138.197.181.131:8080/deliveries")
                , new TypeReference<List<Delivery>>() {
                });
        for (Delivery delivery : deliveries) {
            this.insert(delivery);
        }
        st.close();
        con.close();
    }

    @Override
    protected String getFindStatement(Integer id) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected String getInsertStatement(Delivery delivery) throws SQLException {
        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES " +
                "(" +
                '"' + delivery.getID() + '"' + ", " +
                delivery.getLocation().getX() + ", " +
                delivery.getLocation().getY() + ", " +
                delivery.getVelocity() +
                ");";
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected Delivery convertResultSetToObject(ResultSet rs) throws SQLException {
        Location location = new Location(rs.getFloat(2), rs.getFloat(3));
        return new Delivery(rs.getString(1),
                location,
                rs.getInt(3)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT * FROM " + TABLE_NAME + ";";
    }

    @Override
    protected String getFilterStatement(Integer id) {
        return "SELECT * FROM " + TABLE_NAME + ";";
    }

    @Override
    protected void getInsertCallBack(Delivery delivery) {

    }
}

