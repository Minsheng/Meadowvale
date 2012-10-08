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
import meadowvale.controllers.exceptions.IllegalOrphanException;
import meadowvale.controllers.exceptions.NonexistentEntityException;
import meadowvale.entities.MvPerson;
import java.util.ArrayList;
import java.util.Collection;
import meadowvale.entities.MvPersonGroup;

/**
 *
 * @author Eric
 */
public class MvPersonGroupJpaController extends AbstractController{

    public MvPersonGroupJpaController(EntityManagerFactory emfact) {
        super(emfact);
    }

    public void create(MvPersonGroup mvPersonGroup) {
        if (mvPersonGroup.getMvPersonCollection() == null) {
            mvPersonGroup.setMvPersonCollection(new ArrayList<MvPerson>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<MvPerson> attachedMvPersonCollection = new ArrayList<MvPerson>();
            for (MvPerson mvPersonCollectionMvPersonToAttach : mvPersonGroup.getMvPersonCollection()) {
                mvPersonCollectionMvPersonToAttach = em.getReference(mvPersonCollectionMvPersonToAttach.getClass(), mvPersonCollectionMvPersonToAttach.getId());
                attachedMvPersonCollection.add(mvPersonCollectionMvPersonToAttach);
            }
            mvPersonGroup.setMvPersonCollection(attachedMvPersonCollection);
            em.persist(mvPersonGroup);
            for (MvPerson mvPersonCollectionMvPerson : mvPersonGroup.getMvPersonCollection()) {
                MvPersonGroup oldPersonGroupIdOfMvPersonCollectionMvPerson = mvPersonCollectionMvPerson.getPersonGroupId();
                mvPersonCollectionMvPerson.setPersonGroupId(mvPersonGroup);
                mvPersonCollectionMvPerson = em.merge(mvPersonCollectionMvPerson);
                if (oldPersonGroupIdOfMvPersonCollectionMvPerson != null) {
                    oldPersonGroupIdOfMvPersonCollectionMvPerson.getMvPersonCollection().remove(mvPersonCollectionMvPerson);
                    oldPersonGroupIdOfMvPersonCollectionMvPerson = em.merge(oldPersonGroupIdOfMvPersonCollectionMvPerson);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MvPersonGroup mvPersonGroup) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvPersonGroup persistentMvPersonGroup = em.find(MvPersonGroup.class, mvPersonGroup.getId());
            Collection<MvPerson> mvPersonCollectionOld = persistentMvPersonGroup.getMvPersonCollection();
            Collection<MvPerson> mvPersonCollectionNew = mvPersonGroup.getMvPersonCollection();
            List<String> illegalOrphanMessages = null;
            for (MvPerson mvPersonCollectionOldMvPerson : mvPersonCollectionOld) {
                if (!mvPersonCollectionNew.contains(mvPersonCollectionOldMvPerson)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MvPerson " + mvPersonCollectionOldMvPerson + " since its personGroupId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<MvPerson> attachedMvPersonCollectionNew = new ArrayList<MvPerson>();
            for (MvPerson mvPersonCollectionNewMvPersonToAttach : mvPersonCollectionNew) {
                mvPersonCollectionNewMvPersonToAttach = em.getReference(mvPersonCollectionNewMvPersonToAttach.getClass(), mvPersonCollectionNewMvPersonToAttach.getId());
                attachedMvPersonCollectionNew.add(mvPersonCollectionNewMvPersonToAttach);
            }
            mvPersonCollectionNew = attachedMvPersonCollectionNew;
            mvPersonGroup.setMvPersonCollection(mvPersonCollectionNew);
            mvPersonGroup = em.merge(mvPersonGroup);
            for (MvPerson mvPersonCollectionNewMvPerson : mvPersonCollectionNew) {
                if (!mvPersonCollectionOld.contains(mvPersonCollectionNewMvPerson)) {
                    MvPersonGroup oldPersonGroupIdOfMvPersonCollectionNewMvPerson = mvPersonCollectionNewMvPerson.getPersonGroupId();
                    mvPersonCollectionNewMvPerson.setPersonGroupId(mvPersonGroup);
                    mvPersonCollectionNewMvPerson = em.merge(mvPersonCollectionNewMvPerson);
                    if (oldPersonGroupIdOfMvPersonCollectionNewMvPerson != null && !oldPersonGroupIdOfMvPersonCollectionNewMvPerson.equals(mvPersonGroup)) {
                        oldPersonGroupIdOfMvPersonCollectionNewMvPerson.getMvPersonCollection().remove(mvPersonCollectionNewMvPerson);
                        oldPersonGroupIdOfMvPersonCollectionNewMvPerson = em.merge(oldPersonGroupIdOfMvPersonCollectionNewMvPerson);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mvPersonGroup.getId();
                if (findMvPersonGroup(id) == null) {
                    throw new NonexistentEntityException("The mvPersonGroup with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MvPersonGroup mvPersonGroup;
            try {
                mvPersonGroup = em.getReference(MvPersonGroup.class, id);
                mvPersonGroup.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mvPersonGroup with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MvPerson> mvPersonCollectionOrphanCheck = mvPersonGroup.getMvPersonCollection();
            for (MvPerson mvPersonCollectionOrphanCheckMvPerson : mvPersonCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MvPersonGroup (" + mvPersonGroup + ") cannot be destroyed since the MvPerson " + mvPersonCollectionOrphanCheckMvPerson + " in its mvPersonCollection field has a non-nullable personGroupId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(mvPersonGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MvPersonGroup> findMvPersonGroupEntities() {
        return findMvPersonGroupEntities(true, -1, -1);
    }

    public List<MvPersonGroup> findMvPersonGroupEntities(int maxResults, int firstResult) {
        return findMvPersonGroupEntities(false, maxResults, firstResult);
    }

    private List<MvPersonGroup> findMvPersonGroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MvPersonGroup.class));
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

    public MvPersonGroup findMvPersonGroup(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MvPersonGroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getMvPersonGroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MvPersonGroup> rt = cq.from(MvPersonGroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
