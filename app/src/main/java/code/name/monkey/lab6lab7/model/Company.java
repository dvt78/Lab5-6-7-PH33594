package code.name.monkey.lab6lab7.model;

import com.google.gson.annotations.SerializedName;

public class Company {
    @SerializedName("_id")
    private String id;
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
