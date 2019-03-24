package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class ConnectionFactory {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	
	public static void open()
	{
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("der6");
			entityManager = entityManagerFactory.createEntityManager();
		} catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao servidor!\n\n" + e.getMessage(), "Erro ao connectar", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public static void close()
	{
		entityManager.close();
		entityManagerFactory.close();
	}
	
	public static EntityManager getEntityManager()
	{
		return entityManager;
	}
}
