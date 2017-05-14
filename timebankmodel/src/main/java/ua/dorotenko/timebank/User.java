package ua.dorotenko.timebank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class User {
    public User(){}
    public User(int id, String username, String password, String fio, String bornDate, int balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fio = fio;
        this.registerDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        this.bornDate = bornDate;
        this.rating = 0;
        this.commentaries = new ArrayList<>();
        this.userCommentaries = new ArrayList<>();
        this.orderCommentaries = new ArrayList<>();
        this.balance = balance;
        this.preferTags = new ArrayList<>();
    }

    @Id
    private int id;
    private String username;
    private String password;
    private String fio;
    private String registerDate;
    private String bornDate;
    private int rating;
    private int balance;
    private List<Tag> preferTags;

    // Commentaries to this user
    private List<UserCommentary> commentaries;

    // Commentaries by this user
    private List<UserCommentary> userCommentaries;

    // Commentaries by this user to order
    private List<OrderCommentary> orderCommentaries;

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

    public List<UserCommentary> getUserCommentaries() {
        return userCommentaries;
    }

    public void setUserCommentaries(List<UserCommentary> userCommentaries) {
        this.userCommentaries = userCommentaries;
    }

    public List<OrderCommentary> getOrderCommentaries() {
        return orderCommentaries;
    }

    public void setOrderCommentaries(List<OrderCommentary> orderCommentaries) {
        this.orderCommentaries = orderCommentaries;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Tag> getPreferTags() {
        return preferTags;
    }

    public void setPreferTags(List<Tag> preferTags) {
        this.preferTags = preferTags;
    }
}
