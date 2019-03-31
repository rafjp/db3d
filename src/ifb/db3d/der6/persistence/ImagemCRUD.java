package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;

import ifb.db3d.der6.object.Imagem;

public class ImagemCRUD {
	public static void create(Imagem imagem) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().persist(imagem);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void update(Imagem imagem) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().merge(imagem);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void delete(Imagem imagem) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().remove(imagem);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static Imagem get(int id) {
		EntityManager em = ConnectionFactory.getEntityManager();
		return em.find(Imagem.class, id);
	}
}
