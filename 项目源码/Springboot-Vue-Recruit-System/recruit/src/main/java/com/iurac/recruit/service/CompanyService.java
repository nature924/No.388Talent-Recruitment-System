package com.iurac.recruit.service;

import com.iurac.recruit.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.exception.ServiceException;
import com.iurac.recruit.vo.EchartsVo;
import com.iurac.recruit.vo.PageResultVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface CompanyService extends IService<Company> {

    void join(String id, Company company) throws ServiceException;

    EchartsVo getNumOfCompanyInListedStatus();

    EchartsVo getNumOfCompanyInSize();

    PageResultVo<Company> getByCondition(Long page, Long limit, String name, String listedStatus, String size, String startDate, String endDate);

    void deleteCompany(String id) throws ManageException;
}
