package pl.edu.pja.trainmate.core.api.user;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class UserEndpoints {

    public static final String GET_USER_INFO = "/users/get-current-user-info";
}
