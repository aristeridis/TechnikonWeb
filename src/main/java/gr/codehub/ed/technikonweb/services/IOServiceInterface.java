package gr.codehub.ed.technikonweb.services;

public interface IOServiceInterface {

    void saveOwnersToCsv(String filename);

    void savePropertiesToCsv(String filename);

    void saveRepairsToCsv(String filename);

    int readOwnersCsv(String filename);

    int readPropertiesCsv(String filename);

    int readRepairsFromCsv(String filename);

}
