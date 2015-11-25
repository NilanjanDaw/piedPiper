/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Priyanjit Dey
 */
public class BookingManager {
    final String pathSilk="bookedSilk.csv";
    final String pathSpice="bookedSpice.csv";
    BufferedReader br=null;
    BufferedWriter bw=null;
    File file=null,tempFile=null;
    ArrayList bookedSpice=new ArrayList<>();
    ArrayList bookedSilk=new ArrayList<>();
    
    void createNewFile(String path){
        try{
            file=new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void writeFileSilk(String id,String depart,String tckt){
        String readInfo;
        try{
            file=new File(pathSilk);
            br=new BufferedReader(new FileReader(file));
            bw=new BufferedWriter(new FileWriter(file,true));
            StringTokenizer st=null;
            while((readInfo=br.readLine())!=null){}
            bw.write(id.trim()+"|"+depart.trim()+"|"+tckt.trim());bw.newLine();
            
        }catch(Exception e){
            System.out.println(e.getMessage()+"silk");
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(BookingManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void writeFileSpice(String id,String depart,String tckt){
        String readInfo;
        try{
            file=new File(pathSpice);
            br=new BufferedReader(new FileReader(file));
            bw=new BufferedWriter(new FileWriter(file,true));
            StringTokenizer st=null;
            while((readInfo=br.readLine())!=null){}
            bw.write(id.trim()+"|"+depart.trim()+"|"+tckt.trim());bw.newLine();
            
        }catch(Exception e){
            System.out.println(e.getMessage()+"spice");
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(BookingManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public ArrayList<String> readFileSilk(){
        try{
            file=new File(pathSilk);
            br=new BufferedReader(new FileReader(file));
            String ticketInfo;
            while((ticketInfo=br.readLine())!=null ){
                bookedSilk.add(ticketInfo);
            }
        }catch(FileNotFoundException e) {
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(BookingManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bookedSilk;
    }
    public ArrayList<String> readFileSpice(){
        try{
            file=new File(pathSpice);
            br=new BufferedReader(new FileReader(file));
            String ticketInfo;
            while((ticketInfo=br.readLine())!=null ){
                bookedSpice.add(ticketInfo);
            }
        }catch(FileNotFoundException e) {
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(BookingManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bookedSpice;
    }
    public void initialize(){
        createNewFile(pathSpice);
        createNewFile(pathSilk);
    }
    
}
