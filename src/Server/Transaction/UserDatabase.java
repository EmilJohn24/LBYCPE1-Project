package Server.Transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class UserDatabase {
    private HashMap<String, String> users; //username-password pair
    private BufferedReader file;


    public UserDatabase(String filename){
        try {
            file = new BufferedReader(new FileReader(filename)); //TODO: change to remote file, i.e. make access indirect
            users = databaseParse(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean inDatabase(String username,
                              String password){
            return presentInDataBase(username) && users.get(username).equals(password);
    }

    public boolean presentInDataBase(String username){
       // System.out.println(users.values()); //TODO: Remove checker
        return users.containsKey(username.trim());
    }



    //static stuff

    static private final String userFileSeparator = "!";

    static private HashMap<String, String>
                databaseParse(BufferedReader data){
        HashMap<String, String> _users = new HashMap<>();

        String newData;
        String[] sep;
        try {
            while ((newData = data.readLine()) != null){
                sep = newData.split(userFileSeparator, 2);
                _users.put(sep[0], sep[1]);
            }
        }

        catch (IOException e){
            e.printStackTrace();
        }
        return _users;
    }
}
