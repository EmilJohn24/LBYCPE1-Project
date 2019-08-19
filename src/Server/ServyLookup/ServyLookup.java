/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.ServyLookup;

import Server.ServerLog;
import Server.Structures.Building;
import Server.Structures.Floor;
import Server.Structures.Room;
import Server.Structures.StructureHandler;
import Server.Transaction.UserDatabase;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author emil
 */
public class ServyLookup extends javax.swing.JFrame {

    /**
     * Creates new form ServyLookup
     */
    private DefaultTableModel tableModel;
    private UserDatabase database;
    private StructureHandler structureHandler;
    private ArrayList<Building> buildings;
    private ArrayList<Floor> floors;
    private ArrayList<Room> rooms;
    private Building currentBuilding;
    private Floor currentFloor;
    private Room currentRoom;
    public ServyLookup(UserDatabase data, StructureHandler handler) {
        database = data;
        structureHandler = handler;
        initComponents();
        tableModel = (DefaultTableModel) displayTable.getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents

    public void refresh(){
        clearTable();
        int buildingCount = buildingCB.getSelectedIndex();
        int floorCount = floorCB.getSelectedIndex();
        int roomCount = roomCB.getSelectedIndex();
        //build();
        buildingCB.setSelectedIndex(buildingCount);
        tempNonEditingFlag = true;
        buildingCBActionPerformed(null);
        tempNonEditingFlag = true;
        floorCB.setSelectedIndex(floorCount);
        tempNonEditingFlag = true;
        floorCBActionPerformed(null);
        tempNonEditingFlag = true;
        roomCB.setSelectedIndex(roomCount);
        tempNonEditingFlag = true;
        roomCBActionPerformed(null);


    }


    private void initComponents() {

        buildingCB = new javax.swing.JComboBox<>();
        floorCB = new javax.swing.JComboBox<>();
        roomCB = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Building");
        jLabel1.setToolTipText("");
        buildingCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildingCBActionPerformed(evt);
            }
        });

        floorCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorCBActionPerformed(evt);
            }
        });
        
        roomCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                roomCBActionPerformed(evt);
            }
        });

        jLabel2.setText("Floor");

        jLabel3.setText("Room");

        displayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Date", "Time", "Email", "Duration"
            }
        ));
        jScrollPane1.setViewportView(displayTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(floorCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roomCB, 0, 160, Short.MAX_VALUE)
                        .addComponent(buildingCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buildingCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(floorCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roomCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //[month] [day] [year] [hour] [minute] [duration] [email] [reason]::::

    private void fillUpTables() throws TransformerException, IOException, SAXException {
        clearTable();
        if (currentRoom != null){
            String[] roomData = lookup();
            for (String data : roomData){
                if (data != null || data.trim().length() != 0) {
                    String[] dataArray = data.split(" ");
                    if (dataArray.length != 7) continue;
                    addRowToTable(Integer.parseInt(dataArray[0]), Integer.parseInt(dataArray[1]),
                            Integer.parseInt(dataArray[2]), Integer.parseInt(dataArray[3]), Integer.parseInt(dataArray[4]), dataArray[6], Integer.parseInt(dataArray[5]));
                }
            }
        }
    }

    private boolean tempNonEditingFlag = true;
    private void roomCBActionPerformed(ActionEvent evt) {
        clearTable();
        if (tempNonEditingFlag) {
            tempNonEditingFlag = false;
            return;
        }
        int index = roomCB.getSelectedIndex();
        //if (rooms.size() <= index || index <= -1 || floorCB.getSelectedIndex() < -1) return;
        currentRoom = rooms.get(index);

        try {
            fillUpTables();
        } catch (TransformerException e) {
            e.printStackTrace();
            ServerLog.globalLog("An error has occurred in parsing. Check stack trace above.");
        } catch (IOException e) {
            e.printStackTrace();
            ServerLog.globalLog("An error has occurred in parsing. Check stack trace above.");
        } catch (SAXException e) {
            e.printStackTrace();
            ServerLog.globalLog("An error has occurred in parsing. Check stack trace above.");
        }
    }

    private void floorCBActionPerformed(ActionEvent evt) {
        if (tempNonEditingFlag) {

            tempNonEditingFlag = false;
            return;
        }
        int index = floorCB.getSelectedIndex();
        if (floors.size() <= index || index <= -1 || buildingCB.getSelectedIndex() < -1) return;
        currentFloor = floors.get(index);
        rooms = currentFloor.getRooms();
        tempNonEditingFlag = true;
        roomCB.removeAllItems();
        for (Room r : rooms){
            addRoomName(r.getName());
        }
    }

    private void buildingCBActionPerformed(ActionEvent evt) {
        int index = buildingCB.getSelectedIndex();
        if (buildings.size() <= index || index <= -1) return;
        currentBuilding = buildings.get(index);
        floors = currentBuilding.getFloors();
        tempNonEditingFlag = true;
        floorCB.removeAllItems();
        for (Floor f : floors){
            addFloorNumber(f.getFloorCount());
        }
    }

    /**
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ServyLookup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ServyLookup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ServyLookup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ServyLookup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ServyLookup().setVisible(true);
//            }
//        });
//    }
    
    public void build(){
        buildings = structureHandler.parse();
        for (Building b : buildings){
            addBuildingName(b.getName());
        }

    }

    public void clearTable(){
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.removeRow(i);
        }
    }

    public String[] lookup() throws TransformerException, IOException, SAXException {
        String building = (String) buildingCB.getSelectedItem();
        Integer floor = (Integer) floorCB.getSelectedItem();
        String room = (String) roomCB.getSelectedItem();
        
       //TODO: Add lookup here
        Node roomInfo = structureHandler.lookup(building, floor, room);
        String stringifiedRoomInfo = roomInfo.getTextContent().trim();
        return stringifiedRoomInfo.split(StructureHandler.getReservationSep());
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
    
    public void addBuildingName(String name){
        buildingCB.addItem(name);
    }
    
    public void addFloorNumber(Integer number){
        floorCB.addItem(number);
    }
    
    public void addRoomName(String name){
        roomCB.addItem(name);
    }
    
    public void addRowToTable(int month, int day, int year, int hour, int minute, String email, int duration){
        LocalDateTime dateAndTime = LocalDateTime.of(year, month, day, hour, minute);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:ss");
        String date = dateAndTime.format(dateFormatter);
        String time = dateAndTime.format(timeFormatter);
        String durationStr = String.valueOf(duration);
        tableModel.addRow(new Object[]{date, time, email, durationStr});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> buildingCB;
    private javax.swing.JTable displayTable;
    private javax.swing.JComboBox<Integer> floorCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> roomCB;
    // End of variables declaration//GEN-END:variables
}
