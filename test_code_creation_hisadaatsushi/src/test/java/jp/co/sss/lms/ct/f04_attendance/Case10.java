package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

		// 勤怠ボタンを押下
		WebElement attendanceBtnElement = webDriver.findElement(By.linkText("勤怠"));
		attendanceBtnElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// 勤怠情報管理画面に遷移したことを確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "attendance_page");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {

		// 出勤ボタンを押下
		WebElement punchInBtnElement = webDriver.findElement(By.name("punchIn"));
		punchInBtnElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// 出勤時間が記録されていることを確認
		List<WebElement> timeElement = webDriver.findElements(By.className("w80"));
		assertNotEquals("", timeElement.get(4).getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "punch_in");

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

		// 退勤ボタンを押下
		WebElement punchOutBtnElement = webDriver.findElement(By.name("punchOut"));
		punchOutBtnElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// 退勤時間が記録されていることを確認
		List<WebElement> timeElement = webDriver.findElements(By.className("w80"));
		assertNotEquals("", timeElement.get(5).getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "punch_out");

	}

}
