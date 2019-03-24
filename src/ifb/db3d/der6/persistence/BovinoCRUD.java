package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;

import ifb.db3d.der6.object.Bovino;

public class BovinoCRUD {

	public static void create(Bovino bovino) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().persist(bovino);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void update(Bovino bovino) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().merge(bovino);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void delete(Bovino bovino) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().remove(bovino);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}
	
	public static Bovino get(int id)
	{
		EntityManager em = ConnectionFactory.getEntityManager();
		return em.find(Bovino.class, id);
	}
}
