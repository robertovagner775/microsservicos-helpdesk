package com.roberto.support.storage.validation;

import com.roberto.support.storage.config.constants.ErrorConstants;
import com.roberto.support.storage.handler.exceptions.FileException;
import com.roberto.support.storage.models.enums.Extension;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileValidation {

    public void validate(List<MultipartFile> multipartFiles) throws FileException {
        List<String> extensionsPermited = Extension.getExtensionPermited();
        List<String> contentTypePermited = Extension.getContentTypePermited();

        Map<String, Object> fileErrors = new HashMap<>();

        for (MultipartFile file : multipartFiles) {
            String filename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(filename);
            String contentType = file.getContentType();

            if (!extensionsPermited.contains(extension)) {
                fileErrors.put(filename, ErrorConstants.EXTENSION_INVALID + extension);
            }
            if(!contentTypePermited.contains(contentType)) {
                if (fileErrors.get(filename) != null) {
                    fileErrors.replace(filename, List.of(ErrorConstants.EXTENSION_INVALID + extension, ErrorConstants.CONTENT_TYPE_INVALID + contentType));
                } else {
                    fileErrors.put(filename, ErrorConstants.CONTENT_TYPE_INVALID + contentType);
                }
            }
        }
        if(!fileErrors.isEmpty())
            throw new FileException(ErrorConstants.UPLOAD_FAILURE, fileErrors);
    }
}
