/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeproject;

//import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
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
import javafx.scene.image.ImageView;
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
public class Student extends Application  {
    FunStudent current=null;
//       int id=0;
    static Connection con=null;
    static PreparedStatement pst=null;
    ObservableList<FunStudent> list=FXCollections.observableArrayList();
    TableView<FunStudent> tabel=new TableView<FunStudent>(list);

    Button btnAdd =new Button("Add");
    Button btnUpdate =new Button("Update");
    Button btnDelet =new Button("Delete");
    Button btnSearch =new Button("Search");
    Button btnRefresh =new Button("Refresh");
    Button btnRetrun =new Button("Retrun");
    Label labelId  =new Label("id : ");
    Label labelFName  =new Label("First Name : ");
    Label labelLName  =new Label("Last Name    : ");
    Label labelYear   =new Label("Year           : ");
    Label labelEmail  =new Label("Email            : ");
    Label labelBirth  =new Label("Bithday      : ");
    Label labelDept   =new Label("Department : ");
        Label labelAddress=new Label("Address     : ");
        Label labelPhone  =new Label("Phone         : ");
        Label labelSearch =new Label("Search     : ");
        Text  error1 =new Text("       ");
        Text  error2 =new Text("       ");
        Text  error3 =new Text("       ");
        Text  error4 =new Text("       ");
        Text  error5 =new Text("       ");
        Text  error6 =new Text("       ");
        Text  error7 =new Text("       ");
        Text  error8 =new Text("       ");
        Text  error9 =new Text("       ");
        TextField tfId =new TextField();
        TextField tfFName =new TextField();
        TextField tfLName=new TextField();
        TextField tfYear=new TextField();
        TextField tfEmail=new TextField();
        TextField tfBirhday=new TextField();
        TextField tfDept=new TextField();
        TextField tfAdderess=new TextField();
        TextField tfPhone=new TextField();
        TextField tfSearch=new TextField();
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        try{
        con=DriverManager.getConnection("jdbc:derby://localhost:1527/college","mano","mano");
            System.out.println("done");
        }catch(Exception e){
            System.out.println(e);
        }
       tfSearch.setPromptText("Id , Firstname or lastname");
              
        TableColumn columId=new TableColumn("id student");
        TableColumn columFName=new TableColumn("First Name");
        TableColumn columLName=new TableColumn("Last Name");
        TableColumn columYear=new TableColumn("Year");
        TableColumn columEmail=new TableColumn("Email");
        TableColumn columBirth=new TableColumn("Birthday");
        TableColumn columDept=new TableColumn("Department");
        TableColumn columAddress=new TableColumn("Address");
        TableColumn columPhone=new TableColumn("Phone number");
        columId.setCellValueFactory(new PropertyValueFactory("Id"));
        columFName.setCellValueFactory(new PropertyValueFactory("FName"));
        columLName.setCellValueFactory(new PropertyValueFactory("LName"));
        columYear.setCellValueFactory(new PropertyValueFactory("Year"));
        columEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        columBirth.setCellValueFactory(new PropertyValueFactory("Birth"));
        columDept.setCellValueFactory(new PropertyValueFactory("Dept"));
        columAddress.setCellValueFactory(new PropertyValueFactory("Address"));
        columPhone.setCellValueFactory(new PropertyValueFactory("Phone"));
        tabel.getColumns().addAll(columId,columFName,columLName,columYear,columDept,columEmail,columPhone,columBirth,columAddress);
        
        //design
        HBox hb0=new HBox();
        HBox hb1=new HBox();
        HBox hb2=new HBox();
        VBox vb1=new VBox();
        VBox vb2=new VBox();
        VBox vb3=new VBox();
        BorderPane bp1 =new BorderPane();
        BorderPane bp2 =new BorderPane();
        
        //search
        hb1.getChildren().addAll(labelSearch,tfSearch,btnSearch);
        hb0.getChildren().addAll(btnRetrun,hb1);
        hb1.setSpacing(12);
        hb0.setSpacing(250);
        bp1.setTop(hb0);
        
        hb1.setAlignment(Pos.CENTER);
        
        //link between label and textfield of all
        HBox hbox1=new HBox(labelFName,tfFName,error1);
        HBox hbox2=new HBox(labelYear,tfYear,error2);
        HBox hbox3=new HBox(labelBirth,tfBirhday,error3);
        HBox hbox4=new HBox(labelAddress,tfAdderess,error4);
        HBox hbox5=new HBox(labelLName,tfLName,error5);
        HBox hbox6=new HBox(labelDept,tfDept,error9);
        HBox hbox7=new HBox(labelEmail,tfEmail,error6);
        HBox hbox8=new HBox(labelPhone,tfPhone,error7);
        HBox hboxId=new HBox(labelId,tfId,error8);
        
        //add,delet,update,textfield in center of bp1
        vb1.getChildren().addAll(btnAdd,btnUpdate,btnDelet,btnRefresh);
        vb2.getChildren().addAll(hbox1,hbox2,hbox3,hbox4);
        vb3.getChildren().addAll(hbox5,hbox6,hbox7,hbox8);
        
        hb2.getChildren().addAll(vb1,vb2,vb3,hboxId);
       
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
                current=newValue;
                try{
                tfFName.setText(newValue.getFName());
                tfLName.setText(newValue.getLName());
                tfEmail.setText(newValue.getEmail());
                tfAdderess.setText(newValue.getAddress());
                tfBirhday.setText(newValue.getBirth());
                tfDept.setText(newValue.getDept());
                tfYear.setText(String.valueOf(newValue.getYear()));
                tfId.setText(String.valueOf(newValue.getId()));
                tfPhone.setText(String.valueOf(newValue.getPhone()));
                }catch(Exception e){}
            }
        });
         
         //actions
         btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(check(1));
                if(check(1)){
                    try {
                        int id=Integer.valueOf(tfId.getText());
                        String fname=tfFName.getText();
                        String lname=tfLName.getText();
                        String email=tfEmail.getText();
                        int year=Integer.valueOf(tfYear.getText());
                        String birth=tfBirhday.getText();
                        String dept=tfDept.getText();
                        String address=tfAdderess.getText();
                        int phone=Integer.valueOf(tfPhone.getText());
                        if(tfDept.getText().equals(""))
                            dept=null;
                        pst=con.prepareStatement("insert into Student values(?,?,?,?,?,?,?,?,?)");
                        pst.setInt(1,id );
                        pst.setString(2, fname);
                        pst.setString(3, lname);
                        pst.setInt(4, year);
                        pst.setString(5, email);
                        pst.setString(6, birth);
                        pst.setString(7, address);
                        pst.setInt(8, phone);
                        pst.setString(9, dept);
                        pst.execute();
                        
                        refreshTable();
                         JOptionPane.showMessageDialog(null, "done");
                    } 
                    catch(SQLIntegrityConstraintViolationException e){
                        error9.setText("department not found");
                        error9.setFill(Color.RED);
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    
                    
                    
                }
            }
        });
         btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 if(check(0)){
                try {
                    int id=Integer.valueOf(tfId.getText());
                        String fname=tfFName.getText();
                        String lname=tfLName.getText();
                        String email=tfEmail.getText();
                        int year=Integer.valueOf(tfYear.getText());
                        String birth=tfBirhday.getText();
                        String dept=tfDept.getText();
                        String address=tfAdderess.getText();
                        int phone=Integer.valueOf(tfPhone.getText());
                        System.out.println(id + fname +lname);
                        try{if(tfDept.getText().isEmpty())
                            dept=null;}
                            catch(Exception ex){}
                    pst=con.prepareStatement("update student set fname=?,lname=?,years=?,email=?,birthday=?,address=?,phone=?,dept=? where id_student=? ");
                    pst.setInt(9,id );
                    pst.setString(1, fname);
                    pst.setString(2, lname);
                    pst.setInt(3, year);
                    pst.setString(4, email);
                    pst.setString(5, birth);
                    pst.setString(6, address);
                    pst.setInt(7, phone);
                        pst.setString(8, dept);
                        pst.execute();
                refreshTable();
                }catch(SQLIntegrityConstraintViolationException ex){
                        error9.setText("department not found");
                        error9.setFill(Color.RED);
                    }
                catch (SQLException ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
        });
         btnDelet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
             if(check(0)){
                 try {
                     pst=con.prepareStatement("delete from student where id_student=?");
                     pst.setInt(1, Integer.valueOf(tfId.getText()));
                     pst.execute();
                     refreshTable();
                 } catch (SQLException ex) {
                     System.out.println("error in delet ");
                     Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 
             }
         
         }} );
         btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("search");
                    pst=con.prepareStatement("select * from student where lower(fname)=lower(?) or lower(lname)=lower(?) or id_student=? ");
                    pst.setString(1, tfSearch.getText());
                    pst.setString(2, tfSearch.getText());
                    try{pst.setInt(3, Integer.valueOf(tfSearch.getText()));}catch(NumberFormatException e){pst.setInt(3, -1);}
                    ResultSet rs=pst.executeQuery();
                    list.clear();
                    downloadToTable(rs);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } );
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
                    Stage l=new Stage();
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
        Scene value=new Scene(bp2,966,666);
        bp2.setId("id1");
        bp2.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(value);
        primaryStage.setTitle("Student");
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    public boolean check (int i){
        
         try{
                int ee=Integer.valueOf(tfId.getText());
            }catch(NumberFormatException e){
               error8.setText("enter number");
             error8.setFill(Color.RED);
                return false;
            }
         try{
                int ee=Integer.valueOf(tfYear.getText());
            }catch(NumberFormatException e){
               error2.setText("enter number");
             error2.setFill(Color.RED);
                return false;
            }
         try{
                int ee=Integer.valueOf(tfPhone.getText());
            }catch(NumberFormatException e){
               error7.setText("enter number ");
             error7.setFill(Color.RED);
                return false;
            }
        
        int x=0;
        
         if(tfId.getText().equals("")||Integer.valueOf(tfId.getText())<=0 ){error8.setText("error");
             error8.setFill(Color.RED); x=2;}else{error8.setText("      ");
        }
         if(tfFName.getText().equals("")){error1.setText("error");
             error1.setFill(Color.RED);x=2;}else{error1.setText("      ");
        }
//         if(tfDept.getText().equals("")){error9.setText("error");
//             error9.setFill(Color.RED);x=2;}else{error9.setText("      ");
//        }
         
         if(tfLName.getText().equals("")){error5.setText("error");
             error5.setFill(Color.RED);x=2;}else{error5.setText("      ");
         }
         
         if(tfBirhday.getText().equals("")){error3.setText("error");
             error3.setFill(Color.RED);x=2;}else{error3.setText("      ");
         }
         
         if(tfAdderess.getText().equals("")){error4.setText("error");
             error4.setFill(Color.RED);x=2;}else{error4.setText("      ");
         }
         
         if(tfEmail.getText().equals("")){error6.setText("error");
             error6.setFill(Color.RED);x=2;}else{error6.setText("      ");
         }
         
         if(tfPhone.getText().equals("")){error7.setText("error");
             error7.setFill(Color.RED);x=2;}else{error7.setText("      ");
        }
         
         if(tfYear.getText().equals("")){
            
             error2.setText("error");
             error2.setFill(Color.RED);x=2;}else{error2.setText("      ");
             }
         if(x==2){return false;}
         if(i==1){
        try {
            pst=con.prepareStatement("select id_student from student where id_student=?");
            pst.setInt(1, Integer.valueOf(tfId.getText()));
           
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                 error8.setText("id is found");
             error8.setFill(Color.RED);
             return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
       
         
        
         if(tfEmail.getText().contains(".com")&&tfEmail.getText().contains("@")){}else{
             error6.setText("example@exa.com ");
             error6.setFill(Color.RED);
             return false;
         }
         System.out.println(x);
         if(x==2){return false;}
         else return true;
         
         
           
    }
    public void refreshTable(){
        list.clear();
        try {
        String query="select * from student";
        PreparedStatement pst;
        pst=con.prepareStatement(query);
           ResultSet rs=pst.executeQuery();
           downloadToTable(rs);
        } catch (SQLException ex) {
           System.out.println(ex+ "2");
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
        try{
            while(rs.next()){
                int  id=rs.getInt("id_student");
                String fname=rs.getString("fname");
                String lname=rs.getString("lname");
                String email=rs.getString("email");
                int year=Integer.valueOf(rs.getString("years"));
                String birth=rs.getString("Birthday");
                String dept=rs.getString("dept");
                String address=rs.getString("address");
                int phone=Integer.valueOf(rs.getString("phone"));
                list.add(new FunStudent(id,fname, lname, year, email, birth, dept, address, phone));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
              
    }
    
        
}
