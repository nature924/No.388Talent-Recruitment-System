package com.iurac.recruit.service;

import com.iurac.recruit.entity.ChatMessage;
import com.iurac.recruit.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iurac.recruit.vo.EchartsVo;
import com.iurac.recruit.vo.JobVo;
import com.iurac.recruit.vo.PageResultVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface JobService extends IService<Job> {

    List<JobVo> getJobsByConditionInPage(int skipCount, String jobName, String education, String experience, String startCreateTime, String city, String business);

    EchartsVo getNumOfJobInArea();

    EchartsVo getNumOfJobInBusiness();

    PageResultVo<Job> getByCondition(Long page, Long limit, String job, String education, String area, String business, String startDate, String endDate);
}
