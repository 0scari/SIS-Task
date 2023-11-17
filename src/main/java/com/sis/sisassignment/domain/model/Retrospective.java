package com.sis.sisassignment.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Retrospective {
    @NotNull
    @NotBlank
    private final String name;

}
