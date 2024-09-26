package gr.codehub.ed.technikonweb.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author alexandrosaristeridis
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static EntityManagerFactory factory;

	/**
	 *
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {

            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

	/**
	 *
	 * @return
	 */
	public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

	/**
	 *
	 */
	public static void shutdown() {
        if (factory != null) {
            factory.close();
            factory = null;
        }
    }
}
