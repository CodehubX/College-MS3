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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
public class Register extends Application{
    Connection con=null;
    PreparedStatement pst=null;
    Text errorUser =new Text("               ");
        Text errorName =new Text("                ");
        Text errorPass =new Text("               ");
        Text errorBirth =new Text("             ");
        TextField name=new TextField();
        TextField username=new TextField();
        PasswordField pass=new PasswordField();
        TextField birthday=new TextField();
    @Override
    public void start(Stage primaryStage) throws Exception {
         try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/college", "mano", "mano");
            System.out.println("done");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        Label labelName=new Label("name :");
        Label labelUser=new Label("user :");
        Label labelPass=new Label("password :");
        Label labelBirth=new Label("Birthday :");
        Button btnadd=new Button("Regist");
        Button btncancel=new Button("Cancel");
        
                
                
                
        VBox vb1 =new VBox();
        VBox vb2 =new VBox();
        VBox vb3 =new VBox();
        VBox vb4 =new VBox();
        HBox hbox1=new HBox();
        HBox hbox2=new HBox();
        HBox hbox3=new HBox();
        vb1.getChildren().addAll(labelName,labelUser,labelPass,labelBirth);
//        vb1.getChildren().addAll(labelName,labelUser,labelPass,labelBirth);
        vb4.getChildren().addAll(errorName,errorUser,errorPass,errorBirth);
//        hbox2.getChildren().addAll(username,errorUser);
        
        vb2.getChildren().addAll(name,username,pass,birthday);
        hbox1.getChildren().addAll(vb1,vb2,vb4);
        hbox3.getChildren().addAll(btnadd,btncancel);
        vb3.getChildren().addAll(hbox1,hbox3);
        vb1.setSpacing(33);
        vb2.setSpacing(22);
        vb3.setSpacing(22);
        vb4.setSpacing(33);
        hbox3.setSpacing(33);
        vb1.setAlignment(Pos.CENTER);
        vb2.setAlignment(Pos.CENTER);
        vb3.setAlignment(Pos.CENTER);
        hbox1.setAlignment(Pos.CENTER);
        hbox3.setAlignment(Pos.CENTER);
        
        btnadd.setOnAction(e ->{
        if(checkempty(username.getText())){
  
            try {
                 pst=con.prepareStatement("insert into login values(?,?,?,?)");
                pst.setString(1, username.getText());
                pst.setString(2, pass.getText());
                pst.setString(3, birthday.getText());
                pst.setString(4, name.getText());
                pst.execute();
                Stage lx=new Stage();
                new MainProject().start(lx);
                primaryStage.hide();
                primaryStage.close();
            } catch (Exception ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        });
        btncancel.setOnAction(e ->{
        
  
            try {
                 
                Stage lx=new Stage();
                new login().start(lx);
                primaryStage.hide();
            } catch (Exception ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        });
        
        
        
        
        
        
        Scene l=new Scene(vb3, 333, 333 );
        vb3.setId("id1");
        vb3.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(l);
        primaryStage.show();
        primaryStage.setTitle("Register");

    }
    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean checkempty (String l){
        int x=0;
        if(name.getText().isEmpty()){
            errorName.setText("error ");
            errorName.setFill(Color.RED);
            x=1;
        }
        if(username.getText().isEmpty()){
            errorUser.setText("error ");
            errorUser.setFill(Color.RED);
            x=1;
        }
        if(pass.getText().isEmpty()){
            errorPass.setText("error ");
            errorPass.setFill(Color.RED);
            x=1;
        }
        if(birthday.getText().isEmpty()){
            errorBirth.setText("error ");
            errorBirth.setFill(Color.RED);
            x=1;
        }
        if(x==1)return false;
        if(checkuser(l))return false;
        return true;
                
    }
    public boolean checkuser (String l){
        try {
             pst=con.prepareStatement("Select * from login where username=?");
            pst.setString(1, l);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                errorUser.setText("user not avaliable");
                errorUser.setFill(Color.RED);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
