package pl.edu.pja.trainmate.core.domain.user.mapper;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.user.PersonalInfo;
import pl.edu.pja.trainmate.core.domain.user.UserEntity;
import pl.edu.pja.trainmate.core.domain.user.dto.MenteeCreateDto;

@NoArgsConstructor(access = PRIVATE)
public final class MenteeMapper {

    public static UserEntity mapToUserEntity(MenteeCreateDto createDto, UserId userId) {
        return UserEntity.builder()
            .userId(userId)
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
