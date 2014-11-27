package websocket;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Queue;

/**
 * by DShahtarin on 27.11.2014.
 */
public class NicknameListEncoder implements Encoder.Text<Queue<Nickname>> {
    @Override
    public String encode(Queue<Nickname> nicknames) throws EncodeException {

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (Nickname nickname : nicknames) {
            builder.add(Json.createObjectBuilder()
                    .add("name", nickname.getName())
                    .add("color", nickname.getColor()));
        }
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("names", builder.build());
        return obj.build().toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
