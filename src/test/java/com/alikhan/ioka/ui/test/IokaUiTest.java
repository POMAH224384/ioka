package com.alikhan.ioka.ui.test;

import com.alikhan.ioka.ui.page.IokaForAllPage;
import com.alikhan.ioka.ui.page.IokaHomePage;
import com.alikhan.ioka.ui.page.IokaLoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IokaUiTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    private IokaHomePage homePage;
    private IokaForAllPage forAllPage;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        page = browser.newPage();

        homePage = new IokaHomePage(page);
        forAllPage = new IokaForAllPage(page);
    }

    @AfterEach
    void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @Story("Авторизация / вход")
    @Description("Кнопка 'Войти' отображается и ведет на форму/страницу логина")
    void loginButtonShouldBeVisibleAndOpenLoginPage() {
        homePage
                .open()
                .waitForLoaded();

        assertTrue(homePage.isLoginVisible(), "'Войти' должна быть видна");

        IokaLoginPage loginPage = homePage.clickLogin();
        loginPage.waitForLoaded();
    }

    @Test
    @Story("Авторизация / вход")
    @Description("Кнопка 'Войти' отображается и ведет на форму/страницу логина")
    void errorMessageShouldBeVisibleAfterLoginWithBadCredentials() {
        homePage
                .open()
                .waitForLoaded();

        assertTrue(homePage.isLoginVisible(), "'Войти' должна быть видна");

        IokaLoginPage loginPage = homePage.clickLogin()
                .waitForLoaded()
                .fillCredentials("sdkasfdj@dkfjs.copm", "skdalskd")
                .submit();
        loginPage.isErrorVisible();
    }

    @Test
    @Story("UI-проверка хедера")
    @Description("Ключевые элементы хедера видны и кликабельны")
    void headerElementsShouldBeVisible() {
        homePage
                .open()
                .waitForLoaded();

        assertTrue(homePage.isHeaderLinksVisible(), "Все ссылки хедера должны быть видны");
    }

    @Test
    @Story("Проверка формы (негатив)")
    @Description("Форма 'Подключиться' показывает ошибку при пустой отправке")
    void connectFormShouldShowValidationErrorOnEmptySubmit() {
        forAllPage
                .open()
                .waitForLoaded()
                .openConnectForm()
                .submitEmptyForm();

        assertTrue(forAllPage.isValidationErrorVisible(),
                "Должна появиться ошибка валидации для пустой формы");
    }

    @Test
    @Story("Проверка контента")
    @Description("Ключевой текст на странице 'Решение для всех' отображается")
    void forAllPageContentShouldBeVisible() {
        forAllPage
                .open()
                .waitForLoaded();

        assertTrue(forAllPage.isMainContentVisible(),
                "Основной заголовок и описание должны быть видны");
    }
}
