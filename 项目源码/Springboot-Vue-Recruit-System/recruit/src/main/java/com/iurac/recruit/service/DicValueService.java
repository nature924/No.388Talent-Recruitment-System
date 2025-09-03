package com.iurac.recruit.service;

import com.iurac.recruit.entity.DicValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.vo.PageResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface DicValueService extends IService<DicValue> {

    PageResultVo<DicValue> getDicValue(Long page, Long limit);

    boolean saveValue(DicValue dicValue) throws ManageException;

    boolean updateValue(DicValue dicValue) throws ManageException;
}
