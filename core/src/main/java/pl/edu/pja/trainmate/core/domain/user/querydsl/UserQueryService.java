package pl.edu.pja.trainmate.core.domain.user.querydsl;

import java.util.List;

public interface UserQueryService {

    List<UserProjection> searchUserByCriteria();
}
