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
import javafx.scene.control.ComboBox;
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
public class Grade extends Application{
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
    Label labelIdStd = new Label("id student : ");
    Label labelIdCour = new Label("id course : ");
    Label labelGS = new Label("grade Semester            : ");
    Label labelOr = new Label("       or      ");
//        Label labelAddress=new Label("Address     : ");
    Label labelGF = new Label("grade final         : ");
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
    TextField tfIdStu = new TextField();
    TextField tfIdCour = new TextField();
    
    TextField tfGs = new TextField();
//        TextField tfAdderess=new TextField();
    TextField tfGf = new TextField();
    TextField tfSearch = new TextField();
    ObservableList<String> s=FXCollections.observableArrayList();

         ComboBox dd =new ComboBox(s);
         
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/college", "mano", "mano");
            System.out.println("done");
        } catch (Exception e) {
            System.out.println(e);
        }
        tfSearch.setPromptText("id student or id course");
        dd.setEditable(true);
        TableColumn columId = new TableColumn("id student");
        TableColumn columFName = new TableColumn("id course");
        TableColumn columEmail = new TableColumn("grade Semester");
//        TableColumn columAddress=new TableColumn("Address");
        TableColumn columPhone = new TableColumn("Grade final");
        TableColumn columNameDoc = new TableColumn("name student");
        TableColumn columNameCour = new TableColumn("name course");
        columId.setCellValueFactory(new PropertyValueFactory("IdStudent"));
        columFName.setCellValueFactory(new PropertyValueFactory("IdCourseG"));
        columEmail.setCellValueFactory(new PropertyValueFactory("GradeSeme"));
//        columAddress.setCellValueFactory(new PropertyValueFactory("AddressDoc"));
        columPhone.setCellValueFactory(new PropertyValueFactory("GradeFinal"));
        columNameDoc.setCellValueFactory(new PropertyValueFactory("NameStudentG"));
        columNameCour .setCellValueFactory(new PropertyValueFactory("NameCourseG"));
        tabel.getColumns().addAll(columId, columFName, columEmail, columPhone, columNameDoc,columNameCour);
        tabel.setId("lion");
        tabel.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        
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
        HBox hbox1 = new HBox(labelIdCour, tfIdCour, error1);
//        HBox hbox4=new HBox(labelAddress,tfAdderess,error4);
        HBox hbox7 = new HBox(labelGS, tfGs, error6);
        HBox hbox8 = new HBox(labelGF, tfGf, error7);
        HBox hboxId = new HBox(labelIdStd, tfIdStu, error8);
         HBox hboxOr = new HBox(labelOr , error9);
         HBox hboxnamecourse = new HBox(dd );
                 downnamecourse();

        //add,delet,update,textfield in center of bp1
        vb1.getChildren().addAll(btnAdd, btnUpdate, btnDelet, btnRefresh);
        vb2.getChildren().addAll(hboxId, hbox1, hbox7, hbox8,hboxOr,hboxnamecourse);
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

                    tfIdCour.setText(newValue.getIdCourseG());
                    tfGs.setText(String.valueOf(newValue.getGradeSeme()));
//                tfAdderess.setText(newValue.getAddressDoc());
                    tfIdStu.setText(String.valueOf(newValue.getIdStudent()));
                    tfGf.setText(String.valueOf(newValue.getGradeFinal()));
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
                        System.out.println(tfIdCour.getText());
                        if (checkAllId(tfIdCour.getText(),Integer.valueOf(tfIdStu.getText()))&&checkAllId2(tfIdCour.getText(),Integer.valueOf(tfIdStu.getText()))) {
                            
                        int id = Integer.valueOf(tfIdStu.getText());
                        String fname = tfIdCour.getText();
                        int email = Integer.valueOf(tfGs.getText());
//                        String address=tfAdderess.getText();
                        int phone = Integer.valueOf(tfGf.getText());
                        pst = con.prepareStatement("insert into grade values(?,?,?,?)");
                         pst.setInt(1, id);
                        pst.setString(2, fname);
                       
//                        pst.setString(3, address);  
                       

                        pst.setInt(3, email);
                         pst.setInt(4, phone);
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "done");
                        refreshTable();
                        }
                        
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
                        
                        int id = Integer.valueOf(tfIdStu.getText());
                        String fname = tfIdCour.getText();
                        int email = Integer.valueOf(tfGs.getText());

//                        String address=tfAdderess.getText();
                        
                        int phone = Integer.valueOf(tfGf.getText());
                        
                        pst = con.prepareStatement("update grade set gradeseme=?,gradefinal=? where id_student=? and id_course=?");
                        pst.setInt(1, email);
                        pst.setInt(2, phone);
                        pst.setInt(3, id);
                        pst.setString(4, fname);

                        
                        
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
                        
                            
                            int id = Integer.valueOf(tfIdStu.getText());
                            String fname = tfIdCour.getText();
                            pst = con.prepareStatement("delete from grade where id_student=? and id_course=? ");
                            
                            pst.setInt(1, id);
                            pst.setString(2, fname);
                            pst.execute();
                            refreshTable();
                        

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
                    int id = -1;
                    System.out.println("search");
                    String query = "select grade.id_student,grade.Id_course,gradeseme,gradefinal,fname,lname,name_course from (grade left outer join student on(grade.id_student=student.id_student))left outer join course on(grade.id_course=course.id_course) where grade.id_student=? or grade.id_course=?  ";
                    try { id = Integer.valueOf(tfSearch.getText());}catch(NumberFormatException es){}
                        
                            String fname = tfSearch.getText();
                            System.out.println("111111111");
                    pst = con.prepareStatement(query);
                    System.out.println("2222222222");
                    pst.setInt(1, id);
                    pst.setString(2, fname);
                    ResultSet rs = pst.executeQuery();
                    System.out.println("3333333333");
                    list.clear();
                    downloadToTable(rs);

                } catch (SQLException ex) {
                    
                    System.out.println(ex);
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
        dd.setOnAction(e ->{
            tfIdCour.setText(returnIdCourse(dd.getSelectionModel().getSelectedItem().toString()));
        });
        refreshTable();
        Scene value = new Scene(bp2, 966, 666);
        primaryStage.setScene(value);
        primaryStage.setTitle("Grade");
        bp2.setId("id1");
        bp2.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean check(int i) {

        int x = 0;
        try {
            int email = Integer.valueOf(tfGs.getText());
        } catch (NumberFormatException e) {
            error6.setText("enter number ");
            error6.setFill(Color.RED);
            return false;
        }
        try {
            int ee = Integer.valueOf(tfIdStu.getText());
        } catch (NumberFormatException e) {
            error8.setText("enter number ");
            error8.setFill(Color.RED);
            return false;
        }
        if (tfIdStu.getText().equals("") || Integer.valueOf(tfIdStu.getText()) <= 0) {
            error8.setText("error");
            error8.setFill(Color.RED);
            x = 2;
        } else {
            error8.setText("      ");
        }
        if (tfIdCour.getText().equals("")) {
            error1.setText("error");
            error1.setFill(Color.RED);
            x = 2;
        } else {
            error1.setText("      ");
        }

//         if(tfAdderess.getText().equals("")){error4.setText("error");
//             error4.setFill(Color.RED);x=2;}else{error4.setText("      ");
//         }
        if (tfGs.getText().equals("")) {
            error6.setText("error");
            error6.setFill(Color.RED);
            x = 2;
        } else {
            error6.setText("      ");
        }

        if (tfGf.getText().equals("")) {
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
            int ee = Integer.valueOf(tfGf.getText());
        } catch (NumberFormatException e) {
            error7.setText("enter number ");
            error7.setFill(Color.RED);
            return false;
        }
//        if (tfGf.getText().contains(".com") && tfGf.getText().contains("@")) {
//        } else {
//            error6.setText("example@exa.com ");
//            error6.setFill(Color.RED);
//            return false;
//        }
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
           String query = "select grade.id_student,grade.Id_course,gradeseme,gradefinal,fname,lname,name_course from (grade left outer join student on(grade.id_student=student.id_student))left outer join course on(grade.id_course=course.id_course)   ";
 
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
                int idstu;
                String idcour;
               
                idstu = Integer.valueOf(rs.getString("id_student"));
                idcour = rs.getString("id_course");
                
                System.out.println("2");
                int gradeseme = Integer.valueOf(rs.getString("gradeseme"));
                int gradefinal = Integer.valueOf(rs.getString("gradefinal"));
                String namestu = rs.getString("fname")+" "+rs.getString("Lname");
                String namecour = rs.getString("name_course");
//                String address=rs.getString("address");
//                int phone = Integer.valueOf(rs.getString("phone"));
                
                list.add(new FunStudent(idcour, idstu, gradeseme, gradefinal, namestu, namecour));
            }
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("sdk");
        }

    }

    public boolean checkStudent(int l) {
        try {
            pst = con.prepareStatement("select * from Student where Id_student=?");
            pst.setInt(1, l);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean checkAllId2(String l,int i) {
        try {
            pst = con.prepareStatement("select * from grade where id_course =? and id_student =? ");
            pst.setString(1, l);
            pst.setInt(2, i);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
               error8.setText("id_student found before ");
                    error8.setFill(Color.RED);
                    error1.setText("id_course found before");
                    error1.setFill(Color.RED);
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return true;
    }
    public boolean checkAllId(String l,int i) {
        try {
            pst = con.prepareStatement("select * from student  left outer join course on(true) where id_course =? and id_student =? ");
            pst.setString(1, l);
            pst.setInt(2, i);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
               
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
         if(checkStudent(i)){
                    error1.setText("tcourse not found ");
                    error1.setFill(Color.RED);
                }
                else{
                    error8.setText("studen not found ");
                    error8.setFill(Color.RED);
                }
        return false;
    }
 public void downnamecourse() {
        try {
            pst = con.prepareStatement("select name_course from course ");
            

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                s.add(rs.getString("name_course"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  public String returnIdCourse(String nameCourse){
        try {
            pst = con.prepareStatement("select id_course from course where name_course=? ");
            pst.setString(1, nameCourse);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("id_course");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

