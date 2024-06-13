package pl.edu.pja.trainmate.core.api.file;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class FileStorageEndpoints {

    public static final String GET_ALL = "/file/%s/get-all";
    public static final String ADD = "/file/%s/add";
    public static final String DELETE = "/file/%s/delete";

}
