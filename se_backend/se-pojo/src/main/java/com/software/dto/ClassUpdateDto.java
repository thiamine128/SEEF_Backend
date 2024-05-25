package com.software.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.List;

@Data
public class ClassUpdateDto {
    private Long id;
    private String name;
    private String time;
    private String location;
    private JSONArray timeData;
}

