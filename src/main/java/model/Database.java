package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Database {
    private final ObservableList<Ticket> tickets = FXCollections.observableArrayList();
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
    public void removeTicket(int index) throws IndexOutOfBoundsException{
        tickets.remove(index);
    }
    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Ticket[] persons = tickets.toArray(new Ticket[tickets.size()]);
        oos.writeObject(persons);
        oos.close();
    }

    public void loadFromFile(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            Ticket[] persons = (Ticket[]) ois.readObject();
            tickets.clear();
            tickets.addAll(Arrays.asList(persons));
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
