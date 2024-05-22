package com.software.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String id;
    private Long send;
    private Long to;
    private String content;
    private Date date;
    private boolean read;
}
