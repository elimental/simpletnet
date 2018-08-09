package com.getjavajob.simplenet.service;

import static org.mindrot.jbcrypt.BCrypt.*;

public class PasswordEncryptService {
    public static String genHash(String password) {
        return hashpw(password, gensalt());
    }

    public static boolean checkPass(String password, String hash) {
        return checkpw(password, hash);
    }
}
