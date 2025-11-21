package com.alikhan.ioka.ui.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.alikhan.ioka.config.TestConfig.testConfig;

public class IokaHomePage extends BasePage{
    public IokaHomePage(Page page) {
        super(page);
    }

    public IokaHomePage open() {
        page.navigate(testConfig().uiBaseUrl());
        return this;
    }

    @Override
    public IokaHomePage waitForLoaded() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Подключиться")).waitFor();
        return this;
    }


    private Locator loginLink() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Войти"));
    }

    private Locator productsLink() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Продукты"));
    }

    private Locator tariffsLink() {
        return page
                .getByRole(AriaRole.NAVIGATION) // шапка/меню
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Тарифы"));
    }

    private Locator aboutLink() {
        return page
                .getByRole(AriaRole.NAVIGATION)
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("О компании"));
    }

    private Locator blogLink() {
        return page
                .getByRole(AriaRole.NAVIGATION)
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Блог"));
    }

    private Locator devsLink() {
        return page
                .getByRole(AriaRole.NAVIGATION)
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Разработчикам"));
    }


    public boolean isLoginVisible() {
        return loginLink().isVisible();
    }

    public IokaLoginPage clickLogin() {
        loginLink().click();
        return new IokaLoginPage(page);
    }

    public boolean isHeaderLinksVisible() {
        return productsLink().isVisible()
                && tariffsLink().isVisible()
                && aboutLink().isVisible()
                && blogLink().isVisible()
                && devsLink().isVisible()
                && loginLink().isVisible();
    }

    public IokaForAllPage openForAllPage() {
        page.navigate(testConfig().uiProductsForAllUrl());
        return new IokaForAllPage(page);
    }
}
