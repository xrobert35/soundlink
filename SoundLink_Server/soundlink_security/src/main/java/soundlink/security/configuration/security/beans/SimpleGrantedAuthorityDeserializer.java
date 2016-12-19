package soundlink.security.configuration.security.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SimpleGrantedAuthorityDeserializer extends JsonDeserializer<List<SimpleGrantedAuthority>> {

    @Override
    public List<SimpleGrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext arg1)
            throws IOException, JsonProcessingException {

        List<SimpleGrantedAuthority> autorithies = new ArrayList<>();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        node.forEach(autorithy -> {
            String role = autorithy.get("authority").asText();
            autorithies.add(new SimpleGrantedAuthority(role));
        });

        return autorithies;
    }

}
