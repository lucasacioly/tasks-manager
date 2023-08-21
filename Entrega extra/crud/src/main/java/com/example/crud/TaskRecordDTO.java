package com.example.crud;

import java.time.LocalDate;

public record TaskRecordDTO(

        Long id,
        String name,
        String description,
        LocalDate dueDate,
        Boolean inProgress,
        String userId
) {}
