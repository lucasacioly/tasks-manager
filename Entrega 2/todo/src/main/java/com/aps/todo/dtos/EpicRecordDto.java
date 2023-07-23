package com.aps.todo.dtos;

public record EpicRecordDto(
        String name,
        String description,
        String user_id,
        Integer total_tasks,
        Integer tasks_done

) {}
