package com.stablekernel.interview.ui.login;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityUnitTest {
    @Test
    public void isInputValid_validatesUserInput() throws Exception {
        LoginActivity loginActivity = new LoginActivity();

        assertTrue(loginActivity.isInputValid("one", "two"));
        assertTrue(loginActivity.isInputValid("!_(", ":':"));

        assertFalse(loginActivity.isInputValid("", ""));
        assertFalse(loginActivity.isInputValid("user name", "pass word"));
        assertFalse(loginActivity.isInputValid("valid", "not valid"));
        assertFalse(loginActivity.isInputValid("not valid", "valid"));
    }
}