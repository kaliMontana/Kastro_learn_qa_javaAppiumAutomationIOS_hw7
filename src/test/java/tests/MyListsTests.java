package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
	@Test
	public void testSaveFirstArticleToMyList() {
		SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Java");
		searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

		ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
		articlePageObject.waitForTitleElement();
		String article_title = articlePageObject.getArticleTitle();
		String name_of_folder = "Learning programming";
		articlePageObject.addArticleToMyList(name_of_folder);
		articlePageObject.closeArticle();

		NavigationUI navigationUI = new NavigationUI(driver);
		navigationUI.clickMyList();

		MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
		myListsPageObject.openFolderByName(name_of_folder);
		myListsPageObject.swipeArticleToDelete(article_title);
	}

	@Test
	public void testSaveTwoArticlesToMyList() {
		String searched_word_Barcelona = "Barcelona";
		SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine(searched_word_Barcelona);
		searchPageObject.clickByArticleWithSubstring("City in Catalonia, Spain");

		ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
		articlePageObject.waitForTitleElement();
		String name_of_folder = "Doing the homework";
		articlePageObject.addArticleToMyList(name_of_folder);
		articlePageObject.closeArticle();

		//Is this began steps to second article
		searchPageObject.initSearchInput();
		String searched_word_Moscow = "Moscow";
		searchPageObject.typeSearchLine(searched_word_Moscow);
		searchPageObject.clickByArticleWithSubstring("Capital and most populous city of Russia");

		articlePageObject.waitForTitleElement();
		articlePageObject.addSecondArticleToMyList(name_of_folder);
		articlePageObject.closeArticle();

		NavigationUI navigationUI = new NavigationUI(driver);
		navigationUI.clickMyList();

		MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
		myListsPageObject.openFolderByName(name_of_folder);
		myListsPageObject.swipeArticleToDelete(searched_word_Barcelona);

		String article_title = myListsPageObject.getArticleTitle();
		Assert.assertEquals(
				"Titles are not the same",
				searched_word_Moscow,
				article_title
		);
	}
}
