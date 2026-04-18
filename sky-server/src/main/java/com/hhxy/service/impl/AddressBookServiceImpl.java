package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hhxy.context.BaseContext;
import com.hhxy.entity.AddressBook;
import com.hhxy.mapper.AddressBookMapper;
import com.hhxy.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    @Transactional
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }

    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.selectList(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getUserId, addressBook.getUserId()));
    }

    @Override
    @Transactional
    public void setDefault(AddressBook addressBook) {
        addressBookMapper.update(null, new LambdaUpdateWrapper<AddressBook>()
                .set(AddressBook::getIsDefault, 0)
                .eq(AddressBook::getUserId, BaseContext.getCurrentId()));

        addressBook.setIsDefault(1);
        addressBookMapper.updateById(addressBook);
    }

    @Override
    public AddressBook getDefault() {
        return addressBookMapper.selectOne(new LambdaQueryWrapper<AddressBook>()
                .eq(AddressBook::getUserId, BaseContext.getCurrentId())
                .eq(AddressBook::getIsDefault, 1));
    }
}