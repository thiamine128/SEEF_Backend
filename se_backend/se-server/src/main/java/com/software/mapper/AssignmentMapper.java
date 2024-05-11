package com.software.mapper;

import com.software.dto.AssignmentPublishDto;
import com.software.entity.Assignment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface AssignmentMapper {
    @Insert("insert into assignments (title, description, due_date, attachment_url, class_id) values (#{title}, #{description}, #{dueDate}, #{attachmentUrl}, #{classId})")
    void publishAssignment(AssignmentPublishDto assignmentPublishDto);

    @Select("select * from assignments where class_id=#{classId}")
    List<Assignment> getAssignmentsInClass(Long classId);

    @Insert("insert into student_assignment (student_id, assignment_id, assignment_file, assignment_context) values (#{studentId}, #{assignmentId}, #{assignmentFile}, #{assignmentContext})")
    void submitAssignment(Long studentId, Long assignmentId, String assignmentFile, String assignmentContext);

    @Select("select due_date from assignments where id=#{assignmentId}")
    Date getDueDate(Long assignmentId);
}
