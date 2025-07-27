package com.roberto.suporteTecnico.service;

import com.roberto.suporteTecnico.config.AwsS3Client;
import com.roberto.suporteTecnico.config.constants.RabbitMQConstants;
import com.roberto.suporteTecnico.dto.requests.FileRequestDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessageToQueue(List<MultipartFile> archives) throws IOException {

        if(archives.size() <= 3) {

            List<FileRequestDTO> fileRequests = new ArrayList<FileRequestDTO>();

            for (MultipartFile archive : archives) {
                fileRequests.add(FileRequestDTO.createFileRequest(archive));
            } 

            rabbitTemplate.convertAndSend(RabbitMQConstants.FILE_QUEUE, fileRequests);
        }
    }
   
}
