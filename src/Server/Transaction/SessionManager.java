package Server.Transaction;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;

public class SessionManager {
    private static HashMap<Integer, Account> sessions; //links session IDs to accounts
    private static UserDatabase users;
    private static final String databaseFilename = "users.txt";
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

    private static int addToSessions(Account acc) {
        int hash = getHashCode(acc);
        sessions.put(hash, acc);
        return hash;
    }

    public static Integer addSession(Account newAccount){
        return addToSessions(newAccount);
    }

    public static Integer addSession(String user, String password) throws FailedLoginException {
        Account newLogin = SessionManager.login(user, password);
        return addToSessions(newLogin);
    }


    static{
        users = new UserDatabase(databaseFilename);
        sessions = new HashMap<>();
    }



}
