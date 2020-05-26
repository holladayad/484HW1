/*
 * Author: Amber Holladay
 * Date: May 25, 2020
 * Capstone HW 1
 * Purpose Animal Rescue Information System
 */
package CIS484.HW1;

// FX
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// ArrayLists & Database Connection
import java.sql.*;
import java.time.LocalDate;
import oracle.jdbc.pool.*;
import java.util.*;

// ComboBoxes
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


import javafx.scene.control.*;

//GridPane
import javafx.scene.layout.GridPane;


public class RescueApplication extends Application 
{
    // Storing data into memory:
    ArrayList<Animal> animalData = new ArrayList<>();
    ArrayList<Worker> workerData = new ArrayList<>();
    ArrayList<Worker> supervisorData = new ArrayList<>();
    ArrayList<Worker> scheduleData = new ArrayList<>();
    
    // Creating TabPane and Tabs
    TabPane tbPane = new TabPane();
    Tab tab1 = new Tab("Animals:");
    Tab tab2 = new Tab("Workers:");
    Tab tab3 = new Tab("Supervisors:");
    Tab tab4 = new Tab("Schedules:");
    Tab tab5 = new Tab("Appointments:");
    
    // GridPanes for organizing each Tab
    GridPane overallPane = new GridPane();
    GridPane animalPane = new GridPane();
    GridPane workerPane = new GridPane();
    GridPane supervisorPane = new GridPane();
    GridPane schedulePane = new GridPane();
    GridPane appointmentPane = new GridPane();
    
    // Add Worker/Supervisor
    public Label lblNameWorker = new Label("Name:");
    public TextField txtWorkerName = new TextField();
    
    public Label lblWorkerNum = new Label("Phone Number:");
    public TextField txtWorkerNum = new TextField();
    
    public Label lblWorkStatus = new Label("Work Status:");
    public TextField txtWorkStatus = new TextField();
    
    public Label lblHourlyPay = new Label("Hourly Pay:");
    public TextField txtHourlyPay = new TextField();
    
    public CheckBox chkNewSupervisor = new CheckBox("New Supervisor?");
    // if checked add to the supervisor list
    // if unchecked add to workers
    
    public Button btnAddWorker = new Button("Add Worker ->");
    
    
    // Add Animal
    public Label lblAnimalName = new Label("Name:");
    public TextField txtAnimalName = new TextField();
    
    public Label lblBreed = new Label("Breed:");
    public TextField txtBreed = new TextField();
    
    public Label lblAnimalAge = new Label("Age:");
    public TextField txtAnimalAge = new TextField();
    
    public Label lblAnimalGender = new Label("Gender:");
    public TextField txtAnimalGender = new TextField();
    
    public Label lblIntakeDate = new Label("Intake Date:");
    // Add a calendar for this.

    public Button btnAddAnimal = new Button("Add Animal ->");
    
    
    // Add Appointments
    public Label lblAnimalAppoint = new Label("Animal Name:");
    public TextField txtAnimalAppoint = new TextField();
    
    public Label lblTreatment = new Label("Treatment:");
    public TextField txtTreatment = new TextField();
    
    public Label lblVetName = new Label("Veterinarian Name: Dr.");
    public TextField txtVetName = new TextField();
    
    public Label lblAppointDate = new Label("Appointment Date:");
    // Add a calendar for this.

    public Button btnAddAppointment = new Button("Add Appointment ->");
    
    
    // Create ComboBoxes
    String gender[] = {"Male", "Female"};
    ComboBox cmboGender = new ComboBox(FXCollections.observableArrayList(gender));
    
    TableView<Animal> animalTable;
    ObservableList<Animal> animalTableData;
    
    TableView<Worker> workerTable;
    ObservableList<Worker> tableData;
    
    MenuBar menuBar = new MenuBar();
    
    
    // Database Connection
    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);
        tab5.setClosable(false);
        
        tab1.setContent(animalPane);
        tab2.setContent(workerPane);
        tab3.setContent(supervisorPane);
        tab4.setContent(schedulePane);
        tab5.setContent(appointmentPane);
        
        tbPane.getTabs().add(tab1);
        tbPane.getTabs().add(tab2);
        tbPane.getTabs().add(tab3);
        tbPane.getTabs().add(tab4);
        tbPane.getTabs().add(tab5);
        
        animalPane.setAlignment(Pos.CENTER);
        workerPane.setAlignment(Pos.CENTER);
        supervisorPane.setAlignment(Pos.CENTER);
        schedulePane.setAlignment(Pos.CENTER);
        appointmentPane.setAlignment(Pos.CENTER);
        
        overallPane.setAlignment(Pos.CENTER);
        overallPane.add(menuBar, 0, 0);
        overallPane.add(tbPane, 0, 1);
        
        
        
        
        // create button for add new animal
        StackPane primary = new StackPane();
        Button addAnimal = new Button("Add Animal");
        primary.getChildren().add(addAnimal);
        
        
        //animalPane.add(lblAnimalName, 0, 0);
        
        // set the Scene
        Scene scene = new Scene(primary, 500, 500);
        //Scene primaryScene = new Scene(overallPane,500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Animal Rescue Form");
        primaryStage.show();
        
        DatePicker pickerIntakeDate = new DatePicker();
        
        primary.getChildren().add(tbPane);
        
        addAnimal.setOnAction(e ->{
            GridPane addAnimalPane = new GridPane();
            
            addAnimalPane.add(lblAnimalName, 0, 1);
            addAnimalPane.add(txtAnimalName, 1, 1);
            
            addAnimalPane.add(lblBreed, 0, 2);
            addAnimalPane.add(txtBreed, 1, 2);
            
            addAnimalPane.add(lblAnimalAge, 0, 3);
            addAnimalPane.add(txtAnimalAge, 1, 3);
            
            addAnimalPane.add(lblAnimalGender, 0, 4);
            addAnimalPane.add(cmboGender, 1, 4);
            
            addAnimalPane.add(lblIntakeDate, 0, 5);
            addAnimalPane.add(pickerIntakeDate, 1, 5);
            
            addAnimalPane.add(addAnimal, 1, 6);
            
            
//            btnAddAnimal.setOnAction(a -> {
//            int age = Integer.valueOf(txtAnimalAge.getText());
//            animalData.add(new Animal(txtAnimalName.getText(), txtBreed.getText(),
//                                    age, cmboGender.getValue().toString(), 
//                                    pickerIntakeDate.getValue().toString()));
//            
//            System.out.println(animalData.get(animalData.size()-1).toString());
//            
//            tableData.clear();
//            for (Animal s: animalData)
//            {
//                animalData.add(s);
//            }
//            
//            txtAnimalName.clear();
//            txtBreed.clear();
//            txtAnimalAge.clear();
//            
//            });

            
           
            
            Scene secondScene = new Scene(addAnimalPane, 500, 500);
            Stage secondStage = new Stage();
            secondStage.setScene(secondScene);
            secondStage.setTitle("Add new Animal");
            secondStage.show();
            //secondStage.close(); // closes the previous form
        });
        
        //pickerIntakeDate.setShowWeekNumbers(true);
        tbPane.setMinWidth(scene.getWidth());
        tbPane.setMinHeight(scene.getHeight());
        
        animalTable = new TableView<>();
        animalTableData = FXCollections.observableArrayList(animalData);
        animalTable.setItems(animalTableData);
        
        TableColumn tcAnimalID = new TableColumn("ID");
        TableColumn tcAnimalName = new TableColumn("Name");
        TableColumn tcBreed = new TableColumn("Breed");
        TableColumn tcAge = new TableColumn("Age");
        TableColumn tcGender = new TableColumn("Gender");
        TableColumn tcIntakeDate = new TableColumn("Intake Date");
        
        animalTable.setMinWidth(scene.getWidth());
        
        
        
    }
   
    
    
    // Main
    public static void main(String[] args) 
    {
        launch(args);
    }

    // SQL commands
//    public void sendSQL(String sqlQuery)
//    {
//        String url = "jdbc:oracle:thin:@localhost:1521:XE";
//        String user = "javauser";
//        String pass = "javapass";
//        
//        OracleDataSource ds;
//        
//        try
//        {          
//            //create the connection using the object from Oracle
//            ds = new OracleDataSource();
//            
//            //set the connection url in the object
//            ds.setURL(url);
//            
//            //attempt to connect with specified username and login, default as "javauser" and "javapass"
//            dbConn = ds.getConnection(user, pass);
//            
//            //handling the results
//            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            
//            //send the query to oracle
//            dbResults = commStmt.executeQuery(sqlQuery);
//            
//        }
//        
//        catch(SQLException e)
//        {           
//            System.out.println(e.toString());           
//        }       
//    }
    
    
}
