/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * Class that creates a jForm to display the ticket facilitating the print
 * option to the user
 * @author Anudeep Ghosh
 */
public class Ticket extends javax.swing.JFrame {

    /**
     * Creates new form Ticket
     */
    public Ticket() {
        initComponents();
    }
    /**
     * Method to receive data from another class
     */
    public void setData() {
        jLabel3.setText(uniqCode);
        jTextArea1.setText("\tHi "+name+",\n\tYour Flight Ticket\t" + uniqCode
                + "\n\tfor " + seats +" person/s, \n\ton " + date + " at " + time
                + " has been booked successfully.\n\tDeparting from "+source
                + "\n\tArriving at Singapore"
                +"\n\tVia "+via
                //+ ", on "+ date_&_time_of_arrival);
        );
        jTextArea1.setEditable(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        printButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Booking Number : : ");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel3.setBackground(new java.awt.Color(255, 255, 204));

        printButton.setText("Print");
        printButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(printButton))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 99, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(printButton)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    /**
     * 
     * @param evt receives any action performed on the printButton.
     */
    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        // TODO add your handling code here:
        printJTextArea();
        this.dispose();
    }//GEN-LAST:event_printButtonActionPerformed
    /**
     * Method to print the jTextArea
     */
    public void printJTextArea() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Confirmed_Ticket");
        printerJob.setPrintable((Graphics graphics, PageFormat pageFormat, int pageIndex) -> {
            if(pageIndex>0)
                return Printable.NO_SUCH_PAGE;
            Graphics2D graphics2D = (Graphics2D)graphics;
            graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            jTextArea1.paint(graphics2D);
            return Printable.PAGE_EXISTS;
        });
        if(printerJob.printDialog()==false)
            return;
        try{
            printerJob.print();
        }catch(PrinterException e){
            DisplayManager.infoBox("Error: Unable to print", "");
        }
    }
    /**
     * Method to generate unique code
     */
    public void generateCode() {
        int indexOfOpeningBracket = flight1.indexOf('(');
        int indexOfClosingBracket = flight1.indexOf(')');
        flight1 = flight1.substring(indexOfOpeningBracket+1,indexOfClosingBracket-1);
        indexOfOpeningBracket = flight2.indexOf('(');
        indexOfClosingBracket = flight2.indexOf(')');
        flight2 = flight2.substring(indexOfOpeningBracket+1,indexOfClosingBracket-1);
        uniqCode = "" + flight1 + flight2 + seats + date;//needs the number of seats left.
    }
    /**
     * 
     * @param name      of the user
     * @param source    place of departure
     * @param destination place of arrival
     * @param via       places connecting the total journey
     * @param flight1   name of the domestic flight
     * @param flight2   name of the international flight
     * @param seats     number of seats booked
     * @param date      date of journey
     * @param time      time of journey
     */
    public void getDataForTicket(String name, String source, String destination, String via, String flight1, String flight2, int seats, String date, String time) {
        this.name=name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
        this.source=source;
        this.destination=destination;
        this.flight1=flight1;
        this.flight2=flight2;
        this.seats=seats;
        this.date=date;
        this.time=time;
        this.via=via;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton printButton;
    // End of variables declaration//GEN-END:variables
    private String uniqCode;
    private String destination,source;
    private String via;
    private String name;
    private int seats;
    private String date;
    private String time;
    private String flight1,flight2;
}
