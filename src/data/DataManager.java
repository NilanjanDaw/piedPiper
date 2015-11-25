/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;


/**
 *
 * @author Nilanjan
 */
public class DataManager implements DataInterface {
    
    public boolean fileReadComplete = false;
    private ArrayList flightListSilkAir;
    private ArrayList flightListSpiceJet;
    private final String[][] lookUpTable;

    public DataManager() {
        this.lookUpTable = new String[][]{
            {"Monday", "mon"}, 
            {"Tuesday", "tue"}, 
            {"Wednesday", "wed"},
            {"Thursday","thu"},
            {"Friday", "fri"},
            {"Saturday", "sat"},
            {"Sunday", "sun"}
        };
    }

    /**
     * Getter Method for SilkAir flight ArrayList
     * @return SilkAir Flight ArrayList
     */
    @Override
    public ArrayList getFlightListSilkAir() {
        return flightListSilkAir;
    }
    
    /**
     * Getter Method for SpiceJet flight ArrayList
     * @return SpiceJetAir Flight ArrayList
     */
    @Override
    public ArrayList getFlightListSpiceJet() {
        return flightListSpiceJet;
    }
    /**
     * Reads the SilkAir .csv file, processes it and returns an ArrayList of Flights
     * @param path The path to the .csv file
     * @return Returns an ArrayList of FLights
     */
    private ArrayList<Flight> fileReaderSilkAir(String path) {
        
        BufferedReader reader = null;
        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));
            String read;
            LocalDate[] effectiveDate = new LocalDate[2];
            String effect = reader.readLine();
            reader.readLine();reader.readLine();
            
            StringTokenizer stringTokenizer = new StringTokenizer(new StringTokenizer(effect, "|").nextToken(), " ");    
            stringTokenizer.nextToken();
            String effectiveFrom = stringTokenizer.nextToken() + " " +
                    stringTokenizer.nextToken().substring(0, 3) + " " +
                    stringTokenizer.nextToken();
            effectiveDate[0] = parseDate(effectiveFrom, 1);
            
            stringTokenizer.nextToken();
            String effectiveTo = stringTokenizer.nextToken() + " " +
                    stringTokenizer.nextToken().substring(0, 3) + " " +
                    stringTokenizer.nextToken();
            effectiveDate[1] = parseDate(effectiveTo, 1);
           
            while((read = reader.readLine()) != null && !read.equals("-1") ) {
                     Flight flight = parseLine(read, "Singapore");
                     flight.setEffectiveDate(effectiveDate);
                     flight.setType("International");
                     flight.setVia(null);
                     //printFlight(flight);
                     flightListSilkAir.add(flight);
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found. Check the path");
        } catch (IOException ex) {
            System.out.println("IO Error! Terminating file read");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return flightListSilkAir;
    }
    /**
     * Reads the SpiceJet .csv file, processes it and returns an ArrayList of Flights
     * @param path The path to the .csv file
     * @return Returns an ArrayList of FLights
     */
    private ArrayList fileReaderSpiceJet(String path) {
        BufferedReader reader = null;
        String read;
        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));
            reader.readLine();reader.readLine();
            reader.readLine();reader.readLine();
            reader.readLine();
            while((read = reader.readLine()) != null && !read.equals("-1") ) {
                Flight flight = parseLine(read);
                flight.setType("Domestic");
                flightListSpiceJet.add(flight);
                //printFlight(flight);
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if(reader!= null)
                    reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return flightListSpiceJet;
    }

    /**
     * Method to tokenize and parse a single line of data sent to it
     * @param data Vararg containing the line sent to the method to be processed and any other useful information
     * @return An instance of Flight class
     */
    private Flight parseLine(String... data) {
        
        Flight flight = new Flight();
        StringTokenizer flightData = new StringTokenizer(data[0], "|");
        /**
         * Start of Changed portion
         */
        String departureCity = flightData.nextToken().toUpperCase();
        flight.setDepartureCity(
                (data.length > 1) ? 
                        departureCity.substring(0,departureCity.length() - 6).trim() :
                        departureCity);
        /**
         * end of changed portion
         */
        flight.setArrivalCity((data.length > 1) ? data[1].toUpperCase() : flightData.nextToken());
        String[] scheduleData = flightData.nextToken().split(",");
        ArrayList<String> schedule = new ArrayList<>();
        for (String scheduleData1 : scheduleData) {
            int x = checkTable(scheduleData1);
            if(x < 0) {
                if(scheduleData1.equalsIgnoreCase("Daily"))
                    for (String[] day : lookUpTable) {
                        schedule.add(day[0]);
                }
            }
            else
                schedule.add(lookUpTable[x][0]);
        }
        flight.setDaysOfWeek((String[]) schedule.toArray(new String[0]));
        flight.setFlightNumber(flightData.nextToken());
        if (data.length > 1) {
            String[] timeData = flightData.nextToken().split("/");
            flight.setDepartureTime(parseTime(timeData[0], 1));
            flight.setArrivalTime(parseTime(timeData[1], 1));
        }
        else {
            flight.setDepartureTime(parseTime(flightData.nextToken(), 2));
            flight.setArrivalTime(parseTime(flightData.nextToken(), 2));
            String viaData = flightData.nextToken();
            if(!viaData.equals("-")) {
                StringTokenizer st = new StringTokenizer(viaData, ",");
                String[] via = new String[st.countTokens()];
                int i = 0;
                while(st.hasMoreTokens())
                    via[i++] = st.nextToken();
                flight.setVia(via);
            }
            else 
                flight.setVia(null);
            String dateFrom = flightData.nextToken();
            dateFrom = dateFrom.substring(0, 4) + dateFrom.substring(4).toLowerCase();
            String dateTo = flightData.nextToken();
            dateTo = dateTo.substring(0, 4) + dateTo.substring(4).toLowerCase();
            LocalDate[] effectiveDate = {parseDate(dateFrom, 2), 
                parseDate(dateTo, 2)};
            flight.setEffectiveDate(effectiveDate);
        }
        return flight; 
    }
    /**
     * A method to standardise the different formats of Day notations
     * @param scheduleData1 The day name to be standardised
     * @return The standard day name
     */
    private int checkTable(String scheduleData1) {
        for(int i = 0; i<lookUpTable.length; i++)
            if(lookUpTable[i][0].equalsIgnoreCase(scheduleData1.trim()) || 
                    lookUpTable[i][1].equalsIgnoreCase(scheduleData1.trim()))
                return i;
        return -1;
    }
    /**
     * Temporary method to print a Flight object
     * @param flight The object to be printed
     */
    private void printFlight(Flight flight) {
        try {
            System.out.println(" " + flight.getDepartureCity() + " |" + flight.getArrivalCity() + " |" +flight.getFlightNumber());
            for (String daysOfWeek : flight.getDaysOfWeek()) {
                System.out.print(daysOfWeek + " ");
            }
            System.out.println("\n" + flight.getArrivalTime().toString() + "| "
                    + flight.getDepartureTime().toString() + "| "
                    + flight.getType()
            );
            System.out.println(flight.getEffectiveDate()[0] + "| " 
                    + flight.getEffectiveDate()[1] + "| "
            );
            for(String viaCity: flight.getVia())
                System.out.print(viaCity + "| ");
            System.out.println();
        } catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Method to parse and convert a string into a LocalTime class instance
     * @param timeData The string containing the data to be parsed
     * @param type The type of formatting to be used
     * @return An instance of LocalTime class
     */
    private LocalTime parseTime(String timeData, int type) {
        try {
            LocalTime time;
            DateTimeFormatter formatter = null;
            String timeString = null;
            if(type == 1) {
                StringTokenizer timeToken = new StringTokenizer(timeData, "+");
                String token = timeToken.nextToken();
                timeString = token.substring(0, 2) + ":" + token.substring(2) + ":" + "00";
                formatter = DateTimeFormatter.ISO_TIME;
            }
            else if(type == 2){
                timeString = timeData.substring(0, timeData.length() - 3) + ":00" 
                        + timeData.substring(timeData.length() - 3);
                formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            }
            time = LocalTime.parse(timeString, formatter);
            return time;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    /**
     * Method to parse and convert a string into a LocalDate class instance
     * @param dateData The string containing the data to be parsed
     * @param type The type of formatting to be used
     * @return An instance of LocalDate class
     */
    private LocalDate parseDate(String dateData,int type) {
        try {
            DateTimeFormatter formatter;
            if(type == 1) 
                formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.UK);
            else
                formatter = DateTimeFormatter.ofPattern("dd MMM yy", Locale.ENGLISH);
            LocalDate date;
            date = LocalDate.parse(dateData, formatter);
            return date;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Method to initialise the class and start reading data.
     * @param args Required Arguments
     */
    @Override
    public void init(String[] args) {
        try {
            if(!fileReadComplete) {
                
                flightListSilkAir = new ArrayList();
                fileReaderSilkAir(args[0]);
                flightListSpiceJet = new ArrayList();
                fileReaderSpiceJet(args[1]);
                fileReadComplete = true;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            fileReadComplete = false;
        }
    }
    
    public static void main(String[] args) {
        DataManager obj = new DataManager();
        obj.init(args);
        System.out.println(obj.flightListSilkAir.size());
        System.out.println(obj.flightListSpiceJet.size());
    }

}
