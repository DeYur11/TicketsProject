package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class DatabaseMain {
    public static void main(String[] args){
        Database db = new Database();
        db.addPeople(new Ticket("Debeliak", "Yurii", "38050", TicketType.standard, LocalDate.now()));
        try {
            db.saveToFile(new File("./Some.tic"));
        }catch (IOException e){
            System.out.println(e);
            System.out.println("Error");
        }

    }
}
