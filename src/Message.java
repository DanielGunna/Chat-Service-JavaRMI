
import java.io.Serializable;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lipec
 */
public class Message implements Serializable{
    private String owner;
    private String content;
    private Date date;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type.getValue();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" + "owner=" + owner + ", content=" + content + ", date=" + date + '}';
    }
    
    
    
}
