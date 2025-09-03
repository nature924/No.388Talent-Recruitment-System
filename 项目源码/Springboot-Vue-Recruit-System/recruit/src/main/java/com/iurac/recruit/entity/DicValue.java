package com.iurac.recruit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@TableName("t_dic_value")
public class DicValue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 不能为空，并且要求同一个字典类型下字典值不能重复，具有唯一性。
     */
    private String value;

    /**
     * 排列序号
     */
    private Integer orderNo;

    /**
     * 字典类型id
     */
    private String typeId;

    @TableField(exist = false)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "DicValue{" +
        "id=" + id +
        ", value=" + value +
        ", orderNo=" + orderNo +
        ", typeId=" + typeId +
        "}";
    }
}
