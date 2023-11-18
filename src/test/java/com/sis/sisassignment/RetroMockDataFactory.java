package com.sis.sisassignment;

import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.FeedbackType;
import com.sis.sisassignment.domain.model.Retrospective;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

public class RetroMockDataFactory {

    public static Retrospective getDefaultRetro() {
        return Retrospective.builder()
                .name("Default Retro")
                .date(Instant.now())
                .summary("lorem ipsum...")
                .participants(Arrays.asList("foo", "bar"))
                .feedbackItems(
                    Collections.emptyList()
                )
                .build();
    }

    public static FeedbackItem getFooFeedback() {
        return FeedbackItem.builder()
                .participantName("foo")
                .body("lorem Ipsum...")
                .type(FeedbackType.POSITIVE)
                .build();
    }

    public static FeedbackItem getBarFeedback() {
        return FeedbackItem.builder()
                .participantName("bar")
                .body("lorem Ipsum...")
                .type(FeedbackType.NEGATIVE)
                .build();
    }
}
