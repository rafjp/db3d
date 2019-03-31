package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;

import ifb.db3d.der6.object.Regiao;

public class RegiaoCRUD {

	public static void create(Regiao regiao) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().persist(regiao);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void update(Regiao regiao) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().merge(regiao);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void delete(Regiao regiao) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().remove(regiao);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static Regiao get(int id) {
		EntityManager em = ConnectionFactory.getEntityManager();
		return em.find(Regiao.class, id);
	}
}
