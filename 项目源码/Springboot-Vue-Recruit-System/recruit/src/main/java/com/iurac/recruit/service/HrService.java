package com.iurac.recruit.service;

import com.iurac.recruit.entity.Hr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.exception.ServiceException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface HrService extends IService<Hr> {

    void unbind(String hrId) throws ServiceException;

    void bind(String companyId, String username, String phone, String position) throws ServiceException;

    void deleteHr(String userId) throws ManageException;

    void deleteManager(String userId) throws ManageException;
}
