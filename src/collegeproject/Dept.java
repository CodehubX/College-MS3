/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author a
 */
public class Dept extends Application {

    FunStudent current = null;
    static Connection con = null;
    static PreparedStatement pst = null;
    ObservableList<FunStudent> list = FXCollections.observableArrayList();
    TableView<FunStudent> tabel = new TableView<FunStudent>(list);
    String namedept;
    Button btnAdd = new Button("Add");
    Button btnUpdate = new Button("Update");
    Button btnDelet = new Button("Delete");
    Button btnSearch = new Button("Search");
    Button btnRefresh = new Button("Refresh");
    Button btnRetrun = new Button("Retrun");
    Label labelId = new Label("id doctor : ");
    Label labelFName = new Label("Name department : ");
    Label labelEmail = new Label("Email            : ");
//        Label labelAddress=new Label("Address     : ");
    Label labelPhone = new Label("Phone         : ");
    Label labelSearch = new Label("Search     : ");
    Text error1 = new Text("       ");
    Text error2 = new Text("       ");
    Text error3 = new Text("       ");
    Text error4 = new Text("       ");
    Text error5 = new Text("       ");
    Text error6 = new Text("       ");
    Text error7 = new Text("       ");
    Text error8 = new Text("       ");
    Text error9 = new Text("       ");
    TextField tfId = new TextField();
    TextField tfName = new TextField();
    TextField tfEmail = new TextField();
//        TextField tfAdderess=new TextField();
    TextField tfPhone = new TextField();
    TextField tfSearch = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/college", "mano", "mano");
            System.out.println("done");
        } catch (Exception e) {
            System.out.println(e);
        }
        tfSearch.setPromptText("Name dept");

        TableColumn columId = new TableColumn("id Doctor");
        TableColumn columFName = new TableColumn("Name");
        TableColumn columEmail = new TableColumn("Email");
//        TableColumn columAddress=new TableColumn("Address");
        TableColumn columPhone = new TableColumn("Phone number");
        TableColumn columNameDoc = new TableColumn("name Doctor");
        columId.setCellValueFactory(new PropertyValueFactory("IdDoctor2"));
        columFName.setCellValueFactory(new PropertyValueFactory("NameDept"));
        columEmail.setCellValueFactory(new PropertyValueFactory("EmailDept"));
//        columAddress.setCellValueFactory(new PropertyValueFactory("AddressDoc"));
        columPhone.setCellValueFactory(new PropertyValueFactory("PhoneDept"));
        columNameDoc.setCellValueFactory(new PropertyValueFactory("NameDoc2"));
        tabel.getColumns().addAll(columId, columFName, columEmail, columPhone, columNameDoc);
        HBox hb0 = new HBox();
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();
        BorderPane bp1 = new BorderPane();
        BorderPane bp2 = new BorderPane();
        //design
        //search
        hb1.getChildren().addAll(labelSearch, tfSearch, btnSearch);
        hb0.getChildren().addAll(btnRetrun, hb1);
        hb1.setSpacing(12);
        hb0.setSpacing(250);
        bp1.setTop(hb0);

        hb1.setAlignment(Pos.CENTER);
        bp1.setMargin(hb1, new Insets(12, 12, 12, 12));

        //link between label and textfield of all
        HBox hbox1 = new HBox(labelFName, tfName, error1);
//        HBox hbox4=new HBox(labelAddress,tfAdderess,error4);
        HBox hbox7 = new HBox(labelEmail, tfEmail, error6);
        HBox hbox8 = new HBox(labelPhone, tfPhone, error7);
        HBox hboxId = new HBox(labelId, tfId, error8);

        //add,delet,update,textfield in center of bp1
        vb1.getChildren().addAll(btnAdd, btnUpdate, btnDelet, btnRefresh);
        vb2.getChildren().addAll(hboxId, hbox1, hbox7, hbox8);
        vb3.getChildren().addAll(hbox7, hbox8);

        hb2.getChildren().addAll(vb1, vb2, vb3);

        bp1.setCenter(hb2);

        //main of design 
        bp2.setTop(bp1);
        bp2.setCenter(tabel);
        //setspacing
        hb2.setSpacing(44);
        vb1.setSpacing(12);
        vb2.setSpacing(12);
        vb3.setSpacing(12);
        //select the mouse in table
        TableView.TableViewSelectionModel<FunStudent> selected = tabel.getSelectionModel();
        ReadOnlyObjectProperty<FunStudent> item = selected.selectedItemProperty();

        item.addListener(new ChangeListener<FunStudent>() {
            @Override
            public void changed(ObservableValue<? extends FunStudent> observable, FunStudent oldValue, FunStudent newValue) {
                current = newValue;
                try {
                    String namedept = newValue.getNameDept();

                    tfName.setText(namedept);
                    tfEmail.setText(newValue.getEmailDept());
//                tfAdderess.setText(newValue.getAddressDoc());
                    tfId.setText(String.valueOf(newValue.getIdDoctor2()));
                    tfPhone.setText(String.valueOf(newValue.getPhoneDept()));
                } catch (Exception e) {
                }
            }
        });

        //actions
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (check(1)) {
                    try {
                        int id = Integer.valueOf(tfId.getText());
                        String fname = tfName.getText();
                        String email = tfEmail.getText();
//                        String address=tfAdderess.getText();
                        int phone = Integer.valueOf(tfPhone.getText());
                        pst = con.prepareStatement("insert into department values(?,?,?,?)");

                        pst.setString(1, fname);
                        pst.setInt(2, id);
//                        pst.setString(3, address);  
                        pst.setInt(3, phone);

                        pst.setString(4, email);

                        pst.execute();
                        JOptionPane.showMessageDialog(null, "done");
                        refreshTable();
                    } catch (SQLIntegrityConstraintViolationException e) {
                        error1.setText("name found before");
                        error1.setFill(Color.RED);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "error");
                    }

                }
            }
        });
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (check(0)) {
                    try {

                        int id = Integer.valueOf(tfId.getText());
                        String fname = tfName.getText();
                        String email = tfEmail.getText();

//                        String address=tfAdderess.getText();
                        
                        int phone = Integer.valueOf(tfPhone.getText());
                        
                        pst = con.prepareStatement("update department set ID_DOCTOR=?,phone=?,email=? where NAMEDEPT=? ");
                        pst.setString(4, fname);

                        pst.setInt(1, id);
                        pst.setInt(2, phone);
                        pst.setString(3, email);
//                    pst.setString(3, address);

                        pst.execute();
                        refreshTable();

                    } catch (SQLIntegrityConstraintViolationException ex) {
                        error8.setText("id is not found");
                        error8.setFill(Color.RED);
                    } catch (SQLException ex) {
                        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        btnDelet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (check(0)) {
                    try {
                        if (checkdept(tfName.getText())) {
                            pst = con.prepareStatement("delete from department where NAMEDEPT=?");
                            pst.setString(1, tfName.getText());
                            pst.execute();
                            refreshTable();
                        } else {
                            error1.setText("name is not  found");
                            error1.setFill(Color.RED);
                        }

                    } catch (SQLException ex) {
                        System.out.println("error in delet ");
                        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        });
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("search");
                    String query = "select namedept,department.id_doctor,department.phone,department.email,name_doctor from department left outer join doctor on(department.id_doctor=doctor.id_doctor) where lower(NAMEDEPT)=lower(?)  ";

                    pst = con.prepareStatement(query);
                    pst.setString(1, tfSearch.getText());
                    ResultSet rs = pst.executeQuery();
                    list.clear();
                    downloadToTable(rs);

                } catch (SQLException ex) {
                    System.out.println("heoooooo");
                }

            }
        });
        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshTable();
            }
        });
        btnRetrun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    con.close();
                    Stage l = new Stage();
                    new MainProject().start(l);
                    primaryStage.hide();
                } catch (SQLException ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        refreshTable();
        Scene value = new Scene(bp2, 966, 666);
        bp2.setId("id1");
        bp2.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(value);
        primaryStage.setTitle("Department");
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean check(int i) {

        int x = 0;
        try {
            int ee = Integer.valueOf(tfId.getText());
        } catch (NumberFormatException e) {
            error8.setText("enter number ");
            error8.setFill(Color.RED);
            return false;
        }
        if (tfId.getText().equals("") || Integer.valueOf(tfId.getText()) <= 0) {
            error8.setText("error");
            error8.setFill(Color.RED);
            x = 2;
        } else {
            error8.setText("      ");
        }
        if (tfName.getText().equals("")) {
            error1.setText("error");
            error1.setFill(Color.RED);
            x = 2;
        } else {
            error1.setText("      ");
        }

//         if(tfAdderess.getText().equals("")){error4.setText("error");
//             error4.setFill(Color.RED);x=2;}else{error4.setText("      ");
//         }
        if (tfEmail.getText().equals("")) {
            error6.setText("error");
            error6.setFill(Color.RED);
            x = 2;
        } else {
            error6.setText("      ");
        }

        if (tfPhone.getText().equals("")) {
            error7.setText("error");
            error7.setFill(Color.RED);
            x = 2;
        } else {
            error7.setText("      ");
        }

        if (x == 2) {
            return false;
        }
        if (i == 1) {
//        try {
//            pst=con.prepareStatement("select nameDep from doctor where id_doctor=?");
//                pst.setInt(1, Integer.valueOf(tfId.getText()));
//           
//            ResultSet rs=pst.executeQuery();
//            if(rs.next()){
//                 error8.setText("id is found");
//             error8.setFill(Color.RED);
//             return false;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
//        }
        }

        
        try {
            int ee = Integer.valueOf(tfPhone.getText());
        } catch (NumberFormatException e) {
            error7.setText("enter number ");
            error7.setFill(Color.RED);
            return false;
        }
        if (tfEmail.getText().contains(".com") && tfEmail.getText().contains("@")) {
        } else {
            error6.setText("example@exa.com ");
            error6.setFill(Color.RED);
            return false;
        }
        System.out.println(x);
        if (x == 2) {
            return false;
        } else {
            return true;
        }

    }

    public void refreshTable() {
        list.clear();
        try {
            String query = "select namedept,department.id_doctor,department.phone,department.email,name_doctor from department left outer join doctor on(department.id_doctor=doctor.id_doctor)";

            pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            downloadToTable(rs);
        } catch (SQLException ex) {
            System.out.println(ex + "2");
        }
        error1.setText("       ");
          error2.setText("       ");
          error3.setText("       ");
          error4.setText("       ");
          error5.setText("       ");
          error6.setText("       ");
          error7.setText("       ");
          error8.setText("       ");
          error9.setText("       ");
    }

    public void downloadToTable(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println("1");
                int id;
                try{
                id = Integer.valueOf(rs.getString("id_doctor"));
                }catch(NumberFormatException es){
                    id=0;
                }
                System.out.println("2");
                String name_doctor = rs.getString("name_doctor");
                String name_dept = rs.getString("namedept");
                String email = rs.getString("email");
//                String address=rs.getString("address");
                int phone = Integer.valueOf(rs.getString("phone"));
                
                list.add(new FunStudent(name_dept, id, phone, email, name_doctor));
            }
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("sdk");
        }

    }

    public boolean checkdept(String l) {
        try {
            pst = con.prepareStatement("select nameDept from department where namedept=?");
            pst.setString(1, l);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

