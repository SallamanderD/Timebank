package ua.dorotenko.timebank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Document(collection = "user_commentaries")
public class UserCommentary {
    public UserCommentary(){}
    public UserCommentary(int id, String text, User author, int rating, User user) {
        this.id = id;
        this.text = text;
        this.createDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        this.author = author;
        this.user = user;
    }

    @Id
    private int id;
    private String text;
    private String createDate;
    private User author;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
