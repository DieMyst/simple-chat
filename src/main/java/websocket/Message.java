package websocket;

import java.util.Date;

/**
 * User: diemust
 * Date: 26.11.2014
 * Time: 23:31
 */
public class Message {

    private String message;
    private String nickname;
    private String color;
    private Date received;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }
}
