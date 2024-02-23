package com.test.recruit.repository;

import com.test.recruit.data.entity.MemberLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository {
    int insertMemberLog(MemberLog memberLog);

    List<MemberLog> selectMemberLog(@Param("limit") int limit, @Param("offset") int offset);
}
