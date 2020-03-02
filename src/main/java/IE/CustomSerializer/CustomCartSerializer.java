package IE.CustomSerializer;

import java.io.IOException;

import IE.models.Cart;
import IE.models.Order;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomCartSerializer extends StdSerializer<Cart> {
    public CustomCartSerializer() {
        this(null);
    }

    public CustomCartSerializer(Class<Cart> o) {
        super(o);
    }
    @Override
    public void serialize(Cart cart, JsonGenerator gen, SerializerProvider provider) throws IOException{
        gen.writeStartObject();
        gen.writeArrayFieldStart("orders");
        for(Order order:cart.getOrders()){
            gen.writeStartObject();
            gen.writeStringField("foodName",order.getFoodName());
            gen.writeStringField("numOfOrder", String.valueOf(order.getNumOfOrder()));
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
