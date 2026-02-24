package jp.co.sss.lms.ct.f03_report;

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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		// ユーザー詳細画面に遷移
		WebElement userNameElement = webDriver.findElement(By.xpath("//a[@href='/lms/user/detail']"));
		userNameElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// ユーザー詳細画面に遷移したことを確認
		assertEquals("ユーザー詳細", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "user_detail_page");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 週報が提出済みのセクションを取得し、詳細ボタンを押下
		WebElement sectionElement = webDriver
				.findElement(By.xpath("//tr[td[contains(., '2022年10月2日(日)')]]"));
		WebElement detailElement = sectionElement.findElement(By.xpath(".//input[@value='修正する']"));
		scrollBy("300");
		detailElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "modify_report_page");

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		// 学習項目を未入力に変更
		WebElement learningItemsElement = webDriver.findElement(By.id("intFieldName_0"));
		learningItemsElement.clear();

		// 提出ボタンを押下
		WebElement submitBtnElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		scrollBy("400");
		submitBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エラーが表示されているか確認
		learningItemsElement = webDriver.findElement(By.id("intFieldName_0"));
		assertTrue(learningItemsElement.getAttribute("class").contains("errorInput"));

		// エビデンスを取得
		getEvidence(new Object() {
		}, "learning_items_error");

		// 学習項目に値を入力
		learningItemsElement.sendKeys("ITリテラシー①");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		// 理解度の選択を解除
		Select comprehensionSelect = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		comprehensionSelect.selectByIndex(0);

		// 提出ボタンを押下
		WebElement submitBtnElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		scrollBy("400");
		submitBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		WebElement comprehensionElement = webDriver.findElement(By.id("intFieldValue_0"));
		assertTrue(comprehensionElement.getAttribute("class").contains("errorInput"));

		// エビデンスを取得
		getEvidence(new Object() {
		}, "comprehension_error");

		// 理解度を入力
		comprehensionSelect = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		comprehensionSelect.selectByIndex(2);

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		// 達成度を入力
		WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		achievementLevelElement.clear();
		achievementLevelElement.sendKeys("テスト");

		// 提出ボタンを押下
		WebElement submitBtnElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		scrollBy("400");
		submitBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エラーが表示されているか確認
		achievementLevelElement = webDriver.findElement(By.id("content_0"));
		assertTrue(achievementLevelElement.getAttribute("class").contains("errorInput"));

		// エビデンスを取得
		getEvidence(new Object() {
		}, "achievement_level_error");

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		// 達成度を入力
		WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		achievementLevelElement.clear();
		achievementLevelElement.sendKeys("12");

		// 提出ボタンを押下
		WebElement submitBtnElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		scrollBy("400");
		submitBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エラーが表示されているか確認
		achievementLevelElement = webDriver.findElement(By.id("content_0"));
		assertTrue(achievementLevelElement.getAttribute("class").contains("errorInput"));

		// エビデンスを取得
		getEvidence(new Object() {
		}, "achievement_level_error");

	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		// 達成度の値を削除
		WebElement achievementLevelElement = webDriver.findElement(By.id("content_0"));
		achievementLevelElement.clear();

		// 所感の値を削除
		WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		impressionsElement.clear();

		// 提出ボタンを押下
		WebElement submitBtnElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		scrollBy("400");
		submitBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// 達成度にエラーが表示されているか確認
		achievementLevelElement = webDriver.findElement(By.id("content_0"));
		assertTrue(achievementLevelElement.getAttribute("class").contains("errorInput"));

		// 所感にエラーが表示されているか確認
		impressionsElement = webDriver.findElement(By.id("content_1"));
		assertTrue(impressionsElement.getAttribute("class").contains("errorInput"));

		// エビデンスを取得
		scrollBy("200");
		getEvidence(new Object() {
		}, "achievement_level_and_impressions_error");

		// 達成度に値を入力
		achievementLevelElement.sendKeys("5");

	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		// 所感に値を入力
		WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		impressionsElement.clear();
		impressionsElement.sendKeys("テスト".repeat(667));

		// 報告内容に値を入力
		WebElement reportElement = webDriver.findElement(By.id("content_2"));
		reportElement.clear();
		reportElement.sendKeys("テスト".repeat(667));

		// 提出ボタンを押下
		WebElement submitBtnElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		scrollBy("400");
		submitBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(30);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// 所感にエラーが表示されているか確認
		impressionsElement = webDriver.findElement(By.id("content_1"));
		assertTrue(impressionsElement.getAttribute("class").contains("errorInput"));

		// 報告内容にエラーが表示されているか確認
		impressionsElement = webDriver.findElement(By.id("content_2"));
		assertTrue(impressionsElement.getAttribute("class").contains("errorInput"));

		// エビデンスを取得
		scrollBy("400");
		getEvidence(new Object() {
		}, "impressions_and_report_error");
	}

}
