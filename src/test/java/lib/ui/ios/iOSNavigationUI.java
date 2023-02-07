package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

/**
 * В этом классе надо поменять локаторы, которые имеет wikipedia для
 * система iOS. Посмотреть видео из занятия VII - 05. Рефакторинг тестов на поискГиперссылка
 **/
public class iOSNavigationUI extends NavigationUI {
	static {
		MY_LIST_LINK = "id:saved";
	}

	public iOSNavigationUI(AppiumDriver driver) {
		super(driver);
	}
}
