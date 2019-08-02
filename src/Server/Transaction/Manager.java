package Server.Transaction;

public class Manager {
    private UserDatabase database;

    public Account getAccountLogin(String username,
                                   String password){
        return Account.login(username, password, database);
    }

    Manager(String loginFile){
        database = new UserDatabase(loginFile);
    }
}
