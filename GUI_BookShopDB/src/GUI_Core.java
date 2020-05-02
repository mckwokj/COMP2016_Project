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
import java.awt.event.*;

public class GUI_Core {

    /**
     * @param args the command line arguments
     */

    public JFrame majorFrame;
    private JTextField  orderSearchingTextField, /** order searching**/
                        orderNumUpdatingTextField, bookNumUpdatingTextField, dateUpdatingTextField, /** order updating **/
                        stdOrderMakingTextField, bookNumOrderMakingTextField, paymentCardNumTextField, /** order making **/
                        orderCancelingTextField; /** order cancelling **/
    private JList imageTempList, bookingRecordsList;
    private JTextArea consoleBoxTextArea, allBooksTextArea;
    private JScrollPane imageTempScrollPane, allBooksScrollPane, imageScrollPane, consoleBoxScrollPane;
    private JLabel allBooksLabel, bookingRecordsLabel, orderSearchingLabel, orderMakingLabel, orderCancelingLabel, consoleBoxLabel;
    private JButton orderSearchingButton, dateUpdatingButton, orderMakingButton, orderCancelingButton, allBooksDisplayButton;
    private JRadioButton paymentRdBtnCash, paymentRdBtnCard, paymentRdBtnTransfer;
    private ButtonGroup paymentBtGroup;

    public bookshop managerDB;

    public GUI_Core() {}

    private void initialize()
    {
        Font myFt = new Font("SansSerif", Font.PLAIN, 15);

        /*** ----------------------------------------------------------- ***/
        /**************************
         *** MajorFrame Setting  ***
         ***************************/
        majorFrame = new JFrame();
        majorFrame.setTitle("Testing");
        majorFrame.setBounds(100, 100, 751, 500);
        majorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        majorFrame.getContentPane().setLayout(null);

        /*** -------------------- ***/

        /*** ----------------------------------------------------------- ***/
        /**************************
         ******   Labels     *******
         ***************************/
        allBooksLabel = new JLabel("Available Books: ");
        allBooksLabel.setFont(myFt);
        allBooksLabel.setBounds(10, 10, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(allBooksLabel);

//        bookingRecordsLabel = new JLabel("Console Box (Booking Records): ");
//        bookingRecordsLabel.setFont(myFt);
//        bookingRecordsLabel.setBounds(10, 190, 151, 19);    /***209, 16, 101, 15 ***/
//        majorFrame.getContentPane().add(bookingRecordsLabel);

        consoleBoxLabel = new JLabel("Console Box: ");
        consoleBoxLabel.setFont(myFt);
        consoleBoxLabel.setBounds(10, 190, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(consoleBoxLabel);

        orderSearchingLabel = new JLabel("Order Searching/Updating (dd/mm/yyyy): ");
        orderSearchingLabel.setFont(myFt);
        orderSearchingLabel.setBounds(330, 190, 250, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(orderSearchingLabel);

        orderMakingLabel = new JLabel("Order Making: ");
        orderMakingLabel.setFont(myFt);
        orderMakingLabel.setBounds(330, 265, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(orderMakingLabel);

        orderCancelingLabel = new JLabel("Order Canceling: ");
        orderCancelingLabel.setFont(myFt);
        orderCancelingLabel.setBounds(330, 376, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(orderCancelingLabel);
        /*** -------------------- ***/

        /*** ----------------------------------------------------------- ***/
        /**************************
         ******   TextFields  ******
         ***************************/

        /** order searching **/
        orderSearchingTextField = new JTextField();
        orderSearchingTextField.setText("   1xxxxxxx    (Student ID)  ");
        orderSearchingTextField.setBounds(330, 215, 250, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderSearchingTextField);
        orderSearchingTextField.setColumns(20);

        /** order updating **/
        orderNumUpdatingTextField = new JTextField();
        orderNumUpdatingTextField.setText(" order No. ");
        orderNumUpdatingTextField.setBounds(330, 240, 83, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderNumUpdatingTextField);
        orderNumUpdatingTextField.setColumns(20);

        bookNumUpdatingTextField = new JTextField();
        bookNumUpdatingTextField.setText(" book No. ");
        bookNumUpdatingTextField.setBounds(414, 240, 83, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(bookNumUpdatingTextField);
        bookNumUpdatingTextField.setColumns(20);

        dateUpdatingTextField = new JTextField();
        dateUpdatingTextField.setText(" dd/mm/yyyy ");
        dateUpdatingTextField.setBounds(498, 240, 83, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(dateUpdatingTextField);
        dateUpdatingTextField.setColumns(20);

        /** order making **/
        stdOrderMakingTextField = new JTextField();
        stdOrderMakingTextField.setText(" ... student No. ... ");
        stdOrderMakingTextField.setBounds(330, 290, 124, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(stdOrderMakingTextField);
        stdOrderMakingTextField.setColumns(20);

        bookNumOrderMakingTextField = new JTextField();
        bookNumOrderMakingTextField.setText(" ... book No. ... ");
        bookNumOrderMakingTextField.setBounds(456, 290, 124, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(bookNumOrderMakingTextField);
        bookNumOrderMakingTextField.setColumns(20);

        paymentCardNumTextField = new JTextField();
        paymentCardNumTextField.setText(" ...   card No.    ... ");
        paymentCardNumTextField.setBounds(330, 351, 250, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(paymentCardNumTextField);
        paymentCardNumTextField.setColumns(20);


        /** order canceling **/
        orderCancelingTextField = new JTextField();
        orderCancelingTextField.setText("     ...     (Order No.)   ");
        orderCancelingTextField.setBounds(330, 401, 250, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderCancelingTextField);
        orderCancelingTextField.setColumns(20);
        /*** -------------------- ***/


        /*** ----------------------------------------------------------- ***/
        /**************************
         ****   Radio Buttons  ****
         ***************************/

        paymentRdBtnCash = new JRadioButton("Cash", true);
        paymentRdBtnCash.setBounds(330,315,83,30);

        paymentRdBtnCard = new JRadioButton("Card");
        paymentRdBtnCard.setBounds(414,315,83,30);

        paymentRdBtnTransfer = new JRadioButton("Transfer");
        paymentRdBtnTransfer.setBounds(498,315,83,30);

        paymentBtGroup = new ButtonGroup();
        paymentBtGroup.add(paymentRdBtnCash);
        paymentBtGroup.add(paymentRdBtnCard);
        paymentBtGroup.add(paymentRdBtnTransfer);

        majorFrame.getContentPane().add(paymentRdBtnCash);
        majorFrame.getContentPane().add(paymentRdBtnCard);
        majorFrame.getContentPane().add(paymentRdBtnTransfer);
        /*** -------------------- ***/

        /*** ----------------------------------------------------------- ***/
        /**************************
         *******   Buttons   *******
         ***************************/

        allBooksDisplayButton = new JButton("Update Stock");
        allBooksDisplayButton.setFont(myFt);
        allBooksDisplayButton.setBounds(150, 10, 120, 25);   /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(allBooksDisplayButton);

        allBooksDisplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAllBooks();
            }
        });

        orderSearchingButton = new JButton("Search");
        orderSearchingButton.setFont(myFt);
        orderSearchingButton.setBounds(590, 210, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderSearchingButton);

        orderSearchingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderSearching();
            }
        });

        dateUpdatingButton = new JButton("Update");
        dateUpdatingButton.setFont(myFt);
        dateUpdatingButton.setBounds(590, 240, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(dateUpdatingButton);

        dateUpdatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Update this record ?", "Date Updating",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                /** 0=yes, 1=no, 2=cancel **/
                if(input == 0){
                    dateUpdating();
                }
            }
        });

        orderMakingButton = new JButton("Order");
        orderMakingButton.setFont(myFt);
        orderMakingButton.setBounds(590, 290, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderMakingButton);

        orderMakingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderMaking();
            }
        });

        orderCancelingButton = new JButton("Cancel");
        orderCancelingButton.setFont(myFt);
        orderCancelingButton.setBounds(590, 401, 100, 25);    /** 460, 485, 50, 25 **/
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
//        imageTempList = new JList();
//        imageTempList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//        imageTempList.setFont(myFt);
//        imageTempList.setBounds(10, 35, 140, 131); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/
//
//        imageTempScrollPane = new JScrollPane(imageTempList);
//        imageTempScrollPane.setBounds( imageTempList.getBounds() );
//        majorFrame.getContentPane().add(imageTempScrollPane);
        /* availableBooks */

        /*** allBooksTextArea ***/
        allBooksTextArea = new JTextArea();
        allBooksTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        allBooksTextArea.setFont(myFt);
        allBooksTextArea.setBounds(10, 35, 250, 141); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/
        allBooksTextArea.setLineWrap(true);
        allBooksTextArea.setWrapStyleWord(true);

        allBooksScrollPane = new JScrollPane(allBooksTextArea);
        allBooksScrollPane.setBounds( allBooksTextArea.getBounds() );
        majorFrame.getContentPane().add(allBooksScrollPane);
        /* availableBooks */

        /*** Image ***/
//        bookingRecordsList = new JList();
//        bookingRecordsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//        bookingRecordsList.setFont(myFt);
//        bookingRecordsList.setBounds(10, 215, 270, 151); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/

        imageScrollPane = new JScrollPane();
        imageScrollPane.setBounds(265, 35, 460, 141);
        majorFrame.getContentPane().add(imageScrollPane);
        /* availableBooks */
        /*** -------------------- ***/

        /*** consoleBox ***/
        consoleBoxTextArea = new JTextArea();
        consoleBoxTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        consoleBoxTextArea.setFont(myFt);
        consoleBoxTextArea.setBounds(10, 215, 270, 151);
        consoleBoxTextArea.setLineWrap(true);
        consoleBoxTextArea.setWrapStyleWord(true);

//        System.out.println("Width:" + consoleBoxTextArea.getPreferredSize());

        consoleBoxScrollPane = new JScrollPane(consoleBoxTextArea);
        consoleBoxScrollPane.setBounds(consoleBoxTextArea.getBounds()); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/
        majorFrame.getContentPane().add(consoleBoxScrollPane);

        /*** -------------------- ***/

    }

    /*** ---------------------------------------------------------------------------------------------------------- ***/

    /*** consoleBoxSetText ***/
    public void consoleBoxSetText(String str){
        consoleBoxTextArea.setText(str);
    }
    public void allBooksSetText(String str){ allBooksTextArea.setText(str); }

    /*** getAllBooks ***/
    public void getAllBooks(){
        String bookListStr = "";
        bookListStr = managerDB.printBookList();
        allBooksSetText(bookListStr);
    }

    /*** orderSearching ***/
    public void orderSearching(){
        String orderInfoStr = "";
        orderInfoStr = managerDB.orderSearch(orderSearchingTextField.getText());
        consoleBoxSetText(orderInfoStr);
    }

    /*** order date updating ***/
    public void dateUpdating(){
        String orderStr, bookNumStr, dateStr;
        orderStr = orderNumUpdatingTextField.getText();
        bookNumStr = bookNumUpdatingTextField.getText();
        dateStr = dateUpdatingTextField.getText();

        if(managerDB.orderDateUpdate(orderStr, bookNumStr, dateStr)){
            JOptionPane.showMessageDialog(null, "Successfully updated!");
        }else{
            JOptionPane.showMessageDialog(null, "Update failed!\nPlease check the three input values.");
        }



    }

    /*** order making ***/
    public void orderMaking(){
        String orderConfirmInfoStr = "";
        String stdNumStr, bookNumStr;
        int choice = -1;
        String cardNumber = null;

        stdNumStr = stdOrderMakingTextField.getText();
        bookNumStr = bookNumOrderMakingTextField.getText();


        if(paymentRdBtnCash.isSelected()){
            choice = 1;
        }
        else if(paymentRdBtnCard.isSelected()){
            choice = 2;
            cardNumber = paymentCardNumTextField.getText();
        }else if(paymentRdBtnTransfer.isSelected()){
            choice = 3;
        }

        orderConfirmInfoStr = managerDB.orderMaking(stdNumStr, bookNumStr, choice, cardNumber);

        if(orderConfirmInfoStr != null){
            consoleBoxSetText(orderConfirmInfoStr);
            getAllBooks();
        }else{
            consoleBoxSetText("Order making failed");
        }

    }

    /*** Engine run() ***/
    public void run() {
        getAllBooks();
        while (managerDB.noException){}
    }

    /*** fire ***/
    public void fire(){
        /*** GUI - DB ***/
        String myMenuStr, stdIDStr;

        /*** Display the GUI ***/

        System.out.println("GUI is running!");

        /*** Invoke bookshop ***/
        managerDB = new bookshop();
        if (!managerDB.auTologinProxy()) {
            System.out.println("Login proxy failed, please re-examine your username and password!");
            return;
        }
        if (!managerDB.loginDB()) {
            System.out.println("Login database failed, please re-examine your username and password!");
            return;
        }
        System.out.println("Login succeed!");
        try {
            /*** Initialize GUI components ***/
            initialize();
            majorFrame.setVisible(true);
            majorFrame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e)
                {
                    System.out.println("Closed");
                    managerDB.close();
                    e.getWindow().dispose();
                }
            });
            /*** ------------------------- ***/
            consoleBoxSetText("Welcome to the GUI Testing! Please follow the instruction to check the record:\n 1.\n 2.\n 3.");

            /***  ***/
            run();
        } finally {
            managerDB.close();
        }
    }


    /*** ------------------------------------------------ ***/

    /*** main() ***/
    public static void main(String[] args) {
        // TODO code application logic here
        GUI_Core GUICoreWin = new GUI_Core();
        GUICoreWin.fire();
    }
}
