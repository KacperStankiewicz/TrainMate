package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.user.MenteeFacade;
import pl.edu.pja.trainmate.core.domain.user.dto.MenteeCreateDto;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;


@RequiredArgsConstructor
@RestController
@HasRole(roleType = PERSONAL_TRAINER)
@RequestMapping("/mentees")
public class MenteeController {

    private final MenteeFacade menteeFacade;

    @GetMapping("/search")
    public Page<MenteeProjection> searchMenteesByCriteria(@RequestBody MenteeSearchCriteria criteria, @Parameter(hidden = true) Pageable pageable) {
        return menteeFacade.search(criteria, pageable);
    }

    @PostMapping("/create")
    public ResultDto<Long> createMentee(@RequestBody MenteeCreateDto menteeCreateDto) {
        return menteeFacade.create(menteeCreateDto);
    }
}