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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {

		// 勤怠情報を直接編集するリンクを押下
		WebElement editLinkElement = webDriver.findElement(By.linkText("勤怠情報を直接編集する"));
		editLinkElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 勤怠情報変更画面に遷移したことを確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "attendance_edit_page");

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {

		// 出勤(時)を空白に変更
		Select startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("");

		// 退勤(分)を空白に変更
		Select endMinuteSelect = new Select(webDriver.findElement(By.id("endMinute0")));
		endMinuteSelect.selectByValue("");

		// 更新ボタンを押下
		WebElement completeElement = webDriver.findElement(By.name("complete"));
		scrollBy("300");
		completeElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 勤怠情報変更画面に遷移したことを確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エラーメッセージが表示されているか確認
		List<WebElement> errorMsgElements = webDriver.findElements(By.className("error"));
		assertEquals("* 出勤時間が正しく入力されていません。", errorMsgElements.get(0).getText());
		assertEquals("* 退勤時間が正しく入力されていません。", errorMsgElements.get(1).getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "attendance_null_error");

		// 出勤(時)を9に変更
		startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("9");

		// 退勤(分)を00に変更
		endMinuteSelect = new Select(webDriver.findElement(By.id("endMinute0")));
		endMinuteSelect.selectByValue("0");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {

		// 出勤(時)を空白に変更
		Select startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("");

		// 出勤(分)を空白に変更
		Select startMinuteSelect = new Select(webDriver.findElement(By.id("startMinute0")));
		startMinuteSelect.selectByValue("");

		// 更新ボタンを押下
		WebElement completeElement = webDriver.findElement(By.name("complete"));
		scrollBy("400");
		completeElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 勤怠情報変更画面に遷移したことを確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エラーメッセージが表示されているか確認
		WebElement errorMsgElement = webDriver.findElement(By.className("error"));
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", errorMsgElement.getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "punch_in_null_error");

		// 出勤(時)を9に変更
		startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("9");

		// 出勤(分)を00に変更
		startMinuteSelect = new Select(webDriver.findElement(By.id("startMinute0")));
		startMinuteSelect.selectByValue("0");

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {

		// 出勤(時)を退勤(時)より遅い時間に変更
		Select startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("19");

		// 更新ボタンを押下
		WebElement completeElement = webDriver.findElement(By.name("complete"));
		scrollBy("400");
		completeElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 勤怠情報変更画面に遷移したことを確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エラーメッセージが表示されているか確認
		WebElement errorMsgElement = webDriver.findElement(By.className("error"));
		assertEquals("* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。", errorMsgElement.getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "punch_in_late_error");

		// 出勤(時)を9に変更
		startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("9");

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {

		// 出勤(時)を13に変更
		Select startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("13");

		// 中抜け時間を入力
		Select stepOutSelect = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		stepOutSelect.selectByValue("465");

		// 更新ボタンを押下
		WebElement completeElement = webDriver.findElement(By.name("complete"));
		scrollBy("400");
		completeElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 勤怠情報変更画面に遷移したことを確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エラーメッセージが表示されているか確認
		WebElement errorMsgElement = webDriver.findElement(By.className("error"));
		assertEquals("* 中抜け時間が勤務時間を超えています。", errorMsgElement.getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "step_out_error");

		// 出勤(時)を9に変更
		startHourSelect = new Select(webDriver.findElement(By.id("startHour0")));
		startHourSelect.selectByValue("9");

		// 中抜け時間を変更
		stepOutSelect = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		stepOutSelect.selectByValue("");

	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {

		// 備考を入力
		WebElement noteElement = webDriver.findElement(By.name("attendanceList[0].note"));
		noteElement.clear();
		noteElement.sendKeys("テスト".repeat(34));

		// 更新ボタンを押下
		WebElement completeElement = webDriver.findElement(By.name("complete"));
		scrollBy("400");
		completeElement.click();

		// アラートのボタンを押下
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// 勤怠情報変更画面に遷移したことを確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エラーメッセージが表示されているか確認
		WebElement errorMsgElement = webDriver.findElement(By.className("error"));
		assertEquals("* 備考の長さが最大値(100)を超えています。", errorMsgElement.getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "note_error");
	}

}
