package com.aps.todo.dtos;

import java.time.LocalDate;

public record TaskRecordDto(
        Long epicId,
        String name,
        String description,
        LocalDate dueDate
) {}
