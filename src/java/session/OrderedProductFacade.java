/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author TanjimTalukder
 */
@Stateless
public class OrderedProductFacade extends AbstractFacade<OrderedProduct> {

    @PersistenceContext(unitName = "tTalukderA2PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderedProductFacade() {
        super(OrderedProduct.class);
    }

    // manually created
    public List<OrderedProduct> findByOrderId(Object id) {
        return em.createNamedQuery("OrderedProduct.findByCustomerOrderId").setParameter("customerOrderId", id).getResultList();
    }

    //added addOrderedItems business method 
    public void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        em.flush(); 
        
        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedProducts 
        for (ShoppingCartItem scItem : items) {

            int productId = scItem.getProduct().getId();

            // set up primary key object 
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrderId(order.getId());
            orderedProductPK.setProductId(productId);

            // create ordered item using PK object 
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);

            // set quantity 
            orderedItem.setQuantity(scItem.getQuantity());

            em.persist(orderedItem);
        }
    }

}
