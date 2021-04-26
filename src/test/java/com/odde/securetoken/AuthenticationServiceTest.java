package com.odde.securetoken;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockRsaTokenDao extends RsaTokenDao {
    public String random = "";

    @Override
    public String getRandom(String account) {
        return random;
    }
}

class MockPrinter extends Printer {

    public String msg;

    public void print(String msg) {
        this.msg = msg;
    }
}

public class AuthenticationServiceTest {

    ProfileDao profileDao = new ProfileDao();
    MockPrinter printer = new MockPrinter();
    MockRsaTokenDao rsaTokenDao = new MockRsaTokenDao();
    AuthenticationService1 target = new AuthenticationService1(profileDao, rsaTokenDao, printer);

    @Test
    public void is_valid_test() {

        givenRandomCode("000000");

        boolean result = target.isValid("joey", "91000000");

        assertTrue(result);
    }

    @Test
    public void is_invalid_test() {

        givenRandomCode("111111");

        boolean result = target.isValid("joey", "91000000");

        assertFalse(result);
    }

    @Test
    public void is_log_valid_test() {

        givenRandomCode("111111");

        boolean result = target.isValid("joey", "91000000");

        assertFalse(result);

        assertEquals("Login failed!", printer.msg);
    }

    private void givenRandomCode(String code) {
        rsaTokenDao.random = "111111";
    }

}
