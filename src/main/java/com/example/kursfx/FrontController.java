package com.example.kursfx;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Ticket;
import model.TicketType;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FrontController implements Initializable {
    Controller controller;

    ObservableList<Ticket> viewedTickets = FXCollections.observableArrayList();
    @FXML
    private Button addButton;
    @FXML
    private VBox form;

    @FXML
    private TableColumn<Ticket, LocalDate> dateColumn;

    @FXML
    private DatePicker dateSelector;

    @FXML
    private TableColumn<Ticket, Integer> idColumn;

    @FXML
    private TableColumn<Ticket, String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<Ticket, String> numberColumn;

    @FXML
    private TextField phoneField;

    @FXML
    private TableColumn<Ticket, String> surnameColumn;

    @FXML
    private TextField surnameField;

    @FXML
    private TableView<Ticket> ticketsTable;

    @FXML
    private TableColumn<Ticket, TicketType> typeColumn;

    @FXML
    private ComboBox<TicketType> typeTicketComboBox;


    @FXML
    void onAddButtonPressed(ActionEvent event) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String phoneNumber = phoneField.getText();
        LocalDate date = dateSelector.getValue();
        TicketType ticketType = typeTicketComboBox.getValue();

        controller.addTicket(new Ticket(surname, name, phoneNumber, ticketType, date));
        ticketsTable.refresh();
    }
    private void setData(List<Ticket> tickets){
        ticketsTable.setItems((ObservableList<Ticket>) tickets);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("surname"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("phoneNumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, TicketType>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDate>("date"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("id"));

        typeTicketComboBox.getItems().setAll(TicketType.values());

        this.controller = new Controller();

        this.ticketsTable.setItems((ObservableList<Ticket>) controller.getData());
    }
    @FXML
    public void hideForm(){
        form.setManaged(!form.isManaged());
        form.setVisible(!form.isVisible());
    }
}