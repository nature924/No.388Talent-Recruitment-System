package com.iurac.recruit.service;

import com.iurac.recruit.entity.DicType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iurac.recruit.entity.DicValue;
import com.iurac.recruit.exception.ManageException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface DicTypeService extends IService<DicType> {

    boolean deleteType(String id) throws ManageException;
}
