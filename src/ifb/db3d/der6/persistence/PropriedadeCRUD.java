package ifb.db3d.der6.persistence;

import ifb.db3d.der6.object.Propriedade;

public class PropriedadeCRUD {

	public static void create(Propriedade propriedade) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().persist(propriedade);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void update(Propriedade propriedade) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().merge(propriedade);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void delete(Propriedade propriedade) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().remove(propriedade);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}
}
