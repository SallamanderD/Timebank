package ua.dorotenko.timebank;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tags")
public class Tag {
    public Tag(){}
    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

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
}
