package com.example.dmaker.dto;

import com.example.dmaker.entity.Developer;
import com.example.dmaker.exception.DMakerErrorCode;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperValidationDto {
    private DMakerErrorCode errorCode;
    private String errorMessage;

}
