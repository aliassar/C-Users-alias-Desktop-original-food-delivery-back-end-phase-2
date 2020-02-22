package IE.CustomSerializer;

import java.io.IOException;

import IE.models.Order;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomCartSerializer extends StdSerializer<Order> {
    public CustomCartSerializer() {
        this(null);
    }

    public CustomCartSerializer(Class<Order> o) {
        super(o);
    }
    @Override
    public void serialize(Order order, JsonGenerator gen, SerializerProvider provider) throws IOException{
        gen.writeStartObject();
        gen.writeStringField("foodName",order.getFoodName());
        gen.writeStringField("numOfOrder", String.valueOf(order.getNumOfOrder()));
        gen.writeEndObject();
    }

}
