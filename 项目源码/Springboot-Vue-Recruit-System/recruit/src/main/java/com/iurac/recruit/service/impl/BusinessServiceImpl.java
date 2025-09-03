package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.entity.Business;
import com.iurac.recruit.entity.Type;
import com.iurac.recruit.mapper.BusinessMapper;
import com.iurac.recruit.service.BusinessService;
import com.iurac.recruit.service.TypeService;
import org.springframework.data.convert.TypeMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Service("businessService")
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

}
