package Server.Transaction;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;

public class SessionManager {
    private static HashMap<Integer, Account> sessionID; //links session IDs to accounts
    private static UserDatabase users;
    public static Integer login(String username, String password) throws FailedLoginException {
        Account newLogin = Account.login(username, password, users);
        if (newLogin != null) {
            Integer id = newLogin.getUsername().hashCode();
            return id;
        }
        else{
            throw new FailedLoginException("Login failed. Invalid username or password");
        }
    }

}
