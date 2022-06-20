package ru.test.tplatfom.DTO.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.test.tplatfom.DTO.user.UserShortDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDTO {
    private Long id;
    private Long score;
    private Boolean finished;
    private TestShortDTO test;
    private UserShortDTO user;
}
