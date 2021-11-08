package com.example.project_java2;

import Data_Access_Object.CustomerDAO;
import Data_Access_Object.FilmDAO;
import Data_Access_Object.RentalInfoDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    @FXML
    private Button search;

    @FXML
    private TextField textArea;

    @FXML
    private Button viewAll;

    @FXML
    private TableColumn<Customer, Integer> idCol;
    @FXML
    private TableColumn<Customer, String> firstNameCol;
    @FXML
    private TableColumn<Customer, String> lastNameCol;
    @FXML
    private TableColumn<Customer, String> eMailCol;
    @FXML
    private TableColumn<Customer, String> memberShipCol;

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableView<Film> filmTableView;
    @FXML
    private TableView<RentalInfo> rentalInfoTableView;


    @FXML
    private TableColumn<Film, Integer> idFCol;
    @FXML
    private TableColumn<Film, String> titleCol;
    @FXML
    private TableColumn<Film, String> genreCol;
    @FXML
    private TableColumn<Film, String> desCol;
    @FXML
    private TableColumn<Film, LocalDate> releaseDateCol;
    @FXML
    private TableColumn<Film, Integer> availabilityCol;
    @FXML
    private TableColumn<Film, String> castCol;

    @FXML
    private TableColumn<RentalInfo, Integer> customerCol;
    @FXML
    private TableColumn<RentalInfo, String> titleRCol;
    @FXML
    private TableColumn<RentalInfo, LocalDate> borrowDateCol;
    @FXML
    private TableColumn<RentalInfo, LocalDate> returnDateCol;
    @FXML
    private TableColumn<RentalInfo, Integer> qtyCol;
    @FXML
    private TableColumn<RentalInfo, Integer> availableQtyCol;

    private FilmDAO myFilm;
    private CustomerDAO myCustomer;
    private RentalInfoDAO myRental;
    private Dialog<ArrayList<String>> newCustomer;
    private Dialog<ArrayList<String>> newFilm;
    private Dialog<ArrayList<String>> newRental;

    @FXML
    void initialize(){
        System.out.println("init");
        customer_table_setup();
        film_table_setup();
        rentalInfo_table_setup();
    }

    private void rentalInfo_table_setup() {
        myRental = new RentalInfoList();
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        titleRCol.setCellValueFactory(new PropertyValueFactory<>("filmTitle"));
        borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("last_visit"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        availableQtyCol.setCellValueFactory(new PropertyValueFactory<>("availableQty"));

        // create add-rental-dialog
        newRental = new Dialog<>();
        newRental.setTitle("Rental");
        newRental.setHeaderText("Please fill out all following input fields");
        // set custom add button and add buttons (add, cancel) to dialog's pane
        ButtonType addButtonType = new ButtonType("Rent", ButtonBar.ButtonData.OK_DONE);
        newRental.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        // create text inputs
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField customerId = new TextField();
        customerId.setPromptText("Customer Id");
        TextField filmId = new TextField();
        filmId.setPromptText("Film Id");
        DatePicker borrow_date = new DatePicker();
        borrow_date.getValue();
        grid.add(new Label("Customer Id:"), 0, 0);
        grid.add(customerId, 1, 0);
        grid.add(new Label("Film Id:"), 0, 1);
        grid.add(filmId, 1, 1);
        grid.add(new Label("Borrow Date:"), 0, 2);
        grid.add(borrow_date, 1, 2);
        newRental.getDialogPane().setContent(grid);
        // set focus on first text field
        Platform.runLater(customerId::requestFocus);
        // convert result to ArrayList when add button was clicked
        newRental.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                ArrayList<String> result = new ArrayList<>();
                result.add(customerId.getText());
                result.add(filmId.getText());
                result.add(borrow_date.getValue().toString());
                return result;
            }
            return null;
        });

        rentalInfoTableReload();

        customerCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        titleRCol.setCellFactory(TextFieldTableCell.forTableColumn());
        borrowDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        returnDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        qtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        availableQtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        contextMenu.getItems().add(deleteItem);

        deleteItem.setOnAction(event -> {
            RentalInfo rentalInfo = rentalInfoTableView.getSelectionModel().getSelectedItem();
            myRental.del(rentalInfo.getCustomerId());
            rentalInfoTableReload();
        });

        rentalInfoTableView.setContextMenu(contextMenu);
        rentalInfoTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                    Optional<ArrayList<String>> result = newRental.showAndWait();
                    result.ifPresent(rentalData -> {
                        try {
                            myRental.addNew(new RentalInfo(Integer.parseInt(rentalData.get(0)), Integer.parseInt(rentalData.get(1)), LocalDate.parse(rentalData.get(2))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        customerTableReload();
                    });
                }
            }
        });
    }

    private void customer_table_setup() {
        myCustomer = new Customer_DAO();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        eMailCol.setCellValueFactory(new PropertyValueFactory<>("eMail"));
        memberShipCol.setCellValueFactory(new PropertyValueFactory<>("membershipStatus"));

        // create add-customer-dialog
        newCustomer = new Dialog<>();
        newCustomer.setTitle("Add new customer");
        newCustomer.setHeaderText("Please fill out all following input fields to create a new customer");
        // set custom add button and add buttons (add, cancel) to dialog's pane
        ButtonType addButtonType = new ButtonType("Add customer", ButtonBar.ButtonData.OK_DONE);
        newCustomer.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        // create text inputs
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField firstName = new TextField();
        firstName.setPromptText("First name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last name");
        TextField eMail = new TextField();
        eMail.setPromptText("E-Mail");
        ChoiceBox memberShipStatus = new ChoiceBox<>();
        memberShipStatus.getItems().setAll("Bronze", "Silver", "Gold");
        grid.add(new Label("First name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("E-Mail"), 0, 2);
        grid.add(eMail, 1, 2);
        grid.add(new Label("Membership status:"), 0, 3);
        grid.add(memberShipStatus, 1, 3);
        newCustomer.getDialogPane().setContent(grid);
        // set focus on first text field
        Platform.runLater(firstName::requestFocus);
        // convert result to ArrayList when add button was clicked
        newCustomer.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                ArrayList<String> result = new ArrayList<>();
                result.add(firstName.getText());
                result.add(lastName.getText());
                result.add(eMail.getText());
                result.add((String) memberShipStatus.getValue());
                return result;
            }
            return null;
        });

        customerTableReload();

        idCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        eMailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        memberShipCol.setCellFactory(TextFieldTableCell.forTableColumn());

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        contextMenu.getItems().add(deleteItem);

        deleteItem.setOnAction(event -> {
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            myCustomer.del(customer.getId());
            customerTableReload();
        });

        customerTableView.setContextMenu(contextMenu);
        customerTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                    Optional<ArrayList<String>> result = newCustomer.showAndWait();
                    result.ifPresent(customerData -> {
                        myCustomer.addNew(new Customer(customerData.get(0), customerData.get(1), customerData.get(2), customerData.get(3)));
                        customerTableReload();
                    });
                }
            }
        });
    }

    private void film_table_setup() {
        myFilm = new Film_DAO();
        idFCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        castCol.setCellValueFactory(new PropertyValueFactory<>("cast"));


        // create add-film-dialog
        newFilm = new Dialog<>();
        newFilm.setTitle("Add new film");
        newFilm.setHeaderText("Please fill out all following input fields to create a new film");
        // set film add button and add buttons (add, cancel) to dialog's pane
        ButtonType addButtonType = new ButtonType("Add film", ButtonBar.ButtonData.OK_DONE);
        newFilm.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        // create text inputs
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField title = new TextField();
        title.setPromptText("Title");
        TextField genre = new TextField();
        genre.setPromptText("Genre Id");
        TextField description = new TextField();
        description.setPromptText("Description");
        DatePicker release_date = new DatePicker();
        release_date.getValue();
        TextField availability = new TextField();
        availability.setPromptText("Availability");
        grid.add(new Label("Title:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("Genre:"), 0, 1);
        grid.add(genre, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(description, 1, 2);
        grid.add(new Label("Release Date:"), 0, 3);
        grid.add(release_date, 1, 3);
        grid.add(new Label("Availability"), 0,4);
        grid.add(availability, 1, 4);
        newFilm.getDialogPane().setContent(grid);
        // set focus on first text field
        Platform.runLater(title::requestFocus);
        // convert result to ArrayList when add button was clicked
        newFilm.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                ArrayList<String> result = new ArrayList<>();
                result.add(title.getText());
                result.add(genre.getText());
                result.add(description.getText());
                result.add(release_date.getValue().toString());
                result.add(availability.getText());
                return result;
            }
            return null;
        });

        filmTableReload();

        idFCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        genreCol.setCellFactory(TextFieldTableCell.forTableColumn());
        desCol.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        availabilityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        castCol.setCellFactory(TextFieldTableCell.forTableColumn());

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        contextMenu.getItems().add(deleteItem);

        deleteItem.setOnAction(event -> {
            Film film = filmTableView.getSelectionModel().getSelectedItem();
            myFilm.del(film.getId());
            filmTableReload();
        });

        filmTableView.setContextMenu(contextMenu);
        filmTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {

                    Alert genreInfo = new Alert(Alert.AlertType.INFORMATION);
                    genreInfo.setTitle("Genre Id");
                    genreInfo.setHeaderText("Genre Id");
                    genreInfo.setContentText("1-Comedy 2-Action 3-Thriller" + "\n" + "4-Fantasy 5-Drama 6-Horror" +"\n"+ "7-SciFi 8-Abenteuer 9-Musical");
                    genreInfo.show();

                    Optional<ArrayList<String>> result = newFilm.showAndWait();
                    result.ifPresent(filmData -> {
                        myFilm.addNew(new Film(Integer.parseInt(filmData.get(1)), filmData.get(0), filmData.get(2), LocalDate.parse(filmData.get(3)), Integer.parseInt(filmData.get(4))));
                        filmTableReload();
                    });
                }
            }
        });
    }

    private void customerTableReload(){
        ObservableList<Customer> cusList = FXCollections.observableList(myCustomer.showAll());
        customerTableView.setItems(cusList); // reload customerTable
    }

    private void filmTableReload() {
        ObservableList<Film> filmList = FXCollections.observableList(myFilm.showAll());
        filmTableView.setItems(filmList); // reload filmTable
    }

    private void rentalInfoTableReload() {
        ObservableList<RentalInfo> rentalInfoList = FXCollections.observableList(myRental.showAll());
        rentalInfoTableView.setItems(rentalInfoList); // reload rentalInfoTable
    }

    @FXML
    void viewAll(ActionEvent event){
        customerTableReload();
        filmTableReload();
        rentalInfoTableReload();
    }

    @FXML
    void search(ActionEvent event){
        // can be imporved
        try{
            ObservableList<Customer> cusData = FXCollections.observableArrayList(myCustomer.findAllWithStatus(Membership.valueOf(textArea.getText())));
            customerTableView.setItems(cusData);

        } catch(IllegalArgumentException e) {
            ObservableList<Customer> cusData = FXCollections.observableArrayList(myCustomer.lookUpWithLastName(textArea.getText()));
            customerTableView.setItems(cusData);
        }

        try{
            ObservableList<Film> filmData = FXCollections.observableArrayList(myFilm.findAllWithYear(Integer.parseInt(textArea.getText())));
            filmTableView.setItems(filmData);


        } catch(NumberFormatException e){
            ObservableList<Film> filmData = FXCollections.observableArrayList(myFilm.lookUpWithLastName(textArea.getText()));
            filmTableView.setItems(filmData);
        }

        try {
            ObservableList<RentalInfo> rentalInfosData = FXCollections.observableArrayList(myRental.lookUpWithCustomerId(Integer.parseInt(textArea.getText())));
            rentalInfoTableView.setItems(rentalInfosData);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onUpDate(TableColumn.CellEditEvent<Customer, ?> event){
        Customer customer = event.getRowValue();
        Object newValue = event.getNewValue();

        Object dbfield = event.getTableColumn().getId();
        myCustomer.upDatebyDoubleClicking(customer.getId(),newValue,dbfield);
        customerTableReload();
    }

    @FXML
    public void onUpDateF(TableColumn.CellEditEvent<Film, ?> event){
        Film film = event.getRowValue();
        Object newValue = event.getNewValue();

        Object dbfield = event.getTableColumn().getId();
        myFilm.upDatebyDoubleClicking(film.getId(),newValue,dbfield);
        filmTableReload();
    }

    @FXML
    public void onUpDateR(TableColumn.CellEditEvent<RentalInfo, ?> event){
        RentalInfo rentalInfo = event.getRowValue();
        Object newValue = event.getNewValue();

        Object dbfield = event.getTableColumn().getId();
        myRental.upDatebyDoubleClicking(rentalInfo.getId(),newValue,dbfield);
        rentalInfoTableReload();
    }

}
