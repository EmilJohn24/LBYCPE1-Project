package Server.Session;

public class Account {
    static public Account login(String user, String password){
        return new Account(user, password);
    }

    private int id_number;

    Account(String user, String password){

    }
}
