package pl.edu.pja.trainmate.core.domain.user;

import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_CREATE_MENTEE;
import static pl.edu.pja.trainmate.core.domain.user.mapper.MenteeMapper.mapToUserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserIdProvider;
import pl.edu.pja.trainmate.core.domain.user.dto.MenteeCreateDto;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeQueryService;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserIdProvider userIdProvider;
    private final MenteeRepository menteeRepository;
    private final MenteeQueryService queryService;

    public Page<MenteeProjection> searchByCriteria(MenteeSearchCriteria criteria, Pageable pageable) {
        return queryService.searchMenteeByCriteria(criteria, pageable);
    }

    public ResultDto<Long> createMentee(MenteeCreateDto menteeCreateDto) {
        var mentee = mapToUserEntity(menteeCreateDto, userIdProvider.getLoggedUserId());

        return ResultDto.ofValueOrError(menteeRepository.save(mentee).getId(), COULD_NOT_CREATE_MENTEE);
    }
}