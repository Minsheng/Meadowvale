/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meadowvale.controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import meadowvale.controllers.exceptions.NonexistentEntityException;
import meadowvale.entities.MvContact;

/**
 *
 * @author Eric
 */
public class MvContactJpaController extends AbstractController{

    public MvContactJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }


    public void create(MvContact mvContact) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mvContact);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvContact mvContact) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mvContact = em.merge(mvContact);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvContact.getId();
                if (findMvContact(id) == null) {
                    throw new NonexistentEntityException("The mvContact with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvContact mvContact;
            try {
                mvContact = em.getReference(MvContact.class, id);
                mvContact.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvContact with id " + id + " no longer exists.", enfe);
            }
            em.remove(mvContact);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvContact> findMvContactEntities() {
        return findMvContactEntities(true, -1, -1);
    }

    public List<MvContact> findMvContactEntities(int maxResults, int firstResult) {
        return findMvContactEntities(false, maxResults, firstResult);
    }

    private List<MvContact> findMvContactEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvContact.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MvContact findMvContact(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvContact.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvContactCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvContact> rt = cq.from(MvContact.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
