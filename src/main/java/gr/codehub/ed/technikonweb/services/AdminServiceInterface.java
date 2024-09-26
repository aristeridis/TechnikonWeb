package gr.codehub.ed.technikonweb.services;

import gr.codehub.ed.technikonweb.models.Repair;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author alexandrosaristeridis
 */
public interface AdminServiceInterface {

	/**
	 *
	 * @return
	 */
	List<Repair> getPendingRepairs();

	/**
	 *
	 * @param repairId
	 * @param proposedCost
	 */
	void proposeCost(Long repairId, BigDecimal proposedCost);

	/**
	 *
	 * @param proposedDateOfStart
	 * @param proposedDateOfEnd
	 * @return
	 */
	List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd);

	/**
	 *
	 * @param repairId
	 * @return
	 */
	List<Date> checkActuallDate(Long repairId);

}
