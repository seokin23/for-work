package project.forwork.api.domain.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.forwork.api.common.annotation.Current;
import project.forwork.api.common.api.Api;
import project.forwork.api.domain.resume.controller.model.ResumeDetailResponse;
import project.forwork.api.domain.resume.controller.model.ResumePage;
import project.forwork.api.domain.resume.infrastructure.ResumeSearchCond;
import project.forwork.api.domain.resume.model.Resume;
import project.forwork.api.domain.resume.service.ResumeService;
import project.forwork.api.common.domain.CurrentUser;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin-api/resumes")
@Tag(name = "ResumeController for Admin", description = "어드민 전용 판매 이력서 조회 컨트롤러")

public class ResumeAdminController {

    private final ResumeService resumeService;

    @Operation(summary = "요청 Resume 상세 조회 API", description = "요청 Resume 상세 조회 API")
    @GetMapping("{resumeId}")
    public Api<ResumeDetailResponse> retrieve(
            @Parameter(hidden = true) @Current CurrentUser currentUser,
            @PathVariable Long resumeId
    ){
        Resume resume = resumeService.getByIdWithThrow(currentUser, resumeId);
        return Api.OK(ResumeDetailResponse.from(resume));
    }

    @Operation(summary = "첫 페이지 resume 요청 조회 API", description = "필터링 및 정렬 조건을 선택 할 경우 첫 페이지로 이동")
    @GetMapping("/first")
    public Api<ResumePage> findFirstPage(
            @RequestBody ResumeSearchCond cond,
            @RequestParam(defaultValue = "6") int limit
    ){
        ResumePage resumePage = resumeService.findFirstPage(cond, limit);
        return Api.OK(resumePage);
    }

    @Operation(summary = "마지막 페이지 resume 요청 조회 API", description = "마지막 페이지로 바로 이동")
    @GetMapping("/last")
    public Api<ResumePage> findLastPage(
            @RequestBody ResumeSearchCond cond,
            @RequestParam(defaultValue = "6") int limit
    ){
        ResumePage resumePage = resumeService.findLastPage(cond, limit);
        return Api.OK(resumePage);
    }

    @Operation(summary = "다음 페이지 resume 요청 조회 API", description = "다음 페이지로 이동")
    @GetMapping("/next")
    public Api<ResumePage> findNextPage(
            @RequestBody ResumeSearchCond cond,
            @RequestParam LocalDateTime lastModifiedAt,
            @RequestParam Long lastId,
            @RequestParam(defaultValue = "6") int limit
    ){
        ResumePage resumePage = resumeService.findNextPage(cond, lastModifiedAt, lastId, limit);
        return Api.OK(resumePage);
    }

    @Operation(summary = "이전 페이지 resume 요청 조회 API", description = "이전 페이지로 이동")
    @GetMapping("/previous")
    public Api<ResumePage> findPreviousPage(
            @RequestBody ResumeSearchCond cond,
            @RequestParam LocalDateTime lastModifiedAt,
            @RequestParam Long lastId,
            @RequestParam(defaultValue = "6") int limit
    ){
        ResumePage resumePage = resumeService.findPreviousPage(cond, lastModifiedAt, lastId, limit);
        return Api.OK(resumePage);
    }
}
