package org.example.Domain;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private int id;
    private String category;
    private String description;
    private List<Bid> bidList;

    public Project(int id, String category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.bidList = new ArrayList<>();
    }

    public List<Bid> getBidList() {
        return bidList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
