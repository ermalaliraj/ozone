package al.ozone.bl.dao;

public interface CodeGeneratorDAO {

	/**
	 * Generate an alphanumeric code (xxx/yyyyyyy) with length 10,
	 * where xxx is a sequence of random numbers and yyyyyyyy is an alphanumeric sequence.
	 * @return generated code in xxx/yyyyyyy format
	 */
	public String generateCouponCode();
	
	/**
	 * Generate an alphanumeric code with length 7
	 * @return generated code
	 */
	public String generateDiscountCardCode();

	/**
	 * Generate an alphanumeric code with length 6
	 * @return generated code
	 */
	public String generateNextSecurityCode();
	
}
