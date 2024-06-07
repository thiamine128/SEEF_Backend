package com.software.vo;

import com.software.entity.JoinClassRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinClassRequestVO {
    private String id;
    private Long studentId;
    private Long classId;
    private Integer state;
    private String name;
    private String realName;

    public static JoinClassRequestVO from(JoinClassRequest joinClassRequest, String name, String realName) {
        return new JoinClassRequestVOBuilder()
                .id(joinClassRequest.getId())
                .studentId(joinClassRequest.getStudentId())
                .classId(joinClassRequest.getClassId())
                .state(joinClassRequest.getState())
                .name(name)
                .realName(realName).build();
    }
}
