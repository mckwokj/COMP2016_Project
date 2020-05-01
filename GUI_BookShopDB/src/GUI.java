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

public class GUI {

    /**
     * @param args the command line arguments
     */

    public JFrame majorFrame;
    private JTextField orderSearchingTextField, orderMakingTextField, orderCancelingTextField;
    private JList imageTempList, availableBooksList, bookingRecordsList;
    private JTextArea consoleBoxTextArea;
    private JScrollPane imageTempScrollPane, availableBooksScrollPane, bookingRecordsScrollPane, consoleBoxScrollPane;
    private JLabel availableBooksLabel, bookingRecordsLabel, orderSearchingLabel, orderMakingLabel, orderCancelingLabel, consoleBoxLabel;
    private JButton orderSearchingButton, orderMakingButton, orderCancelingButton;

    public bookshop managerDB;

    public GUI() {}

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

//        bookingRecordsLabel = new JLabel("Console Box (Booking Records): ");
//        bookingRecordsLabel.setFont(myFt);
//        bookingRecordsLabel.setBounds(10, 190, 151, 19);    /***209, 16, 101, 15 ***/
//        majorFrame.getContentPane().add(bookingRecordsLabel);

        consoleBoxLabel = new JLabel("Console Box: ");
        consoleBoxLabel.setFont(myFt);
        consoleBoxLabel.setBounds(10, 190, 171, 19);    /***209, 16, 101, 15 ***/
        majorFrame.getContentPane().add(consoleBoxLabel);

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
                orderSearching();
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
//        bookingRecordsList = new JList();
//        bookingRecordsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//        bookingRecordsList.setFont(myFt);
//        bookingRecordsList.setBounds(10, 215, 270, 151); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/

//        bookingRecordsScrollPane = new JScrollPane(bookingRecordsList);
//        bookingRecordsScrollPane.setBounds( bookingRecordsList.getBounds() );
//        majorFrame.getContentPane().add(bookingRecordsScrollPane);
        /* availableBooks */
        /*** -------------------- ***/

        /*** consoleBox ***/
        consoleBoxTextArea = new JTextArea();
        consoleBoxTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        consoleBoxTextArea.setFont(myFt);
        consoleBoxTextArea.setBounds(10, 215, 280, 151);

//        System.out.println("Width:" + consoleBoxTextArea.getPreferredSize());

        consoleBoxScrollPane = new JScrollPane(consoleBoxTextArea);
        consoleBoxScrollPane.setBounds(consoleBoxTextArea.getBounds()); /*** labelList original Bounds: x=12, y=62, width=130, height=403 ***/
        majorFrame.getContentPane().add(consoleBoxScrollPane);

        /*** -------------------- ***/

    }

    public void consoleBoxSetText(String str){
        consoleBoxTextArea.setText(str);
    }

    public void getShopMenus(){
        String menuStr;
        menuStr = managerDB.shopMenus();
        consoleBoxSetText(menuStr);
    }

    public void orderSearching(){
        String orderInfoStr = "";
        orderInfoStr = managerDB.orderSearch(orderSearchingTextField.getText());
        consoleBoxSetText(orderInfoStr);
    }

    /*** Engine run() ***/
    public void run() {
    while (managerDB.noException){}
    }
//    public void run() {
//        String str = "";
//        while (managerDB.noException) {
//            getShopMenus();
//            String line = in.nextLine();
////			String line = menuStr;
//            if (line.equalsIgnoreCase("exit"))
//                return;
//            int choice = -1;
//            try {
//                choice = Integer.parseInt(line);
//            } catch (Exception e) {
//                System.out.println("Enter wrong number!");
//                continue;
//            }
//            if (!(choice >= 1 && choice <= menus.length)) {
//                System.out.println("Enter wrong number!");
//                continue;
//            }
//            if (menus[choice - 1].equals("1. Order Search/Update")) {
//                orderSearch();
//            } else if (menus[choice - 1].equals("2. Order Making")) {
//                orderMaking();
//            } else if (menus[choice - 1].equals("3. Order Cancelling")) {
//                orderCancelling();
//            } else if (menus[choice - 1].equals("exit")) {
//                break;
//            }
//        }
//    }


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
            consoleBoxSetText("Welcome to the GUI Testing! Please follow \n the instruction to check the record:\n 1.\n 2.\n 3.");

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
        GUI GUIWin = new GUI();
        GUIWin.fire();
    }
}
