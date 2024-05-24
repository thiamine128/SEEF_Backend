package com.software.dto;

import lombok.Data;

import java.util.List;

@Data
public class JoinClassRequestPendDto {
    List<String> ids;
    Integer state;
}
