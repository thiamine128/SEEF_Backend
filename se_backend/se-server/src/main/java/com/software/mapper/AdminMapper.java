package com.software.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author
 * @Description：
 * @date
 */
@Mapper
public interface AdminMapper {
    @Delete("delete from classes where id =#{classId} ")
    void deleteClass(Long classId);
}
