package com.test.recruit.data.entity;

import com.test.recruit.data.enumval.Status;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class MemberLog {
    private int memberNo;
    private String type;
    private Status result;
    private LocalDateTime registered;

    //add
    private String id;
}
