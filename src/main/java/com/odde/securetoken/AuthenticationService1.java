package com.odde.securetoken;


class Printer {
    public void print(String msg) {
        System.out.println(msg);
    }
}

public class AuthenticationService1 {

    private final ProfileDao profileDao;
    private final RsaTokenDao rsaToken;
    private final Printer printer;

    public AuthenticationService1(ProfileDao profileDao, RsaTokenDao rsaToken, Printer printer) {
        this.profileDao = profileDao;
        this.rsaToken = rsaToken;
        this.printer = printer;
    }

    public boolean isValid(String account, String password) {
        // 根據 account 取得自訂密碼
        String passwordFromDao = profileDao.getPassword(account);

        // 根據 account 取得 RSA token 目前的亂數
        String randomCode = rsaToken.getRandom(account);

        // 驗證傳入的 password 是否等於自訂密碼 + RSA token亂數
        String validPassword = passwordFromDao + randomCode;
        boolean isValid = password.equals(validPassword);

        if (isValid) {
            return true;
        } else {
            printer.print("Login failed!");
            return false;
        }
    }
}
