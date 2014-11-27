package websocket;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.text.SimpleDateFormat;

/**
 * User: diemust
 * Date: 26.11.2014
 * Time: 23:30
 */
public class ChatMessageEncoder implements Encoder.Text<Message> {

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    @Override
    public String encode(Message message) throws EncodeException {
        return Json.createObjectBuilder()
                .add("message", message.getMessage())
                .add("nickname", message.getNickname())
                .add("color", message.getColor())
                .add("received", format.format(message.getReceived())).build()
                .toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
