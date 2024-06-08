package com.software.service.impl;

import com.software.constant.MessageConstant;
import com.software.dto.*;
import com.software.entity.Assignment;
import com.software.entity.StudentAssignment;
import com.software.entity.User;
import com.software.exception.AssignmentOverdueException;
import com.software.mapper.AssignmentMapper;
import com.software.mapper.UserMapper;
import com.software.service.AssignmentService;
import com.software.vo.AssignmentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private UserMapper userMapper;

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
    public Assignment getAssignment(Long assignmentId) {
        return assignmentMapper.getAssignment(assignmentId);
    }

    @Override
    public void markHw(HomeWorkFeedBackDTO homeWorkFeedBackDTO) {
        assignmentMapper.markHw(homeWorkFeedBackDTO);
    }

    @Override
    public List<AssignmentVO> getAllAssignments(AssignmentQueryDto assignmentQueryDto) {
        return assignmentMapper.getAllAssignments(assignmentQueryDto);
    }

    @Override
    public List<StudentAssignmentDTO> getStudentAssignments(Long assignmentId) {
        List<StudentAssignment> studentAssignments = assignmentMapper.getStudentAssignments(assignmentId);
        List<StudentAssignmentDTO> result = new ArrayList<StudentAssignmentDTO>();
        for (StudentAssignment studentAssignment : studentAssignments) {
           User user= userMapper.getByID(studentAssignment.getStudentId());
           if (user == null)
               continue;
           StudentAssignmentDTO studentAssignmentDTO = new StudentAssignmentDTO(studentAssignment.getStudentId(),user.getName(),studentAssignment.getAssignmentId(),studentAssignment.getAssignmentFile(),studentAssignment.getGrade(),studentAssignment.getSubmissionTime(),studentAssignment.getAttachmentContext(),studentAssignment.getFeedback());
            result.add(studentAssignmentDTO);
        }
        return result;
    }
}
