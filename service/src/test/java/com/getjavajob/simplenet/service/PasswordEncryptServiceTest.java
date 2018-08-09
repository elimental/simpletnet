package com.getjavajob.simplenet.service;

import org.junit.Test;

import static com.getjavajob.simplenet.service.PasswordEncryptService.genHash;
import static org.junit.Assert.assertEquals;

public class PasswordEncryptServiceTest {

    @Test
    public void checkPass() {
        String password = "P@ssword6583#";
        String hash = genHash(password);
        boolean except = true;
        assertEquals(except, PasswordEncryptService.checkPass(password, hash));
    }
}