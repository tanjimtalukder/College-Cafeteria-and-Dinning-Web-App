/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import session.CustomerFacade;
import session.CustomerOrderFacade; 
import session.OrderedProductFacade;
import session.ProductFacade;

/**
 *
 * @author TanjimTalukder
 */
@WebServlet(name = "AdminServlet",
        urlPatterns = {"/admin/",
            "/admin/viewOrders",
            "/admin/viewCustomers",
            "/admin/customerRecord",
            "/admin/orderRecord",
            "/admin/logout"})
public class AdminServlet extends HttpServlet {
 
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;
    @EJB
    private ProductFacade productFacade;

    private String userPath;
    private Customer customer;
    private CustomerOrder order;
    private List orderList = new ArrayList();
    private List customerList = new ArrayList();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();

        // if viewCustomers is requested
        if (userPath.equals("/admin/viewCustomers")) {
            customerList = customerFacade.findAll();
            request.setAttribute("customerList", customerList);
        }

        // if viewOrders is requested
        if (userPath.equals("/admin/viewOrders")) {
            orderList = customerOrderFacade.findAll();
            request.setAttribute("orderList", orderList);
        }

        // if customerRecord is requested
        if (userPath.equals("/admin/customerRecord")) {

            // get customer id from request
            String customerId = request.getQueryString();

            // get customer details
            customer = customerFacade.find(Integer.parseInt(customerId));
            request.setAttribute("customerRecord", customer);

            // get customer order details
            order = customerOrderFacade.findByCustomer(customer);
            request.setAttribute("order", order);
        }

        // if orderRecord is requested
        if (userPath.equals("/admin/orderRecord")) {

            // get customer id from request
            String orderId = request.getQueryString();

            // get order details
            Map orderMap = getOrderDetails(Integer.parseInt(orderId));

            // place order details in request scope
            request.setAttribute("customer", orderMap.get("customer"));
            request.setAttribute("products", orderMap.get("products"));
            request.setAttribute("orderRecord", orderMap.get("orderRecord"));
            request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));
        }

        // if logout is requested
        if (userPath.equals("/admin/logout")) {
            session = request.getSession();
            session.invalidate();   // terminate session
            response.sendRedirect("/tTalukderA2/admin/");
            return;
        }

        // use RequestDispatcher to forward request internally
        userPath = "/admin/index.jsp";
        try {
            request.getRequestDispatcher(userPath).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private Map getOrderDetails(int orderId) {

        Map orderMap = new HashMap();

        // get order 
        CustomerOrder order = customerOrderFacade.find(orderId);

        // get customer 
        Customer customer = order.getCustomer();

        // get all ordered products 
        List<OrderedProduct> orderedProducts = orderedProductFacade.findByOrderId(orderId);

        // get product details for ordered items 
        List<Product> products = new ArrayList<>();

        for (OrderedProduct op : orderedProducts) {

            Product p = (Product) productFacade.find(op.getOrderedProductPK().getProductId());
            products.add(p);
        }
        // add each item to orderMap 
        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("products", products);

        return orderMap;
    }
}
