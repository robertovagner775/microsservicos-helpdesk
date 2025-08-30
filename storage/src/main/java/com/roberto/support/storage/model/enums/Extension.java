package com.roberto.support.storage.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum Extension {

    PNG("image/png"),
    PDF("application/pdf"),
    JPG("image/jpg"),
    JPEG("image/jpeg"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    TXT("text/plain");

    private String value;

    Extension(String extension) {
        this.value = extension;
    }

    public String getValue() {
        return this.value;
    }

    public static List<String> getContentTypePermited() {
        return Stream.of(Extension.values()).map(Extension::getValue).toList();
    }

    public static List<String> getExtensionPermited() {
        return Arrays.stream(Extension.values()).map(e -> e.name().toLowerCase()).toList();
    }
}
