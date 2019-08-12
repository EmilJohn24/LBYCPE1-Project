package Server.Structures;

import Server.Transaction.Account;

public class RoomSlot {
    public RoomSlot(){
        client = null;
    }

    public RoomSlot(Account client){
        this.client = client;
    }

    protected Account getClient(){
        return this.client;
    }


    public boolean isEmpty(){
        return getClient() == null;
    }
    private Account client;
}