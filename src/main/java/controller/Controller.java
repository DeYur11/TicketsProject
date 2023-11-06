package controller;

import javafx.scene.control.Alert;
import model.Database;
import model.Ticket;

import java.io.File;
import java.io.IOException;
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

    public void deleteTicket(int index){
        try{
            database.removeTicket(index);
        }catch (IndexOutOfBoundsException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Помилка при видалені даних. Спробуйте ще раз!");
            alert.show();
        }

    }
    public void saveToFile(File file) throws IOException {
        database.saveToFile(file);
    }
    public void loadFromFile(File file) throws IOException {
        database.loadFromFile(file);
    }

}
