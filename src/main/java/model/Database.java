package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();
    public Database(){
    }
    public Ticket getTicket(int index) throws IndexOutOfBoundsException {
        return tickets.get(index);
    }

    public List<Ticket> getData(){
        return tickets;
    }
    public void addPeople(Ticket toAdd){
        tickets.add(toAdd);
    }
    public void removePeople(int index) throws IndexOutOfBoundsException{
        tickets.remove(index);
    }
}
