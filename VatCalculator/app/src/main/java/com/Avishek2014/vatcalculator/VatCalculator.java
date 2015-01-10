package com.Avishek2014.vatcalculator;

/**
 * Created by Avishek on 07-Jan-15.
 */
public class VatCalculator {
    private long id;
    private String dateTime;
    private float grandTotal;
    private float totalVat;

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateTime(){
        return dateTime;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public float getGrandTotal(){
        return grandTotal;
    }

    public void setGrandTotal(float grandTotal){
        this.grandTotal = grandTotal;
    }

    public float getTotalVat(){
        return totalVat;
    }

    public void setTotalVat(float totalVat){
        this.totalVat = totalVat;
    }

    @Override
    public String toString(){
        return dateTime+"\nGrand total: " + grandTotal + " | Total VAT: " + totalVat;
    }
}
