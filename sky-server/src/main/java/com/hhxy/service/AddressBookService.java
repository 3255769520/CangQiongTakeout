package com.hhxy.service;

import com.hhxy.entity.AddressBook;
import java.util.List;

public interface AddressBookService {
    void add(AddressBook addressBook);
    List<AddressBook> list(AddressBook addressBook);
    void setDefault(AddressBook addressBook);
    AddressBook getDefault();
}