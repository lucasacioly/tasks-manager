package com.aps.todo.dtos;

import java.time.LocalDate;

public record TaskRecordDto(

        Long id,
        Long epicId,
        String name,
        String description,
        LocalDate dueDate,
        Boolean inProgress,
        String userId
) {}
