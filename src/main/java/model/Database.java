package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MergeSort;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Yura
 * @version 1.0
 * */
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
    /**
     * Method to save data from tickets to file
     * @param file File to save to
     * @throws IOException if tickets are corupted
     * */
    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Ticket[] persons = tickets.toArray(new Ticket[tickets.size()]);
        oos.writeObject(persons);
        oos.close();
    }
    /**
     * Method to read data to tickets from file
     * @param file - File to read from
     * @throws IOException if file is corrupted or is not valid
     * Also can throw it if version of Ticket class is different
     * */
    public void loadFromFile(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            Ticket[] persons = (Ticket[]) ois.readObject();
            tickets.clear();
            tickets.addAll(Arrays.asList(persons));
            int maxId = tickets.get(0).getId();
            for(var i: tickets){
                if(i.getId() > maxId){
                    maxId = i.getId();
                }
            }
            Ticket.setAmount(maxId+1);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
/**
 * Method to group tickets to their dates of expiring and then to sort them in alphabetic order for surname.
 * */
    public void customSort(){
        HashMap<LocalDate, ObservableList<Ticket>> map = new HashMap<>(); // Using hashMap to get unique dates
        for(var i: tickets){
            if(!map.containsKey(i.getExpireDate())){
                map.put(i.getExpireDate(), FXCollections.observableArrayList());
            }
            map.get(i.getExpireDate()).add(i);
        }

        tickets.clear();
        ArrayList<LocalDate> tmp = new ArrayList<>();
        for(var i: map.keySet()){ //Adding keys to sort them later
            tmp.add(i);
        }
        tmp.sort(LocalDate::compareTo);
        for(var i: tmp){
            MergeSort<Ticket> mergeSort = new MergeSort<>(new Comparator<Ticket>() { //Creating sorting object
                @Override
                public int compare(Ticket o1, Ticket o2) {
                    return Integer.compare(o1.getSurname().compareTo(o2.getSurname()), 0);
                }
            });
            map.replace(i, mergeSort.sort(map.get(i))); //Sorting and replacing data in hashMap
            tickets.addAll(map.get(i)); //Adding all students to final list
        }
    }
    public void clear(){
        tickets.clear();
    }
}
