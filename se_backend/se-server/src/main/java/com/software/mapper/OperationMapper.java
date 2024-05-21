package com.software.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author
 * @Description：
 * @date
 */
@Mapper
public interface OperationMapper {
    @Insert("insert into user_blog_operation (user_id, blog_id, operation_type) values (#{userId}, #{blogId}, #{operationType})")
    public void insertOperation(Long userId, Long blogId, Integer operationType);
    @Delete("delete from user_blog_operation where user_id=#{userId} and blog_id=#{blogId} and operation_type=#{operationType}")
    public void deleteOperation(Long userId, Long blogId, Integer operationType);

}
