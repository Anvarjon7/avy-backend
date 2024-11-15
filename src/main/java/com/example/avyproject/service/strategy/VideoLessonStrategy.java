package com.example.avyproject.service.strategy;

import com.example.avyproject.dto.lesson.*;
import com.example.avyproject.entity.AvyModule;
import com.example.avyproject.entity.Lesson;
import com.example.avyproject.entity.VideoLesson;
import com.example.avyproject.exceptions.CourseNotFoundException;
import com.example.avyproject.exceptions.NoVideoAttachedException;
import com.example.avyproject.repository.LessonRepository;
import com.example.avyproject.service.AvyModuleService;
import com.example.avyproject.service.VideoService;
import com.example.avyproject.service.utility.RelativePathConverter;
import com.example.avyproject.service.utility.VideoMetadataReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Component
@Slf4j
public class VideoLessonStrategy implements LessonStrategy {

    private final VideoService videoService;
    private final LessonConverter lessonConverter;
    private final AvyModuleService avyModuleService;
    private final LessonRepository lessonRepository;

    public VideoLessonStrategy (VideoService videoService
            , LessonConverter lessonConverter
            , AvyModuleService avyModuleService
            , LessonRepository lessonRepository) {
        this.videoService = videoService;
        this.lessonConverter = lessonConverter;
        this.avyModuleService = avyModuleService;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public boolean supports(String lessonType) {
        return "video".equals(lessonType);
    }

    @Override
    public String getLessonType() {
        return "video";
    }

    @Override
    public Class<? extends CreateLessonDto> getSupportedDtoClass() {
        return CreateVideoLessonDto.class;
    }


    @Override
    public Lesson createLesson(CreateLessonDto createLessonDto) {
        CreateVideoLessonDto createVideoLessonDto = (CreateVideoLessonDto) createLessonDto;
        if (createVideoLessonDto.getModuleId() == null) {
            throw new CourseNotFoundException("Module ID cannot be null");
        }
        AvyModule module = avyModuleService.getById(createVideoLessonDto.getModuleId());
        String pathToVideo = videoService.uploadVideo(createVideoLessonDto.getLessonVideo());

        File videoFile = new File(pathToVideo);
        Long aLong = VideoMetadataReader.extractDurationMetadata(videoFile);
        log.info("get file duration: "+aLong);

        VideoLesson videoLesson = lessonConverter.convertToEntity(createVideoLessonDto,VideoLesson.class);
        videoLesson.setLinkToVideo(pathToVideo);
        videoLesson.setAvyModule(module);
        videoLesson.setModuleId(module.getId());
        videoLesson.setItemOrder(checkLessonOrder(module));
        return lessonRepository.save(videoLesson);
    }

    private int checkLessonOrder (AvyModule avyModule){
        List<Lesson> lessonList = avyModule.getItems();
        int size = lessonList.size();
        return size++;
    }

    @Override
    public boolean requiredFile() {
        return true;
    }

    @Override
    public CreateLessonDto processFile(CreateLessonDto createLessonDto, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new NoVideoAttachedException("No video attached to lesson");
        }
        CreateVideoLessonDto createVideoLessonDto = (CreateVideoLessonDto) createLessonDto;
        createVideoLessonDto.setLessonVideo(file);
        log.info("Here I get the incoming file "+file.getOriginalFilename());
        return createVideoLessonDto;
    }

    @Override
    public LessonDto covertToDto(Lesson lesson) {
        VideoLesson videoLesson = (VideoLesson) lesson;
        VideoLessonDto videoLessonDto = lessonConverter.convertToDto(lesson, VideoLessonDto.class);
        String shortLinkToVideo = RelativePathConverter.getRelativePath(videoLessonDto.getLinkToVideo());
        videoLessonDto.setLinkToVideo(shortLinkToVideo);
        return videoLessonDto;
    }
}