package pl.edu.pja.trainmate.core.api.mentee;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class MenteeEndpoints {

    public static final String SEARCH = "/mentees/search";
    public static final String INVITE = "/mentees/invite";
    public static final String UPDATE_PERSONAL_DATA = "/mentees/update-personal-data";
    public static final String INITIAL_REPORT = "/mentees/initial-report";
    public static final String DEACTIVATE = "/mentees/%s/deactivate";
    public static final String ACTIVATE = "/mentees/%s/activate";
}
