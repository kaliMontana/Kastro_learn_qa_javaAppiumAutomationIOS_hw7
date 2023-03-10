package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import static io.appium.java_client.touch.offset.PointOption.point;

public class MainPageObject {
	protected AppiumDriver driver;

	public MainPageObject(AppiumDriver driver) {
		this.driver = driver;
	}

	public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
		By by = this.getLocatorByString(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		return wait.until(
				ExpectedConditions.presenceOfElementLocated(by)
		);
	}

	public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
		element.click();
		return element;
	}

	public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
		element.sendKeys(value);
		return element;
	}

	public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
		By by = this.getLocatorByString(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		return wait.until(
				ExpectedConditions.invisibilityOfElementLocated(by)
		);
	}

	public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
		element.clear();
		return element;
	}

	public void swipeUp(int timeOfSwipe) {
		TouchAction action = new TouchAction(driver);
		Dimension size = driver.manage().window().getSize();
		int x = size.width / 2;
		int start_y = (int) (size.getHeight() * 0.8);
		int end_y = (int) (size.getHeight() * 0.2);

		action
				.press(point(x, start_y))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(timeOfSwipe)))
				.moveTo(point(x, end_y))
				.release()
				.perform();
	}

	public void swipeQuick() {
		swipeUp(200);
	}

	public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
		By by = this.getLocatorByString(locator);
		int already_swiped = 0;
		while (driver.findElements(by).size() == 0) {

			if (already_swiped > max_swipes) {
				waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
				return;
			}

			swipeQuick();
			already_swiped++;
		}
	}

	public void swipeUpTillElementAppear(String locator, String error_message, int max_sipes) {
		int already_swiped = 0;

		while (!this.isElementLocatedOnTheScreen(locator)) {
			if (already_swiped > max_sipes) {
				Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
			}

			swipeQuick();
			++already_swiped;
		}
	}

	public boolean isElementLocatedOnTheScreen(String locator) {
		int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 1).getLocation().getY();
		int screen_size_by_y = driver.manage().window().getSize().getHeight();
		return element_location_by_y < screen_size_by_y;
	}

	public void clickElementToTheRightUpperCorner(String locator, String error_message) {
		WebElement element = this.waitForElementPresent(locator + "/..", error_message, 5);
		int right_x = element.getLocation().getX();
		int upper_y = element.getLocation().getY();
		int lower_y = upper_y + element.getSize().getHeight();
		int middle_y = (upper_y + lower_y) / 2;
		int width = element.getSize().getWidth();

		int point_to_click_x = (right_x + width) - 3;
		int point_to_click_y = middle_y;

		TouchAction action = new TouchAction(driver);
		action.tap(point(point_to_click_x, point_to_click_y)).perform();
	}

	public void swipeElementToLeft(String locator, String error_message) {
		WebElement element = waitForElementPresent(
				locator,
				error_message,
				10);

		int left_x = element.getLocation().getX();
		int right_x = left_x + element.getSize().getWidth();
		int upper_y = element.getLocation().getY();
		int lower_y = upper_y + element.getSize().getHeight();
		int middle_y = (upper_y + lower_y) / 2;

		TouchAction action = new TouchAction(driver);

		action.press(point(right_x, middle_y));
		action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)));

		if (Platform.getInstance().isAndroid()) {
			action.moveTo(point(left_x, middle_y));
		} else {
			int offset_x = (-1 * element.getSize().getWidth());
			action.moveTo(point(offset_x, 0));
		}

		action.release();
		action.perform();
	}

	public int getAmountOfElements(String locator) {
		By by = this.getLocatorByString(locator);
		List elements = driver.findElements(by);
		return elements.size();
	}

	public void assertElementNotPresent(String locator, String error_message) {
		int amount_of_elements = getAmountOfElements(locator);
		if (amount_of_elements > 0) {
			String default_message = "An element '" + locator + "' suppose to be not present";
			throw new AssertionError(default_message + " " + error_message);
		}
	}

	public void assertElementPresentsLessThanThree(String locator, String error_message, int result_amount) {
		int amount_of_elements = getAmountOfElements(locator);
		if (amount_of_elements >= result_amount) {
			String default_message = "Result elements less than three " + result_amount;
			throw new AssertionError(default_message + " " + error_message);
		}
	}

	public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
		return element.getAttribute(attribute);
	}

	public void assertElementPresent(String locator, String attribute, String error_message, long timeoutIntSeconds) {
		String attribute_value = waitForElementAndGetAttribute(
				locator,
				attribute,
				error_message,
				timeoutIntSeconds
		);

		if (!attribute_value.contains("_title_")) {
			String default_message = "Element 'Title' by '" + locator + "' suppose to be present. \n";
			throw new AssertionError(default_message + " " + error_message);
		}
	}

	private By getLocatorByString(String locator_with_type) {
		String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
		String by_type = exploded_locator[0];
		String locator = exploded_locator[1];

		if (by_type.equals("xpath")) {
			return By.xpath(locator);
		} else if (by_type.equals("id")) {
			return By.id(locator);
		} else {
			throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
		}
	}
}
