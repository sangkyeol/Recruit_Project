package com.test.recruit.repository;

import com.test.recruit.data.entity.MemberLog;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository {
    int insertMemberLog(MemberLog memberLog);
}
