package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NewAdmin
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
