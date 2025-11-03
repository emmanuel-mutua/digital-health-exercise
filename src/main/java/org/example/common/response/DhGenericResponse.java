package org.example.common.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhGenericResponse<T> {
        private String message;
        private boolean success;
        private T data;
        private String debugMessage;
}

