package com.iurac.recruit.mapper;

import com.iurac.recruit.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CompanyMapper extends BaseMapper<Company> {

    List<Map<String, Object>> getNumOfCompanyInListedStatus();

    List<Map<String, Object>> getNumOfCompanyInSize();

    List<Company> getByCondition(@Param("skipCount") long skipCount, @Param("limit") Long limit,
                                 @Param("name") String name, @Param("listedStatus") String listedStatus,
                                 @Param("size") String size,
                                 @Param("startDate") String startDate, @Param("endDate") String endDate);

    Long getCountByCondition(@Param("name") String name, @Param("listedStatus") String listedStatus,
                             @Param("size") String size,
                             @Param("startDate") String startDate, @Param("endDate") String endDate);
}
