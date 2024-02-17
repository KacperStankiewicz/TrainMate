package pl.edu.pja.trainmate.core.common.errorcode;

import static lombok.AccessLevel.PROTECTED;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public class CommonErrorCode {

    public static final String RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER = "RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER";
    public static final String ENTITY_NOT_FOUND = "ENTITY_NOT_FOUND";
}