package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ifb.db3d.der6.javafx.control.MsgPopupControl;

public class ConnectionFactory {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	public static void open() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("der6");
			entityManager = entityManagerFactory.createEntityManager();
		} catch (Exception e) {
			MsgPopupControl.showExceptionAlert("Erro ao conectar ao servidor!", "Erro ao connectar", "Erro ao connectar", e);
			System.exit(0);
		}
	}

	public static void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
}
