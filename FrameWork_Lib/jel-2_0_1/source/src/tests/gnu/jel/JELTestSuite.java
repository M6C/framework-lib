package gnu.jel;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JELTestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite();
	suite.addTestSuite(IntegerStackTest.class);
	suite.addTestSuite(LibraryTest.class);
	suite.addTestSuite(LibraryDotOPTest.class);
	suite.addTestSuite(ParserTest.class);
	suite.addTestSuite(ClassFileTest.class);
	suite.addTestSuite(ClassFileExprTest.class);
	suite.addTestSuite(IntegralDotOperatorTest.class);
	suite.addTestSuite(IntegralDowncastingTest.class);
	suite.addTestSuite(IntegralDynamicVariablesTest.class);
	suite.addTestSuite(IntegralErrorTest.class);
	suite.addTestSuite(IntegralExceptionsPassingTest.class);
	suite.addTestSuite(IntegralPrimitiveOPsTest.class);
	suite.addTestSuite(IntegralReferencesWideningTest.class);
	suite.addTestSuite(IntegralStaticTest.class);
	suite.addTestSuite(IntegralVarARRTest.class);
	suite.addTestSuite(IntegralVirtualTest.class);

	/* ALERT: Don't forget to add new testsuites here before release */

        return suite;
    }

    /**
     * Runs the test suite using the textual runner.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
