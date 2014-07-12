package com.example.policeDetails;

public class Police_Details {
	//private variables
    int _id;
    String _name;
    String _phone_number;
    double _lon;
    double _lan;

    // Empty constructor
    public Police_Details(){

    }
    // constructor
    public Police_Details(int id, String name, String _phone_number,double lan,double lon){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._lan = lan;
        this._lon = lon;
    }

    // constructor
    public Police_Details(String name, String _phone_number,double lan,double lon){
        this._name = name;
        this._phone_number = _phone_number;
        this._lan = lan;
        this._lon = lon;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }
	
    public double getLan() {
		// TODO Auto-generated method stub
		return this._lan;
	}
	//setting latitude
	public void setLan(double lan){
        this._lan = lan;
    }
	
	
	public double getLon() {
		// TODO Auto-generated method stub
		return this._lon;
	}
	//setting longitude
			public void setLon(double lon){
		        this._lon = lon;
		    }

}
