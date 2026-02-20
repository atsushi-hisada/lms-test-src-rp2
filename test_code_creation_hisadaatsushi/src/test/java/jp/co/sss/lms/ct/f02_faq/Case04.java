package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		// トップページURLにアクセス
		goTo("http://localhost:8080/lms");

		// ログイン画面が表示されているか確認
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "login_page");

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		// ログインIDを入力
		WebElement loginIdElement = webDriver.findElement(By.name("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA01");

		// パスワードを入力
		WebElement passwordElement = webDriver.findElement(By.name("password"));
		passwordElement.clear();
		passwordElement.sendKeys("TestUser123");

		// ログインボタンを押下
		WebElement loginButtonElement = webDriver.findElement(By.className("btn"));
		loginButtonElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// コース詳細画面に遷移したことを確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// ログインできているか確認
		WebElement userNameElement = webDriver.findElement(By.xpath("//a[@href='/lms/user/detail']"));
		assertEquals("ようこそ受講生ＡＡ１さん", userNameElement.getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "course_details_page");

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		// 機能ボタンを押下し、ドロップダウンを表示
		WebElement functionButtonElement = webDriver.findElement(By.linkText("機能"));
		functionButtonElement.click();

		// ヘルプボタンを押下
		WebElement helpElement = webDriver.findElement(By.linkText("ヘルプ"));
		helpElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// ヘルプ画面に遷移したことを確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "help_page");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		// ヘルプボタンを押下
		WebElement FAQElement = webDriver.findElement(By.linkText("よくある質問"));
		FAQElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// ウィンドウが開いているタブの情報を取得
		Object[] windowHandles = webDriver.getWindowHandles().toArray();

		// よくある質問画面のタブに移動
		webDriver.switchTo().window((String) windowHandles[1]);

		// よくある質問画面に遷移したことを確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "frequently_asked_questions_page");

	}

}
