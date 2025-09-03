package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iurac.recruit.entity.Resume;
import com.iurac.recruit.mapper.ResumeMapper;
import com.iurac.recruit.service.ResumeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.vo.PageResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Service("resumeService")
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public PageResultVo<Resume> getByCondition(Long page, Long limit, String username) {
        PageResultVo<Resume> pageResultVo = new PageResultVo<>();
        pageResultVo.setRecords(resumeMapper.getByCondition((page-1)*limit,limit,username));
        pageResultVo.setTotal(resumeMapper.getCountByCondition(username));
        return pageResultVo;
    }

    @Override
    public Resume getByUserId(String id) {
        return baseMapper.selectOne(new QueryWrapper<Resume>()
            .eq("user_id",id)
            .last("limit 1"));
    }
}
