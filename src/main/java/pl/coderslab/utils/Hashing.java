package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Hashing {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
