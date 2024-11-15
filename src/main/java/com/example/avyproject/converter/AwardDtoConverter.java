package com.example.avyproject.converter;

import com.example.avyproject.dto.AwardDto;
import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.entity.Award;
import com.example.avyproject.service.AvyUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AwardDtoConverter {
    private final ModelMapper mapper;
    @Autowired
    @Lazy
    AvyUserService avyUserService;

    /**
     * Converts an {@link AwardDto} object to an {@link Award} object.
     *
     * @param awardDto The {@link AwardDto} object to be converted.
     * @return The converted {@link Award} object.
     */
    public Award dtoToAward(AwardDto awardDto) {
        Award entity = mapper.map(awardDto, Award.class);
        AvyUser user = avyUserService.getById(awardDto.getUserId());
        entity.setUser(user);
        return entity;
    }

    /**
     * Converts a list of {@link Award} objects to a list of {@link AwardDto} objects.
     *
     * @param awards The list of {@link Award} objects to be converted.
     * @return The list of {@link AwardDto} objects.
     */
    public List<AwardDto> awardsToDtos(List<Award> awards) {
        List<AwardDto> awardDtos = new LinkedList<>();
        for (Award award : awards) {
            awardDtos.add(mapper.map(award, AwardDto.class));
        }
        return awardDtos;
    }

    /**
     * Converts an {@link Award} object to an {@link AwardDto} object.
     *
     * @param award The {@link Award} object to be converted.
     * @return The converted {@link AwardDto} object.
     */
    public AwardDto awardToDto(Award award) {
        AwardDto dto = mapper.map(award, AwardDto.class);
        dto.setUserId(award.getUser().getId());
        return dto;
    }
}
