package com.aps.todo.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "epics")
public class EpicModel implements Serializable {

    //private static final long serialVersionUID = 1L;

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

    public void Epic() {
    }

    public void Epic(String name, String description) {
        this.name = name;
        this.description = description;
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
}
