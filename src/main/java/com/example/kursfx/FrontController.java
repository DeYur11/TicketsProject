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
import utils.MergeSort;
import utils.Validate;

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
    private RadioMenuItem formMenu;
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
    private ObservableList<Ticket> shownList;
    /**
     * @author Yurii Debeliak
     * Method to react to add Ticket
     * */
    @FXML
    void onAddButtonPressed(ActionEvent event) {
        try {
            validateFields();
        }catch (Exception e){
            return;
        }
        String name = nameField.getText();
        String surname = surnameField.getText();
        String phoneNumber = phoneField.getText();
        LocalDate date = dateSelector.getValue();
        TicketType ticketType = typeTicketComboBox.getValue();



        controller.addTicket(new Ticket(surname, name, phoneNumber, ticketType, date));
        ticketsTable.refresh();
    }
    /**
     * Method to validate fields, as phone number.
     * Checks if all field are filled.
     * */
    private void validateFields() throws Exception {
        if(dateSelector.getValue() != null &&
                nameField.getText() != null &&
                surnameField.getText() != null &&
                phoneField.getText() != null &&
                typeTicketComboBox.getValue() != null
        ){
            if(Validate.validateNumber(phoneField.getText())){

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка!");
                alert.setContentText("Неправильна форма номеру телефону\n" +
                        "Можлива форма: +380000000000");
                alert.setHeaderText("Помилка при введені даних");
                alert.showAndWait();
                throw new Exception();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Заповність усі поля!");
            alert.setHeaderText("Помилка при введені даних");
            alert.showAndWait();
            throw new Exception();
        }

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
        this.shownList = (ObservableList<Ticket>) controller.getData();
        this.ticketsTable.setItems(shownList);

        formMenu.setSelected(true);
    }
    /**
     * Method to hide and show form using animation
     * */
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

            ArrayList<Integer> some = new ArrayList<Integer>();
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    AnchorPane.setRightAnchor(ticketsTable, -180.0); // Width of panel
                }
            });
            timeline.play();
        }else{
            tableWidth = ticketsTable.getWidth()-180; // Width of form
            Timeline timeline = new Timeline();
            AnchorPane.setRightAnchor(ticketsTable, null);
            KeyFrame key1 = new KeyFrame(Duration.seconds(animationTime), // Seting starting frame
                    new KeyValue(formPanel.translateXProperty(), 0),
                    new KeyValue(ticketsTable.prefWidthProperty(), tableWidth),
                    new KeyValue(ticketsTable.translateXProperty(), 0));
            KeyFrame key2 = new KeyFrame(Duration.ZERO, // Setting end frame
                    new KeyValue(formPanel.translateXProperty(), -formPanel.getPrefWidth()-1),
                    new KeyValue(ticketsTable.translateXProperty(), -180),
                    new KeyValue(ticketsTable.prefWidthProperty(), tableWidth+180));

            timeline.getKeyFrames().addAll(key2, key1);

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
            e.printStackTrace();
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
    @FXML
    private void onFindByPhone(){
        if(ticketsTable.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Пуста таблиця");
            alert.setHeaderText("Помилка");
            alert.showAndWait();
            return;
        }

        TextInputDialog textInputDialog = new TextInputDialog();
        DialogPane pane = textInputDialog.getDialogPane();
        pane.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());

        Optional<String> result = textInputDialog.showAndWait();
        TextField input = textInputDialog.getEditor();
        if(input.getText() != null && input.getText().length() != 0){
            if(Validate.validateNumber(input.getText())){
                ObservableList<Ticket> tmp = FXCollections.observableArrayList();
                for(var i: controller.getData()){
                    if(i.getPhoneNumber().equals(input.getText())){
                        tmp.add(i);
                        System.out.println(i.getPhoneNumber());
                    }
                    ticketsTable.setItems(tmp);

                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("None entered!");
                alert.setContentText("Please enter valid number");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("None entered!");
            alert.setContentText("Please enter valid number");
            alert.showAndWait();
        }
    }

    @FXML
    private void resetClicked(){
        ticketsTable.setItems(shownList);
    }
    @FXML
    private void findMostFreq(){
        if(ticketsTable.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Пуста таблиця");
            alert.setHeaderText("Помилка");
            alert.showAndWait();
            return;
        }
        HashMap<TicketType, Integer> frequency = new HashMap<>();
        for(var i: TicketType.values()){
            frequency.put(i, 0);
        }
        shownList = (ObservableList<Ticket>) controller.getData();
        Ticket ticket = shownList.get(0);
        for(var i: shownList){
            frequency.replace(i.getType(), frequency.get(i.getType())+1);
        }
        int max = frequency.get(TicketType.values()[0]);
        TicketType maxType = TicketType.values()[0];
        for(var i: TicketType.values()){
            if(max < frequency.get(i)){
                max = frequency.get(i);
                maxType = i;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Most common type");
        alert.setContentText("Most common type:" + maxType);
        alert.showAndWait();
    }
    @FXML
    private void onFindBySurname() {
        if(ticketsTable.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Пуста таблиця");
            alert.setHeaderText("Помилка");
            alert.showAndWait();
            return;
        }


        TextInputDialog textInputDialog = new TextInputDialog();
        DialogPane pane = textInputDialog.getDialogPane();

        Optional<String> result = textInputDialog.showAndWait();
        TextField input = textInputDialog.getEditor();
        if (input.getText() != null && input.getText().length() != 0) {
            ObservableList<Ticket> tmp = FXCollections.observableArrayList();
            for (var i : controller.getData()) {
                if (i.getSurname().equals(input.getText())) {
                    tmp.add(i);
                }
                ticketsTable.setItems(tmp);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("None entered!");
            alert.setContentText("Please enter valid number");
            alert.showAndWait();
        }
    }
    @FXML
    public void onSameName(){
        if(ticketsTable.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка!");
            alert.setContentText("Пуста таблиця");
            alert.setHeaderText("Помилка");
            alert.showAndWait();
            return;
        }


        HashMap<String, ArrayList<Ticket>> map = new HashMap<>(); // Adding to hash map unique names
        for(var i: controller.getData()){
            if(!map.containsKey(i.getName())){
                map.put(i.getName(), new ArrayList<>());
            }
            map.get(i.getName()).add(i);
        }
        ObservableList<Ticket> toDisplay = FXCollections.observableArrayList();
        for(var i: map.keySet()){
            if(map.get(i).size() >= 2){ // Checking if this name has more than 1 tickets.
                toDisplay.addAll(map.get(i));
            }
        }

        this.ticketsTable.setItems(toDisplay);

    }
    @FXML
    private void mergeSortTickets(){
        controller.mergeSort();
    }

    @FXML
    private void clearTable(){
        controller.clear();
    }
}