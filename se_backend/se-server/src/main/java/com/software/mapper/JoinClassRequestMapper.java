package com.software.mapper;

import com.github.pagehelper.Page;
import com.software.dto.JoinClassRequestPageQueryDto;
import com.software.entity.JoinClassRequest;
import com.software.vo.TopicVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface JoinClassRequestMapper {
    @Insert("insert into join_class_requests values(#{id}, #{studentId}, #{classId}, 0)")
    void newRequest(String id, Long studentId, Long classId);

    @Update("update join_class_requests set state=#{state} where id=#{id} and state=0")
    Long updateState(String id, Integer state);

    @Select("select student_id from join_class_requests where student_id=#{studentId} and class_id=#{classId} and (state=0 or state=1)")
    Long canRequest(Long studentId, Long classId);

    @Select("select r.id, r.student_id, r.class_id, r.state from join_class_requests r left join classes c on r.class_id = c.id where c.course_id = #{courseId}")
    Page<JoinClassRequest> pageQuery(Long courseId);

    @Select("select * from join_class_requests where id=#{id}")
    JoinClassRequest getRequest(String id);
}
