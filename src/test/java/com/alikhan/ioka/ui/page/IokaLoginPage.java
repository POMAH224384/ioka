package com.alikhan.ioka.ui.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class IokaLoginPage extends BasePage{
    public IokaLoginPage(Page page) {
        super(page);
    }

    @Override
    public IokaLoginPage waitForLoaded() {
        emailInput().waitFor();
        return this;
    }

    private Locator emailInput() {
        return page.locator("input[type='email'], input[name='email']").first();
    }

    private Locator passwordInput() {
        return page.locator("input[type='password'], input[name='password']").first();
    }

    private Locator submitButton() {
        return page.getByText("Войти").first(); // при необходимости уточни
    }

    public IokaLoginPage fillCredentials(String email, String password) {
        emailInput().fill(email);
        passwordInput().fill(password);
        return this;
    }

    public IokaLoginPage submit() {
        submitButton().click();
        return this;
    }

    public boolean isErrorVisible() {
        return page.getByText("Не авторизован.").isVisible();
    }
}
