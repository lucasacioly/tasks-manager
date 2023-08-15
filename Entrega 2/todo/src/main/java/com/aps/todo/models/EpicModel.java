package com.aps.todo.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "epics")
public class EpicModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_tasks")
    private Integer totalTasks;

    @Column(name = "tasks_done")
    private Integer tasksDone;

    // Constructors, getters e setters

    public EpicModel() {
    }

    public EpicModel(String name, String description, String userId, Integer totalTasks, Integer tasksDone) {
        this.name = name;
        this.description = description;
        this.tasksDone = tasksDone;
        this.totalTasks = totalTasks;
        this.userId = userId;
    }

    // Getters and Setters

    public Long getId() {
        return id;
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

    public String getUserId() {return userId;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Integer getTasksDone() {
        return tasksDone;
    }

    public void setTasksDone(Integer tasksDone) {
        this.tasksDone = tasksDone;
    }
}
