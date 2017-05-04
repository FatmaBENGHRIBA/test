package com.example.bji.smssend;

/**
 * Created by bji on 01/03/2017.
 */
public class AccesValeur {
    //private variables
    String  X;
    String Y;
    String Z;

    // Empty constructor
    public AccesValeur (){

    }
    // constructor
    public AccesValeur (String X,String Y,String Z){
        this.X=X;
        this.Y=Y;
        this.Z=Z;
    }
    public String getX(){
        return this.X;
    }

    // setting id
    public void setX(String X){
        this.X=X ;
    }

    // getting ID
    public String getY(){
        return this.Y;
    }

    // setting id
    public void setY(String Y){
        this.Y=Y ;
    }
    public String getZ(){
        return this.Z;
    }

    // setting id
    public void setZ (String Z){
        this.Z = Z;
    }



}
