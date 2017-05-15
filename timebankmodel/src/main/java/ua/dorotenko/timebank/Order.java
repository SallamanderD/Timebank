package ua.dorotenko.timebank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {
    public Order() {}
    public Order(int id, String name, String description, List<Tag> tags, User author, User executer, int count, boolean isIot) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        this.tags = tags;
        this.author = author;
        this.isCompletedByAuthor = false;
        this.isCompletedByExecuter = false;
        this.executer = executer;
        this.commentaries = new ArrayList<OrderCommentary>();
        this.count = count;
        this.possibleExecuters = new ArrayList<>();
        this.isIot = isIot;
        this.isCompleted = false;
    }

    @Id
    private int id;
    private String name;
    private String description;
    private String createDate;
    private List<Tag> tags;
    private User author;
    private boolean isCompletedByAuthor;
    private boolean isCompletedByExecuter;
    private boolean isCompleted;
    private User executer;
    private List<OrderCommentary> commentaries;
    private int count;
    private List<User> possibleExecuters;
    private boolean isIot;

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

    public boolean getIsCompletedByAuthor() {
        return isCompletedByAuthor;
    }

    public void setIsCompletedByAuthor(boolean completedByAuthor) {
        isCompletedByAuthor = completedByAuthor;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getIsCompletedByExecuter() {
        return isCompletedByExecuter;
    }

    public void setIsCompletedByExecuter(boolean completedByExecuter) {
        isCompletedByExecuter = completedByExecuter;
    }

    public List<User> getPossibleExecuters() {
        return possibleExecuters;
    }

    public void setPossibleExecuters(List<User> possibleExecuters) {
        this.possibleExecuters = possibleExecuters;
    }

    public boolean isIot() {
        return isIot;
    }

    public void setIot(boolean iot) {
        isIot = iot;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }
}
