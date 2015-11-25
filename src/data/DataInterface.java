/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Nilanjan Daw
 */
public interface DataInterface {
    
    /**
     Method to initialise the class and start reading data.
     * @param args Required Arguments
     */
    public void init(String[] args);

    /**
     * Getter Method for SilkAir flight ArrayList
     * @return SilkAir Flight ArrayList
     */
    public ArrayList getFlightListSilkAir();

    /**
     * Getter Method for SpiceJet flight ArrayList
     * @return SpiceJetAir Flight ArrayList
     */
    public ArrayList getFlightListSpiceJet();
}
