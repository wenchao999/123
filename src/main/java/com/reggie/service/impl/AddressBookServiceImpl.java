package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.mapper.AddressBookMapper;
import com.reggie.pojo.AddressBook;
import com.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author NewAdmin
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
