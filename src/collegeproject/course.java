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
import javafx.scene.control.TextArea;
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
public class course extends  Application{
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
    Label labelId  =new Label("id course : ");
    Label labelFName  =new Label("Name course: ");
    Label labeldept  =new Label("dept            : ");
        Label labelDescription=new Label("Year/ Semester     : ");
        Label labelIdDoc  =new Label("Id doctor         : ");
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
        TextField tfDept=new TextField();
        TextField tfDescrip=new TextField();
        TextField tfIdDoc=new TextField();
//        TextField tfNameDoc=new TextField();
        TextField tfSearch=new TextField();
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
        con=DriverManager.getConnection("jdbc:derby://localhost:1527/college","mano","mano");
            System.out.println("done");
        }catch(Exception e){
            System.out.println(e);
        }
       tfSearch.setPromptText("Id or Name or dept");
              tfDescrip.setPrefColumnCount(18);
//              tfDescrip.setPrefRowCount(4);
        TableColumn columId=new TableColumn("id Course");
        TableColumn columFName=new TableColumn("Name Course");
        TableColumn columDept=new TableColumn("Dept");
        TableColumn columIdDoc=new TableColumn("id Doctor");
        TableColumn columNameDoc=new TableColumn("name Doctor");
        TableColumn columdescript=new TableColumn("Year/ Semester");
        columId.setCellValueFactory(new PropertyValueFactory("IdCourse"));
        columFName.setCellValueFactory(new PropertyValueFactory("NameCourse"));
        columDept.setCellValueFactory(new PropertyValueFactory("DeptCourse"));
        columIdDoc.setCellValueFactory(new PropertyValueFactory("IdDoctor3"));
        columNameDoc.setCellValueFactory(new PropertyValueFactory("NameDoctor3"));
        columdescript.setCellValueFactory(new PropertyValueFactory("Description"));
        tabel.getColumns().addAll(columId,columFName,columDept,columNameDoc,columIdDoc,columdescript);
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
        HBox hbox4=new HBox(labelIdDoc,tfIdDoc,error4);
        HBox hbox7=new HBox(labeldept,tfDept,error6);
        HBox hbox8=new HBox(labelDescription,tfDescrip,error7);
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
                tfName.setText(newValue.getNameCourse());
                tfDept.setText(newValue.getDeptCourse());
                tfDescrip.setText(newValue.getDescription());
                tfId.setText(String.valueOf(newValue.getIdCourse()));
                tfIdDoc.setText(String.valueOf(newValue.getIdDoctor3()));
//                tfNameDoc.setText(String.valueOf(newValue.getNameDoctor3()));
                
                }catch(Exception e){}
            }
        });
         
         //actions
         btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(check(1)){
                    try {
                        String id=tfId.getText();
                        String fname=tfName.getText();
                        String dept=tfDept.getText();
                        String descrip=tfDescrip.getText();
                        int idDoc=Integer.valueOf(tfIdDoc.getText());
                        boolean ll=false;
                        if(tfDept.getText().isEmpty()){
                            dept=null;
                            ll=true;
                        }
                        if(checkdept(dept)||ll){
//                            if(!checkCourseName(fname)){
                        pst=con.prepareStatement("insert into course values(?,?,?,?,?)");
                        pst.setString(1,id );
                        pst.setString(2, fname);
                        pst.setString(3, dept);  
                        pst.setInt(4, idDoc);
                        
                        pst.setString(5, descrip);
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "done");
                        refreshTable();
//                            }else{
//                                error1.setText("course is found before");
//                                error1.setFill(Color.RED);
//                            }
                                    
                        }else{
                            error6.setText("dept not found");
                        error6.setFill(Color.RED);
                        }
                    } catch(SQLIntegrityConstraintViolationException e){
                        System.out.println(e);
                        error4.setText("id doctor not found");
                        error4.setFill(Color.RED);
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
                    String id=tfId.getText();
                        String fname=tfName.getText();
                        String dept=tfDept.getText();
                        String descrip=tfDescrip.getText();
                        int idDoc=Integer.valueOf(tfIdDoc.getText());
                        int x=0;
                        if(tfDept.getText().isEmpty()){
                            dept=null;
                            System.out.println("null");
                            x=1;
                        }
                            
                        if(x==1||checkdept(dept)){
                            if(checkCourseId(id)){
                                
                            
                            pst=con.prepareStatement("update course set name_course=?,dept=?,id_doctor=?,description=? where id_course=? ");
                            pst.setString(5,id );
                            pst.setString(1, fname);
                            pst.setString(2, dept);
                            pst.setInt(3, idDoc);
                            pst.setString(4, descrip);
                            pst.execute();
                         refreshTable();
                            }
                            else{
                                error8.setText("id is not found");
                                error8.setFill(Color.RED);
                            }
                        }
                        else{
                            error6.setText("dept not found");
                        error6.setFill(Color.RED);
                        }
                }catch(SQLIntegrityConstraintViolationException ex){
                        
                        error4.setText("id doctor not found");
                        error4.setFill(Color.RED);
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
                     pst=con.prepareStatement("delete from course where id_course=?");
                     pst.setString(1, tfId.getText());
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
                    String query="select ID_COURSE,course.id_doctor,name_course,dept,name_doctor,description from course left outer join doctor on(course.id_doctor=doctor.id_doctor) where lower(name_course)=lower(?) or id_course=? or dept=? ";
   
                    pst=con.prepareStatement(query);
                    pst.setString(1, tfSearch.getText());
                    pst.setString(3, tfSearch.getText());
                    pst.setString(2, tfSearch.getText());
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
        primaryStage.show();
                primaryStage.setTitle("course");

    }
    public static void main(String[] args) {
        launch(args);
    }
    public boolean check (int i){
        
        
        
        int x=0;
        
         if(tfId.getText().equals("") ){error8.setText("error");
             error8.setFill(Color.RED); x=2;}else{error8.setText("      ");
        }
         if(tfName.getText().equals("")){error1.setText("error");
             error1.setFill(Color.RED);x=2;}else{error1.setText("      ");
        }
         
         
         if(tfDescrip.getText().equals("")){error7.setText("error");
             error7.setFill(Color.RED);x=2;}else{error7.setText("      ");
         }
         
//         if(tfDept.getText().equals("")){error6.setText("error");
//             error6.setFill(Color.RED);x=2;}else{error6.setText("      ");
//         }
         try {
             if(tfIdDoc.getText().equals("null"))
                 System.out.println("u   null");
             else{
            int ee = Integer.valueOf(tfIdDoc.getText());
             }
        } catch (NumberFormatException e) {
            error4.setText("enter number ");
            error4.setFill(Color.RED);
            return false;
        }
         if(tfIdDoc.getText().equals("")){error4.setText("error");
             error4.setFill(Color.RED);x=2;}else{error4.setText("      ");
        }
         
         if(x==2){return false;}
         if(i==1){
        try {
            pst=con.prepareStatement("select id_course from course where id_course=?");
                pst.setString(1, tfId.getText());
           
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
       
         
         
         
         System.out.println(x);
         if(x==2){return false;}
         else return true;
         
         
           
    }
    public void refreshTable(){
        list.clear();
        try {
        String query="select ID_COURSE,course.id_doctor,name_course,dept,name_doctor,description from course left outer join doctor on(course.id_doctor=doctor.id_doctor)";
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
                String  id=rs.getString("id_course");
                String nameCourse=rs.getString("name_course");
                String dept=rs.getString("dept");
                String nameDoc=rs.getString("name_doctor");
                String descrip=rs.getString("description");
                String idDoc=rs.getString("id_doctor");
                
//                int phone=Integer.valueOf(rs.getString("phone"));
                list.add(new FunStudent(id, nameCourse, dept, idDoc, descrip, nameDoc));
            }
        }catch(Exception e){
            System.out.println(e);
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
    public boolean checkCourseId(String id) {
        try {
            pst = con.prepareStatement("select id_course from course where id_course=?");
            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("found");
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("not found");
        return false;
    }
    public boolean checkCourseName(String name) {
        try {
            pst = con.prepareStatement("select name_course from course where name_course=?");
            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("found");
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("not found");
        return false;
    }
        
}



