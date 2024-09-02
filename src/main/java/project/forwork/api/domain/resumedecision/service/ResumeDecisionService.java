package project.forwork.api.domain.resumedecision.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.forwork.api.common.error.UserErrorCode;
import project.forwork.api.common.exception.ApiException;
import project.forwork.api.common.service.port.ClockHolder;
import project.forwork.api.domain.resume.controller.model.ResumeAdminResponse;
import project.forwork.api.domain.resume.service.port.ResumeRepository;
import project.forwork.api.domain.resume.controller.model.ResumePage;
import project.forwork.api.domain.resumedecision.controller.model.ResumeDecisionResponse;
import project.forwork.api.domain.resumedecision.infrastructure.ResumeDecision;
import project.forwork.api.domain.resumedecision.infrastructure.enums.DecisionStatus;
import project.forwork.api.domain.resume.infrastructure.querydsl.ResumeSearchCond;
import project.forwork.api.domain.resumedecision.service.port.ResumeDecisionRepository;
import project.forwork.api.domain.user.controller.model.CurrentUser;
import project.forwork.api.domain.user.model.User;
import project.forwork.api.domain.user.service.port.UserRepository;
import org.springframework.data.domain.Sort;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeDecisionService {

    private final ResumeDecisionRepository resumeDecisionRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public ResumeDecisionResponse approve(CurrentUser currentUser, Long resumeDecisionId){
        User admin = userRepository.getByIdWithThrow(currentUser.getId());
        if(admin.isAdminMismatch()){
            throw new ApiException(UserErrorCode.USER_DISALLOW);
        }

        ResumeDecision resumeDecision = resumeDecisionRepository.getById(resumeDecisionId);
        resumeDecision = decideResumeSale(resumeDecision, admin, DecisionStatus.APPROVE);
        resumeDecision = resumeDecisionRepository.save(resumeDecision);

        return ResumeDecisionResponse.from(resumeDecision);
    }

    public ResumeDecisionResponse deny(CurrentUser currentUser, Long resumeDecisionId){
        User admin = userRepository.getByIdWithThrow(currentUser.getId());
        if(admin.isAdminMismatch()){
            throw new ApiException(UserErrorCode.USER_DISALLOW);
        }

        ResumeDecision resumeDecision = resumeDecisionRepository.getById(resumeDecisionId);
        resumeDecision = decideResumeSale(resumeDecision, admin, DecisionStatus.DENY);
        resumeDecision = resumeDecisionRepository.save(resumeDecision);

        return ResumeDecisionResponse.from(resumeDecision);
    }

    private ResumeDecision decideResumeSale(ResumeDecision resumeDecision, User admin, DecisionStatus status) {
        resumeDecision = resumeDecision.decide(admin, status, clockHolder);
        return resumeDecision;
    }
}
