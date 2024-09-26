package project.forwork.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResumeErrorCode implements ErrorCodeIfs{

    RESUME_NOT_FOUND(404, 3401, "이력서를 찾을 수 없습니다."),
    ACCESS_NOT_PERMISSION(403, 3402, "이력서 접근 권한이 없습니다."),
    PRICE_NOT_VALID(400, 3403, "판매 가격은 10만원을 초과 할 수 없습니다."),
    RESUME_NO_CONTENT(204, 3404, "컨텐츠가 존재 하지 않습니다."),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
