package com.iurac.recruit.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iurac.recruit.entity.*;
import com.iurac.recruit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

@Component
public class SystemInitLoader implements ServletContextAware {

    @Autowired
    DicTypeService dicTypeService;
    @Autowired
    DicValueService dicValueService;
    @Autowired
    ProvinceService provinceService;
    @Autowired
    CityService cityService;
    @Autowired
    TypeService typeService;
    @Autowired
    BusinessService businessService;

    @Override
    public void setServletContext(ServletContext servletContext) {
        initProvinceCity(servletContext);
        initBusinessType(servletContext);
        initDictionary(servletContext);
    }

    private void initBusinessType(ServletContext servletContext) {
        List<Type> typeList = typeService.list();
        servletContext.setAttribute("typeList",typeList);
        typeList.forEach(type -> {
            QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type_id",type.getId());
            List<Business> businessList = businessService.list(queryWrapper);
            servletContext.setAttribute("businessInType"+type.getId(),businessList);
        });
    }

    private void initProvinceCity(ServletContext servletContext) {
        List<Province> provinceList = provinceService.list();
        servletContext.setAttribute("provinceList",provinceList);
        provinceList.forEach(province -> {
            QueryWrapper<City> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("province_id",province.getId()).orderByAsc("city_no");
            List<City> cityList = cityService.list(queryWrapper);
            servletContext.setAttribute("cityInProvince"+province.getId(),cityList);
        });
    }

    private void initDictionary(ServletContext servletContext) {
        List<DicType> dicTypeList = dicTypeService.list();
        dicTypeList.forEach(type -> {
            QueryWrapper<DicValue> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type_id",type.getId()).orderByAsc("order_no");
            List<DicValue> dicValueList = dicValueService.list(queryWrapper);
            servletContext.setAttribute(type.getType()+"List",dicValueList);
        });
    }
}
