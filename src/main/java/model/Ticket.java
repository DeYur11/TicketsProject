package model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
/**
 * @author Yurii Debeliak
 *
 *
 *
 * **/
public class Ticket {
    private int id;
    private static int amount = 1;
    private final String surname;
    private final String name;
    private final String phoneNumber;
    private final TicketType type;
    private LocalDate date;
    public Ticket(){
        this.surname = "Default";
        this.name = "Default";
        this.phoneNumber = "+380 00 000 00 00";
        this.type = TicketType.standart;
        this.date = LocalDate.of(2005, 5, 11);

        this.id = amount;
        amount++;
    }

    public Ticket(String surname,
                  String name,
                  String phoneNumber,
                  TicketType type, LocalDate date) {
        this.surname = surname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.date = date;
        this.id = amount;
        amount++;
    }

    public Ticket(@NotNull Ticket toCopy){
        this.name = toCopy.name;
        this.surname = toCopy.surname;
        this.type = toCopy.type;
        this.phoneNumber = toCopy.phoneNumber;
        this.date = toCopy.date;
        this.id = toCopy.id;
        amount++;
    }

    public int getId() {
        return id;
    }

    public static int getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public TicketType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", number='" + phoneNumber + '\'' +
                ", type=" + type +
                ", date=" + date +
                '}';
    }
    
}
