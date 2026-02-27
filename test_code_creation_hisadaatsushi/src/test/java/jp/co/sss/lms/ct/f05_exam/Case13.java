package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 詳細ボタンを押下
		List<WebElement> detailBtnElements = webDriver.findElements(By.xpath("//input[@value='詳細']"));
		detailBtnElements.get(1).click();

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
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {

		// 詳細ボタンを押下
		WebElement detailBtnElement = webDriver.findElement(By.xpath("//input[@value='詳細']"));
		detailBtnElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 試験画面に遷移したことを確認
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "test_page");

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {

		// 試験を開始するボタンを押下
		WebElement detailBtnElement = webDriver.findElement(By.xpath("//input[@value='試験を開始する']"));
		detailBtnElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 試験画面に遷移したことを確認
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "test_input_page");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {

		// 確認画面ヘ進むボタンを押下
		WebElement buttonElement = webDriver.findElement(By.xpath("//input[@value='確認画面へ進む']"));
		scrollBy("4500");
		buttonElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 試験入力画面に遷移したことを確認
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "test_confirm_page");

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {

		// 回答を送信するボタンを押下
		WebElement buttonElement = webDriver.findElement(By.xpath("//button[text()='回答を送信する']"));
		scrollBy("2000");
		buttonElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		date = new Date();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 試験確認画面に遷移したことを確認
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "test_result_page");

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {

		// 戻るボタンを押下
		WebElement buttonElement = webDriver.findElement(By.xpath("//input[@value='戻る']"));
		scrollBy("5000");
		buttonElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 試験画面に遷移したことを確認
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
		String testDate = sdf.format(date);

		// 試験結果が存在するか確認
		List<WebElement> trElements = webDriver.findElements(By.tagName("tr"));
		WebElement testElement = trElements.getLast();
		assertTrue(testElement.getText().contains(testDate));
		assertTrue(testElement.getText().contains("0.0点"));

		// エビデンスを取得
		scrollBy("500");
		getEvidence(new Object() {
		}, "test_complete_page");

	}

}
