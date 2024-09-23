package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.models.Repair;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AdminServiceInterface {

    List<Repair> getPendingRepairs();

    void proposeCost(Long repairId, BigDecimal proposedCost);

    List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd);

    List<Date> checkActuallDate(Long repairId);

}
