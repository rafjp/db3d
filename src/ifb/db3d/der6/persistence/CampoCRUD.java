package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;

import ifb.db3d.der6.object.Campo;

public class CampoCRUD {

	public static void create(Campo campo) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().persist(campo);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void update(Campo campo) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().merge(campo);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void delete(Campo campo) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().remove(campo);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}
	
	public static Campo get(int id)
	{
		EntityManager em = ConnectionFactory.getEntityManager();
		return em.find(Campo.class, id);
	}
}
