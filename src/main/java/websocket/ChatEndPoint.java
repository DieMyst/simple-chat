package websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * User: diemust
 * Date: 26.11.2014
 * Time: 23:00
 */
@ServerEndpoint(
        value = "/chat",
        encoders = {ChatMessageEncoder.class, NicknameListEncoder.class},
        decoders = {ChatMessageDecoder.class})
public class ChatEndPoint {

    private final Logger log = Logger.getLogger(getClass().getName());
    private static Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    private static Queue<Session> sessionQueue = new ConcurrentLinkedQueue<>();
    private static Queue<Nickname> nicknameQueue = new ConcurrentLinkedQueue<>();

    private Nickname nickname;

    @OnOpen
    public void open(final Session session) throws IOException, EncodeException {
        log.info("new session");
        for (Message msg : messageQueue) {
            session.getBasicRemote().sendObject(msg);
        }
        sessionQueue.add(session);
    }

    @OnMessage
    public void onMessage(final Session session, final Message msg) {
        try {
            //условие для первого сообщения, когда входит юзер
            if (nickname == null && msg.getMessage() == null) {
                nickname = new Nickname();
                nickname.setName(msg.getNickname());
                nickname.setColor(msg.getColor());
                nicknameQueue.add(nickname);
                sendToAll(nicknameQueue);
                log.info(msg.getNickname() + " connected");
            } else {
                log.info(msg.getNickname() + " send message");
                messageQueue.add(msg);
                sendToAll(msg);
            }
        } catch (IOException | EncodeException e) {
            log.warning(e.getMessage());
        }
    }

    private void sendToAll(Object object) throws IOException, EncodeException {
        for (Session s : sessionQueue) {
            if (s.isOpen()) {
                s.getBasicRemote().sendObject(object);
            }
        }
    }

    @OnClose
    public void close(Session session) {
        sessionQueue.remove(session);
        if (nickname != null) {
            log.info(nickname.getName() + " close socket");
            nicknameQueue.remove(nickname);
            try {
                for (Session s : sessionQueue) {
                    if (s.isOpen()) {
                        s.getBasicRemote().sendObject(nicknameQueue);
                    }
                }
            } catch (IOException | EncodeException e) {
                log.warning(e.getMessage());
            }
        }

    }

    @OnError
    public void error(Session session, Throwable t) {
        log.warning(t.getMessage());
    }
}



