package Server.Transaction;

public class Account {
    String user;

    static public Account login(String _user,
                                String password,
                                UserDatabase data){

        if (data.inDatabase(_user, password))
            return new Account(_user);
        else return null;
    }

    protected Account(String _user){
        this.user = _user;
    }
}
