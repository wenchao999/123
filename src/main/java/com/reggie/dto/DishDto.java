package com.reggie.dto;

import com.reggie.pojo.Dish;
import com.reggie.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * dto 是数据传输对象，一般用于服务层和展示层之间的数据传输
 * 为了解决两边对象的数据不一致的情况
 * @author NewAdmin
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
