package com.iurac.recruit.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.Db;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iurac.recruit.entity.ChatMessage;
import com.iurac.recruit.entity.DicValue;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.mapper.DicTypeMapper;
import com.iurac.recruit.mapper.DicValueMapper;
import com.iurac.recruit.service.DicValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.vo.PageResultVo;
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
@Service("dicValueService")
public class DicValueServiceImpl extends ServiceImpl<DicValueMapper, DicValue> implements DicValueService {

    @Autowired
    private DicTypeMapper dicTypeMapper;
    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public PageResultVo<DicValue> getDicValue(Long page, Long limit) {
        PageResultVo<DicValue> pageResultVo = new PageResultVo<>();
        pageResultVo.setRecords(dicValueMapper.getDicValue((page-1)*limit,limit));
        pageResultVo.setTotal(Long.valueOf(dicValueMapper.selectCount(Wrappers.emptyWrapper())));
        return pageResultVo;
    }

    @Override
    @Transactional(rollbackFor = ManageException.class)
    public boolean saveValue(DicValue dicValue) throws ManageException {
        QueryWrapper<DicValue> valueQueryWrapper = new QueryWrapper<>();
        valueQueryWrapper.eq("type_id", dicValue.getTypeId()).eq("order_no",dicValue.getOrderNo());
        if(dicValueMapper.selectCount(valueQueryWrapper)>0){
            updateOrderNo(dicValue);
        }
        if(dicValueMapper.insert(dicValue)!=1){
            throw new ManageException("添加字典值时错误");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = ManageException.class)
    public boolean updateValue(DicValue dicValue) throws ManageException {
        QueryWrapper<DicValue> valueQueryWrapper = new QueryWrapper<>();
        valueQueryWrapper.eq("type_id", dicValue.getTypeId()).eq("order_no",dicValue.getOrderNo());
        if(dicValueMapper.selectCount(valueQueryWrapper)>0){
            updateOrderNo(dicValue);
        }
        if(dicValueMapper.updateById(dicValue)!=1){
            throw new ManageException("添加字典值时错误");
        }
        return true;
    }

    private void updateOrderNo(DicValue dicValue) throws ManageException {
        QueryWrapper<DicValue> valueQueryWrapper = new QueryWrapper<>();
        valueQueryWrapper.eq("type_id", dicValue.getTypeId()).ge("order_no",dicValue.getOrderNo());
        int count = dicValueMapper.selectCount(valueQueryWrapper);

        if(dicValueMapper.updateOrderNo(dicValue.getTypeId(),dicValue.getOrderNo())!=count){
            throw new ManageException("更新优先级队列时错误");
        }
    }
}
