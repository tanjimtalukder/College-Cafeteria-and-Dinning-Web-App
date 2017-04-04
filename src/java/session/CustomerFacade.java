/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author TanjimTalukder
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "tTalukderA2PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    //add addCustomer business method 
    public Customer addCustomer(String name, String email, String phone, String address, String ccNumber) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        // customer.setCityRegion(cityRegion); 
        customer.setCcNumber(ccNumber);

        em.persist(customer);
        return customer;
    }

}
