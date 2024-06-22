package pl.edu.pja.trainmate.core.domain.file;

import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AttachmentFileTypes {

    IMAGE_JPEG("image/jpeg"), //.jpg , .jpeg
    IMAGE_PNG("image/png"), //.png
    IMAGE_HEIC("image/heic"); //.heic

    private final String type;

    public static boolean isPermitted(String type) {
        return Arrays.stream(AttachmentFileTypes.values())
            .anyMatch(val -> val.getFileType().equals(type));
    }

    public String getFileType() {
        return type;
    }
}