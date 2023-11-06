package com.example.kursfx;

import controller.Controller;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ticket;
import model.TicketType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FrontController implements Initializable {
    Controller controller;
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
    private final FileChooser fileChooser = new FileChooser();

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
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("TICKET", "*.tic");
        fileChooser.getExtensionFilters().add(ext);

        surnameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("surname"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("phoneNumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, TicketType>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDate>("date"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("id"));

        typeTicketComboBox.getItems().setAll(TicketType.values());

        this.controller = new Controller();

        this.ticketsTable.setItems((ObservableList<Ticket>) controller.getData());
    }
    @FXML
    public void hideForm(){
        TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), form);
        slideIn.setToX(0);


        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), form);
        slideOut.setToX(-form.getWidth());
        if(form.getTranslateX()==0){

            slideOut.setOnFinished(actionEvent -> form.setTranslateX(-form.getWidth()));
            slideOut.play();

        }else{
            slideIn.setOnFinished(actionEvent -> form.setTranslateX(0));
            slideIn.play();
        }
       // form.setManaged(!form.isManaged());
       // form.setVisible(!form.isVisible());
    }
    @FXML
    public void deleteRow(ActionEvent e){
        System.out.println(ticketsTable.getSelectionModel().getSelectedIndex());
        controller.deleteTicket(ticketsTable.getSelectionModel().getSelectedIndex());
    }
    @FXML
    public void saveToFile(){
        try {
            File file = fileChooser.showSaveDialog(new Stage());
            if(file != null){
                controller.saveToFile(file);
            }else{
                controller.saveToFile(file);
            }
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Помилка при збереженні даних у файл. Спробуйте ще раз!");
            alert.show();
        }

    }
    @FXML
    public void loadFromFile(){
        Alert alert;
        if(!controller.getData().isEmpty()){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setContentText("Якщо ви завантажите дані з файлу, всі ваші незбережені дані у цьому сеансі буде втрачено." +
                    "Ви бажаєте продовжити?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                loadData();
            }
        }else{
            loadData();
        }
    }

    private void loadData(){
        File file = fileChooser.showOpenDialog(new Stage()).getAbsoluteFile();
        try {
            controller.loadFromFile(file);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Помилка при завантажені даних з файлу. Спробуйте ще раз!");
            alert.show();
        }
    }

}