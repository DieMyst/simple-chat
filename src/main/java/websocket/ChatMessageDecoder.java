package websocket;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Date;

/**
 * User: diemust
 * Date: 26.11.2014
 * Time: 23:35
 */
public class ChatMessageDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(String stringMsg) throws DecodeException {
        Message message = new Message();
        JsonObject obj = Json.createReader(new StringReader(stringMsg)).readObject();
        message.setMessage(obj.getString("message"));
        message.setNickname(obj.getString("nickname"));
        message.setColor(obj.getString("color"));
        message.setReceived(new Date());
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
