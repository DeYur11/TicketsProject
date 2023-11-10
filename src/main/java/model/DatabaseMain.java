package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MergeSort;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;

public class DatabaseMain {
    public static void main(String[] args){
        Database db = new Database();
        MergeSort<Ticket> mergeSort = new MergeSort<>(new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                if(o1.getDate().isAfter(o2.getDate())){
                    return 1;
                }else if (o1.equals(o2)){
                    return 0;
                }else{
                    return -1;
                }
            }
        });

        ObservableList<Ticket> some = FXCollections.observableArrayList();
        some.add(new Ticket("BYurii", "Debeliak", "_#003",TicketType.standard, LocalDate.of(2000, Month.APRIL, 23)));
        some.add(new Ticket("CYurii", "Debeliak", "_#003",TicketType.standard, LocalDate.of(2001, Month.APRIL, 23)));
        some.add(new Ticket("AYurii", "Debeliak", "_#003",TicketType.standard, LocalDate.of(1999, Month.APRIL, 23)));
        some = (ObservableList<Ticket>) mergeSort.sort(some);
        for(var i: some){
            System.out.println(i);
        }



    }
}
