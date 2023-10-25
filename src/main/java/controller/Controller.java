package controller;

import model.Database;
import model.Ticket;

import java.util.List;

public class Controller {
    Database database;

    public Controller(){
        database = new Database();
    }
    public List<Ticket> getData(){
        return database.getData();
    }
    public void addTicket(Ticket toAdd){
        database.addPeople(toAdd);
    }

}
