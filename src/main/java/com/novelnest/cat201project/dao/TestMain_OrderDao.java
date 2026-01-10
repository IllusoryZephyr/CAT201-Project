package com.novelnest.cat201project.dao;

import com.novelnest.cat201project.models.BookInfo;
import com.novelnest.cat201project.models.Cart;

public class TestMain_OrderDao {

    public static void main(String[] args) {
        System.out.println("=== STARTING BACKEND TEST ===");

        // 1. Simulate a User Session: Create a Cart
        Cart myCart = new Cart();
        System.out.println("Step 1: Cart created.");

        // 2. Simulate Adding Books to Cart
        // Note: We are manually creating books here because we don't have the Catalog module yet.
        // Constructor: ID, Title, Synopsis, Price, Quantity (Stock), ImagePath
        BookInfo book1 = new BookInfo(101, "Java Programming", "John Doe", "Education", "Learn Java", 50.00, 10, "images/java.jpg");
        BookInfo book2 = new BookInfo(102, "Oracle SQL", "Jane Doe", "Education", "Master Database", 30.00, 5, "images/sql.jpg");

        // Add 1 copy of Java and 2 copies of SQL
        myCart.addItem(book1, 1);
        myCart.addItem(book2, 2);

        System.out.println("Step 2: Added items to cart.");
        System.out.println("   - Total Price Calculated by Logic: RM " + myCart.grandTotal());

        // 3. Define Test Data for Checkout
        int testUserId = 999; // Using a dummy ID since we don't have a Users table linked yet
        String testAddress = "123 Test Street, Penang, Malaysia";

        // 4. CALL THE DAO (The Real Test)
        System.out.println("Step 3: Attempting to save order to Oracle Database...");

        OrderDao dao = new OrderDao();
        int newOrderId = dao.saveOrder(myCart, testUserId, testAddress);

        // 5. Verify Results
        if (newOrderId > 0) {
            System.out.println("SUCCESS! ================================");
            System.out.println("Order was saved to database.");
            System.out.println("Generated Order ID: " + newOrderId);
            System.out.println("Check your 'ORDERS' and 'ORDER_DETAILS' tables in FreeSQL to verify.");
        } else {
            System.out.println("FAILED. =================================");
            System.out.println("Order ID returned was -1 or 0.");
            System.out.println("Check the console error logs above for SQL exceptions.");
        }
    }
}