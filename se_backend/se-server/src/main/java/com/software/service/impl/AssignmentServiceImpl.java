package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.constant.MessageConstant;
import com.software.dto.AssignmentPublishDto;
import com.software.dto.AssignmentQueryDto;
import com.software.dto.AssignmentSubmitDto;
import com.software.dto.HomeWorkFeedBackDTO;
import com.software.entity.Assignment;
import com.software.exception.AssignmentOverdueException;
import com.software.mapper.AssignmentMapper;
import com.software.result.PageResult;
import com.software.service.AssignmentService;
import com.software.vo.AssignmentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Override
    public void publishAssignment(AssignmentPublishDto assignmentPublishDto) {
        assignmentMapper.publishAssignment(assignmentPublishDto);
    }

    @Override
    public List<Assignment> getAssignmentsInClass(Long classId) {
        return assignmentMapper.getAssignmentsInClass(classId);
    }

    @Override
    public void submitAssignment(Long studentId, AssignmentSubmitDto assignmentSubmitDto) {
        if (new Date(System.currentTimeMillis()).after(assignmentMapper.getDueDate(assignmentSubmitDto.getAssignmentId()))) {
            throw new AssignmentOverdueException(MessageConstant.SUBMISSION_OVERDUE);
        }
        assignmentMapper.submitAssignment(studentId, assignmentSubmitDto.getAssignmentId(), assignmentSubmitDto.getAssignmentFile(), assignmentSubmitDto.getAssignmentContext());
    }

    @Override
    public void markHw(HomeWorkFeedBackDTO homeWorkFeedBackDTO) {
        assignmentMapper.markHw(homeWorkFeedBackDTO);
    }

    @Override
    public List<AssignmentVO> getAllAssignments(AssignmentQueryDto assignmentQueryDto) {
        return assignmentMapper.getAllAssignments(assignmentQueryDto);
    }
}
