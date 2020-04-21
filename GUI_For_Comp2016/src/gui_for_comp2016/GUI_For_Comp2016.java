/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_for_comp2016;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author leone
 */
public class GUI_For_Comp2016 {

    /**
     * @param args the command line arguments
     */
    
    public JFrame majorFrame;
    private JTextField orderSearchingTextField, orderMakingTextField, orderCancelingTextField;
    private JList imageTempList, availableBooksList, bookingRecordsList;
    private JScrollPane imageTempScrollPane, availableBooksScrollPane, bookingRecordsScrollPane;
    private JLabel availableBooksLabel, bookingRecordsLabel, orderSearchingLabel, orderMakingLabel, orderCancelingLabel;
    private JButton orderSearchingButton, orderMakingButton, orderCancelingButton;
    
    
    public GUI_For_Comp2016()
    {
        initialize();
    }
    
    private void initialize()
    {
        Font myFt = new Font("SansSerif", Font.PLAIN, 15);
        
        /*** ----------------------------------------------------------- ***/
        /**************************
        *** MajorFrame Setting  ***
        ***************************/
        majorFrame = new JFrame();
        majorFrame.setTitle("Testing");
        majorFrame.setBounds(100, 100, 751, 400);
        majorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        majorFrame.getContentPane().setLayout(null);
        /*** -------------------- ***/
        
        /*** ----------------------------------------------------------- ***/
        /**************************
        ******   Labels     *******
        ***************************/
        availableBooksLabel = new JLabel("Available Books: ");
        availableBooksLabel.setFont(myFt);
        availableBooksLabel.setBounds(10, 10, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(availableBooksLabel);
        
        bookingRecordsLabel = new JLabel("Booking Records: ");
        bookingRecordsLabel.setFont(myFt);
        bookingRecordsLabel.setBounds(10, 190, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(bookingRecordsLabel);
        
        orderSearchingLabel = new JLabel("Order Searching/Updating: ");
        orderSearchingLabel.setFont(myFt);
        orderSearchingLabel.setBounds(330, 215, 210, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(orderSearchingLabel);
        
        orderMakingLabel = new JLabel("Order Making: ");
        orderMakingLabel.setFont(myFt);
        orderMakingLabel.setBounds(330, 265, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(orderMakingLabel);
        
        orderCancelingLabel = new JLabel("Order Canceling: ");
        orderCancelingLabel.setFont(myFt);
        orderCancelingLabel.setBounds(330, 315, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(orderCancelingLabel);
        /*** -------------------- ***/
        
        /*** ----------------------------------------------------------- ***/
        /**************************
        ******   TextFields  ******
        ***************************/
        orderSearchingTextField = new JTextField();
        orderSearchingTextField.setText("   1xxxxxxx    (Student ID)  ");
        orderSearchingTextField.setBounds(330, 240, 230, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderSearchingTextField);
        orderSearchingTextField.setColumns(10);
        
        orderMakingTextField = new JTextField();
        orderMakingTextField.setText("     ...     (Book No.)   ");
        orderMakingTextField.setBounds(330, 290, 230, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderMakingTextField);
        orderMakingTextField.setColumns(10);
        
        orderCancelingTextField = new JTextField();
        orderCancelingTextField.setText("     ...     (Order No.)   ");
        orderCancelingTextField.setBounds(330, 340, 230, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderCancelingTextField);
        orderCancelingTextField.setColumns(10);
        /*** -------------------- ***/
       
        
        
        /*** ----------------------------------------------------------- ***/
        /**************************
        *******   Buttons   *******
        ***************************/     
        orderSearchingButton = new JButton("Search");
        orderSearchingButton.setFont(myFt);
        orderSearchingButton.setBounds(570, 240, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderSearchingButton);

        orderSearchingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                TimeSeriesMoveLeft();
            }
        });
        
        orderMakingButton = new JButton("Order");
        orderMakingButton.setFont(myFt);
        orderMakingButton.setBounds(570, 290, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderMakingButton);

        orderMakingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                TimeSeriesMoveLeft();
            }
        });
        
        orderCancelingButton = new JButton("Cancel");
        orderCancelingButton.setFont(myFt);
        orderCancelingButton.setBounds(570, 340, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderCancelingButton);

        orderCancelingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                TimeSeriesMoveLeft();
            }
        });
        /*** -------------------- ***/
        
        
        
        
        /*** ----------------------------------------------------------- ***/
        /**************************
        ******   Lists  ******
        ***************************/
            /*** availableBooks ***/
            imageTempList = new JList();
            imageTempList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            imageTempList.setFont(myFt);
            imageTempList.setBounds(10, 35, 140, 131); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/

            imageTempScrollPane = new JScrollPane(imageTempList);
            imageTempScrollPane.setBounds( imageTempList.getBounds() );
            majorFrame.getContentPane().add(imageTempScrollPane);
            /* availableBooks */
        
            /*** availableBooks ***/
            availableBooksList = new JList();
            availableBooksList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            availableBooksList.setFont(myFt);
            availableBooksList.setBounds(170, 35, 540, 131); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/

            availableBooksScrollPane = new JScrollPane(availableBooksList);
            availableBooksScrollPane.setBounds( availableBooksList.getBounds() );
            majorFrame.getContentPane().add(availableBooksScrollPane);
            /* availableBooks */
            
            /*** bookingRecords ***/
            bookingRecordsList = new JList();
            bookingRecordsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            bookingRecordsList.setFont(myFt);
            bookingRecordsList.setBounds(10, 215, 270, 151); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/

            bookingRecordsScrollPane = new JScrollPane(bookingRecordsList);
            bookingRecordsScrollPane.setBounds( bookingRecordsList.getBounds() );
            majorFrame.getContentPane().add(bookingRecordsScrollPane);
            /* availableBooks */
        /*** -------------------- ***/
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        GUI_For_Comp2016 guiWin = new GUI_For_Comp2016();
        guiWin.majorFrame.setVisible(true);
    }
    
}
