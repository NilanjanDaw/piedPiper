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
    
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    BookingManager bm=new BookingManager();
    
    /*
    **************************DECLARATION OF VARIABLES********************************
    */
    
    private int ticketNo;                             //Number of Passengers
    private String src,dayOfWeek;                     //Source, Day of Journey
    private LocalDate departDate;                     //Date of Journey
    final DateTimeFormatter dtf, tmf;                 //Formatters
    public final LocalDate firstDate;                        //Valid From
    public final LocalDate lastDate;                         //Valid Upto
    private ArrayList<Flight> SpiceJet;               //Stores list of SpiceJet Flights
    private ArrayList<Flight> SilkAir;                //Store list of SilkAir Flights
    private ArrayList<Flight> flightFoundSpice,flightFoundSilk;             //Stores Flights matching the given condtions
    private ArrayList<String> bookedFlightSpice,bookedFlightSilk;           //Stores booked History of Files
    private ArrayList<String> noSilk,noSpice;                               //Number of passengers in new booked ticket
    private boolean InputSuccess, bookNext;                                 
    //********************************END OF DECLARATION********************************
     
    public ManageFlight() {
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tmf=DateTimeFormatter.ISO_TIME;
        firstDate = LocalDate.parse("2015-10-01",dtf);
        lastDate = LocalDate.parse("2015-10-25",dtf);
        SpiceJet = new ArrayList<>();
        SilkAir = new ArrayList<>();
    }
    
    
    /*
    ***********************************INPUT*******************************************
    ***********************************************************************************
    checkInput: Checks if the information provided are correct and within range.
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
    takeInput: Takes Input from user and displays the menu.
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
    //*********************************END OF INPUT**********************************
    
    
    /**
     ***********************************BOOKING*************************************
     *******************************************************************************
     * confirmBook: Books the ticket as per the information provided by the user.
     */
    
    @Override
    public void confirmBook(int ch, int mode){
        try{
            System.out.println("Press 1 to confirm. 0 to cancel");
            int confirm;
            confirm = (mode == 0)? Integer.parseInt(br.readLine().trim()): 1;
            Flight flight;
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
    //***********************************END OF BOOKING*****************************************
    
    
    /*
    ***************************************DISPLAY FLIGHT INFO*************************************
    ***********************************************************************************************
    showList: Responsible for showing the information about the flights matching the requirements of the user.
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
    //******************************END OF DISPLAY FLIGHT INFO****************************************
    
    /*
    **********************************DATE/TIME MANIPULATION******************************************
    **************************************************************************************************
    makeDate: converts the date portion of a Calendar object into String.
    */
    String makeDate(Calendar cal){
        String temp,y,m,d;
        int yy,mm,dd;
        yy=cal.get(Calendar.YEAR);mm=cal.get(Calendar.MONTH)+1;dd=cal.get(Calendar.DAY_OF_MONTH);
        y=yy+"";
        if(mm<10){
            m="0"+mm+"";
        }else m=mm+"";
        
        if(dd<10){
            d="0"+dd+"";
        }else d=dd+"";
        
        temp=y+"-"+m+"-"+d;
        return temp;
    }
    /*
    setDate(LocalDate,Localtime): converts a LocalDate and a Localtime Object into Calendar object
    */
    Calendar setDate(LocalDate dt,LocalTime tt){
        Calendar cal=Calendar.getInstance();
        cal.set(dt.getYear(),dt.getMonthValue()-1,dt.getDayOfMonth(),tt.getHour(),tt.getMinute(),tt.getSecond());
        return cal;
    }
    /*
    setdate(Calendar,LocalTime): set the time of a Calendar object as that of a LocalTime object
    */
    Calendar setDate(Calendar dt,LocalTime tt){
        Calendar cal=Calendar.getInstance();
        cal.set(dt.get(Calendar.YEAR),dt.get(Calendar.MONTH),dt.get(Calendar.DAY_OF_MONTH),tt.getHour(),tt.getMinute(),tt.getSecond());
        return cal;
    }
    //**********************************END OF DATE/TIME MANIPULATION*******************************
    
    
    /*
    **********************************CHECK AVAILABILITY**********************************
    **************************************************************************************
    checkAvailibitySpice: checks the number of tickets available on a particular date on a SpiceJet Flight.
    */
    int checkAvailabilitySpice(String flightNo,String depDate){
        int tckt=15,bookedTckt,iterSpice,totalBookedTckt=0; String tempFlight,id,date;
        StringTokenizer st=null;
        for(iterSpice=0;iterSpice<bookedFlightSpice.size();iterSpice++){
            tempFlight=bookedFlightSpice.get(iterSpice);
            st=new StringTokenizer(tempFlight,"|");
            id=st.nextToken().trim();date=st.nextToken().trim();
            if(id.equals(flightNo.trim()) && depDate.equals(date.trim())){
                bookedTckt=Integer.parseInt(st.nextToken().trim());
                totalBookedTckt+=bookedTckt;
            }
        }
        return tckt-totalBookedTckt;
    }
    /*
    checkAvailabilitySilk: checks the number of tickets available on a particular date on a SilkAir Flight.
    */
    int checkAvailabilitySilk(String flightNo,String depDate){
        int tckt=15,bookedTckt,iterSilk,totalBookedTckt=0; String tempFlight,id,date;
        StringTokenizer st=null;
        for(iterSilk=0;iterSilk<bookedFlightSilk.size();iterSilk++){
            tempFlight=bookedFlightSilk.get(iterSilk);
            st=new StringTokenizer(tempFlight,"|");
            id=st.nextToken().trim();date=st.nextToken().trim();
            if(id.equals(flightNo) && date.equals(depDate)){
                bookedTckt=Integer.parseInt(st.nextToken().trim());
                totalBookedTckt+=bookedTckt;
            }
        }
        return tckt-totalBookedTckt;
    }
    //*********************************END OF CHECK AVAILABILITY**************************************
    
    
    /*
    **************************************SEARCH FUNCTIONS***********************************************
    *****************************************************************************************************
    getFinalFlightList: Checks if the two selected flights run on same day of week and stores information accordingly.
    */
    void getFinalFlightList(Flight curFlightSpice,Flight curFlightSilk,Calendar finalDate){
        String weekdaysilk,day;
        LocalDate dayweeksilk;
        int tcktAvail;
        weekdaysilk=makeDate(finalDate);
        dayweeksilk=LocalDate.parse(weekdaysilk,dtf);
        day=dayweeksilk.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        for(String srr:curFlightSilk.getDaysOfWeek()){
            if(day.equals(srr)){
                tcktAvail=checkAvailabilitySilk(curFlightSilk.getFlightNumber(),makeDate(finalDate));
                if(tcktAvail>=ticketNo){
                    flightFoundSilk.add(curFlightSilk);noSpice.add(departDate+"");
                    flightFoundSpice.add(curFlightSpice);noSilk.add(makeDate(finalDate));
                    break;
                }
            }   
        }
    }
    /*
    searchFlightSilk: Searches for a SilkAir Flight matching the user requirements. If found, it calls getFinalFlight method
                      for making final list of flights found.
    */
    void searchFlightSilk(Flight curFlightSpice){
        Flight curFlightSilk;
        int iterSilk;
        Calendar begDate,endDate,tempDate1,tempDate2;
        LocalDate checkBeg,checkEnd;
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
            
            for(iterSilk=0;iterSilk<SilkAir.size();iterSilk++){
                curFlightSilk=SilkAir.get(iterSilk);
                if(curFlightSpice.getArrivalCity().equals(curFlightSilk.getDepartureCity())){
                    auxFlightTime=curFlightSilk.getDepartureTime();
                    tempDate1=setDate(begDate,auxFlightTime);tempDate2=setDate(endDate,auxFlightTime);
                    
                    if((tempDate1.before(endDate) && tempDate1.after(begDate))){
                        getFinalFlightList(curFlightSpice,curFlightSilk,tempDate1);
                    }
                    
                    if(tempDate1.compareTo(tempDate2)!=0 && (tempDate2.before(endDate) && tempDate2.after(begDate))){
                        getFinalFlightList(curFlightSpice,curFlightSilk,tempDate2);
                    }
                }
            }
        }
    }
    
    /*
    searchFlight: search the available SpiceJet Flights according to the given conditions 
                  and calls searchFlightSilk for searching SilkAir Flights
    */
    @Override
    public void searchFlight(){
        flightFoundSpice=new ArrayList<>();
        flightFoundSilk=new ArrayList<>();
        noSilk=new ArrayList<>();noSpice=new ArrayList<>();
        
        Flight curFlightSpice;
        LocalDate arDate[];
        int iterSpice,ticketAvailable;
        dayOfWeek=departDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        
        bookedFlightSilk=bm.readFileSilk();
        bookedFlightSpice=bm.readFileSpice();
        
        for(iterSpice=0;iterSpice<SpiceJet.size();iterSpice++){
            curFlightSpice=SpiceJet.get(iterSpice);
            arDate=curFlightSpice.getEffectiveDate();
            
            if(src.equals(curFlightSpice.getDepartureCity())){
                if((departDate.isAfter(arDate[0]) && departDate.isBefore(arDate[1])) 
                || departDate.isEqual(arDate[0]) || departDate.isEqual(arDate[1])){
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
    }
    //******************************END OF SEARCH FUNCTIONS***************************************
    
    
    
    /*
    *********************************INFORMATION RETRIEVAL/CLEAR*********************************
    *********************************************************************************************
    getValues: retrieves the value of all the flights from the .csv files.
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
    clearEverything: responsible for clearing all the Arraylists.
    */
    void clearEverything(){
        flightFoundSilk.clear();
        flightFoundSpice.clear();
        bookedFlightSpice.clear();bookedFlightSilk.clear();
        noSilk.clear();noSpice.clear();
    }
    public void init(DataManager manager) {
        getValues(manager);
        
    }
    //****************************END OF INFORMATION RETRIEVAL/CLEAR********************************
    
    
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
        //mm.run();
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
