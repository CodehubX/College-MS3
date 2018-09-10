/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeproject;

import static java.awt.SystemColor.text;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author a
 */
public class login extends Application {
     Connection  con=null;
     PreparedStatement pst=null;
     String namex;
     boolean x=true;
    @Override
    public void start(Stage primaryStage) {
        //use 2 thread in same time 
        //create executorservice to use thread and determine 2 thread 
         ExecutorService mano=Executors.newFixedThreadPool(2);
         
          try {
              //connection to database by method get connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/college", "mano", "mano");
            
        } catch (Exception e) {
            System.out.println(e);
        }
          
          //create design 
        StackPane mains=new StackPane();
        BorderPane borderPane=new BorderPane();
        VBox vb=new VBox();
        HBox hb1=new HBox();
        HBox hb2=new HBox();
        HBox hb3=new HBox();
        
        vb.setSpacing(44);
        hb1.setSpacing(44);
        hb2.setSpacing(44);
        
        StackPane stackPane=new StackPane();
        stackPane.setMinSize(88, 88);
        
        Text textLogin=new Text("Login");
        Text textUsername=new Text("username ");
        Text textPassword=new Text("password ");
        Button btnLogin =new Button("log in");
        Button btnReg =new Button("Register");
        TextField textuser=new TextField();
        PasswordField textpass=new PasswordField();
        
         borderPane.setId("pane");
         
         textUsername.setFont(Font.font(33));
         textPassword.setFont(Font.font(33));
         textLogin.setFill(Color.ORANGE);
         textUsername.setFill(Color.WHEAT);
         textPassword.setFill(Color.WHEAT);
         btnLogin.setFont(Font.font(22));
         textuser.setPrefColumnCount(22);
         textpass.setPrefColumnCount(22);
         textuser.setMinSize(88, 44);
         textpass.setMinSize(88, 44);
         btnLogin.setMinSize(44, 22);
         
         //set id for css
         btnLogin.setId("id");
        
        textLogin.setFont(Font.font(STYLESHEET_CASPIAN, FontPosture.REGULAR, 34));
        textuser.setPrefColumnCount(7);
        textpass.setPrefColumnCount(5);
         vb.setAlignment(Pos.CENTER);
       mains.setAlignment(Pos.CENTER);
       btnLogin.setAlignment(Pos.CENTER);
        mains.getChildren().add(vb);
//        mains.setStyle("-fx-border-width: 3;" +
//                      "-fx-border-radius: 8;" + 
//                      "-fx-border-color: blue;");
        hb1.getChildren().addAll(textUsername,textuser);
        hb2.getChildren().addAll(textPassword,textpass);
        hb3.getChildren().addAll(btnLogin,btnReg);
        btnLogin.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.CENTER);
        hb3.setSpacing(44);
        vb.getChildren().addAll(hb1,hb2,hb3);
        HBox hbox=new HBox(textLogin,stackPane);
        hbox.setSpacing(99);

        stackPane.setId("circle");
        
        //upload style css to my design
        stackPane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
       
        
        
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String user2=textuser.getText();
                String pass2=textpass.getText();
                if(checkLogin(user2, pass2)){
                   Stage l=new Stage();
                    try {
                       MainProject m=new MainProject();
                       m.getname(setname());
                       m.start(l);
                       
                        x=false;
                       
                        mano.shutdownNow();
                        
                        primaryStage.hide();
                        
                    } catch (Exception ex) {
                        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "error");
                    textuser.setText("");
                    textpass.setText("");
                }
            }
        });
        //threads
         primaryStage.setOnCloseRequest(e ->{
            
            x=false;
        });
        Thread l=new Thread(new Runnable() {
            int i=3;
              @Override
              public void run() {
                  
                  while(getstatus()){
                      try {
                          i=i+20;
                          Thread.sleep(80);
                         
                          stackPane.setRotate(i);
                          
                          if(i>360){
                              i=0;
                          }   } catch (InterruptedException ex) {
                          Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }
                 mano.shutdown();
                  
//                  
              }
          });
        l.start();
//        
        
        
        borderPane.setTop(hbox);
        borderPane.setCenter(mains);
        btnReg.setOnAction(e ->{
              try {
                  Stage lx=new Stage();
                  new Register().start(lx);
                  
                  primaryStage.hide();
                  mano.shutdown();
              } catch (Exception ex) {
                  Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
              }
        });
        
        
        
        Scene scene =new Scene(borderPane,444,444);
      
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
   
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean checkLogin(String user,String pass){
         try {
             
             pst=con.prepareStatement("select * from login where username=? and pass=? ");
             pst.setString(1, user);
             pst.setString(2, pass);
             ResultSet rs=pst.executeQuery();
             if(rs.next()){
                 namex=rs.getString("name");
                  return true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
        
    }
    public String setname(){
         
        return namex;
    }
    public boolean getstatus(){
        if(x==false)
        return false;
        else 
            return true;
        
    }
}
