package com.software.service;

import com.software.dto.*;
import com.software.entity.Assignment;
import com.software.vo.AssignmentVO;

import java.util.List;

public interface AssignmentService {
    void publishAssignment(AssignmentPublishDto assignmentPublishDto);
    List<Assignment> getAssignmentsInClass(Long classId);
    void submitAssignment(Long studentId, AssignmentSubmitDto assignmentSubmitDto);
    Assignment getAssignment(Long assignmentId);
    void markHw(HomeWorkFeedBackDTO homeWorkFeedBackDTO);
    List<AssignmentVO> getAllAssignments(AssignmentQueryDto assignmentQueryDto);
    List<StudentAssignmentDTO> getStudentAssignments(Long assignmentId);
}
