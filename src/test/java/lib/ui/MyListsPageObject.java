package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class MyListsPageObject extends MainPageObject {
	protected static String
			FOLDER_BY_NAME_TPL,
			ARTICLE_BY_TITLE_TPL,
			TITLE;

	public static String getFolderXpathByName(String name_of_folder) {
		return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
	}

	public static String getSavedArticleXpathByTitle(String article_title) {
		return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
	}

	public MyListsPageObject(AppiumDriver driver) {
		super(driver);
	}

	public void openFolderByName(String name_of_folder) {
		String folder_by_name_xpath = getFolderXpathByName(name_of_folder);
		this.waitForElementAndClick(
				folder_by_name_xpath,
				"Cannot find folder by name " + name_of_folder,
				15
		);
	}

	public void waitForToArticleAppearByTitle(String article_title) {
		String article_xpath = getFolderXpathByName(article_title);
		this.waitForElementPresent(
				article_xpath,
				"Cannot find Saved article by title " + article_title,
				15
		);
	}

	public void waitForToArticleDisappearByTitle(String article_title) {
		String article_xpath = getSavedArticleXpathByTitle(article_title);
		this.waitForElementNotPresent(
				article_xpath,
				"Saved article still present with title " + article_title,
				15
		);
	}

	public void swipeArticleToDelete(String article_title) {
		this.waitForToArticleAppearByTitle(article_title);
		String article_xpath = getSavedArticleXpathByTitle(article_title);
		this.swipeElementToLeft(
				article_xpath,
				"Cannot find saved article"
		);

		if (Platform.getInstance().isIOS()) {
			this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
		}

		this.waitForToArticleDisappearByTitle(article_title);
	}

	public WebElement waitForTitleElement() {
		return this.waitForElementPresent(TITLE, "Cannot find article title on page!", 15);
	}

	public String getArticleTitle() {
		WebElement title_element = waitForTitleElement();
		return title_element.getAttribute("text");
	}
}
