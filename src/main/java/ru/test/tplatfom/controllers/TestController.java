package ru.test.tplatfom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.test.tplatfom.DTO.question.QuestionDTO;
import ru.test.tplatfom.DTO.test.TestDTO;
import ru.test.tplatfom.DTO.test.TestResultDTO;
import ru.test.tplatfom.DTO.test.TestShortDTO;
import ru.test.tplatfom.domain.Test;
import ru.test.tplatfom.domain.TestResult;
import ru.test.tplatfom.mappers.TestMapper;
import ru.test.tplatfom.mappers.TestResultMapper;
import ru.test.tplatfom.requests.test.TestRequest;
import ru.test.tplatfom.requests.test.UpdateTestRequest;
import ru.test.tplatfom.services.QuestionService;
import ru.test.tplatfom.services.TestService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    @Autowired
    private final TestService testService;

    @Autowired
    private final QuestionService questionService;

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<TestShortDTO>> getTests() {
        List<TestShortDTO> tests = testService.getAll();
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Teacher')")
    public ResponseEntity<String> createTest(
            @RequestBody @Valid TestRequest request, Principal principal
    ) throws URISyntaxException {
        Long id = testService.create(request, principal);
        return ResponseEntity.created(new URI(String.format("/api/test/%d", id))).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TestDTO> getTest(@PathVariable(name = "id") Long id) {
        Test test = testService.getById(id);
        return new ResponseEntity<>(TestMapper.fromTestToDTO(test), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('Teacher')")
    public ResponseEntity<String> updateTest(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid UpdateTestRequest request
    ) {
        testService.updateTest(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Teacher')")
    public ResponseEntity<TestDTO> deleteTest(@PathVariable(name = "id") Long id) {
        TestDTO test = testService.delete(id);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @GetMapping("/{id}/testresults")
    @PreAuthorize("hasAuthority('Teacher')")
    public ResponseEntity<List<TestResultDTO>> getResults(@PathVariable(name = "id") Long id) {
        List<TestResultDTO> results = testService.getTestResults(id);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}/quest")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<QuestionDTO>> getTestQuestions(@PathVariable(name = "id") Long id) {
        Test test = testService.getById(id);
        List<QuestionDTO> questions = questionService.getAllByTest(test);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping("{id}/finish")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TestResultDTO> finish(
            @PathVariable(name = "id") Long id,
            Principal principal
    ) {
        TestResult result = testService.finish(id, principal);
        return new ResponseEntity<>(TestResultMapper.fromTestResultToDTO(result), HttpStatus.OK);
    }
}
