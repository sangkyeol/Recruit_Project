package com.test.recruit.data.entity;

import com.test.recruit.data.enumval.MemberStatus;
import com.test.recruit.data.enumval.Role;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class Member {
    private Integer memberNo;
    private String id;
    private String password;
    private Role role;
    private LocalDateTime lastLogin;

    private MemberStatus memberStatus;
    private LocalDateTime registered;
}
