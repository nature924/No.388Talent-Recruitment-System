package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iurac.recruit.entity.DicType;
import com.iurac.recruit.entity.DicValue;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.mapper.DicTypeMapper;
import com.iurac.recruit.mapper.DicValueMapper;
import com.iurac.recruit.service.DicTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Service("dicTypeService")
public class DicTypeServiceImpl extends ServiceImpl<DicTypeMapper, DicType> implements DicTypeService {

    @Autowired
    private DicTypeMapper dicTypeMapper;
    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    @Transactional
    public boolean deleteType(String id) throws ManageException {
        QueryWrapper<DicValue> valueQueryWrapper = new QueryWrapper<>();
        valueQueryWrapper.eq("type_id",id);
        int count = dicValueMapper.selectCount(valueQueryWrapper);
        if(dicValueMapper.delete(valueQueryWrapper)!=count){
            throw new ManageException("删除所属值时发生错误");
        }

        if(dicTypeMapper.deleteById(id)!=1){
            throw new ManageException("删除字典类型时发生错误");
        }
        return true;
    }
}
