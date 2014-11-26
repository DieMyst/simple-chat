package websocket;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: diemust
 * Date: 26.11.2014
 * Time: 23:00
 */
@ServerEndpoint(
        value = "/chat",
        encoders = { ChatMessageEncoder.class },
        decoders = { ChatMessageDecoder.class })
public class ChatEndPoint {

    static Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void open(final Session session) throws IOException, EncodeException {
        System.out.println("new session");
        for (Message msg : messageQueue) {
            session.getBasicRemote().sendObject(msg);
        }
    }

    @OnMessage
    public void onMessage(final Session session, final Message msg) {
        try {
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(msg);
                }
            }
        } catch (IOException | EncodeException e) {
        }
    }
}

