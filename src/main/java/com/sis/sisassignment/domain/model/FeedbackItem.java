package com.sis.sisassignment.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class FeedbackItem {
    @NotBlank
    private final String participantName;

    @NotBlank
    private final String body;

    @NotNull
    private final FeedbackType type;
}
