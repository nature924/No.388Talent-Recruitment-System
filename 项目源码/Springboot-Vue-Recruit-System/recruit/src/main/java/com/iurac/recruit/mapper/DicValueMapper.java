package com.iurac.recruit.mapper;

import com.iurac.recruit.entity.DicValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
public interface DicValueMapper extends BaseMapper<DicValue> {

    List<DicValue> getDicValue(@Param("skipCount") long skipCount,@Param("limit") Long limit);

    int updateOrderNo(@Param("typeId") String typeId, @Param("orderNo") Integer orderNo);
}
