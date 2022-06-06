package com.fc_study.monsterGrowth.dto;

import com.fc_study.monsterGrowth.exception.MMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MMakerErrorResponse {
    private MMakerErrorCode errorCode;
    private String errorMessage;
}
