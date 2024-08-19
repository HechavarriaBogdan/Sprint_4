package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

import static praktikum.EnvConfig.EXPLICIT_WAIT;

public class MainPage {
    private final WebDriver driver;

    // Локатор кнопки "Заказать" в верхней части страницы
    private final By topOrderButton = By.xpath("(//button[@class='Button_Button__ra12g'])[1]");
    // Локатор кнопки "Заказать" в нижней части страницы
    private final By bottomOrderButton = By.className("Home_FinishButton__1_cWm");
    // Локатор кнопки "Go" в хедере
    private final By goButton = By.cssSelector(".Header_Button__28dPO");
    // Локатор инпута "Введите номер заказа"
    private final By orderField = By.className("Input_Input__1iN_Z");
    // Локатор кнопки "Статус заказа"
    private final By statusButton = By.className("Header_Link__1TAG7");
    // Локатор кнопки "Да все привыкли" для принятия куки
    private final By acceptCookie = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод открывает Web страницу и закрывает боттом-шит про куки
    public void open() {
        driver.get(EnvConfig.BASE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(acceptCookie));
        driver.findElement(acceptCookie).click();
    }

    // Метод нажимает на кнопку "Заказать" в верхней части страницы
    public void topOrderClick() {
        driver.findElement(topOrderButton).click();
    }

    // Метод нажимает на кнопку "Заказать" в нижней части страницы
    public void bottomOrderClick() {
        WebElement scrollElement = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollElement);
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(bottomOrderButton));
        scrollElement.click();
    }

    // Метод нажимает на кнопку "GO" в хедере
    public StatusPage clickOnGo() {
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(goButton));
        driver.findElement(goButton).click();
        return new StatusPage(driver);
    }
    // Метод вводит невалидный номер заказа в инпут "Введите номер заказа"
    public void enterOrderId(String orderNumber) {
        driver.findElement(orderField).sendKeys(orderNumber);
    }
    // Метод нажимает на кнопку "Статус заказа"
    public void clickOnStatus() {
        driver.findElement(statusButton).click();
    }

    // Метод скроллит экран до нужного локатора
    public void scrollForElement (By locator) {
        WebElement scrollElement = driver.findElement(locator);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollElement);
    }

    // Метод кликает на стрелку с часто задаваемым вопросом
    public void clickOnQuestion(By locator) {
        driver.findElement(locator).click();
    }


}
