package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.entity.Hr;
import com.iurac.recruit.entity.Type;
import com.iurac.recruit.mapper.TypeMapper;
import com.iurac.recruit.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
