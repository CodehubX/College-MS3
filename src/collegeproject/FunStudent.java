/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeproject;

/**
 *
 * @author a
 */
public class FunStudent {
    String fName,lName,email,birth,dept,address;
    int id,year,phone;
    //doctor 
    String  nameDoc,address_Doc,email_Doc;
    int id_Doc,phone_Doc;
    //Departmnent
    String nameDept,emailDept,nameDoc2;
    int idDoctor2,phoneDept;
    //course
    String idCourse,nameCourse,deptCouse,descrip,nameDoc3,idDoc3;
    //grade
    String idCourseG,nameStudentG,nameCourseG;
    int idStudent,gradeSeme,gradeFinal;
    
    public FunStudent(){
        
    }
    public FunStudent(String idcourseg,int idstudent,int gradeseme,int gradefinal,String namestudG,String namecourseg){
        idStudent=idstudent;
        gradeSeme=gradeseme;
        gradeFinal=gradefinal;
        idCourseG=idcourseg;
        nameStudentG=namestudG;
        nameCourseG=namecourseg;
        
    }
    public FunStudent(String idCourse,String nameCourse,String deptCouse,String idDoc3,String descrip,String nameDoc3){
         this.nameCourse=nameCourse;
         this.deptCouse=deptCouse;
         this.descrip=descrip;
         this.idCourse=idCourse;
         this.idDoc3=idDoc3;
         this.nameDoc3=nameDoc3;
    }
    public FunStudent(String  nameDept,int idDoc2,int phoneDept,String emailDept,String nameDoc2){
        this.nameDept=nameDept;
        this.idDoctor2=idDoc2;
        this.phoneDept=phoneDept;
        this.emailDept=emailDept;
        this.nameDoc2=nameDoc2;
        
    }
    public FunStudent(int Id_Doctor,String Name_Doctor,String Address,String Email,int phone,int i){
        if(i==0){
         id_Doc = Id_Doctor;
        phone_Doc=phone;
        nameDoc=Name_Doctor;
        address_Doc=Address;
        email_Doc=Email;}
        else if(i==1){
            
        }
    }
    public FunStudent(int id,String fName,String lName,int year,String email,String birth,String dept,String address, int phone){
        this.id=id;
        this.fName=fName;
        this.lName=lName;
        this.year=year;
        this.email=email;
        this.birth=birth;
        this.dept=dept;
        this.address=address;
        this.phone=phone;
    }
    public String getFName(){
        return fName;
    }
    public String getLName(){
        return lName;
    }
    public String getEmail(){
        return email;
    }
    public int getYear(){
        return year;
    }
    public String getBirth(){
        return birth;
    }
    public String getDept(){
        return dept;
    }
    public String getAddress(){
        return address;
    }
    public int getPhone(){
        return phone;
    }
    public int getId(){
        return id;
    }
    //fun Doctor
    public int getIdDoc(){
        return id_Doc;
    }
    public String getNameDoc(){
        return nameDoc;
    }
    public String getEmailDoc(){
        return email_Doc;
    }
    public String getAddressDoc(){
        return address_Doc;
    }
    public int getPhoneDoc(){
        return phone_Doc;
    }
    //fun Department
    
    public String getNameDept(){
        return nameDept;
    }
    public int getIdDoctor2(){
        return idDoctor2;
    }
    public int getPhoneDept(){
        return phoneDept;
    }
    public String getEmailDept(){
        return emailDept;
    }
    public String getNameDoc2(){
        return nameDoc2;
    }
    //fun course
    
    public String getNameCourse(){
        return nameCourse;
    }
    public String getDeptCourse(){
        return deptCouse;
    }
    public String getDescription(){
        return descrip;
    }
    public String getIdCourse(){
        return idCourse;
    }
    public String getIdDoctor3(){
        return idDoc3;
    }
    public String getNameDoctor3(){
        return nameDoc3;
    }
    //grade student
    public int getIdStudent(){
        return idStudent;
    }
    public String getNameStudentG(){
        return nameStudentG;
    }
    public String getIdCourseG(){
        return idCourseG;
    }
    public int getGradeSeme(){
        return gradeSeme;
    }
    public int getGradeFinal(){
        return gradeFinal;
    }
   
    public String getNameCourseG(){
        return nameCourseG;
    }
   
}
