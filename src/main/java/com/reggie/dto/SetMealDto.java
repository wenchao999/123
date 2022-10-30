package com.reggie.dto;
import com.reggie.pojo.SetMeal;
import com.reggie.pojo.SetMealDish;
import lombok.Data;
import java.util.List;

/**
 * @author NewAdmin
 */
@Data
public class SetMealDto extends SetMeal {

    private List<SetMealDish> setMealDishes;

    private String categoryName;
}
