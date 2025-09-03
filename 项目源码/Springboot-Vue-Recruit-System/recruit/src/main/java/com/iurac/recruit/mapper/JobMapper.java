package com.iurac.recruit.mapper;

import com.iurac.recruit.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iurac.recruit.vo.JobVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface JobMapper extends BaseMapper<Job> {

    List<JobVo> getJobsByConditionInPage(@Param("skipCount") int skipCount, @Param("jobName") String jobName,
                                         @Param("education") String education, @Param("experience") String experience,
                                         @Param("startCreateTime") String startCreateTime,
                                         @Param("city") String city, @Param("business") String business);

    List<Map<String, Object>> getNumOfJobInBusiness();

    List<Map<String, Object>> getNumOfJobInArea();

    List<Job> getByCondition(@Param("skipCount") long skipCount, @Param("limit") Long limit,
                             @Param("job") String job, @Param("education") String education,
                             @Param("area") String area, @Param("business") String business,
                             @Param("startDate") String startDate, @Param("endDate") String endDate);

    Long getCountByCondition(@Param("job") String job, @Param("education") String education,
                             @Param("area") String area, @Param("business") String business,
                             @Param("startDate") String startDate, @Param("endDate") String endDate);
}
