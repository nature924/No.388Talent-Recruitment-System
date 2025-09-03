package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iurac.recruit.entity.ChatMessage;
import com.iurac.recruit.entity.Job;
import com.iurac.recruit.mapper.JobMapper;
import com.iurac.recruit.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.vo.EchartsVo;
import com.iurac.recruit.vo.JobVo;
import com.iurac.recruit.vo.PageResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Service("jobService")
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Override
    public List<JobVo> getJobsByConditionInPage(int skipCount, String jobName, String education, String experience, String startCreateTime, String city, String business) {
        return jobMapper.getJobsByConditionInPage(skipCount,jobName,education,experience,startCreateTime,city,business);
    }

    @Override
    public EchartsVo getNumOfJobInArea() {
        EchartsVo echartsVo = new EchartsVo();
        List<Map<String ,Object>> list = jobMapper.getNumOfJobInArea();;
        int total = jobMapper.selectCount(Wrappers.emptyWrapper());
        echartsVo.setDataList(list);
        echartsVo.setTotal(total);
        return echartsVo;
    }

    @Override
    public EchartsVo getNumOfJobInBusiness() {
        EchartsVo echartsVo = new EchartsVo();
        List<Map<String ,Object>> list = jobMapper.getNumOfJobInBusiness();;
        int total = jobMapper.selectCount(Wrappers.emptyWrapper());
        echartsVo.setDataList(list);
        echartsVo.setTotal(total);
        return echartsVo;
    }

    @Override
    public PageResultVo<Job> getByCondition(Long page, Long limit, String job, String education, String area, String business, String startDate, String endDate) {
        PageResultVo<Job> pageResultVo = new PageResultVo<>();
        pageResultVo.setRecords(jobMapper.getByCondition((page-1)*limit,limit,job,education,area,business,startDate,endDate));
        pageResultVo.setTotal(jobMapper.getCountByCondition(job,education,area,business,startDate,endDate));
        return pageResultVo;
    }
}
