package praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pages.MainPage;
import praktikum.pages.StatusPage;

public class InvalidOrderScooterTest {
    @Rule
    public DriverRule factory = new DriverRule();
    private WebDriver driver;

    private String INVALID_ORDER_ID = "123";

    @Before
    public void setUp() {
        driver = factory.getDriver();
    }

    /** Если ввести неправильный номер заказа, попадёшь на страницу статуса заказа.
     На ней должно быть написано, что такого заказа нет */

    @Test
    public void openMainPage() throws Exception {
        var mainPage = new MainPage(driver);

        mainPage.open();

        mainPage.clickOnStatus();
        mainPage.enterOrderId(INVALID_ORDER_ID);

        StatusPage statusPage = mainPage.clickOnGo();
        statusPage.checkErrorMessage();
    }
}
