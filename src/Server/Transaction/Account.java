package Server.Transaction;

public class Account {
    private String user;
    private boolean passiveState;
    public String getUsername(){
        return user;
    }
    static public Account pseudoLogin(String _user, UserDatabase data){
        if (data.presentInDataBase(_user)){
            return new Account(_user, true);
        }
        else {
            return null;

        }
    }

    static public Account login(String _user,
                                String password,
                                UserDatabase data){
        if (data.inDatabase(_user, password))
            return new Account(_user, false);
        else return null;
    }

    protected Account(String _user, boolean isPassive){
        this.user = _user;
        passiveState = isPassive;
    }
    //security-protocol
    public boolean isPassive(){
        return passiveState;
    }
}

