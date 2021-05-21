package al.ozone.bl.manager;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test suite which runs all other test classes.
 * Run this class to run all test cases.
 * 
 * @author Ermal
 */
public class AllTestsSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTestsSuite.class.getName());

		suite.addTestSuite(PartnerManagerTest.class);
		//suite.addTestSuite(PublicationManagerTest.class);
		suite.addTestSuite(DealManagerTest.class);
		suite.addTestSuite(CitiesManagerTest.class);
		suite.addTestSuite(CustomerManagerTest.class);
		suite.addTestSuite(UserManagerTest.class);
		suite.addTestSuite(CreditManagerTest.class);

		return suite;
	}

}
