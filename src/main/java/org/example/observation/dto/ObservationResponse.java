package org.example.observation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObservationResponse {
    private Long id;
    private LocalDateTime date;
    private String type;
    private String value;
}