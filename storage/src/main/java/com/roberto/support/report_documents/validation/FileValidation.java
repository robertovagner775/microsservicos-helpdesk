package com.roberto.support.report_documents.validation;

import com.roberto.support.report_documents.config.constants.ErrorConstants;
import com.roberto.support.report_documents.dtos.FileErrorDTO;
import com.roberto.support.report_documents.handler.exceptions.FileException;
import com.roberto.support.report_documents.model.enums.Extension;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
