package ua.dorotenko.timebank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {
    public Order(int id, String name, String description, List<Tag> tags, User author, boolean isCompleted, User executer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        this.tags = tags;
        this.author = author;
        this.isCompleted = isCompleted;
        this.executer = executer;
        this.commentaries = new ArrayList<OrderCommentary>();
    }

    @Id
    private int id;
    private String name;
    private String description;
    private String createDate;
    private List<Tag> tags;
    private User author;
    private boolean isCompleted;
    private User executer;
    private List<OrderCommentary> commentaries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public User getExecuter() {
        return executer;
    }

    public void setExecuter(User executer) {
        this.executer = executer;
    }

    public List<OrderCommentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<OrderCommentary> commentaries) {
        this.commentaries = commentaries;
    }
}
