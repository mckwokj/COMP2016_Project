package OnlineBookShop;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class OnlineBookShop {

    //Setting
    Scanner in = null;
    Connection conn = null;
    // Database Host
    final String databaseHost = "orasrv1.comp.hkbu.edu.hk";
    // Database Port
    final int databasePort = 1521;
    // Database name
    final String database = "pdborcl.orasrv1.comp.hkbu.edu.hk";
    final String proxyHost = "faith.comp.hkbu.edu.hk";
    final int proxyPort = 22;
    final String forwardHost = "localhost";
    int forwardPort;
    Session proxySession = null;
    boolean noException = true;
    // JDBC connecting host
    String jdbcHost;
    // JDBC connecting port
    int jdbcPort;

    public OnlineBookShop() {
         in = new Scanner(System.in);
    }

    /**
     * Get YES or NO. Do not change this function.
     *
     * @return boolean
     */
    boolean getYESorNO(String message) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(message));
        JOptionPane pane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = pane.createDialog(null, "Question");
        dialog.setVisible(true);
        boolean result = JOptionPane.YES_OPTION == (int) pane.getValue();
        dialog.dispose();
        return result;
    }

    /**
     * Get username & password. Do not change this function.
     *
     * @return username & password
     */
    String[] getUsernamePassword(String title) {
        JPanel panel = new JPanel();
        final TextField usernameField = new TextField();
        final JPasswordField passwordField = new JPasswordField();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Username"));
        panel.add(usernameField);
        panel.add(new JLabel("Password"));
        panel.add(passwordField);
        JOptionPane pane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
            private static final long serialVersionUID = 1L;

            @Override
            public void selectInitialValue() {
                usernameField.requestFocusInWindow();
            }
        };
        JDialog dialog = pane.createDialog(null, title);
        dialog.setVisible(true);
        dialog.dispose();
        return new String[] { usernameField.getText(), new String(passwordField.getPassword()) };
    }

    /**
     * Login the proxy. Do not change this function.
     *
     * @return boolean
     */
    public boolean loginProxy() {
        if (getYESorNO("Using ssh tunnel or not?")) { // if using ssh tunnel
            String[] namePwd = getUsernamePassword("Login cs lab computer");
            String sshUser = namePwd[0];
            String sshPwd = namePwd[1];
            try {
                proxySession = new JSch().getSession(sshUser, proxyHost, proxyPort);
                proxySession.setPassword(sshPwd);
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                proxySession.setConfig(config);
                proxySession.connect();
                proxySession.setPortForwardingL(forwardHost, 0, databaseHost, databasePort);
                forwardPort = Integer.parseInt(proxySession.getPortForwardingL()[0].split(":")[0]);
            } catch (JSchException e) {
                e.printStackTrace();
                return false;
            }
            jdbcHost = forwardHost;
            jdbcPort = forwardPort;
        } else {
            jdbcHost = databaseHost;
            jdbcPort = databasePort;
        }
        return true;
    }

    /**
     * Login the oracle system. Change this function under instruction.
     *
     * @return boolean
     */
    public boolean loginDB() {
        String username = "e1234567";//Replace e1234567 to your username
        String password = "e1234567";//Replace e1234567 to your password

        /* Do not change the code below */
        if(username.equalsIgnoreCase("e1234567") || password.equalsIgnoreCase("e1234567")) {
            String[] namePwd = getUsernamePassword("Login sqlplus");
            username = namePwd[0];
            password = namePwd[1];
        }
        String URL = "jdbc:oracle:thin:@" + jdbcHost + ":" + jdbcPort + "/" + database;

        try {
            System.out.println("Logging " + URL + " ...");
            conn = DriverManager.getConnection(URL, username, password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * Functions for onlineBookShop
     *
     */
    String[] menus = {
            "1. Order Search/Update",
            "2. Order Making",
            "3. Order Cancelling"
    };

    public void run() {
        while (noException) {
            shopMenus();
            String line = in.nextLine();
            if (line.equalsIgnoreCase("exit"))
                return;
            int choice = -1;
            try {
                choice = Integer.parseInt(line);
            } catch (Exception e) {
                System.out.println("This option is not available");
                continue;
            }
            if (!(choice >= 1 && choice <= menus.length)) {
                System.out.println("This option is not available");
                continue;
            }
            if (menus[choice - 1].equals("1. Order Search/Update")) {
                orderSearch();
            } else if (menus[choice - 1].equals("2. Order Making")) {
                orderMaking();
            } else if (menus[choice - 1].equals("3. Order Cancelling")) {
                orderCancelling();
            } else if (menus[choice - 1].equals("exit")) {
                break;
            }
        }
    }

    public void close() {
        System.out.println("Thanks for using this manager! Bye...");
        try {
            if (conn != null)
                conn.close();
            if (proxySession != null) {
                proxySession.disconnect();
            }
            in.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shopMenus() {
        System.out.println("Please choose following option:");
        for (int i = 0; i < menus.length; ++i) {
            System.out.println(menus[i]);
        }
    }

    private void orderSearch() {
        System.out.println("Please input the Student ID to search order info:");
        String line = in.nextLine();
        line = line.trim();
        if (line.equalsIgnoreCase("exit"))
            return;

        int snum = Integer.parseInt(line);
        printOrderBySnum(snum);
    }

    private void printOrderBySnum(int snum) {
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT PLACE_ORDER.ORDER_NO,STUDENT,ORDER_DATE,BOOKS_ORDERED,TOTAL_PRICE,PAYMENT_METHOD,CARD_NO,"
                    + "BNUM,DELIVERY_DATE"
                    + " FROM PLACE_ORDER,DELIVER WHERE STUDENT = " + snum
                    + " AND PLACE_ORDER.ORDER_NO = DELIVER.ORDER_NO";
            ResultSet rs = stm.executeQuery(sql);
            String[] heads = { "Order_No", "Student", "Order_Date", "Books_Ordered",
                    "Total_Price", "Payment_method", "Card_No","Bnum","Deliver_Date",
                    "Bnum", "Delivery_Date"};
            while (rs.next()) {
                for (int i = 0; i < 9; ++i) { //Print 9 attributes from PLACE_ORDER + DELIVER
                    try {
                        String value = rs.getString( i + 1 );
                        if (value == null) {value = "";}
                        System.out.println(heads[i] + " : " + value);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("-------------------------");
            }

            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }
    }

    private void orderMaking() {
        int choice = 0;

        while (choice != 3) {
            System.out.println("Please choose an action:");
            System.out.println("1. Display list of books.");
            System.out.println("2. Make a new order.");
            System.out.println("3. Back to main menus.");
            String line = in.nextLine();
            choice = Integer.parseInt(line);

            if (choice == 1) {
                //print book table
                printBookList();
            }else if (choice == 2) {
                System.out.println("Please input your student number!");

                //check outstanding order
                line = in.nextLine();
                line = line.trim();
                if (line.equalsIgnoreCase("exit"))
                    return;
                int Snum = Integer.parseInt(line);
                if (checkSnum(Snum) == false)
                    return;

                //making order
                System.out.println("Please input book number:");
                System.out.println("(If more than 1 book, please input by following format: book1,book2,book3)");
                line = in.nextLine();
                String[] Bnum = line.split(",");
                String book_name = null;
                for (int i = 0; i < Bnum.length; ++i) {
                    Bnum[i] = Bnum[i].trim();
                    if (checkBookAmount(Bnum[i]) == false)
                        return;
                }

                for (int i = 0; i < Bnum.length; ++i) {
                    if (i == 0)
                        book_name = getBookName(Bnum[i]);

                    if (i > 0)
                        book_name = book_name + ", " + getBookName(Bnum[i]);
                    //System.out.println("Book number:" + Bnum[i]);
                }
                System.out.println("Book ordered:" + book_name);

                //display total price
                double total_price = calTotalPrice(Snum,Bnum);
                System.out.println("Total price: $" + (total_price));

                //ask for payment method
                String[] payment_Method = new String[2];
                payment_Method = getPaymentMethod();

                //create order_no
                String order_no = getNextOrderNo();
                //System.out.println(order_no);

                //take current time
                String order_date = getOrderDate();
                //System.out.println(order_date);

                //Insert record into PLACE_ORDER TABLE
                try {
                    Statement stm = conn.createStatement();
                    String sql = "INSERT INTO PLACE_ORDER VALUES(" + "'" + order_no + "', " +
                            Snum + ", " +
                            "TO_DATE('" + order_date + "', 'dd/mm/yyyy:hh24/mi/ss'), " +
                            "'" + book_name + "', " +
                            total_price + "," +
                            "'" + payment_Method[0] + "', " +
                            "'" + payment_Method[1] + "')";
                    //System.out.println(sql);
                    stm.executeUpdate(sql);
                    stm.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    noException = false;
                }
                //Insert finish

                //Update Book stocks
                for (int i = 0; i < Bnum.length; ++i) {
                    updateBookStock(Bnum[i]);
                }
                //Finish update

                //Update discount_lv into Student Table
                updateDiscountLV(total_price,Snum);
                //Update finish

                //Update Deliver Table
                for (int i = 0; i < Bnum.length; ++i) {
                    updateDeliver(order_no,Bnum[i]);
                }

                System.out.println("Succeed to make order! ");

            }else if(choice == 3){
                continue;
            }else {
                System.out.println("Enter wrong number!");
            }
        }
    }

    private void orderCancelling() {

    }

    private boolean checkSnum(int Snum) {
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT SNUM FROM STUDENT" +
                    " WHERE SNUM = " + Snum;
            ResultSet rs = stm.executeQuery(sql);
            if(!rs.next()) {
                System.out.println("Student number not found!");
                rs.close();
                stm.close();
                return false;
            }
            rs.close();

            sql = "SELECT DELIVERY_DATE"
                    + " FROM PLACE_ORDER,DELIVER WHERE STUDENT = " + Snum
                    + " AND PLACE_ORDER.ORDER_NO = DELIVER.ORDER_NO";
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                try {
                    String value = rs.getString(1);
                    if (value == null) {
                        System.out.println("Books ordered earlier hadn't been delivered!");
                        System.out.println("New order making fail!");
                        rs.close();
                        stm.close();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }
        return true;
    }

    private void printBookList() {
        System.out.println("The book inside bookshop are:");
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM BOOK";
            ResultSet rs = stm.executeQuery(sql);
            String[] heads = { "Bnum","Title","Author","Price","Amount"};
            while (rs.next()) {
                for (int i = 0; i < 5; ++i) { //Print 5 attributes from BOOK
                    try {
                        String value = rs.getString( i + 1 );
                        if (value == null) {value = "";}
                        System.out.println(heads[i] + " : " + value);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("-------------------------");
            }
            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }
    }

    private boolean checkBookAmount(String Bnum) {
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT AMOUNT FROM BOOK" +
                    " WHERE BNUM = '" + Bnum.toUpperCase() + "'";
            ResultSet rs = stm.executeQuery(sql);
            if(!rs.next()) {
                System.out.println("Book: " + Bnum + " not found!");
                rs.close();
                stm.close();
                return false;
            }
            else {
                try {
                    String value = rs.getString(1);
                    if (Integer.parseInt(value) == 0) {
                        System.out.println("Books: " + Bnum + " out of stocks!");
                        rs.close();
                        stm.close();
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }

        return true;
    }

    private String getBookName(String Bnum) {
        String bookname = null;
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT TITLE FROM BOOK WHERE Bnum = '" + Bnum.toUpperCase() + "'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                try {
                    bookname = rs.getString( 1 );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }
        bookname = bookname.replaceAll("\\s+","");

        return bookname;
    }

    private double calTotalPrice(int Snum, String[] Bnum) {
        double total_price = 0.0;
        double discount_lv = 1.0;

        try {
            Statement stm = conn.createStatement();

            for(int i = 0 ; i < Bnum.length; i++) {
                String sql = "SELECT PRICE"
                        + " FROM BOOK WHERE BNUM = '" + Bnum[i].toUpperCase() +"'";
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next())
                    try {
                        String value = rs.getString(1);
                        if (value == null) {
                            System.out.println("Book number" + Bnum[i] + " not found!");
                        }
                        else {
                            total_price += Double.parseDouble(value);
                            //System.out.println("price: $" + value);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                rs.close();
            }

            String sql = "SELECT DISCOUNT_LEVEL"
                    + " FROM STUDENT WHERE SNUM = " + Snum;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next())
                try {
                    String value = rs.getString(1);
                    if (value != null) {
                        discount_lv = Double.parseDouble(value);
                    }
                    System.out.println("DISCOUNT_LEVEL: " + discount_lv);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }

        return (total_price * discount_lv);
    }

    private String[] getPaymentMethod() {
        System.out.println("Please choose a payment method!");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        System.out.println("3. Bank transfer");

        String line = in.nextLine();
        int payment_choice = Integer.parseInt(line);
        String[] paymentMethod = new String[2];
        paymentMethod[0] = "CASH";

        if (payment_choice == 2) {
            paymentMethod[0] = "CARD";
            System.out.println("Please input credit card number!");
            paymentMethod[1] = in.nextLine();
            paymentMethod[1] = paymentMethod[1].trim();
        }
        if (payment_choice == 3) {
            paymentMethod[0] = "BANK_TRANSFER";
        }

        return paymentMethod;
    }

    private String getNextOrderNo() {
        String order_no = null;

        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT MAX(ORDER_NO)"
                    + " FROM PLACE_ORDER";
            ResultSet rs = stm.executeQuery(sql);
            if(rs.next()) {
                try {
                    String value = rs.getString(1);
                    //System.out.println(value);
                    try {
                        order_no = "" + (Integer.parseInt(value.substring(4,value.length()))+1);
                        order_no = String.format("%6s", order_no).replace(' ', '0');
                        order_no = "ORD_" + order_no;
                        //System.out.println(order_int);
                    }catch (NumberFormatException e) {}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }

        return order_no;
    }

    private String getOrderDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String order_date = dateFormat.format(date);

        return order_date;
    }

    private int getBookAmount(String Bnum) {
        int amount = 0;
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT AMOUNT FROM BOOK" +
                    " WHERE BNUM = '" + Bnum.toUpperCase() + "'";
            ResultSet rs = stm.executeQuery(sql);
            if(rs.next()) {
                try {
                    String value = rs.getString(1);
                    amount = Integer.parseInt(value) - 1;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }

        return amount;
    }

    private void updateBookStock(String Bnum) {
        try {
            Statement stm = conn.createStatement();
            int amount = getBookAmount(Bnum);

            String sql = "UPDATE BOOK " +
                    "SET AMOUNT = " + amount +
                    " WHERE BNUM = '" + Bnum.toUpperCase() + "'";

            //System.out.println(sql);
            stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }

    }

    private void updateDiscountLV(Double total_price,int Snum) {
        Double discount_Lv = 1.0;

        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT SUM(TOTAL_PRICE) FROM PLACE_ORDER " +
                    "WHERE STUDENT = " + Snum;
            ResultSet rs = stm.executeQuery(sql);
            if(rs.next()) {
                try {
                    String value = rs.getString(1);
                    //System.out.println(value);
                    if (value == null) {value = "0";}
                    total_price = total_price + Double.parseDouble(value);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            rs.close();

            if (total_price >= 1000 && total_price < 2000) {
                discount_Lv = 0.9;
            }else if (total_price >= 2000) {
                discount_Lv = 0.8;
            }else {
                return;
            }

            sql = "UPDATE STUDENT " +
                    "SET DISCOUNT_LEVEL = " + discount_Lv +
                    " WHERE Snum = " + Snum;

            //System.out.println(sql);
            stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }

    }

    private void updateDeliver(String order_no, String Bnum) {
        try {
            Statement stm = conn.createStatement();
            String sql = "INSERT INTO DELIVER VALUES(" + "'" + order_no + "', " +
                    "'" + Bnum.toUpperCase() + "', " +
                    "null)" ;
            //System.out.println(sql);
            stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            noException = false;
        }
    }
    /**
     *
     * main() Function
     *
     */

    public static void main(String[] args) {
        OnlineBookShop onlineBookShop = new OnlineBookShop();
        if (!onlineBookShop.loginProxy()) {
            System.out.println("Login proxy failed, please re-examine your username and password!");
            return;
        }
        if (!onlineBookShop.loginDB()) {
            System.out.println("Login database failed, please re-examine your username and password!");
            return;
        }
        System.out.println("Login succeed!");
        try {
            onlineBookShop.run();
        } finally {
            onlineBookShop.close();
        }
    }
}
