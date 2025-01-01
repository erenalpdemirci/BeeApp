package com.example.beeapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "bees")
public class Bee {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String type;
    private String job;
    private String features;
    private String description;
    private String youtubeVideoId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bee bee = (Bee) o;
        return id == bee.id &&
                Objects.equals(name, bee.name) &&
                Objects.equals(type, bee.type) &&
                Objects.equals(job, bee.job) &&
                Objects.equals(features, bee.features) &&
                Objects.equals(description, bee.description) &&
                Objects.equals(youtubeVideoId, bee.youtubeVideoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, job, features, description, youtubeVideoId);
    }
}