package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

public class CoreTestCase extends TestCase {

	protected Platform platform;
	protected AppiumDriver driver;


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.platform = new Platform();
		driver = this.platform.getDriver();
		this.rotateScreenPortrait();
	}

	@Override
	protected void tearDown() throws Exception {
		driver.quit();

		super.tearDown();
	}

	protected void rotateScreenPortrait() {
		driver.rotate(ScreenOrientation.PORTRAIT);
	}

	protected void rotateScreenLandscape() {
		driver.rotate(ScreenOrientation.LANDSCAPE);
	}

	protected void backGroundApp(int seconds) {
		driver.runAppInBackground(Duration.ofSeconds(seconds));
	}
}
