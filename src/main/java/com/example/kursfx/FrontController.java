package com.example.kursfx;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ticket;
import model.TicketType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class FrontController implements Initializable {
    private static final double animationTime = 0.3;
    Controller controller;
    @FXML
    private BorderPane formPanel;
    @FXML
    private Button addButton;
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
    private MenuBar menuBar;
    @FXML
    private AnchorPane scene;

    private boolean isHiden;

    private double tableWidth;

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
        isHiden = false;
        ticketsTable.setFocusTraversable(false);
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
        if(!isHiden){
            tableWidth = ticketsTable.getWidth();
            Timeline timeline = new Timeline();
            AnchorPane.setRightAnchor(ticketsTable, null);

            KeyFrame key1 = new KeyFrame(Duration.ZERO,
                    new KeyValue(formPanel.translateXProperty(), 0),
                    new KeyValue(ticketsTable.prefWidthProperty(), tableWidth),
                    new KeyValue(ticketsTable.translateXProperty(), 0));

            KeyFrame key2 = new KeyFrame(Duration.seconds(animationTime),
                    new KeyValue(formPanel.translateXProperty(), -formPanel.getPrefWidth()-1),
                    new KeyValue(ticketsTable.translateXProperty(), -180),
                    new KeyValue(ticketsTable.prefWidthProperty(), tableWidth+180));

            timeline.getKeyFrames().addAll(key1, key2);
            //timeline.setAutoReverse(true);
            //timeline.setCycleCount(2);
            ArrayList<Integer> some = new ArrayList<Integer>();
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    AnchorPane.setRightAnchor(ticketsTable, -180.0);
                }
            });
            timeline.play();
        }else{
            tableWidth = ticketsTable.getWidth()-180;
            Timeline timeline = new Timeline();
            AnchorPane.setRightAnchor(ticketsTable, null);
            KeyFrame key1 = new KeyFrame(Duration.seconds(animationTime),
                    new KeyValue(formPanel.translateXProperty(), 0),
                    new KeyValue(ticketsTable.prefWidthProperty(), tableWidth),
                    new KeyValue(ticketsTable.translateXProperty(), 0));
            KeyFrame key2 = new KeyFrame(Duration.ZERO,
                    new KeyValue(formPanel.translateXProperty(), -formPanel.getPrefWidth()-1),
                    new KeyValue(ticketsTable.translateXProperty(), -180),
                    new KeyValue(ticketsTable.prefWidthProperty(), tableWidth+180));

            timeline.getKeyFrames().addAll(key2, key1);
            //timeline.setAutoReverse(true);
            //timeline.setCycleCount(2);
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    AnchorPane.setRightAnchor(ticketsTable, 0.0);
                }
            });
            timeline.play();
        }
        isHiden = !isHiden;
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
    @FXML
    private void onCloseClicked(){
        javafx.application.Platform.exit();
    }
    @FXML
    private void onFullscreenClicked(){
        Stage stage = (Stage) formPanel.getScene().getWindow();
        if(stage.isFullScreen()){
            stage.setFullScreen(false);
        }else{
            stage.setFullScreen(true);
        }
    }

    @FXML
    private void onHideWindowClicked(){
        Stage stage = (Stage) formPanel.getScene().getWindow();
        stage.setIconified(true);
    }
}