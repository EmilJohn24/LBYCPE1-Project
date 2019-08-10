package Server.Transaction;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;

public class SessionManager {
    private static HashMap<Integer, Account> sessions; //links session IDs to accounts
    private static UserDatabase users;

    public static Integer getHashCode(Account acc){
        return acc.getUsername().hashCode();
    }
    public static Account login(String username, String password) throws FailedLoginException {
        Account newLogin = Account.login(username, password, users);
        if (newLogin != null) {
            return newLogin;
        }
        else{
            throw new FailedLoginException("Login failed. Invalid username or password");
        }
    }

    private static Integer addToSessions(Account acc) {
        Integer hash = getHashCode(acc);
        sessions.put(hash, acc);
        return hash;
    }

    public static Integer addSession(Account newAccount){
        return addToSessions(newAccount);
    }

    public static Integer addSession(String user, String password) throws FailedLoginException {
        return addToSessions(SessionManager.login(user, password));
    }




}
