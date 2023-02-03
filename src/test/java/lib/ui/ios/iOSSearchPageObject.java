package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

/**
 * В этом классе надо поменять локаторы, которые имеет wikipedia для
 * система iOS. Посмотреть видео из занятия VII - 02. Рефакторинг тестов на поискГиперссылка
 **/
public class iOSSearchPageObject extends SearchPageObject {
	static {
		SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
		SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]";
		SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
		SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
		SEARCH_RESULT_BY_TITLE_SUBSTRING_AND_DESCRIPTION_SUBSTRING_TPL = "xpath://*[@class='android.widget.LinearLayout']/*[@text='{TITLE_SUBSTRING}']/../*[@text='{DESCRIPTION_SUBSTRING}']";
		SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
		SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
		SEARCH_ARTICLE_CONTAINER = "id:org.wikipedia:id/search_results_container";
	}

	public iOSSearchPageObject(AppiumDriver driver) {
		super(driver);
	}
}
