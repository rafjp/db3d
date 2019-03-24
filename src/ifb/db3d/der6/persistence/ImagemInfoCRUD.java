package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;

import ifb.db3d.der6.object.ImagemInfo;

public class ImagemInfoCRUD {
	public static void create(ImagemInfo imagemInfo) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().persist(imagemInfo);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void update(ImagemInfo imagemInfo) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().merge(imagemInfo);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void delete(ImagemInfo imagemInfo) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().remove(imagemInfo);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}
	
	public static ImagemInfo get(int id)
	{
		EntityManager em = ConnectionFactory.getEntityManager();
		return em.find(ImagemInfo.class, id);
	}
}
