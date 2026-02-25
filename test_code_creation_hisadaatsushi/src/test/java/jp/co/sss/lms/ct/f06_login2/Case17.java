package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {

		// ログインIDを入力
		WebElement loginIdElement = webDriver.findElement(By.name("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA03");

		// パスワードを入力
		WebElement passwordElement = webDriver.findElement(By.name("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA03");

		// ログインボタンを押下
		WebElement loginButtonElement = webDriver.findElement(By.className("btn"));
		loginButtonElement.click();

		// ページが遷移するまで待機
		pageLoadTimeout(5);

		// セキュリティ規約画面に遷移したことを確認
		assertEquals("セキュリティ規約 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "security_policy_page");

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {

		// 同意しますボタンを押下
		WebElement agreeBtnElement = webDriver.findElement(By.tagName("label"));
		agreeBtnElement.click();

		// 次へボタンを押下
		WebElement nextBtnElement = webDriver.findElement(By.xpath("//button[text()='次へ']"));
		scrollBy("100");
		nextBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// パスワード変更画面に遷移したことを確認		
		assertEquals("パスワード変更 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "password_change_page");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {

		// 現在のパスワードを入力
		WebElement currentPasswordElement = webDriver.findElement(By.id("currentPassword"));
		currentPasswordElement.sendKeys("StudentAA03");

		// 新しいパスワードを入力
		WebElement newPasswordElement = webDriver.findElement(By.id("password"));
		newPasswordElement.sendKeys("TestUser123");

		// 確認パスワードを入力
		WebElement confirmPasswordElement = webDriver.findElement(By.id("passwordConfirm"));
		confirmPasswordElement.sendKeys("TestUser123");

		// 変更ボタンを押下
		WebElement changeBtnElement = webDriver.findElement(By.xpath("//button[text()='変更']"));
		scrollBy("100");
		changeBtnElement.click();

		// モーダルウィンドウが表示されるまで待機
		visibilityTimeout(By.id("upd-btn"), 10);

		// モーダルウィンドウの変更ボタンを押下
		WebElement confirmChangeBtnElement = webDriver.findElement(By.id("upd-btn"));
		confirmChangeBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// コース詳細画面に遷移したことを確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// ログインできているか確認
		WebElement userNameElement = webDriver.findElement(By.xpath("//a[@href='/lms/user/detail']"));
		assertEquals("ようこそ受講生ＡＡ３さん", userNameElement.getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "course_details_page");

	}

}
