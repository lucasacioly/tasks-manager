package com.aps.todo.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class TaskModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "in_progress")
    private Boolean inProgress;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "epic_id")
    private EpicModel epic;

    // Constructors, getters e setters
    public void Task() {}

    public void Task(String name, String description, LocalDate dueDate, EpicModel epic, Boolean inProgress, String userId) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.epic = epic;
        this.inProgress = inProgress;
        this.userId = userId;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id){
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public EpicModel  getEpic() {
        return epic;
    }

    public Long getEpicId() {
        if (epic != null){
            return epic.getId();
        }
        return null;
    }

    public void setEpic(EpicModel  epic) {
        this.epic = epic;
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
