package com.test.recruit.data.entity;

import com.test.recruit.data.enumval.ActiveStatus;
import com.test.recruit.data.enumval.Status;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class HostList {
    private Integer hostNo;
    private int memberNo;
    private String name;
    private String ip;
    private Status lastStatus;
    private LocalDateTime lastChecked;
    private ActiveStatus activeStatus;
    private LocalDateTime registered;
    private LocalDateTime updated;
}
