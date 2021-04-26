package com.odde.securetoken;

public class ProfileDao {
    public String getPassword(String account) {
        return Context.getPassword(account);
    }
}
