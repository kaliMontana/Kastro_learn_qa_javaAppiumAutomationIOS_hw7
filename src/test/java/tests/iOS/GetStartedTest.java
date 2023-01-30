package tests.iOS;

import lib.iOSCoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends iOSCoreTestCase {

	@Test
	public void testPassThroughWelcome() {
		WelcomePageObject welcomePage = new WelcomePageObject(driver);

		welcomePage.waitForLearnMoreLink();
		welcomePage.clickNextButton();

		welcomePage.waitForNewWayToExplorerText();
		welcomePage.clickNextButton();

		welcomePage.waitForAddOrEditPreferredLangText();
		welcomePage.clickNextButton();

		welcomePage.waitForLearnMoreAboutDataCollectedText();
		welcomePage.clickGetStartedButton();
	}
}
