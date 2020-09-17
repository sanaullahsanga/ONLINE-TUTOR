package com.constructors.Model;

public class TutorModel {

//    int id;
//    String tutName;
//    String tutEmail;
    String tutPhoneNo;
    String tutAddress;
    String tutQualification;
    String tutExperience;
    String tutCity;
    String tutClass;
    String tutSubjects, tutGender;
//    int tutTiming;

    public TutorModel(){

    }

    public TutorModel(String tutPhoneNo, String tutAddress, String tutQualification, String tutExperience, String tutCity) {
        this.tutPhoneNo = tutPhoneNo;
        this.tutAddress = tutAddress;
        this.tutQualification = tutQualification;
        this.tutExperience = tutExperience;
        this.tutCity = tutCity;

    }

    public String getTutUserName() {
        return tutUserName;
    }

    public void setTutUserName(String tutUserName) {
        this.tutUserName = tutUserName;
    }

    public String getTutPassword() {
        return tutPassword;
    }

    public void setTutPassword(String tutPassword) {
        this.tutPassword = tutPassword;
    }

    String tutUserName, tutPassword;




    public String gettutGender() {
        return tutGender;

    }

    public void settutGender(String tutGender) {
        this.tutGender = tutGender;

    }


    public String gettutPhoneNo() {
        return tutPhoneNo;

    }

    public void settutPhoneNo(String tutPhoneNo) {
        this.tutPhoneNo = tutPhoneNo;

    }

    public String gettutAddress() {
        return tutAddress;

    }

    public void settutAddress(String tutAddress) {
        this.tutAddress = tutAddress;

    }

    public String getutQualification() {
        return tutQualification;

    }

    public void settutQualification(String tutQualification) {
        this.tutQualification = tutQualification;

    }

    public String gettutExperience() {
        return tutExperience;

    }

    public void settutExperience(String tutExperience) {
        this.tutExperience = tutExperience;

    }

    public String gettutCity() {
        return tutCity;

    }

    public void settutCity(String tutCity) {
        this.tutCity = tutCity;

    }

    public String gettutSubjects() {
        return tutSubjects;

    }

    public void settutSubjects(String tutSubjects) {
        this.tutSubjects = tutSubjects;

    }

    public String gettutClass() {
        return tutClass;

    }

    public void settutClass(String tutClass) {
        this.tutClass = tutClass;

    }

//    public int gettutTiming() {
//        return tutTiming;
//
//    }
//
//    public void settutTiming(int tutTiming) {
//        this.tutTiming = tutTiming;
//
//    }


}
