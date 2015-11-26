/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Prithviraj Dhar
 */
public class Flight {
    
    private String departureCity;         //String containing the departure city of the Flight.
    private String arrivalCity;         //String containing the arrival city of the Flight.
    private String[] daysOfWeek;    //String array containing the days of week the Flight is active
    private String flightNumber;       //String for holding the Flight number
    private LocalTime departureTime;           //Time object containing the departure time.
    private LocalTime arrivalTime;           //Time object containing the arrival time
    private String type;                //whether Domestic or International Flight
    private LocalDate[] effectiveDate;  //Effective Dates between which the schedule is valid
    private String[] via;               // The stops via which the Flight will go.
    
    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }
    public String getDepartureCity() {
        return departureCity;
    }

    void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String[] getDaysOfWeek() {
        return daysOfWeek;
    }

    void setDaysOfWeek(String[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate[] getEffectiveDate() {
        return effectiveDate;
    }

    void setEffectiveDate(LocalDate[] effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String[] getVia() {
        return via;
    }

    void setVia(String[] via) {
        this.via = via;
    }
    
}
