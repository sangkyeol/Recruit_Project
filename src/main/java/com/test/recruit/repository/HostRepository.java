package com.test.recruit.repository;

import com.test.recruit.data.entity.HostCheck;
import com.test.recruit.data.entity.HostList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostRepository {
    int insertHostList(HostList hostList);

    int selectHostListCntByNameAndIp(@Param("name") String name, @Param("ip") String ip);

    int selectHostListCntByName(String name);

    int selectHostListCntByIp(String ip);

    int selectHostListCnt();

    List<HostList> selectHostList();

    HostList selectHostListByHostNo(int hostNo);

    int updateHostListActiveStatusDead(int hostNo);

    int updateHostList(HostList hostList);

    List<HostList> selectHostListByMultiHostNo(List<Integer> hostNoList);

    int insertHostCheck(HostCheck hostCheck);

    HostCheck selectHostCheckByHostNo(int hostNo);

    List<HostList> selectHostListWithCheck(Integer hostNo);

}
