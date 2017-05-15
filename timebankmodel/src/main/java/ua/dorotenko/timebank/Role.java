package ua.dorotenko.timebank;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {
    public Role() {
    }

    public Role(int id, String name) {
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
