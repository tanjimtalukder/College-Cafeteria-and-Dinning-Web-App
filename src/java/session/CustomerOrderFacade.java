/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import entity.Customer;
import entity.CustomerOrder;
import java.math.BigDecimal;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author TanjimTalukder
 */
@Stateless
public class CustomerOrderFacade extends AbstractFacade<CustomerOrder> {

    @PersistenceContext(unitName = "tTalukderA2PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerOrderFacade() {
        super(CustomerOrder.class);
    }

    // overridden - refresh method called to retrieve order id from database
    public CustomerOrder find(Object id) {
        CustomerOrder order = em.find(CustomerOrder.class, id);
        em.refresh(order);
        return order;
    }

    // manually created
    // in this implementation, there is only one order per customer
    // the data model however allows for multiple orders per customer
    public CustomerOrder findByCustomer(Object customer) {
        return (CustomerOrder) em.createNamedQuery("CustomerOrder.findByCustomer").setParameter("customer", customer).getSingleResult();
    } 

    //add CustomerOrder addOrder business method 
    public CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        // set up customer order 
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number 
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);

        em.persist(order);
        return order;
    }
}
