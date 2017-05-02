package ua.dorotenko.timebank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Document(collection = "users")
public class User {
    public User(int id, String username, String password, String fio, String bornDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fio = fio;
        this.registerDate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance());
        this.bornDate = bornDate;
        this.rating = 0;
        this.commentaries = new ArrayList<UserCommentary>();
    }

    @Id
    private int id;
    private String username;
    private String password;
    private String fio;
    private String registerDate;
    private String bornDate;
    private int rating;
    private List<UserCommentary> commentaries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<UserCommentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<UserCommentary> commentaries) {
        this.commentaries = commentaries;
    }
}
