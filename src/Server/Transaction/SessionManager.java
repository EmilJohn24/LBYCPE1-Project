package Server.Transaction;

import Server.Structures.Room;
import org.xml.sax.SAXException;

import javax.security.auth.login.FailedLoginException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Calendar;
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

    public static String reserve(String sessionID, String building, String floor, String room, int month, int day, int year, int hour, int minute) throws TransformerException, IOException, SAXException {
        Account acc = getSessionAccount(Integer.parseInt(sessionID));
        return Manager.reserve(acc, building, Integer.parseInt(floor), room, month, day, year, hour, minute);
    }


    public static Account getSessionAccount(Integer sessionID){
        return sessions.get(sessionID);
    }


    static{
        users = new UserDatabase(databaseFilename);
        sessions = new HashMap<>();
    }



}
