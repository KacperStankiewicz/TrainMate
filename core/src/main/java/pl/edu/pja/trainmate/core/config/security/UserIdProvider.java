package pl.edu.pja.trainmate.core.config.security;


import pl.edu.pja.trainmate.core.common.UserId;

public interface UserIdProvider {

    UserId getLoggedUserId();
}