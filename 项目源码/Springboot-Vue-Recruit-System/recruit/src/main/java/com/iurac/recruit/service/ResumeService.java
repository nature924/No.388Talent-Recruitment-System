package com.iurac.recruit.service;

import com.iurac.recruit.entity.Resume;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iurac.recruit.vo.PageResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface ResumeService extends IService<Resume> {

    PageResultVo<Resume> getByCondition(Long page, Long limit, String username);

    Resume getByUserId(String id);
}
