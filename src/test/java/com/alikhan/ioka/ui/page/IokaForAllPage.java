package com.alikhan.ioka.ui.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.alikhan.ioka.config.TestConfig.testConfig;

public class IokaForAllPage extends BasePage{
    public IokaForAllPage(Page page) {
        super(page);
    }

    public IokaForAllPage open() {
        page.navigate(testConfig().uiProductsForAllUrl());
        return this;
    }

    @Override
    public IokaForAllPage waitForLoaded() {
        mainTitle().waitFor();
        return this;
    }

    private Locator mainTitle() {
        return page.getByText("Решение для всех").first();
    }

    private Locator description() {
        return page.getByText("Внедрите легкую онлайн оплату").first();
    }

    private Locator connectLink() {
        return page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Подключиться"));
    }

    // форма "Подключиться" — селекторы подгонишь по факту

    private Locator nameInput() {
        return page.locator("input[name='name']").first();
    }

    private Locator phoneInput() {
        return page.locator("input[name='phone']").first();
    }

    private Locator submitButton() {
        return page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Отправить"));
    }

    private Locator validationError() {
        // общий локатор ошибки — поправишь под реальный текст
        return page.getByText("Обязательное поле").first();
    }

    public IokaForAllPage openConnectForm() {
        connectLink().click();
        return this;
    }

    public IokaForAllPage submitEmptyForm() {
        submitButton().click();
        return this;
    }

    public IokaForAllPage fillValidForm(String name, String phone) {
        nameInput().fill(name);
        phoneInput().fill(phone);
        return this;
    }

    public boolean isValidationErrorVisible() {
        return validationError().isVisible();
    }

    public boolean isMainContentVisible() {
        return mainTitle().isVisible() && description().isVisible();
    }
}
