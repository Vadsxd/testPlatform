package ru.test.tplatfom.requests.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.test.tplatfom.requests.question.BundledQuestionRequest;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestRequest {
    @NotNull
    private String name;
    @NotNull
    private Set<BundledQuestionRequest> questions;
}
