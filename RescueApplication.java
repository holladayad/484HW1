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
import javafx.application.Platform;

// ComboBoxes
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

//GridPane
import javafx.scene.layout.GridPane;
import javafx.stage.Window;


public class RescueApplication extends Application 
{
    // Storing data into memory:
    ArrayList<Animal> animalData = new ArrayList<>();
    ArrayList<Worker> workerData = new ArrayList<>();
    ArrayList<Worker> superData = new ArrayList<>();
    ArrayList<Worker> supervisorData = new ArrayList<>();
    ArrayList<Worker> scheduleData = new ArrayList<>();
    
    // Creating TabPane and Tabs
    TabPane tbPane = new TabPane();
    Tab tab1 = new Tab("Animals:");
    Tab tab2 = new Tab("Workers:");
    Tab tab3 = new Tab("Supervisors:");
    Tab tab4 = new Tab("Schedules:");
    Tab tab5 = new Tab("Appointments:");
    Tab tab6 = new Tab("Shelter Selection");
    
    // GridPanes for organizing each Tab
    GridPane overallPane = new GridPane();
    GridPane animalPane = new GridPane();
    GridPane workerPane = new GridPane();
    GridPane supervisorPane = new GridPane();
    GridPane schedulePane = new GridPane();
    GridPane appointmentPane = new GridPane();
    GridPane shelterPane = new GridPane();
    
    // Add Worker/Supervisor
    public Label lblWorkerTab = new Label("Workers");
    public Label lblFirstNameWorker = new Label("First Name:");
    public TextField txtWorkerFirstName = new TextField();
    
    public Label lblLastNameWorker = new Label("Last Name:");
    public TextField txtWorkerLastName = new TextField();
    
    public Label lblEmailWorker = new Label("Email:");
    public TextField txtWorkerEmail = new TextField();
    
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
    String workerStatuses[] = {"Worker", "Volunteer", "Supervisor"};
    ComboBox cmboStatus = new ComboBox(FXCollections.observableArrayList(workerStatuses));
    ComboBox cmboGender = new ComboBox(FXCollections.observableArrayList(gender));
    
    TableView<Animal> animalTable;
    ObservableList<Animal> animalTableData;
    
    TableView<Worker> workerTable;
    TableView<Worker> superTable;
    ObservableList<Worker> workerTableData;
    ObservableList<Worker> superTableData;
    
    MenuBar menuBar = new MenuBar();
    
    
    // Database Connection
    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Temp fixtures
        Animal arya = new Animal("Arya", "Corgi", 2, "Female", "2018-06-01");
        animalData.add(arya);
        Worker amber = new Worker("Amber", "Holladay", "1234567", "Worker", 15.0, "nrfflkmas");
        workerData.add(amber);
        Worker tyler = new Worker("Tyler", "Handley", "1234567", "Supervisor", 15.0, "nrfflkmas");
        superData.add(tyler);
        //
        
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);
        tab5.setClosable(false);
        tab6.setClosable(false);
        
        tab1.setContent(animalPane);
        tab2.setContent(workerPane);
        tab3.setContent(supervisorPane);
        tab4.setContent(schedulePane);
        tab5.setContent(appointmentPane);
        tab6.setContent(shelterPane);
        
        tbPane.getTabs().add(tab1);
        tbPane.getTabs().add(tab2);
        tbPane.getTabs().add(tab3);
        tbPane.getTabs().add(tab4);
        tbPane.getTabs().add(tab5);
        tbPane.getTabs().add(tab6);
        
        animalPane.setAlignment(Pos.CENTER);
        workerPane.setAlignment(Pos.CENTER);
        // Doing some testing
        workerPane.add(lblWorkerTab, 0, 0);
        //
        
        supervisorPane.setAlignment(Pos.CENTER);
        schedulePane.setAlignment(Pos.CENTER);
        appointmentPane.setAlignment(Pos.CENTER);
        shelterPane.setAlignment(Pos.CENTER);
        
        overallPane.setAlignment(Pos.CENTER);
        overallPane.add(menuBar, 0, 0);
        overallPane.add(tbPane, 0, 1);
        
        
        
        
        // create button for add new animal
        StackPane primary = new StackPane();
        //        primary.getChildren().add(addAnimal);
        Button addAnimal = new Button("Add Animal");
        animalPane.add(addAnimal, 0, 0);
        Button addWorker = new Button("Add Worker");
        workerPane.add(addWorker, 0, 0);
        Button addSupervisor = new Button("Add Supervisor");
        supervisorPane.add(addSupervisor,0,0);
        Button addShelter = new Button("Add Shelter");
        shelterPane.add(addShelter, 0, 0);
        //animalPane.add(lblAnimalName, 0, 0);
        
        // set the Scene
        Scene scene = new Scene(primary, 500, 500);
        //Scene primaryScene = new Scene(overallPane,500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Animal Rescue Form");
        primaryStage.show();
        tbPane.setMinWidth(scene.getWidth());
        tbPane.setMinHeight(scene.getHeight());
        DatePicker pickerIntakeDate = new DatePicker();
        
        primary.getChildren().add(tbPane);
        // Add Animal and Animal tab stuff
        addAnimal.setOnAction(e ->{
            GridPane addAnimalPane = new GridPane();
            Button addAnimalInner = new Button("Add Animal");
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
            
            addAnimalPane.add(addAnimalInner, 1, 6);
            
            
            addAnimalInner.setOnAction(a -> {
                boolean valid = true;
                
                if (valid)
                {
                    int age = Integer.valueOf(txtAnimalAge.getText());
                    animalData.add(new Animal(txtAnimalName.getText(), txtBreed.getText(),
                                            age, cmboGender.getValue().toString(), 
                                            pickerIntakeDate.getValue().toString()));

        //            System.out.println(animalData.get(animalData.size()-1).toString());
        //            
        //            System.out.println("Howdy");
                    animalTableData.clear();
                    for (Animal s: animalData)
                    {
                        System.out.println(s);
                        animalTableData.add(s);
                    }
        //            System.out.println("Hello");
                    txtAnimalName.clear();
                    txtBreed.clear();
                    txtAnimalAge.clear();
                    Window window = ((Node)(a.getSource())).getScene().getWindow();
                    ((Stage) window).close();
                }
            
            });

            
           
            
            Scene secondScene = new Scene(addAnimalPane, 500, 500);
            Stage secondStage = new Stage();
            secondStage.setScene(secondScene);
            secondStage.setTitle("Add new Animal");
            secondStage.show();
//            secondStage.close(); // closes the previous form
        });
        
        //pickerIntakeDate.setShowWeekNumbers(true);
        
        
        animalTable = new TableView<>();
        animalTableData = FXCollections.observableArrayList(animalData);
        animalTable.setItems(animalTableData);
        
        TableColumn tcAnimalID = new TableColumn("ID");
        TableColumn tcAnimalName = new TableColumn("Name");
        TableColumn tcBreed = new TableColumn("Breed");
        TableColumn tcAge = new TableColumn("Age");
        TableColumn tcGender = new TableColumn("Gender");
        TableColumn tcIntakeDate = new TableColumn("Intake Date");
        
        tcAnimalID.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("animalID"));
        tcAnimalName.setCellValueFactory(new PropertyValueFactory<Animal, String>("name"));
        tcBreed.setCellValueFactory(new PropertyValueFactory<Animal, String>("breed"));
        tcAge.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("age"));
        tcGender.setCellValueFactory(new PropertyValueFactory<Animal, String>("gender"));
        tcIntakeDate.setCellValueFactory(new PropertyValueFactory<Animal, LocalDate>("intakeDate"));
        
        
        
        animalTable.getColumns().addAll(tcAnimalID, tcAnimalName, tcBreed, tcAge, tcGender, tcIntakeDate);
        animalTable.setMinWidth(scene.getWidth());
        animalPane.add(animalTable, 0, 1);
        // End Add animal and animal tab stuff
        
        // Add worker and worker tab stuff
        addWorker.setOnAction(e -> {
            GridPane addWorkerPane = new GridPane();
            Button addWorkerInner = new Button("Add Worker");
            
            addWorkerPane.add(lblWorkerTab, 0, 0);
            addWorkerPane.add(lblFirstNameWorker, 0, 1);
            addWorkerPane.add(txtWorkerFirstName, 1,1);
            addWorkerPane.add(lblLastNameWorker, 0, 2);
            addWorkerPane.add(txtWorkerLastName, 1,2);
            addWorkerPane.add(lblWorkerNum, 0,3);
            addWorkerPane.add(txtWorkerNum, 1,3);
            addWorkerPane.add(lblEmailWorker, 0,4);
            addWorkerPane.add(txtWorkerEmail, 1,4);
            addWorkerPane.add(lblWorkStatus, 0,5);
            addWorkerPane.add(cmboStatus, 1,5);
            addWorkerPane.add(lblHourlyPay, 0,6);
            addWorkerPane.add(txtHourlyPay, 1,6);
            
            addWorkerPane.add(addWorkerInner, 0, 7);
            
            addWorkerInner.setOnAction(a -> {
                //Add validation
                boolean valid = true;
                // For each check if empty
                
                // for email check if has @
                
                // for hourly check if number
                
                // If any of those are not satisfied valid = false
                
                if (valid){
                   double hourly = Double.valueOf(txtHourlyPay.getText());
                    Worker newGuy = new Worker(txtWorkerFirstName.getText(), txtWorkerLastName.getText(),
                        txtWorkerNum.getText(), cmboStatus.getValue().toString(), hourly, txtWorkerEmail.getText());

                    if("Supervisor".equals(cmboStatus.getValue().toString())){
                        superData.add(newGuy);
                    }else{
                        workerData.add(newGuy);
                    }
                    workerTableData.clear();
                    superTableData.clear();
                    for (Worker s: workerData)
                    {
                        workerTableData.add(s);
                    }
                    for (Worker s: superData)
                    {
                        superTableData.add(s);
                    }
                    txtWorkerFirstName.clear();
                    txtWorkerLastName.clear();
                    txtWorkerNum.clear();
                    txtWorkStatus.clear();
                    txtHourlyPay.clear();
                    Window window = ((Node)(a.getSource())).getScene().getWindow();
                    ((Stage) window).close();
                }
                });
        
            Scene secondScene = new Scene(addWorkerPane, 500, 500);
            Stage secondStage = new Stage();
            secondStage.setScene(secondScene);
            secondStage.setTitle("Add new Animal");
            secondStage.show();
        });
        
        workerTable = new TableView<>();
        workerTableData = FXCollections.observableArrayList(workerData);
        workerTable.setItems(workerTableData);
        
        TableColumn tcWorkerID = new TableColumn("ID");
        TableColumn tcWorkerFirstName = new TableColumn("First Name");
        TableColumn tcWorkerLastName = new TableColumn("Last Name");
        TableColumn tcWorkerNum = new TableColumn("Phone Number");
        TableColumn tcWorkerEmail = new TableColumn("Email");
        TableColumn tcWorkerStatus = new TableColumn("Work Status");
        TableColumn tcWorkerHourly = new TableColumn("Hourly Pay");
        
        tcWorkerID.setCellValueFactory(new PropertyValueFactory<Worker, Integer>("workerID"));
        tcWorkerFirstName.setCellValueFactory(new PropertyValueFactory<Worker, String>("firstName"));
        tcWorkerLastName.setCellValueFactory(new PropertyValueFactory<Worker, String>("lastName"));
        tcWorkerNum.setCellValueFactory(new PropertyValueFactory<Worker, String>("phoneNumber"));
        tcWorkerEmail.setCellValueFactory(new PropertyValueFactory<Worker, String>("email"));
        tcWorkerStatus.setCellValueFactory(new PropertyValueFactory<Worker, String>("workStatus"));
        tcWorkerHourly.setCellValueFactory(new PropertyValueFactory<Worker, Double>("hourlyPay"));
        
        
        
        workerTable.getColumns().addAll(tcWorkerID, tcWorkerFirstName, tcWorkerLastName,
                tcWorkerNum, tcWorkerEmail, tcWorkerStatus, tcWorkerHourly);
        workerTable.setMinWidth(scene.getWidth());
        workerPane.add(workerTable, 0, 1);
        
        addSupervisor.setOnAction(addWorker.getOnAction());
        superTable = new TableView<>();
        superTableData = FXCollections.observableArrayList(superData);
        superTable.setItems(superTableData);
        
        TableColumn tcSWorkerID = new TableColumn("ID");
        TableColumn tcSWorkerFirstName = new TableColumn("First Name");
        TableColumn tcSWorkerLastName = new TableColumn("Last Name");
        TableColumn tcSWorkerNum = new TableColumn("Phone Number");
        TableColumn tcSWorkerEmail = new TableColumn("Email");
        TableColumn tcSWorkerStatus = new TableColumn("Work Status");
        TableColumn tcSWorkerHourly = new TableColumn("Hourly Pay");
        
        tcSWorkerID.setCellValueFactory(new PropertyValueFactory<Worker, Integer>("workerID"));
        tcSWorkerFirstName.setCellValueFactory(new PropertyValueFactory<Worker, String>("firstName"));
        tcSWorkerLastName.setCellValueFactory(new PropertyValueFactory<Worker, String>("lastName"));
        tcSWorkerNum.setCellValueFactory(new PropertyValueFactory<Worker, String>("phoneNumber"));
        tcSWorkerEmail.setCellValueFactory(new PropertyValueFactory<Worker, String>("email"));
        tcSWorkerStatus.setCellValueFactory(new PropertyValueFactory<Worker, String>("workStatus"));
        tcSWorkerHourly.setCellValueFactory(new PropertyValueFactory<Worker, Double>("hourlyPay"));
        
        
        superTable.getColumns().addAll(tcSWorkerID, tcSWorkerFirstName, tcSWorkerLastName,
                tcSWorkerNum, tcSWorkerEmail, tcSWorkerStatus, tcSWorkerHourly);
        superTable.setMinWidth(scene.getWidth());
        supervisorPane.add(superTable, 0, 1);
        
        // End Add worker, worker tab, and supervisor tab stuff
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
