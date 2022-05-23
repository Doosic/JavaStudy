package com.example.dmaker.dto;

import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class DevDto {
        @NonNull
        private String name;
        @NonNull
        private Integer age;
        private LocalDateTime startAt;
}
