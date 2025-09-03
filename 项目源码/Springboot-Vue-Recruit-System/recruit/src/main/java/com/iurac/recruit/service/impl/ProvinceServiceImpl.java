package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.entity.DicValue;
import com.iurac.recruit.entity.Province;
import com.iurac.recruit.mapper.DicValueMapper;
import com.iurac.recruit.mapper.ProvinceMapper;
import com.iurac.recruit.service.DicValueService;
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
@Service("provinceService")
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper, Province> implements ProvinceService {

}
