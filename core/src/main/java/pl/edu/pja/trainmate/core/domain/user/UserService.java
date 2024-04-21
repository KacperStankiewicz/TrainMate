package pl.edu.pja.trainmate.core.domain.user;

import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_CREATE_MENTEE;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.user.dto.MenteeCreateDto;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;

@Service
@RequiredArgsConstructor
class UserService {

    private final LoggedUserDataProvider userIdProvider;
    private final UserRepository userRepository;
    private final UserQueryService queryService;

    public Page<MenteeProjection> searchByCriteria(MenteeSearchCriteria criteria, Pageable pageable) {
        return queryService.searchMenteeByCriteria(criteria, pageable);
    }

    public ResultDto<Long> createMentee(MenteeCreateDto menteeCreateDto) {
        var mentee = buildUserEntity(menteeCreateDto);

        return ResultDto.ofValueOrError(userRepository.save(mentee).getId(), COULD_NOT_CREATE_MENTEE);
    }

    private UserEntity buildUserEntity(MenteeCreateDto createDto) {
        return UserEntity.builder()
            .userId(userIdProvider.getLoggedUserId())
            .personalInfo(PersonalInfo.builder()
                .firstname(createDto.getFirstname())
                .lastname(createDto.getLastname())
                .email(createDto.getEmail())
                .phone(createDto.getPhone())
                .dateOfBirth(createDto.getDateOfBirth())
                .build())
            .build();
    }
}