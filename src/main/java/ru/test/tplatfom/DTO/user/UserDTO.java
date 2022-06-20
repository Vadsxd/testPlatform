package ru.test.tplatfom.DTO.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.test.tplatfom.DTO.question.QuestionResultDTO;
import ru.test.tplatfom.DTO.test.TestResultShortDTO;
import ru.test.tplatfom.DTO.role.RoleDTO;
import ru.test.tplatfom.DTO.test.TestShortDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private List<RoleDTO> roles;
    private List<TestShortDTO> tests;
    private List<TestResultShortDTO> testResults;
    private List<QuestionResultDTO> questionResults;
}