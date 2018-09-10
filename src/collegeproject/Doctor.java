/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeproject;

import static collegeproject.Student.pst;
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
public class Doctor extends Application{
FunStudent current=null;
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
    Label labelId  =new Label("id doctor : ");
    Label labelFName  =new Label("Name : ");
    Label labelEmail  =new Label("Email            : ");
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
        TextField tfName =new TextField();
        TextField tfEmail=new TextField();
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
       tfSearch.setPromptText("Id or Name");
              
        TableColumn columId=new TableColumn("id Doctor");
        TableColumn columFName=new TableColumn("Name");
        TableColumn columEmail=new TableColumn("Email");
        TableColumn columAddress=new TableColumn("Address");
        TableColumn columPhone=new TableColumn("Phone number");
        columId.setCellValueFactory(new PropertyValueFactory("IdDoc"));
        columFName.setCellValueFactory(new PropertyValueFactory("NameDoc"));
        columEmail.setCellValueFactory(new PropertyValueFactory("EmailDoc"));
        columAddress.setCellValueFactory(new PropertyValueFactory("AddressDoc"));
        columPhone.setCellValueFactory(new PropertyValueFactory("PhoneDoc"));
        tabel.getColumns().addAll(columId,columFName,columEmail,columPhone,columAddress);
        HBox hb0=new HBox();
        HBox hb1=new HBox();
        HBox hb2=new HBox();
        VBox vb1=new VBox();
        VBox vb2=new VBox();
        VBox vb3=new VBox();
        BorderPane bp1 =new BorderPane();
        BorderPane bp2 =new BorderPane();
        //design
        //search
        hb1.getChildren().addAll(labelSearch,tfSearch,btnSearch);
        hb0.getChildren().addAll(btnRetrun,hb1);
        hb1.setSpacing(12);
        hb0.setSpacing(250);
        bp1.setTop(hb0);
        
        hb1.setAlignment(Pos.CENTER);
        bp1.setMargin(hb1, new Insets(12,12,12,12));
        
        //link between label and textfield of all
        HBox hbox1=new HBox(labelFName,tfName,error1);
        HBox hbox4=new HBox(labelAddress,tfAdderess,error4);
        HBox hbox7=new HBox(labelEmail,tfEmail,error6);
        HBox hbox8=new HBox(labelPhone,tfPhone,error7);
        HBox hboxId=new HBox(labelId,tfId,error8);
        
        //add,delet,update,textfield in center of bp1
        vb1.getChildren().addAll(btnAdd,btnUpdate,btnDelet,btnRefresh);
        vb2.getChildren().addAll(hboxId,hbox1,hbox4,hbox7,hbox8);
        vb3.getChildren().addAll(hbox7,hbox8);
        
        hb2.getChildren().addAll(vb1,vb2,vb3);
       
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
                tfName.setText(newValue.getNameDoc());
                tfEmail.setText(newValue.getEmailDoc());
                tfAdderess.setText(newValue.getAddressDoc());
                tfId.setText(String.valueOf(newValue.getIdDoc()));
                tfPhone.setText(String.valueOf(newValue.getPhoneDoc()));
                }catch(Exception e){}
            }
        });
         
         //actions
         btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(check(1)){
                    try {
                        int id=Integer.valueOf(tfId.getText());
                        String fname=tfName.getText();
                        String email=tfEmail.getText();
                        String address=tfAdderess.getText();
                        int phone=Integer.valueOf(tfPhone.getText());
                        pst=con.prepareStatement("insert into doctor values(?,?,?,?,?)");
                        pst.setInt(1,id );
                        pst.setString(2, fname);
                        pst.setString(3, address);  
                        pst.setString(4, email);
                        
                        pst.setInt(5, phone);
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "done");
                        refreshTable();
                    } catch(SQLIntegrityConstraintViolationException e){
                        error8.setText("id is found");
                        error8.setFill(Color.RED);
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "error");
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
                        String fname=tfName.getText();
                        String email=tfEmail.getText();
                        String address=tfAdderess.getText();
                        int phone=Integer.valueOf(tfPhone.getText());
                    pst=con.prepareStatement("update doctor set name_doctor=?,email=?,address=?,phone=? where id_doctor=? ");
                    pst.setInt(5,id );
                    pst.setString(1, fname);
                    pst.setString(2, email);
                    pst.setString(3, address);
                    pst.setInt(4, phone);
                        pst.execute();
                refreshTable();
                } catch(SQLIntegrityConstraintViolationException ex){
                        error8.setText("id is not found");
                        error8.setFill(Color.RED);
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
                     pst=con.prepareStatement("delete from doctor where id_doctor=?");
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
                    pst=con.prepareStatement("select * from doctor where lower(name_doctor)=lower(?) or id_doctor=? ");
                    pst.setString(1, tfSearch.getText());
                    try{pst.setInt(2, Integer.valueOf(tfSearch.getText()));}catch(NumberFormatException e){pst.setInt(2, -1);}
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
        primaryStage.setTitle("Doctor");
        
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public boolean check (int i){
        
         try{
                int ee=Integer.valueOf(tfPhone.getText());
            }catch(NumberFormatException e){
               error7.setText("enter number ");
             error7.setFill(Color.RED);
                return false;
            }
         try{
                int ee=Integer.valueOf(tfId.getText());
            }catch(NumberFormatException e){
               error8.setText("enter number");
             error8.setFill(Color.RED);
                return false;
            }
        int x=0;
        
         if(tfId.getText().equals("")||Integer.valueOf(tfId.getText())<=0 ){error8.setText("error");
             error8.setFill(Color.RED); x=2;}else{error8.setText("      ");
        }
         if(tfName.getText().equals("")){error1.setText("error");
             error1.setFill(Color.RED);x=2;}else{error1.setText("      ");
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
         
         if(x==2){return false;}
         if(i==1){
        try {
            pst=con.prepareStatement("select id_doctor from doctor where id_doctor=?");
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
        String query="select * from doctor";
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
                int  id=rs.getInt("id_doctor");
                String fname=rs.getString("name_doctor");
                String email=rs.getString("email");
                String address=rs.getString("address");
                int phone=Integer.valueOf(rs.getString("phone"));
                list.add(new FunStudent(id,fname, address, email, phone,0));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
              
    }
    
        
}

