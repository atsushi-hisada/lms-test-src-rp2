package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		// ログインIDを入力
		WebElement loginIdElement = webDriver.findElement(By.name("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA10");

		// パスワードを入力
		WebElement passwordElement = webDriver.findElement(By.name("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA10");

		// ログインボタンを押下
		WebElement loginButtonElement = webDriver.findElement(By.className("btn"));
		loginButtonElement.click();

		// ページが更新されるまで待機
		pageLoadTimeout(5);

		// 画面遷移されていないか確認
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// エラーメッセージが表示されているか確認
		WebElement errorMsgElement = webDriver.findElement(By.className("error"));
		assertEquals("* ログインに失敗しました。", errorMsgElement.getText());

		// エビデンスを取得
		getEvidence(new Object() {
		}, "error_message");

	}

}
