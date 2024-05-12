package com.software.service;

import com.software.dto.AssignmentPublishDto;
import com.software.dto.AssignmentSubmitDto;
import com.software.dto.HomeWorkFeedBackDTO;
import com.software.entity.Assignment;

import java.util.List;

public interface AssignmentService {
    void publishAssignment(AssignmentPublishDto assignmentPublishDto);
    List<Assignment> getAssignmentsInClass(Long classId);
    void submitAssignment(Long studentId, AssignmentSubmitDto assignmentSubmitDto);

    void markHw(HomeWorkFeedBackDTO homeWorkFeedBackDTO);
}
