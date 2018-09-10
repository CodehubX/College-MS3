/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeproject;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author a
 */
public class MainProject  extends Application {
    String name;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage l=new Stage();
        Button btnStd =new Button("student");
        
                
        Button btnDoc =new Button("Doctor");
        Button btnDept =new Button("Department");
        Button btnCourse =new Button("Course");
        Button btnGrade =new Button("Grade");
        Button btnlogout =new Button("logout");
         btnStd.setFont(Font.font(22));
         btnDoc.setFont(Font.font(22));
         btnDept.setFont(Font.font(22));
         btnCourse.setFont(Font.font(22));
         btnGrade.setFont(Font.font(22));
         btnlogout.setFont(Font.font(22));
        //design
        BorderPane pb=new BorderPane();
        HBox hbox =new HBox();
        VBox vbox1 =new VBox();
        VBox vbox2 =new VBox();
        VBox vbox3 =new VBox();
        btnlogout.setMinSize(0, 0);
        btnStd.setMinSize(100,100);
        btnDoc.setMinSize(100, 100);
        btnDept.setMinSize(100, 100);
        btnCourse.setMinSize(100, 100);
        btnGrade.setMinSize(100, 100);
        vbox1.getChildren().addAll(btnStd,btnDoc);
        vbox2.getChildren().addAll(btnDept,btnCourse);
        
        
        hbox.getChildren().addAll(vbox1,vbox2);
        vbox3.getChildren().addAll(hbox,btnGrade);
        vbox1.setSpacing(111);
        vbox2.setSpacing(111);
        vbox3.setSpacing(111);
        hbox.setSpacing(111);
        
        hbox.setAlignment(Pos.CENTER);
        vbox1.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.CENTER);
        vbox3.setAlignment(Pos.CENTER);
        btnGrade.setAlignment(Pos.CENTER);
        pb.setCenter(vbox3);
        
        pb.setRight(btnlogout);
        
        btnStd.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        btnCourse.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        btnDept.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        btnDoc.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        btnGrade.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        btnlogout.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        btnStd.setId("student");
        btnCourse.setId("course");
        btnDept.setId("dept");
        btnDoc.setId("doc");
        btnGrade.setId("grade");
        btnlogout.setId("log");
        
//       
        //action
        
        btnStd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                   System.out.println(name);
                    new Student().start(l);
                    primaryStage.hide();
                } catch (Exception ex) {
                    Logger.getLogger(MainProject.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        btnDoc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               try {
                    
                    new Doctor().start(l);
                    primaryStage.hide();
                } catch (Exception ex) {
                    Logger.getLogger(MainProject.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        btnDept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    new Dept().start(l);
                    primaryStage.hide();
                } catch (Exception ex) {
                    Logger.getLogger(MainProject.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        btnCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               
                try {
                    new course().start(l);
                    primaryStage.hide();
                } catch (Exception ex) {
                    Logger.getLogger(MainProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnGrade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               
                 try {
                    new Grade().start(l);
                    primaryStage.hide();
                } catch (Exception ex) {
                    Logger.getLogger(MainProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
         btnlogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage lx=new Stage();
                    new login().start(lx);
                    primaryStage.hide();
                } catch (Exception ex) {
                    Logger.getLogger(MainProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             
         });
        
        
        Scene scene=new Scene(pb,1900 , 1150);
        
        pb.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        pb.setId("back");
        primaryStage.setTitle("college prgram");
        primaryStage.setScene(scene);
        primaryStage.show();
        btnStd.setStyle("-fx-border-color: green;");
        btnDoc.setStyle("-fx-border-color: green;");
        btnDept.setStyle("-fx-border-color: green;");
        btnCourse.setStyle("-fx-border-color: green;");
        btnGrade.setStyle("-fx-border-color: green;");
        
//        primaryStage.setResizable(false);
        System.out.println("hello againg");
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void getname(String l){
        name=l;
    }
}
