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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.text.Document;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class bookshop {

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

	String global_sshUser = "exxxxxxx"; // exxxxxxx
	String global_sshPwd = "exxxxxxx"; // exxxxxxx

	boolean exitFlag = false;

	public bookshop() {
		System.out.println("Welcome to ABC University Online Bookshop!");
		in = new Scanner(System.in);
	}

	String[] menus = {
			"1. Order Search/Update",
			"2. Order Making",
			"3. Order Cancelling"
	};

	/*** shopMenus **/
	public String shopMenus() {
		String menuStr = "";
		System.out.println("Please choose an action:");
		for (int i = 0; i < menus.length; ++i) {
			System.out.println(menus[i]);
			menuStr += menus[i] + "\n";
		}
		return menuStr;
	}

	/*** DBExit ***/
	public boolean DBExit(){
		boolean myExitFlag = exitFlag;
		return myExitFlag;
	}

	/*** close ***/
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

	/*** orderSearch ***/
	public String orderSearch(String stdNumStr){  //main function 1:
		/** searchFlag = true: search, searchFlag = false: update **/
		System.out.println("Please input the Student ID to search order info:");
//		String line = in.nextLine();
		String  orderSearchOrUpdatingStr = "";
		String line = null;
		line = stdNumStr;
		line = line.trim();
		/** order searching **/
		if(line != null){
			int snum = Integer.parseInt(line);
			boolean checkNo = checkSnum(snum);
			if (checkNo == true){
				orderSearchOrUpdatingStr = printOrderBySnum(snum);
				return orderSearchOrUpdatingStr;
			}else{
				orderSearchOrUpdatingStr = "Student number not found!";
				return orderSearchOrUpdatingStr;
			}

		}

		orderSearchOrUpdatingStr = "Your input student ID is null value.";
		return orderSearchOrUpdatingStr;
	}

	/*** orderDateUpdate ***/
	public boolean orderDateUpdate(String orderNumStr, String bookNumStr, String dateUpdtStr){  //main function 1:
		String line1, line2, line3, line4;
		line1 = orderNumStr;
		line2 = bookNumStr;
		line3 = dateUpdtStr;

		line1 = line1.trim();
		/** order date updating **/

					System.out.println("admin logged in!");

					System.out.println("admin function:");

					/** ---- **/
					System.out.println("1. Show all orders");
					System.out.println("2. Show orders by student ID");
					/** ---- **/

					System.out.println("3. Update Order");
					System.out.println("4. Back to main menu");


					System.out.println("Please input order number:");
//										String Order_no = in.nextLine();
					String Order_no = line1;

					Order_no = Order_no.trim();
					System.out.println("Please input book Number:");

//										String Book_no = in.nextLine();
					String Book_no = line2;

					Book_no = Book_no.trim();

					if(!printOrderByOrderNoAndBnum(Order_no,Book_no))
						return false;

					System.out.println("Update this record? (Yes / No):");

					System.out.println("Please input a date (dd/mm/yyyy)");
//													String date = in.nextLine();

					String date = line3;
					date = date.trim();

					if(!updateDeliveryDate(Order_no, Book_no, date))
						return false;

					System.out.println("Updated record!");

					return true;
	}

	/*** orderMaking ***/
	public String orderMaking(String stdNumStr, String bookNumStr, int paymentChoice, String paymentCardNumber) {  //main function 2:

		String orderConfirmInfoStr = "";

		int choice = paymentChoice;
		String cardNumber = paymentCardNumber;
		System.out.println("--------------Order making requirements--------------");
		System.out.println("I. Every book in the order is NOT out of stock");
		System.out.println("II. Not have any outstanding orders(All books ordered earlier had been delivered)");
		System.out.println("-----------------------------------------------------");
//        while (choice != 3) {
		System.out.println("Please choose an action:");
		System.out.println("1. Display list of books.");
		System.out.println("2. Make a new order.");
		System.out.println("3. Back to main menus.");
//		String line = in.nextLine();

//		choice = Integer.parseInt(line);

		//            if (choice == 1) {
		//                //print book table
		//                printBookList();
		//            }else if (choice == 2) {
		System.out.println("Please input your student number!");

		//check outstanding order
		//                            line = in.nextLine();

		String line = stdNumStr;
		line = line.trim();

//		if (line.equalsIgnoreCase("exit"))
//			return null;

		int Snum = Integer.parseInt(line);

		String checkOrder = null;
		checkOrder = checkNextOrder(Snum);
		if (!checkOrder.contains("Yes")){
			return checkOrder;
		}

		//making order
		System.out.println("Please input book number:");
		System.out.println("(If more than 1 book, please input by following format: book1,book2,book3)");
		//                            line = in.nextLine();

		line = bookNumStr;
		String[] Bnum = line.split(",");
		String book_name = null;
		for (int i = 0; i < Bnum.length; ++i) {
			Bnum[i] = Bnum[i].trim();
			String checkBookAmountStr = checkBookAmount(Bnum[i]);
			if (!checkBookAmountStr.contains("Yes"))
				return checkBookAmountStr;
		}

		for (int i = 0; i < Bnum.length; ++i) {
			if (i == 0)
				book_name = getBookName(Bnum[i]);

			if (i > 0)
				book_name = book_name + ", " + getBookName(Bnum[i]);
			//System.out.println("Book number:" + Bnum[i]);
		}
		System.out.println("Book ordered:" + book_name);

		orderConfirmInfoStr += "Book ordered:" + book_name + "\n";

		//display total price
		double total_price = calTotalPrice(Snum,Bnum);
		System.out.println("Total price: $" + (total_price));

		orderConfirmInfoStr += "Total price: $" + (total_price) + "\n";

		//ask for payment method
		String[] payment_Method = new String[2];
		payment_Method = getPaymentMethod(choice, cardNumber);


		//create order_no
		String order_no = getNextOrderNo();
		//System.out.println(order_no);

		//take current time
		String order_date = getCurrentDate();
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
		updateDiscountLV(Snum);
		//Update finish

		//Update Deliver Table
		for (int i = 0; i < Bnum.length; ++i) {
			updateDeliver(order_no,Bnum[i]);
		}

		System.out.println("Succeed to make order! ");

		orderConfirmInfoStr += "Succeed to make order! \n";

		return orderConfirmInfoStr;

		//            }else if(choice == 3){
		//                continue;
		//            }else {
		//                System.out.println("Enter wrong number!");
		//            }
//        }
	}

	/*** orderCancelling ***/
	public String orderCancelling(String orderNum) {  //main function 3:
		String orderCancellingInfoStr = null;
		String anOrderStr = null;

		System.out.println("------------Order cancelling requirements------------");
		System.out.println("I. None of the books in the order has been delivered");
		System.out.println("II. Order was made within 7 days");
		System.out.println("-----------------------------------------------------");
		System.out.println("Please input the Student ID or type 'exit' back to main menu:");
//		String line = in.nextLine();
//		line = line.trim();
//		System.out.println(line);
//		if (line.equalsIgnoreCase("exit"))
//			return null;

//		int snum = Integer.parseInt(line);
//		boolean checkNo = checkSnum(snum);
//		if (checkNo == true) {
//			System.out.println("-----------------------------------------------------");
//			System.out.println("--------------Display all order records--------------");
//			System.out.println("-----------------------------------------------------");
//			printOrderBySnum(snum);
//		}

		System.out.println("Please enter an order number to finish order cancelling:");
//		String order_no = in.nextLine();
		String order_no = orderNum;
		order_no = order_no.trim();

//		anOrderStr = printOrderByOrderNo(order_no);

		System.out.println("Cancel this order? (Yes / No):");
//        String orderCancelChoice = in.nextLine();

//        orderCancelChoice = orderCancelChoice.trim();
//        if(orderCancelChoice.equalsIgnoreCase("yes")) {

		//check at least order book delivered or not
		if (checkOrderDelivered(order_no) == false) {
			System.out.println("Order cancelling failed!");
			System.out.println("Some book of this order had been delivered!");
			orderCancellingInfoStr = "Order cancelling failed!\n" + "Some book of this order had been delivered!\n";
			return orderCancellingInfoStr;
		}

		if (checkOrderDateWithin7(order_no) == false) {
			System.out.println("Order cancelling failed!");
			System.out.println("Order made before 7 days!");
			orderCancellingInfoStr = "Order cancelling failed!\n" + "Order made before 7 days!\n";
			return orderCancellingInfoStr;
		}

		String snum = getStudentNum(order_no);

		if(snum == null){
			System.out.println("Get student ID error! We cannot update the discount level.");
			orderCancellingInfoStr = "Get student ID error! We cannot update the discount level.";
			return orderCancellingInfoStr;
		}

		String[] orderBnums = getBnumByOrderNo(order_no); /** 1 **/

		for (int i = 0; orderBnums[i] != null; i++)
		{
//				System.out.println(orderBnum[i]);
			if(!cancelBook(orderBnums[i]))  /** new 2 **/
				return "Cannot cancel book orders!";
		}

		if(!cancelOrder(order_no)){
			orderCancellingInfoStr = "Order cancelling failed\n";
			return orderCancellingInfoStr;
		}

		updateDiscountLV(Integer.parseInt(snum)); /** new 3 **/

//		System.out.println("cancelBook invoked.");

		System.out.println("Successfully cancel the order!");

		orderCancellingInfoStr = "Successfully cancel the order!\n";

//        }else if(orderCancelChoice.equalsIgnoreCase("no")) {
//
//        }else {
//
//        }
		return orderCancellingInfoStr;

	}

	/*** getStudentNum ***/
	public String getStudentNum(String orderNum){
		String order_no = orderNum;
		order_no = order_no.trim();
		System.out.println("Order Num:" + order_no);
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT DISTINCT STUDENT"
					+ " FROM PLACE_ORDER"
					+ " WHERE ORDER_NO = '" + order_no + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
					try {
						String value = rs.getString( 1 );
						if (value == null) {value = "";}
						System.out.println("Student ID: " + value);

						return value;

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
		return null;
	}

	/*** printAllOrders ***/
	public String printAllOrders() {
		String allOrdersStr = "";
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT * FROM PLACE_ORDER";
			ResultSet rs = stm.executeQuery(sql);
			String[] heads = { "Order_Number", "Student", "Order_Date", "Books_Ordered",
					"Total_Price", "Payment_method", "Card_No"};
			while (rs.next()) {
				for (int i = 0; i < heads.length; ++i) { //Print 9 attributes from PLACE_ORDER + DELIVER
					try {
						String value = rs.getString( i + 1 );
						if (value == null) {value = "";}
						System.out.println(heads[i] + " : " + value);
						allOrdersStr += heads[i] + " : " + value + "\n";
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println("-------------------------");
				allOrdersStr += "-------------------------\n";
			}

			rs.close();
			stm.close();
			return allOrdersStr;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
			return null;
		}
	}

	/*** printOrderBySnum ***/
	private String printOrderBySnum(int Snum) {
		String orderStr = "";
		String orderNum = null;
		double discount_lv = 0.0;
		try {
			String[] heads1 = { "Student_ID", "Name", "Gender", "Major", "Discount_Level"};

			String[] heads2 = { "Order_Number", "Student", "Order_Date", "Books_Title", "Price", "Bnum", "Payment_method", "Card_No", "Deliver_Date"};

			Statement stm = conn.createStatement();

			String sql =
					"SELECT SNUM, NAME, GENDER, MAJOR, DISCOUNT_LEVEL"
							+ " FROM STUDENT"
							+ " WHERE SNUM = " + Snum;
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				for (int i = 0; i < heads1.length; ++i) { //Print 5 attributes from PLACE_ORDER + DELIVER
					try {
						String value = rs.getString( i + 1 );
						if (value == null) {value = "";}
						if(heads1[i].contains("Discount_Level")){
							discount_lv = Double.parseDouble(value);
						}
						System.out.println(heads1[i] + " : " + value);
						orderStr += heads1[i] + " : " + value + "\n";
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println("-------------------------\n\n");
				orderStr += "-------------------------\n\n";
			}

			sql = "SELECT DELIVER.ORDER_NO, STUDENT, ORDER_DATE, BOOK.TITLE, BOOK.PRICE, BOOK.BNUM, PAYMENT_METHOD, CARD_NO, DELIVERY_DATE"
					+ " FROM PLACE_ORDER, DELIVER, BOOK"
					+ " WHERE STUDENT = " + Snum + " AND DELIVER.BNUM = BOOK.BNUM AND DELIVER.ORDER_NO = PLACE_ORDER.ORDER_NO";
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				for (int i = 0; i < heads2.length; ++i) { //Print 9 attributes from PLACE_ORDER + DELIVER
					try {
						String value = rs.getString( i + 1 );
						if (value == null) {value = "";}
						System.out.println(heads2[i] + " : " + value);

						if(heads2[i].contains("Order_Number")){ /** Get order number **/
							orderNum = value;
						}

						if(heads2[i].contains("Books_Title")){
							System.out.println(heads2[i] + " : " + value);
							orderStr += heads2[i] + " : " + value;
						}else if(heads2[i].contains("Price")){

							double calculateDiscount = singleBookDiscount(orderNum); /** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! **/

							System.out.println(heads2[i] + " : " + (calculateDiscount*Double.parseDouble(value)));
							orderStr += heads2[i] + " : " + (calculateDiscount * Double.parseDouble(value)) + "\n";
						}
						else{
							orderStr += heads2[i] + " : " + value + "\n";
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println("-------------------------");
				orderStr += "-------------------------\n";
			}
			rs.close();
			stm.close();
			return orderStr;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
			return null;
		}
	}

	/*** printOrderByOrderNoAndBnum ***/
	private double singleBookDiscount(String orderNum){
		double totalPrice = 0.0;
		try {
			Statement stm = conn.createStatement();

			String sql = "SELECT TOTAL_PRICE FROM PLACE_ORDER WHERE ORDER_NO = '" + orderNum + "'";

//			System.out.println("Total price 2 (orderNum): " + orderNum);

			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
					try {
						String value = rs.getString(1);
						if (value == null) {value = "";}

						System.out.println("Total price(singleBookDiscount): " + value);
						totalPrice = Double.parseDouble(value);

					} catch (SQLException e) {
						e.printStackTrace();
						return 1.0;
					}
			}
//			System.out.println("Total price 2 (singleBookDiscount): " + totalPrice);

			sql = "SELECT PRICE FROM DELIVER, BOOK WHERE ORDER_NO = '" + orderNum + "' AND DELIVER.BNUM = BOOK.BNUM";

			rs = stm.executeQuery(sql);

			double sum = 0.0;

			while (rs.next()) {
				try {
					String value = rs.getString(1);
					if (value == null) {value = "";}
					sum += Double.parseDouble(value);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			rs.close();
			stm.close();
			System.out.println("Sum singleBookDiscount(): " + sum);
			System.out.println("singleBookDiscount(): " + totalPrice/sum);
			return totalPrice/sum;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
			return 1.0;
		}
	}

	/*** printOrderByOrderNoAndBnum ***/
	private boolean printOrderByOrderNoAndBnum(String Order_no, String Bnum) {
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT ORDER_NO, DELIVER.BNUM, BOOK.TITLE, DELIVERY_DATE FROM DELIVER, BOOK"
					+ " WHERE ORDER_NO = '" + Order_no.toUpperCase() + "'"
					+ " AND DELIVER.BNUM = '" + Bnum.toUpperCase() + "'"
					+ " AND BOOK.BNUM = DELIVER.BNUM";
			ResultSet rs = stm.executeQuery(sql);
			String[] heads = { "Order_Number", "Book_Number", "Book_Name", "Deliver_Date"};
			while (rs.next()) {
				for (int i = 0; i < 4; ++i) { //Print 3 attributes from PLACE_ORDER + DELIVER
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
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
			return false;
		}
	}

	/*** updateDeliveryDate ***/
	public boolean updateDeliveryDate(String order_no, String Bnum, String date) {
		try {
			Statement stm = conn.createStatement();
			String sql = "UPDATE DELIVER" +
					" SET DELIVERY_DATE =" + "TO_DATE('" + date + "', 'dd/mm/yyyy') "+
					" WHERE ORDER_NO = '" + order_no.toUpperCase() + "'"+
					" AND BNUM = '" + Bnum.toUpperCase() +"'";
			System.out.println("Updated successfully!");
			//System.out.println(sql);
			stm.executeUpdate(sql);
			stm.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
			return false;
		}
	}

	/*** checkSnum ***/
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
			stm.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
		}
		return true;
	}

	/*** checkNextOrder ***/
	private String checkNextOrder(int Snum) {
		String checkOrderStr = null;
		try {
			if(!checkSnum(Snum)){
				return "Student number not found!";
			}

//			System.out.println("getCurrentDate(): " + getCurrentDate());


			Statement stm = conn.createStatement();
			String sql = "SELECT DELIVERY_DATE"
					+ " FROM PLACE_ORDER,DELIVER WHERE STUDENT = " + Snum
					+ " AND PLACE_ORDER.ORDER_NO = DELIVER.ORDER_NO";
			ResultSet rs = stm.executeQuery(sql);


			while (rs.next()) {
				try {
					String value = rs.getString(1);
					if (value == null) { /** When the value is null, it means delivery_date has not set yet **/
						System.out.println("Books ordered earlier hadn't been delivered!");
						System.out.println("New order making failed!");

						checkOrderStr = "Books ordered earlier hadn't been delivered!\n";
						checkOrderStr += "New order making failed!\n";

						rs.close();
						stm.close();
						return checkOrderStr;
					}
					else{
						try {
							DateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							DateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							value = value.substring(0, 19);

							Date deliveryDate = dbDateFormat.parse(value);
							String newDeliveryDateStr = currentDateFormat.format(deliveryDate);

							Date newDeliveryDate = currentDateFormat.parse(newDeliveryDateStr);
							Date currentDate = currentDateFormat.parse(getCurrentDate());

							if(newDeliveryDate.compareTo(currentDate) > 0){
								System.out.println("Books ordered earlier hadn't been delivered!");
								System.out.println("New order making failed!");

								checkOrderStr = "Books ordered earlier hadn't been delivered!\n";
								checkOrderStr += "New order making failed!\n";
								rs.close();
								stm.close();
								return checkOrderStr;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
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
		return "Yes";
	}

	/*** printBookList ***/
	public String printBookList() {
		String bookListStr = "";
		System.out.println("The book inside bookshop are:");
		bookListStr += "The book inside bookshop are:";
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
						bookListStr += heads[i] + " : " + value + "\n";
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println("-------------------------");
				bookListStr += "-------------------------\n";
			}
			rs.close();
			stm.close();
			return bookListStr;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
		}
		return null;
	}

	/*** checkBookAmount ***/
	private String checkBookAmount(String Bnum) {
		String bookAmountStr = null;
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT AMOUNT FROM BOOK" +
					" WHERE BNUM = '" + Bnum.toUpperCase() + "'";
			ResultSet rs = stm.executeQuery(sql);
			if(!rs.next()) {
				System.out.println("Book: " + Bnum + " not found!");
				bookAmountStr = "Book: " + Bnum + " not found!\n";
				rs.close();
				stm.close();
				return bookAmountStr;
			}
			else {
				try {
					String value = rs.getString(1);
					if (Integer.parseInt(value) == 0) {
						System.out.println("Books: " + Bnum + " out of stocks!");
						bookAmountStr = "Books: " + Bnum + " out of stocks!\n";
						rs.close();
						stm.close();
						return bookAmountStr;
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
		return "Yes";
	}

	/*** getBookName ***/
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

	/*** calTotalPrice ***/
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

	/*** getPaymentMethod ***/
	private String[] getPaymentMethod(int choice, String cardNumber) {
		System.out.println("Please choose a payment method!");
		System.out.println("1. Cash");
		System.out.println("2. Credit Card");
		System.out.println("3. Bank transfer"); /** ? - error **/

		String[] paymentMethod = new String[2];
		paymentMethod[0] = "CASH";
		paymentMethod[1] = "NULL";

//		String line = in.nextLine();
//		int payment_choice = Integer.parseInt(line);

		int payment_choice = choice;

		if (payment_choice == 2) {
			paymentMethod[0] = "CARD";
			System.out.println("Please input credit card number!");
//			paymentMethod[1] = in.nextLine();
			paymentMethod[1] = cardNumber;
			paymentMethod[1] = paymentMethod[1].trim();
		}
		if (payment_choice == 3) {
			paymentMethod[0] = "BANK_TRANSFER";
		}

		return paymentMethod;
	}

	/*** getNextOrderNo ***/
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

	/*** getCurrentDate ***/
	private String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String order_date = dateFormat.format(date);

		return order_date;
	}

	/*** getBookAmount ***/
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

	/*** updateBookStock ***/
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

	/*** updateDiscountLV ***/
	private void updateDiscountLV(int Snum) {
		Double discount_Lv = 1.0;
		Double total_price = 0.0;

		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT SUM(TOTAL_PRICE) FROM PLACE_ORDER " +
					"WHERE STUDENT = " + Snum;
			ResultSet rs = stm.executeQuery(sql);
			if(rs.next()) {
				try {
					String value = rs.getString(1);
					System.out.println("SUM(TOTAL_PRICE) FROM PLACE_ORDER: " + value);
					if (value == null) {value = "0";}
					total_price = Double.parseDouble(value);
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
				discount_Lv = 1.0;
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

	/*** updateDeliver ***/
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

	/*** printOrderByOrderNo ***/
	public String printOrderByOrderNo (String Order_no) {
		String orderInfoStr = "";
		String orderNumLine = Order_no;
		orderNumLine = orderNumLine.trim();
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT * FROM PLACE_ORDER"
					+ " WHERE ORDER_NO = '" + orderNumLine.toUpperCase() + "'";
			ResultSet rs = stm.executeQuery(sql);
			String[] heads = {"Order_Number", "Student", "Order_Date", "Books_Ordered",
					"Total_Price", "Payment_method", "Card_No"};
			while (rs.next()) {
				for (int i = 0; i < 7; ++i) { //Print 7 attributes from PLACE_ORDER + DELIVER
					try {
						String value = rs.getString( i + 1 );
						if (value == null) {value = "";}
						System.out.println(heads[i] + " : " + value);
						orderInfoStr += heads[i] + " : " + value + "\n";

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println("-------------------------");
				orderInfoStr += "-------------------------\n";
			}
			rs.close();
			stm.close();
			return orderInfoStr;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
			return null;
		}
	}

	/*** checkOrderDelivered ***/
	private boolean checkOrderDelivered(String order_no) {
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT DELIVERY_DATE FROM DELIVER" +
					" WHERE ORDER_NO = '" + order_no.toUpperCase() + "'";
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				String value = rs.getString(1);
				if (value != null)
				{
					rs.close();
					stm.close();
					return false;
				}
				//System.out.println(value);
			}
			rs.close();
			stm.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
		}
		return true;
	}

	/*** checkOrderDateWithin7 ***/
	private boolean checkOrderDateWithin7 (String order_no) {
		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT ORDER_DATE FROM PLACE_ORDER"
					+ " WHERE ORDER_NO = '" + order_no.toUpperCase() + "'";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				try {
					String order_date = rs.getString(1);
//					System.out.println(order_date);
					DateFormat orderdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					try {
						order_date = order_date.substring(0, 19);
//						System.out.println(order_date);
						Date date = orderdateFormat.parse(order_date);
						order_date = dateFormat.format(date);
//						System.out.println(order_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String today = getCurrentDate();
//					System.out.println(today);
					Date d1 = null;
					Date d2 = null;
					try {
						d1 = dateFormat.parse(order_date);
						d2 = dateFormat.parse(today);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					long differents = d2.getTime() - d1.getTime();
					long diffHours = differents / (60 * 60 * 1000);
					//System.out.println("Book order before :" + diffHours);
					if (diffHours > 168) //7 days = 168 hrs
					{
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

	/*** cancelOrder ***/
	private boolean cancelOrder(String order_no) {
		try {
			Statement stm = conn.createStatement();
			String sql = "DELETE FROM DELIVER WHERE ORDER_NO = '" + order_no.toUpperCase() + "'" ;
			//System.out.println(sql);
			stm.executeUpdate(sql);
			sql = "DELETE FROM PLACE_ORDER WHERE ORDER_NO = '" + order_no.toUpperCase() + "'" ;
			stm.executeUpdate(sql);
			stm.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
			return false;
		}
	}

	/*** cancelBook ***/
	private boolean cancelBook(String Bnum) {
		try {
			Statement stm = conn.createStatement();
			int amount = getBookAmount(Bnum) + 2;

			String sql = "UPDATE BOOK " +
					"SET AMOUNT = " + amount +
					" WHERE BNUM = '" + Bnum.toUpperCase() + "'";

			//System.out.println(sql);
			stm.executeUpdate(sql);
			stm.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			noException = false;
		}
		return false;
	}

	/*** getBnumByOrderNo ***/
	private String[] getBnumByOrderNo(String order_no) {

		String orderedBnum[] = new String[20];
		int bookNumberCount = 0;

		try {
			Statement stm = conn.createStatement();
			String sql = "SELECT BNUM FROM DELIVER"
					+ " WHERE ORDER_NO = '" + order_no.toUpperCase() + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				try {
					String value = rs.getString(1);
					orderedBnum[bookNumberCount] = value;
					bookNumberCount++;
					System.out.println("getBnumByOrderNo value: " + value);
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

		return orderedBnum;
	}

	/*** ------------------------------------------------ ***/

	/*** main() ***/
//	public static void main(String[] args) {
//		bookshop manager = new bookshop();
//		if (!manager.auTologinProxy()) {
//			System.out.println("Login proxy failed, please re-examine your username and password!");
//			return;
//		}
//		if (!manager.loginDB()) {
//			System.out.println("Login database failed, please re-examine your username and password!");
//			return;
//		}
//		//System.out.println("Login succeed!");
//		try {
//			/*** Invoke the GUI ***/
//			GUI guiWin = new GUI();
//			guiWin.majorFrame.setVisible(true);
//			System.out.println("GUI is running!");
//			/*** Invoke run() ***/
//			manager.run();
//
//		} finally {
//			manager.close();
//		}
//	}

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
//		dialog.addWindowListener(new WindowAdapter() {
//			public void windowClosed(WindowEvent e)
//			{
//				System.exit(0);
//			}
//		});
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
//			String sshUser = namePwd[0];
//			String sshPwd = namePwd[1];
			global_sshUser = namePwd[0];
			global_sshPwd = namePwd[1];
			try {
				proxySession = new JSch().getSession(global_sshUser, proxyHost, proxyPort);
				proxySession.setPassword(global_sshPwd);
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
//		if(username.equalsIgnoreCase("e1234567") || password.equalsIgnoreCase("e1234567")) {
//			String[] namePwd = getUsernamePassword("Login sqlplus");
//			username = namePwd[0];
//			password = namePwd[1];
			username = global_sshUser;
			password = global_sshUser; /** Since both the account and password are the ssh account name **/
//		}
		String URL = "jdbc:oracle:thin:@" + jdbcHost + ":" + jdbcPort + "/" + database;

		try {
			//System.out.println("Logging " + URL + " ...");
			conn = DriverManager.getConnection(URL, username, password);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*** auTologinProxy ***/
	public boolean auTologinProxy() {
		String sshUser = global_sshUser;  //Change login ac
		String sshPwd = global_sshPwd;
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

		return true;
	}

}