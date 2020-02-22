package IE.CustomSerializer;

import java.io.IOException;

import IE.models.Food;
import IE.models.Restaurant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


public class CustomRestaurantSerializer extends StdSerializer<Restaurant> {

    public CustomRestaurantSerializer() {
        this(null);
    }

    public CustomRestaurantSerializer(Class<Restaurant> o) {
        super(o);
    }

    @Override
    public void serialize(Restaurant restaurant, JsonGenerator gen, SerializerProvider provider) throws IOException{
        gen.writeStartObject();
        gen.writeStringField("name",restaurant.getName());
        gen.writeStringField("description",restaurant.getDescription());
        gen.writeStringField("description",restaurant.getName());
        gen.writeObjectFieldStart("location");
        gen.writeStringField("x",String.valueOf(restaurant.getLocation().getX()));
        gen.writeStringField("y",String.valueOf(restaurant.getLocation().getY()));
        gen.writeEndObject();
        gen.writeArrayFieldStart("menu");
        for(Food food: restaurant.getMenu()) {
            CustomFoodSerializer.foodSerializer(food, gen);

        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
