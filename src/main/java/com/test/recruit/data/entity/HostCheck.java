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
public class HostCheck {
    private Integer checkNo;
    private int hostNo;
    private Status status;
    private String reason;
    private LocalDateTime registered;
}
