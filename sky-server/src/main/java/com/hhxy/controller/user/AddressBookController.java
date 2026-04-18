package com.hhxy.controller.user;

import com.hhxy.context.BaseContext;
import com.hhxy.entity.AddressBook;
import com.hhxy.result.Result;
import com.hhxy.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Tag(name = "C端-地址簿接口")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @Operation(summary = "新增地址")
    public Result save(@RequestBody AddressBook addressBook) {
        addressBookService.add(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @Operation(summary = "查询当前登录用户的所有地址")
    public Result<List<AddressBook>> list() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    @PutMapping("/default")
    @Operation(summary = "设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    @GetMapping("/default")
    @Operation(summary = "查询默认地址")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = addressBookService.getDefault();
        return addressBook != null ? Result.success(addressBook) : Result.error("没有找到默认地址");
    }
}