package pl.edu.pja.trainmate.core.domain.file;

import static java.util.Locale.ROOT;

import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AttachmentFileNameExtensions {

    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    HEIC("heic");

    private final String type;

    public static boolean isPermitted(String fileName) {
        return Arrays.stream(AttachmentFileNameExtensions.values()).anyMatch(val -> val.getFileType().equals(extensionFromFileName(fileName)));
    }

    public String getFileType() {
        return type;
    }

    public static String extensionFromFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(ROOT);
    }
}