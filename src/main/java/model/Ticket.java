package model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * @author Yurii Debeliak
 * Class for tickets
 * **/
public class Ticket implements Serializable {
    private int id;
    private static int amount = 1;
    private final String surname;
    private final String name;
    private final String phoneNumber;
    private final TicketType type;
    private final LocalDate expireDate;
    public Ticket(){
        this.surname = "Default";
        this.name = "Default";
        this.phoneNumber = "+380 00 000 00 00";
        this.type = TicketType.standard;
        this.expireDate = LocalDate.of(2005, 5, 11);

        this.id = amount;
        amount++;
    }

    public static void setAmount(int amount) {
        Ticket.amount = amount;
    }

    public Ticket(String surname,
                  String name,
                  String phoneNumber,
                  TicketType type, LocalDate expireDate) {
        this.surname = surname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.expireDate = expireDate;
        this.id = amount;
        amount++;
    }

    public Ticket(@NotNull Ticket toCopy){
        this.name = toCopy.name;
        this.surname = toCopy.surname;
        this.type = toCopy.type;
        this.phoneNumber = toCopy.phoneNumber;
        this.expireDate = toCopy.expireDate;
        this.id = toCopy.id;
        amount++;
    }

    public int getId() {
        return id;
    }

    public static int getAmount() {
        return amount;
    }

    public LocalDate getExpireDate() {
        return expireDate;
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
    public String toString() { // Method to represent ticket as a String
        return "Ticket{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", number='" + phoneNumber + '\'' +
                ", type=" + type.toString() +
                ", date=" + expireDate.toString() +
                '}';
    }
    
}
