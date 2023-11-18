package com.sis.sisassignment.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Retrospective {
    @NotBlank
    private final String name;

    private final String summary;

    @NotNull
    private final Instant date;

    @NotNull
    @NotEmpty
    private final List<String> participants;

    @NotNull
    @Size(max = 0)
    private final List<FeedbackItem> feedbackItems;
}
