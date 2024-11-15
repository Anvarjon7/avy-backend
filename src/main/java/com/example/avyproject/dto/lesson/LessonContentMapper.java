package com.example.avyproject.dto.lesson;

import com.example.avyproject.exceptions.InvalidFormatException;
import com.example.avyproject.exceptions.NoFileAttachedException;
import com.example.avyproject.service.strategy.LessonHandler;
import com.example.avyproject.service.strategy.LessonStrategy;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class LessonContentMapper {

    @Autowired
    LessonHandler lessonHandler;

    public CreateLessonDto mapToCreateLessonDto (HttpServletRequest request){
        try {
            MultipartHttpServletRequest multiPartRequest;
            MultipartFile file;
            if (request instanceof MultipartHttpServletRequest) {
                multiPartRequest = (MultipartHttpServletRequest) request;
                file = multiPartRequest.getFile("file");
            } else {
                throw new RuntimeException("Bad request");
            }

            Map<String, String[]> allParams = multiPartRequest.getParameterMap();
            Map<String, String> parameters = new HashMap<>();
            allParams.forEach((key, value) -> parameters.put(key, value[0]));

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(parameters);
            String type = parameters.get("itemType");

            CreateLessonDto createLessonDto;

            LessonStrategy lessonStrategy = lessonHandler.getStrategy(type);
            createLessonDto = objectMapper.readValue(json, lessonStrategy.getSupportedDtoClass());


            if (multiPartRequest.getFile("file") != null && !(Objects.requireNonNull(multiPartRequest.getFile("file")).getSize() ==0)) {
                file = multiPartRequest.getFile("file");
            }

            if (lessonStrategy.requiredFile()){
                if(file!=null&&!file.isEmpty()){
                lessonStrategy.processFile(createLessonDto,file);
                }else {
                    throw new NoFileAttachedException("File required");
                }

            }

            return createLessonDto;
        } catch (JacksonException e) {
            throw new InvalidFormatException(e.getOriginalMessage());
        }
    }
}
