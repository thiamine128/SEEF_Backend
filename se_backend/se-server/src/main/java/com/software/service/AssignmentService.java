package com.software.service;

import com.software.dto.AssignmentPublishDto;
import com.software.dto.AssignmentQueryDto;
import com.software.dto.AssignmentSubmitDto;
import com.software.dto.HomeWorkFeedBackDTO;
import com.software.entity.Assignment;
import com.software.result.PageResult;
import com.software.vo.AssignmentVO;

import java.util.Date;
import java.util.List;

public interface AssignmentService {
    void publishAssignment(AssignmentPublishDto assignmentPublishDto);
    List<Assignment> getAssignmentsInClass(Long classId);
    void submitAssignment(Long studentId, AssignmentSubmitDto assignmentSubmitDto);

    void markHw(HomeWorkFeedBackDTO homeWorkFeedBackDTO);
    List<AssignmentVO> getAllAssignments(AssignmentQueryDto assignmentQueryDto);
}
