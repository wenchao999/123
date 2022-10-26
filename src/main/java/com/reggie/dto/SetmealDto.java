package com.reggie.dto;
import com.reggie.pojo.SetMeal;
import com.reggie.pojo.SetMealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends SetMeal {

    private List<SetMealDish> setMealDishes;

    private String categoryName;
}
