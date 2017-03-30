package com.example.arnal.movies.model;


public class Trailer {
    private String site;
    private String id;
    private String name;
    private String type;
    private String key;
    private String size;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ClassPojo [site = " + site + ", id = " + id + ", name = " + name + ", type = " + type + ", key = " + key + ", size = " + size + "]";
    }
}
