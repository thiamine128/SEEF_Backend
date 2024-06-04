package com.software.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.software.dto.ClassTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClass {
    private Long id;
    private Long courseId;
    private String name;
    private String time;
    private String location;
    private JSONArray timeData;
}
