package com.software.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@Builder
public class ChatMsgVO {
    private String MFromUser;
    private String MMsg;

}
