package ifb.db3d.der6.persistence;

import javax.persistence.EntityManager;

import ifb.db3d.der6.object.Sensor;

public class SensorCRUD {
	public static void create(Sensor sensor) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().persist(sensor);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void update(Sensor sensor) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().merge(sensor);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static void delete(Sensor sensor) {
		ConnectionFactory.getEntityManager().getTransaction().begin();
		ConnectionFactory.getEntityManager().remove(sensor);
		ConnectionFactory.getEntityManager().getTransaction().commit();
	}

	public static Sensor get(int id) {
		EntityManager em = ConnectionFactory.getEntityManager();
		return em.find(Sensor.class, id);
	}
}
