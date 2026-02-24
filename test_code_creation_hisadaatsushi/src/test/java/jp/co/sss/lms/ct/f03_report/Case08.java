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

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 週報が提出済みのセクションを取得し、詳細ボタンを押下
		WebElement sectionElement = webDriver
				.findElement(By.xpath("//tr[td[contains(., '2022年10月2日(日)')]]"));
		WebElement detailElement = sectionElement.findElement(By.xpath(".//input[@value='詳細']"));
		detailElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// セクション詳細画面に遷移したことを確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "section_detail_page");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 週報の確認ボタンを押下
		WebElement confirmBtnElement = webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']"));
		scrollBy("200");
		confirmBtnElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移したことを確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "confirm_report_page");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		// 所感の修正
		WebElement impressionsElement = webDriver.findElement(By.id("content_1"));
		scrollBy("300");
		impressionsElement.clear();
		impressionsElement.sendKeys("修正後の所感です。");

		// 報告内容の修正
		WebElement reportElement = webDriver.findElement(By.id("content_2"));
		reportElement.clear();
		reportElement.sendKeys("修正後の報告内容です。");

		// エビデンスを取得
		getEvidence(new Object() {
		}, "modify_report");

		// 提出ボタンを押下
		WebElement submitBtnElement = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		scrollBy("100");
		submitBtnElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// セクション詳細画面に遷移したことを確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "section_detail_page");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		// 週報のセクションを取得し、詳細ボタンを押下
		WebElement sectionElement = webDriver
				.findElement(By.xpath("//tr[td[contains(., '2022年10月2日(日)')]]"));
		WebElement detailElement = sectionElement.findElement(By.xpath(".//input[@value='詳細']"));
		scrollBy("400");
		detailElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// レポート詳細画面に遷移したことを確認
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());

		// 所感が修正した内容になっているか確認
		WebElement impressionsTitleElement = webDriver.findElement(By.xpath("//tr[contains(., '所感')]"));
		WebElement impressionsTextElement = impressionsTitleElement.findElement(By.tagName("td"));
		assertEquals("修正後の所感です。", impressionsTextElement.getText());

		// 報告内容が修正した内容になっているか確認
		WebElement reportTitleElement = webDriver.findElement(By.xpath("//tr[contains(., '一週間の振り返り')]"));
		WebElement reportTextElement = reportTitleElement.findElement(By.tagName("td"));
		assertEquals("修正後の報告内容です。", reportTextElement.getText());

		// エビデンスを取得
		scrollBy("100");
		getEvidence(new Object() {
		}, "report_detail_page");

	}

}
