package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.entity.City;
import com.iurac.recruit.entity.Province;
import com.iurac.recruit.mapper.CityMapper;
import com.iurac.recruit.service.CityService;
import com.iurac.recruit.service.ProvinceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Service("cityService")
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {

}
