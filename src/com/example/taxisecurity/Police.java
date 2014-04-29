package com.example.taxisecurity;

import java.util.List;

public class Police {
	 //private variables
    int Id;
    String PoliceName;
    String PhoneNumber;
    String PoliceArea;
    
    
     
    // Empty constructor
    public Police(){
         
    }
    // constructor
    public Police(int id, String police_name, String phone_number){
        this.Id = id;
        this.PoliceName = police_name;
        this.PhoneNumber = phone_number;
    }
     
    // constructor
    public Police(String police_name, String phone_number){
        this.PoliceName = police_name;
        this.PhoneNumber = phone_number;
    }
    // getting ID
    public int getID(){
        return this.Id;
    }
     
    // setting id
    public void setID(int id){
        this.Id = id;
    }
     
    // getting name
    public String getName(){
        return this.PoliceName;
    }
     
    // setting name
    public void setName(String police_name){
        this.PoliceName = police_name;
    }
     
    // getting phone number
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }
     
    // setting phone number
    public void setPhoneNumber(String phone_number){
        this.PhoneNumber = phone_number;
    }
    
 // Adding new contact
    public void addPolice(Police police) {}
     
    // Getting single contact
    public Police getPolice(int Id) {
		return null;}
     
    // Getting All Contacts
    public List<Police> getAllPolice() {
		return null;}
     
    // Getting contacts Count
    public int getPoliceCount() {
		return (Integer) null;}
    // Updating single contact
    public int updatePolice(Police police) {
		return Id;}
     
    // Deleting single contact
    public void deletePolice(Police police) {}
}

