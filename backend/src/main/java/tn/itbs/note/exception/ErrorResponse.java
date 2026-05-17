package tn.itbs.note.exception;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private int status;

    private String message;

    private String errorCode;

    private Long timestamp;

    private String path;

    private java.util.Map<String, String> validationErrors;
}
