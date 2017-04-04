/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author TanjimTalukder
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {
    @PersistenceContext(unitName = "tTalukderA2PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

}