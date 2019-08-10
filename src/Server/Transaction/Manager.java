package Server.Transaction;

import Server.Structures.StructureParser;

//central manager of every operation
//will be set as a static class
public class Manager {

    private static UserDatabase database;
    private static StructureParser parser;
    public Account getAccountLogin(String username,
                                   String password){
        return Account.login(username, password, database);
    }

    static {
        database = new UserDatabase("users.txt");

    }
}
