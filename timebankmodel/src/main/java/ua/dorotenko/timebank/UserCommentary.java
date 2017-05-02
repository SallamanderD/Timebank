package ua.dorotenko.timebank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Document(collection = "user_commentaries")
public class UserCommentary {
    public UserCommentary(int id, String text, String createDate, User author, int rating, User user) {
        this.id = id;
        this.text = text;
        this.createDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance());
        this.author = author;
        this.rating = 0;
        this.user = user;
    }

    @Id
    private int id;
    private String text;
    private String createDate;
    private User author;
    private int rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
