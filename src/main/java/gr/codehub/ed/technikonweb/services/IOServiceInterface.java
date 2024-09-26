package gr.codehub.ed.technikonweb.services;

/**
 *
 * @author alexandrosaristeridis
 */
public interface IOServiceInterface {

	/**
	 *
	 * @param filename
	 */
	void saveOwnersToCsv(String filename);

	/**
	 *
	 * @param filename
	 */
	void savePropertiesToCsv(String filename);

	/**
	 *
	 * @param filename
	 */
	void saveRepairsToCsv(String filename);

	/**
	 *
	 * @param filename
	 * @return
	 */
	int readOwnersCsv(String filename);

	/**
	 *
	 * @param filename
	 * @return
	 */
	int readPropertiesCsv(String filename);

	/**
	 *
	 * @param filename
	 * @return
	 */
	int readRepairsFromCsv(String filename);

}
