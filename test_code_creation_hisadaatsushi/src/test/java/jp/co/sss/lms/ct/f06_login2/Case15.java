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
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

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
		loginIdElement.sendKeys("StudentAA02");

		// パスワードを入力
		WebElement passwordElement = webDriver.findElement(By.name("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA02");

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
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {

		// 次へボタンを押下
		WebElement nextBtnElement = webDriver.findElement(By.xpath("//button[text()='次へ']"));
		scrollBy("100");
		nextBtnElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// エラーメッセージが表示されているか確認
		WebElement errorMsgElement = webDriver.findElement(By.className("error"));
		assertEquals("セキュリティ規約への同意は必須です。", errorMsgElement.getText());

		// エビデンスを取得
		scrollBy("100");
		getEvidence(new Object() {
		}, "security_policy_error");

	}

}
