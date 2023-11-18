package com.sis.sisassignment;

import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.FeedbackType;
import com.sis.sisassignment.domain.model.Retrospective;

import java.time.Instant;
import java.util.Arrays;

public class RetroMockFactory {

    public static Retrospective getDefaultRetro() {
        return Retrospective.builder()
                .name("Default Retro")
                .date(Instant.now())
                .summary("lorem ipsum...")
                .participants(Arrays.asList("foo", "bar"))
                .feedbackItems(
                    Arrays.asList(
                            FeedbackItem.builder()
                                    .participantName("foo")
                                    .body("lorem Ipsum...")
                                    .type(FeedbackType.POSITIVE)
                                    .build(),
                            FeedbackItem.builder()
                                    .participantName("bar")
                                    .body("lorem Ipsum...")
                                    .type(FeedbackType.NEGATIVE)
                                    .build()
                    )
                )
                .build();
    }
}
