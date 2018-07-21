package com.like.zero.spring.service.v2;

import com.like.zero.spring.dao.v2.AccountDao;
import com.like.zero.spring.dao.v2.ItemDao;

/**
 * Created by like 2018/6/9
 */
public class PetStoreService {

    private AccountDao accountDao;

    private ItemDao itemDao;

    private String owner;

    private int version;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
