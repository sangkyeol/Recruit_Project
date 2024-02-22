package com.test.recruit.repository;

import com.test.recruit.data.entity.HostList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostRepository {
    int insertHostList(HostList hostList);

    int selectHostListCntByNameAndIp(@Param("name") String name, @Param("ip") String ip);
}
