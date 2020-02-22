package IE.CustomSerializer;

import java.io.IOException;

import IE.models.Food;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomFoodSerializer extends StdSerializer<Food> {

    public CustomFoodSerializer() {
        this(null);
    }

    public CustomFoodSerializer(Class<Food> o) {
        super(o);
    }

    @Override
    public void serialize(Food food, JsonGenerator gen, SerializerProvider provider) throws IOException{
        foodSerializer(food, gen);
    }

    static void foodSerializer(Food food, JsonGenerator gen) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("foodName",food.getName());
        gen.writeStringField("price", String.valueOf(food.getPrice()));
        gen.writeStringField("description",food.getDescription());
        gen.writeStringField("popularity",String.valueOf(food.getPopularity()));
        gen.writeEndObject();
    }

}
