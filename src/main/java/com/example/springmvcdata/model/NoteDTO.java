package com.example.springmvcdata.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class NoteDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    @Size(max = 255)
    private String content;

    @DateTimeFormat(fallbackPatterns = "DD-mm-YY")
    private String dateCreated;


}
