package com.test.recruit.repository;

import com.test.recruit.data.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {
    Member selectMemberById(@Param("id") String id);

    int insertMember(Member member);
}
