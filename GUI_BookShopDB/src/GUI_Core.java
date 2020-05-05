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
import javax.swing.text.Document;
import org.jdesktop.swingx.prompt.PromptSupport;

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
    private JTextArea consoleBoxTextArea, allBooksTextArea, allOrdersBoxTextArea;
    private JScrollPane imageTempScrollPane, allBooksScrollPane, imageScrollPane, allOrdersScrollPane, consoleBoxScrollPane;
    private JLabel allBooksLabel, allOrdersLabel, bookingRecordsLabel, orderSearchingLabel, orderMakingLabel, orderCancelingLabel, consoleBoxLabel;
    private JButton orderSearchingButton,dateUpdatingButton, orderMakingButton, orderCancelingButton, allBooksDisplayButton,
                    allOrdersDisplayButton, showAnOrderingButton, infoDisplayButton;
    private JRadioButton paymentRdBtnCash, paymentRdBtnCard, paymentRdBtnTransfer;
    private ButtonGroup paymentBtGroup;

    public bookshop managerDB;

    final String errorInfo = "Exception:\n---------------------------\n---------------------------\n";

    public GUI_Core() {}

    private void initialize() {
        Font myFt = new Font("SansSerif", Font.PLAIN, 15);

        /*** ----------------------------------------------------------- ***/
        /**************************
         *** MajorFrame Setting  ***
         ***************************/
        majorFrame = new JFrame();
        majorFrame.setTitle("Online Book Store - Group Twelve");
        majorFrame.setBounds(170, 170, 751, 500);
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

        allOrdersLabel = new JLabel("All Orders: ");
        allOrdersLabel.setFont(myFt);
        allOrdersLabel.setBounds(265, 10, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(allOrdersLabel);

        consoleBoxLabel = new JLabel("Console Box: ");
        consoleBoxLabel.setFont(myFt);
        consoleBoxLabel.setBounds(10, 190, 151, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(consoleBoxLabel);

        orderSearchingLabel = new JLabel("Order Searching/Updating (dd/mm/yyyy): ");
        orderSearchingLabel.setFont(myFt);
        orderSearchingLabel.setBounds(330, 190, 330, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(orderSearchingLabel);

        orderMakingLabel = new JLabel("Order Making: (using ',' to divide book No.)");
        orderMakingLabel.setFont(myFt);
        orderMakingLabel.setBounds(330, 265, 330, 19);    /***209, 16, 101, 15 ***/
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
        PromptSupport.setPrompt("1xxxxxxx    (Student ID)", orderSearchingTextField);
        orderSearchingTextField.setBounds(330, 215, 275, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderSearchingTextField);
        orderSearchingTextField.setColumns(20);

        /** order updating **/
        orderNumUpdatingTextField = new JTextField();
        PromptSupport.setPrompt(" order No. ", orderNumUpdatingTextField);
        orderNumUpdatingTextField.setBounds(330, 240, 87, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderNumUpdatingTextField);
        orderNumUpdatingTextField.setColumns(20);

        bookNumUpdatingTextField = new JTextField();
        PromptSupport.setPrompt(" book No. ", bookNumUpdatingTextField);
        bookNumUpdatingTextField.setBounds(420, 240, 87, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(bookNumUpdatingTextField);
        bookNumUpdatingTextField.setColumns(20);

        dateUpdatingTextField = new JTextField();
        PromptSupport.setPrompt(" dd/mm/yyyy ", dateUpdatingTextField);
        dateUpdatingTextField.setBounds(510, 240, 99, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(dateUpdatingTextField);
        dateUpdatingTextField.setColumns(20);

        /** order making **/
        stdOrderMakingTextField = new JTextField();
        PromptSupport.setPrompt("  ... student No. ... ", stdOrderMakingTextField);
        stdOrderMakingTextField.setBounds(330, 290, 137, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(stdOrderMakingTextField);
        stdOrderMakingTextField.setColumns(20);

        bookNumOrderMakingTextField = new JTextField();
        PromptSupport.setPrompt("using ',' to divide book No. ", bookNumOrderMakingTextField);
        bookNumOrderMakingTextField.setBounds(468, 290, 137, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(bookNumOrderMakingTextField);
        bookNumOrderMakingTextField.setColumns(20);

        paymentCardNumTextField = new JTextField();
        PromptSupport.setPrompt("       ... please input your card No. ... ", paymentCardNumTextField);
        paymentCardNumTextField.setBounds(330, 351, 275, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(paymentCardNumTextField);
        paymentCardNumTextField.setColumns(20);


        /** order canceling **/
        orderCancelingTextField = new JTextField();
        PromptSupport.setPrompt("           ...     (Order No.)   ...  ", orderCancelingTextField);
        orderCancelingTextField.setBounds(330, 401, 275, 19); /***328, 12, 114, 19**/
        majorFrame.getContentPane().add(orderCancelingTextField);
        orderCancelingTextField.setColumns(20);
        /*** -------------------- ***/


        /*** ----------------------------------------------------------- ***/
        /**************************
         ****   Radio Buttons  ****
         ***************************/

        paymentRdBtnCash = new JRadioButton("Cash", true);
        paymentRdBtnCash.setBounds(330,315,91,30);

        paymentRdBtnCard = new JRadioButton("Card");
        paymentRdBtnCard.setBounds(422,315,91,30);

        paymentRdBtnTransfer = new JRadioButton("Transfer");
        paymentRdBtnTransfer.setBounds(513,315,95,30);

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

        allBooksDisplayButton = new JButton("All Stock");
        allBooksDisplayButton.setFont(myFt);
        allBooksDisplayButton.setBounds(145, 10, 120, 25);   /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(allBooksDisplayButton);

        allBooksDisplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAllBooks();
            }
        });

        allOrdersDisplayButton = new JButton("All Orders");
        allOrdersDisplayButton.setFont(myFt);
        allOrdersDisplayButton.setBounds(610, 10, 120, 25);   /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(allOrdersDisplayButton);

        allOrdersDisplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAllOrders();
            }
        });

        infoDisplayButton = new JButton("Info");
        infoDisplayButton.setFont(myFt);
        infoDisplayButton.setBounds(183, 190, 120, 25);   /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(infoDisplayButton);

        infoDisplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookStoreRecordInfo();
            }
        });

        orderSearchingButton = new JButton("Search");
        orderSearchingButton.setFont(myFt);
        orderSearchingButton.setBounds(610, 210, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderSearchingButton);

        orderSearchingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderSearching();
            }
        });

        dateUpdatingButton = new JButton("Update");
        dateUpdatingButton.setFont(myFt);
        dateUpdatingButton.setBounds(610, 240, 100, 25);    /** 460, 485, 50, 25 **/
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
        orderMakingButton.setBounds(610, 290, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderMakingButton);

        orderMakingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderMaking();
            }
        });

        showAnOrderingButton = new JButton("Show");
        showAnOrderingButton.setFont(myFt);
        showAnOrderingButton.setBounds(610, 401, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(showAnOrderingButton);

        showAnOrderingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAnOrder();
            }
        });

        orderCancelingButton = new JButton("Cancel");
        orderCancelingButton.setFont(myFt);
        orderCancelingButton.setBounds(610, 431, 100, 25);    /** 460, 485, 50, 25 **/
        majorFrame.getContentPane().add(orderCancelingButton);

        orderCancelingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Cancel this record ?", "Date Cancelling",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                /** 0=yes, 1=no, 2=cancel **/
                if(input == 0){
                    orderCancelling();
                }
            }
        });

        /*** -------------------- ***/


        /*** ----------------------------------------------------------- ***/
        /**************************
         ***  allBooksTextArea  ***
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

        allOrdersBoxTextArea = new JTextArea();
        allOrdersBoxTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        allOrdersBoxTextArea.setFont(myFt);
        allOrdersBoxTextArea.setBounds(265, 35, 460, 141); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/
        allOrdersBoxTextArea.setLineWrap(true);
        allOrdersBoxTextArea.setWrapStyleWord(true);

        allOrdersScrollPane = new JScrollPane(allOrdersBoxTextArea);
        allOrdersScrollPane.setBounds( allOrdersBoxTextArea.getBounds() );
        majorFrame.getContentPane().add(allOrdersScrollPane);


        /*** Image ***/
//        bookingRecordsList = new JList();
//        bookingRecordsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//        bookingRecordsList.setFont(myFt);
//        bookingRecordsList.setBounds(10, 215, 270, 151); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/

//        imageScrollPane = new JScrollPane();
//        imageScrollPane.setBounds(265, 35, 460, 141);
//        majorFrame.getContentPane().add(imageScrollPane);
        /* availableBooks */
        /*** -------------------- ***/

        /*** consoleBox ***/
        consoleBoxTextArea = new JTextArea();
        consoleBoxTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        consoleBoxTextArea.setFont(myFt);
        consoleBoxTextArea.setBounds(10, 215, 307, 231);
        consoleBoxTextArea.setLineWrap(true);
        consoleBoxTextArea.setWrapStyleWord(true);

//        System.out.println("Width:" + consoleBoxTextArea.getPreferredSize());

        consoleBoxScrollPane = new JScrollPane(consoleBoxTextArea);
        consoleBoxScrollPane.setBounds(consoleBoxTextArea.getBounds()); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/
        majorFrame.getContentPane().add(consoleBoxScrollPane);

        /*** -------------------- ***/

    }

    /*** ---------------------------------------------------------------------------------------------------------- ***/

    /************************** bookStoreRecordInfo ***/
    /***********************************************************************
     * bookStoreRecordInfo() displays rules about order date updating and
     *  order cancelling
     **********************************************************************/
    public void bookStoreRecordInfo(){
        String info = "";
        info += "Welcome to the Online Book Store - Group Twelve! Please follow the instruction to check the record:\n\n";

        info += "1:\n";

        info += "---Order making requirements---\n\n";
        info += "I. Every book in the order is NOT out of stock\n";
        info += "II. Not have any outstanding orders(All books ordered earlier had been delivered)\n\n";
        info += "-----------------------------\n\n";

        info += "2:\n";
        info += "--Order cancelling requirements--\n\n";
        info += "I. None of the books in the order has been delivered\n";
        info += "II. Order was made within 7 days\n\n";
        info += "-----------------------------\n";

        consoleBoxSetText(info);
    }


    /************************** consoleBoxSetText ***/
    /***********************************************************************
     * consoleBoxSetText() sets text into consoleBoxTextArea
     **********************************************************************/
    public void consoleBoxSetText(String str){
        consoleBoxTextArea.setText(str);
    }

    /*** allBooksSetText ***/
    /***********************************************************************
     * allBooksSetText() sets text into allBooksTextArea
     **********************************************************************/
    public void allBooksSetText(String str){ allBooksTextArea.setText(str); }

    /*** allOrdersBoxSetText ***/
    /***********************************************************************
     * allOrdersBoxSetText() sets text into allOrdersBoxTextArea
     **********************************************************************/
    public void allOrdersBoxSetText(String str){ allOrdersBoxTextArea.setText(str); }

    /*** getAllBooks ***/
    /***********************************************************************
     * getAllBooks() gets all the book records from Book table in
     * database and pass a string to allBooksSetText()
     **********************************************************************/
    public void getAllBooks(){
        String bookListStr = "";
        bookListStr = managerDB.printBookList();
        allBooksSetText(bookListStr);
    }

    /*** getAllOrders ***/
    /***********************************************************************
     * getAllOrders() gets all the order records from Place_Order table
     * in database and pass a string to allOrdersBoxSetText()
     **********************************************************************/
    public void getAllOrders(){
        String allOrdersStr = null;
        allOrdersStr = managerDB.printAllOrders();
        if(allOrdersStr == null){
            allOrdersBoxSetText("Exception: printAllOrders()");
        }else{
            allOrdersBoxSetText(allOrdersStr);
        }
    }

    /*** getAnOrder ***/
    /***********************************************************************
     * getAnOrder() gets one order record from Place_Order table
     * in database by a order No. and pass a string to consoleBoxSetText()
     **********************************************************************/
    public void getAnOrder(){
        String ordersStr;
        String orderTextFieldStr = orderCancelingTextField.getText();
        if(orderTextFieldStr.equals("")){
            consoleBoxSetText(errorInfo + "Your order No. is empty!\nPlease enter a correct order No..");
        }else{
            ordersStr = managerDB.printOrderByOrderNo(orderTextFieldStr);
            consoleBoxSetText(ordersStr);
        }

    }

    /*** orderSearching ***/
    /***********************************************************************
     * orderSearching() gets all order records from Place_Order table
     * in database by a student No. and pass a string to consoleBoxSetText()
     **********************************************************************/
    public void orderSearching(){
        String orderInfoStr = "";
        String orderSearchTextFieldStr = orderSearchingTextField.getText();
        if(orderSearchTextFieldStr.equals("")){
            consoleBoxSetText( errorInfo + "Your student No. is empty!\nPlease enter a correct student No..");
        }else{
            orderInfoStr = managerDB.orderSearch(orderSearchTextFieldStr);
            consoleBoxSetText(orderInfoStr);
        }

    }

    /*** dateUpdating ***/
    /***********************************************************************
     * dateUpdating() updates an order record into Deliver table
     * in database by three parameters: order No., Book No. date
     * Then it passes a string to consoleBoxSetText()
     **********************************************************************/
    public void dateUpdating(){
        String orderStr, bookNumStr, dateStr;
        orderStr = orderNumUpdatingTextField.getText();
        bookNumStr = bookNumUpdatingTextField.getText();
        dateStr = dateUpdatingTextField.getText();

        System.out.println("orderStr: " + orderStr);
        System.out.println("bookNumStr: " + bookNumStr);
        System.out.println("dateStr: " + dateStr);

        if(orderStr == null || bookNumStr == null || dateStr == null || orderStr.equals("") || bookNumStr.equals("")|| dateStr.equals("")){
            consoleBoxSetText( errorInfo + "Update failed!\nPlease check the your book No. and Order No. .");
        }
        else{
            if(managerDB.orderDateUpdate(orderStr, bookNumStr, dateStr)){
//            JOptionPane.showMessageDialog(null, "Successfully updated!");

                consoleBoxSetText("Successfully updated!");
            }else{
//            JOptionPane.showMessageDialog(null, "Update failed!\nPlease check the your book No. and Order No. .");
                consoleBoxSetText( errorInfo + "Update failed!\nPlease check the your book No. , order No. or your delivery date.\n" +
                        "(Delivery date cannot be earlier than the order date.)\n");
            }
        }

    }

    /*** orderMaking ***/
    /***********************************************************************
     * orderMaking() makes an order record into Place_Order table
     * in database by three parameters: student No., book No.s, payment methods
     * Then it passes a string to consoleBoxSetText()
     **********************************************************************/
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

        if(stdNumStr == null || bookNumStr == null || stdNumStr.equals("") || bookNumStr.equals("")){
            consoleBoxSetText( errorInfo + "Update failed!\nPlease check the your student No. and book No. \n(Please" +
                    " (check if you have orders which delivery date is in the future as well.)\n" +
                    "You cannot make new orders if you have undelivered order.).");
        }
        else{
            if(choice == 2 && cardNumber.equals("")){
                cardNumber = null;
            }
            orderConfirmInfoStr = managerDB.orderMaking(stdNumStr, bookNumStr, choice, cardNumber);
            consoleBoxSetText(orderConfirmInfoStr);
            getAllBooks();
            getAllOrders();
        }

    }

    /*** orderCancelling ***/
    /***********************************************************************
     * orderCancelling() cancels an order record from Place_Order table
     * in database by a parameters: order No.
     * Then it passes a string to consoleBoxSetText() and invokes getAllBooks()
     * and getAllOrders() to refresh book stock and orders information
     **********************************************************************/
    public void orderCancelling(){
        String orderInfoStr = null;
        orderInfoStr = managerDB.orderCancelling(orderCancelingTextField.getText());
        if(orderInfoStr == null || orderInfoStr.equals("")){
            consoleBoxSetText( errorInfo + "Please enter a correct order No.!");
        }else{
            consoleBoxSetText(orderInfoStr);
        }
        getAllBooks();
        getAllOrders();
    }

    /*** Engine run() ***/
    /***********************************************************************
     * run() invokes three information display methods: bookStoreRecordInfo(),
     * getAllBooks() and getAllOrders()
     **********************************************************************/
    public void run() {
        bookStoreRecordInfo();
        getAllBooks();
        getAllOrders();
        while (managerDB.noException){}
    }

    /*** fire ***/
    /***********************************************************************
     * fire() initiates a bookshop object and UI components.
     * Then it invokes run()
     **********************************************************************/
    public void fire(){
        /*** GUI - DB ***/
        String myMenuStr, stdIDStr;

        /*** Display the GUI ***/

        System.out.println("GUI is running!");

        /*** Invoke bookshop ***/
        managerDB = new bookshop();
        if (!managerDB.loginProxy()) {
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
            run();
            /***  ***/
        } finally {
            managerDB.close();
        }
    }


    /*** ------------------------------------------------ ***/

    /*** main() ***/
    /***********************************************************************
     * main() initiates a GUI_Core object and invokes run()
     **********************************************************************/
    public static void main(String[] args) {
        // TODO code application logic here
        GUI_Core GUICoreWin = new GUI_Core();
        GUICoreWin.fire();
    }
}
