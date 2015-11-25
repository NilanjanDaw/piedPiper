/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import data.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;
/**
 * @author Priyanjit Dey
 */
public class ManageFlight implements LogicInterface {
    /*
    ticketNo=Number of passengers. src=Source. departDate=Date of journey. dayOfWeek=Day of Journey
    International flight valid through firstDate to lastDate
    */
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    BookingManager bm=new BookingManager();
    
    private int ticketNo;
    private String src,dayOfWeek;
    private LocalDate departDate;
    final DateTimeFormatter dtf, tmf;
    final LocalDate firstDate;
    final LocalDate lastDate;
    final LocalTime temptime;
    private ArrayList<Flight> SpiceJet;
    private ArrayList<Flight> SilkAir;
    private ArrayList<Flight> flightFoundSpice,flightFoundSilk;
    private ArrayList<String> bookedFlightSpice,bookedFlightSilk;
    private ArrayList<String> noSilk,noSpice;
    private boolean InputSuccess, bookNext;

     
    public ManageFlight() {
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tmf=DateTimeFormatter.ISO_TIME;
        firstDate = LocalDate.parse("2015-03-29",dtf);
        lastDate = LocalDate.parse("2015-10-25",dtf);
        temptime = LocalTime.parse("21:00:00",tmf);
        SpiceJet = new ArrayList<>();
        SilkAir = new ArrayList<>();
    }
    /*
    Input Validation is done in this method.
    */
    boolean checkInput(){
        if(ticketNo>10 || ticketNo<1){
            System.out.println("Number of Passengers must be between 1-10");
            return false;
        }
        if(!src.equals("DELHI") || !src.equals("MUMBAI") || !src.equals("PUNE") ){
            System.out.println("No such source present.");
            return false;
        }
        if(departDate.isBefore(firstDate)==true || departDate.isAfter(lastDate)==true){
            System.out.println("Service available between 29-03-2015 to 24-101-2015.Please Re-enter:");
            return false;
        }
        return true;
    }
    /*
    Ensure successful input of data from user.
    */
    boolean takeInput(){
        String s;
        try{
            System.out.println("Welcome to the Flight Reservation System!");
            System.out.println("-----------------------------------------");
            System.out.println("-----------------------------------------");
            System.out.println("Please enter the Date of Travel(yyyy-mm-dd):");
            s=br.readLine();
            departDate=LocalDate.parse(s,dtf);
            System.out.println("Please enter Source City (DELHI, MUMBAI or PUNE):");
            src=br.readLine().trim();
            src=src.toUpperCase();
            System.out.print("Destination is Singapore.\nHow many tickets?(1-10)\n");
            ticketNo=Integer.parseInt(br.readLine().trim());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    /**
     * Input is taken here
     */
    
    @Override
    public void confirmBook(int ch, int mode){
        try{
            System.out.println("Press 1 to confirm. 0 to cancel");
            int confirm;
            confirm = (mode == 0)? Integer.parseInt(br.readLine().trim()): 1;
            Flight flight;
            String id;
            if(confirm==1){
                flight=flightFoundSpice.get(ch);
                bm.writeFileSpice(flight.getFlightNumber(),noSpice.get(ch),ticketNo+"");
                flight=flightFoundSilk.get(ch);
                bm.writeFileSilk(flight.getFlightNumber(),noSilk.get(ch),ticketNo+"");
                System.out.println("Your Ticket is Booked.");
            }
        }catch(IOException | NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }
    
    
    /*
    Display the search results.
    */
    public void showList(){
        if(flightFoundSpice.isEmpty()){
            System.out.println("No Flights available matching your requirements");
        }
        else{
            System.out.println("\tID\t\tSource\t\tDestination\t\tDeparture\t\tArrival");
            int i,cnt=1,ch;
            Flight flight;
            for(i=0;i<flightFoundSpice.size();i++){
                
                flight=flightFoundSpice.get(i);
                System.out.print(cnt+". "+flight.getFlightNumber()+"\t\t"+flight.getDepartureCity()+"\t\t"+flight.getArrivalCity()+"\t\t");
                System.out.println(flight.getDepartureTime()+"\t\t"+flight.getArrivalTime());
                flight=flightFoundSilk.get(i);
                System.out.print("   "+flight.getFlightNumber()+"\t\t"+flight.getDepartureCity()+"\t\t"+flight.getArrivalCity()+"\t\t");
                System.out.println(flight.getDepartureTime()+"\t\t"+flight.getArrivalTime());
                cnt++;
            }
            try{
                System.out.println("Enter the flight no. you want to book.");
                ch=Integer.parseInt(br.readLine());
                confirmBook(ch-1, 0);
            }catch(IOException | NumberFormatException e){
                System.out.println(e.getMessage());
            }
        }
    }
    /*
    Date/Time Manipulation
    */
    String makeDate(Calendar cal){
        String temp,y,m,d;
        int yy,mm,dd;
        yy=cal.get(Calendar.YEAR);mm=cal.get(Calendar.MONTH)+1;dd=cal.get(Calendar.DAY_OF_MONTH);
        y=yy+"";
        if(mm<10){
            m="0"+mm+"";
        }
        else m=mm+"";
        
        if(dd<10){
            d="0"+dd+"";
        }
        else d=dd+"";
        
        temp=y+"-"+m+"-"+d;
        return temp;
    }
    Calendar setDate(LocalDate dt,LocalTime tt){
        Calendar cal=Calendar.getInstance();
        cal.set(dt.getYear(),dt.getMonthValue()-1,dt.getDayOfMonth(),tt.getHour(),tt.getMinute(),tt.getSecond());
        return cal;
    }
    Calendar setDate(Calendar dt,LocalTime tt){
        Calendar cal=Calendar.getInstance();
        cal.set(dt.get(Calendar.YEAR),dt.get(Calendar.MONTH),dt.get(Calendar.DAY_OF_MONTH),tt.getHour(),tt.getMinute(),tt.getSecond());
        return cal;
    }
    /*
    Check Available Tickets
    */
    int checkAvailabilitySpice(String flightNo,String depDate){
        int tckt=15,ftckt,i,cnt=0; String tempFlight,id,date;
        StringTokenizer st=null;
        for(i=0;i<bookedFlightSpice.size();i++){
            tempFlight=bookedFlightSpice.get(i);
            st=new StringTokenizer(tempFlight,"|");
            id=st.nextToken().trim();date=st.nextToken().trim();
            if(id.equals(flightNo.trim()) && depDate.equals(date.trim())){
                ftckt=Integer.parseInt(st.nextToken().trim());cnt+=ftckt;
            }
        }
        return tckt-cnt;
    }
    int checkAvailabilitySilk(String flightNo,String depDate){
        int tckt=15,ftckt,i,cnt=0; String tempFlight,id,date;
        StringTokenizer st=null;
        for(i=0;i<bookedFlightSilk.size();i++){
            tempFlight=bookedFlightSilk.get(i);
            st=new StringTokenizer(tempFlight,"|");
            id=st.nextToken().trim();date=st.nextToken().trim();
            if(id.equals(flightNo) && date.equals(depDate)){
                ftckt=Integer.parseInt(st.nextToken().trim());cnt+=ftckt;
            }
        }
        return tckt-cnt;
    }
    /*
    Search available flights based on given information.
    */
    void searchFlightSilk(Flight curFlightSpice){
        Flight curFlightSilk;
        int j,tcktAvail;
        Calendar begDate,endDate,tempDate1,tempDate2;
        LocalDate dayweeksilk,checkBeg,checkEnd;
        String weekdaysilk,day;
        LocalTime flightTime,auxFlightTime;
        flightTime=curFlightSpice.getArrivalTime();
        
        begDate=setDate(departDate,flightTime);endDate=setDate(departDate,flightTime);
        begDate.add(Calendar.HOUR_OF_DAY, 2);endDate.add(Calendar.HOUR_OF_DAY, 6);
        checkBeg=LocalDate.parse(makeDate(begDate),dtf);
        checkEnd=LocalDate.parse(makeDate(endDate),dtf);
        
        if(checkBeg.isBefore(lastDate)){
            if(checkEnd.isAfter(lastDate) || checkEnd.isEqual(lastDate)){
                endDate.set(departDate.getYear(),departDate.getMonthValue()-1,departDate.getDayOfMonth(),23,59,59);
            }
            
            for(j=0;j<SilkAir.size();j++){
                curFlightSilk=SilkAir.get(j);
                if(curFlightSpice.getArrivalCity().equals(curFlightSilk.getDepartureCity())){
                    auxFlightTime=curFlightSilk.getDepartureTime();
                    tempDate1=setDate(begDate,auxFlightTime);tempDate2=setDate(endDate,auxFlightTime);
                    
                    if((tempDate1.before(endDate) && tempDate1.after(begDate))){
                        weekdaysilk=makeDate(tempDate1);
                        dayweeksilk=LocalDate.parse(weekdaysilk,dtf);
                        day=dayweeksilk.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                        for(String srr:curFlightSilk.getDaysOfWeek()){
                            if(day.equals(srr)){
                                tcktAvail=checkAvailabilitySilk(curFlightSilk.getFlightNumber(),makeDate(tempDate1));
                                if(tcktAvail>=ticketNo){
                                    flightFoundSilk.add(curFlightSilk);noSpice.add(departDate+"");
                                    flightFoundSpice.add(curFlightSpice);noSilk.add(makeDate(tempDate1));
                                    break;
                                }
                            }   
                        }
                    }
                    
                    if(tempDate1.compareTo(tempDate2)!=0 && (tempDate2.before(endDate) && tempDate2.after(begDate))){
                        weekdaysilk=makeDate(tempDate2);
                        dayweeksilk=LocalDate.parse(weekdaysilk,dtf);
                        day=dayweeksilk.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                        for(String srr:curFlightSilk.getDaysOfWeek()){
                            if(day.equals(srr)){
                                tcktAvail=checkAvailabilitySilk(curFlightSilk.getFlightNumber(),makeDate(tempDate2));
                                if(tcktAvail>=ticketNo){
                                    flightFoundSilk.add(curFlightSilk);noSpice.add(departDate+"");
                                    flightFoundSpice.add(curFlightSpice);noSilk.add(makeDate(tempDate2));
                                    break;
                                }
                            } 
                        }
                    }
                    
                }
            }
        }
    }
    
    @Override
    public void searchFlight(){
        flightFoundSpice=new ArrayList<>();
        flightFoundSilk=new ArrayList<>();
        noSilk=new ArrayList<>();noSpice=new ArrayList<>();
        
        Flight curFlightSpice;
        LocalDate arDate[];
        int i,ticketAvailable;
        dayOfWeek=departDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        
        bookedFlightSilk=bm.readFileSilk();
        bookedFlightSpice=bm.readFileSpice();
        
        for(i=0;i<SpiceJet.size();i++){
            curFlightSpice=SpiceJet.get(i);
            arDate=curFlightSpice.getEffectiveDate();
            
            if(src.equals(curFlightSpice.getDepartureCity())){
                if((departDate.isAfter(arDate[0]) && departDate.isBefore(arDate[1])) || departDate.isEqual(arDate[0]) || departDate.isEqual(arDate[1])){
                    for(String sr:curFlightSpice.getDaysOfWeek()){
                        if(dayOfWeek.equals(sr)){
                            ticketAvailable=checkAvailabilitySpice(curFlightSpice.getFlightNumber(),departDate+"");
                            if(ticketAvailable>=ticketNo){
                                searchFlightSilk(curFlightSpice);break;
                            }
                        }
                    }
                }
            }
        }
        //showList();
    }
    
    /*
    Initialisation and retrieving flight information from the DataManager Class.
    */
    void getValues(String[] args){
        DataManager dm=new DataManager();
        dm.init(args);bm.initialize();
        SilkAir=dm.getFlightListSilkAir();
        SpiceJet=dm.getFlightListSpiceJet();
    }
    void getValues(DataManager manager) {
        bm.initialize();
        SilkAir=manager.getFlightListSilkAir();
        SpiceJet=manager.getFlightListSpiceJet();
    }
    
    /*
    To continue booking or not.
    */
    private boolean bookNext(){
        int ch;
        try{
            System.out.print("Please Selct your Choice:\n1.Book Another Ticket\n2.Exit\n");
            ch=Integer.parseInt(br.readLine());
            return ch==1;
        }catch(IOException | NumberFormatException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    void clearEverything(){
        flightFoundSilk.clear();
        flightFoundSpice.clear();
        bookedFlightSpice.clear();bookedFlightSilk.clear();
        noSilk.clear();noSpice.clear();
    }
    
    public void init(DataManager manager) {
        getValues(manager);
        
    }
    public void run() {
        
        while(true){
            if(InputSuccess == true){
                searchFlight();
                if(bookNext == false){
                    break;
                }
            }
            else{
                System.out.println("Please Re-enter the information. ");
                continue;
            }
            clearEverything();
        }
        System.out.println("Thank you");
    }
    
    
    public static void main(String args[]) throws IOException {
        ManageFlight mm=new ManageFlight();
        boolean InputSuccess;
        mm.getValues(args);
        while(true){
            InputSuccess=mm.takeInput();
            if(InputSuccess==true){
                mm.searchFlight();
                if(mm.bookNext()==false){
                    break;
                }
            }
            else{
                System.out.println("Please Re-enter the information. ");
                continue;
            }
            mm.clearEverything();
        }
        System.out.println("Thank you");
    }

    public DateTimeFormatter getDtf() {
        return dtf;
    }

    public LocalDate getDepartDate() {
        return departDate;
    }

    public void setDepartDate(LocalDate departDate) {
        this.departDate = departDate;
    }

    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
    
    public boolean isInputSuccess() {
        return InputSuccess;
    }

    public void setInputSuccess(boolean InputSuccess) {
        this.InputSuccess = InputSuccess;
    }

    public boolean isBookNext() {
        return bookNext;
    }

    public void setBookNext(boolean bookNext) {
        this.bookNext = bookNext;
    }

    @Override
    public ArrayList<Flight> getFlightFoundSpice() {
        return flightFoundSpice;
    }

    @Override
    public ArrayList<Flight> getFlightFoundSilk() {
        return flightFoundSilk;
    }
   
}
