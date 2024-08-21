package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    // Переменная содержит общий шаблон локаторов для кнопок с вопросами
    private final String buttonIdTemplate = "accordion__heading-";

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
    // Метод нажимает на кнопку "Заказать" вверху или внизу страницы
    public void bottomOrderClick(String orderButtonPosition) {
        String bottomName = "Кнопка 1";
        if(orderButtonPosition.equals(bottomName)){
            driver.findElement(topOrderButton).click();
        } else {
            WebElement scrollElement = driver.findElement(bottomOrderButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrollElement);
            new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                    .until(ExpectedConditions.visibilityOfElementLocated(bottomOrderButton));
            scrollElement.click();
        }
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

    // Метод возвращает список параметров для использования в параметризованных тестах
    public List<Object[]> addDataToArray(String[] QUESTIONS, String[] ANSWERS) {
        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < QUESTIONS.length; i++) {
            data.add(new Object[]{buttonIdTemplate + i, QUESTIONS[i], ANSWERS[i]});
        }
        return data;
    }
    // Метод нажимает на вопросы и проверяет текст
    public void clickOnQuestionsAndCheckContent(String buttonId, String expectedQuestionText, String expectedAnswerText) {
        // Создание локаторов для вопросов и ответов
        By questionButton = By.id(buttonId);
        By answerText = By.id(buttonId.replace("heading", "panel"));
        // Прокручиваем страницу до нужного элемента
        scrollForElement(questionButton);
        // Добавили явное ожидание
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(questionButton));
        clickOnQuestion(questionButton);
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(answerText));
        String actualQuestionTest = driver.findElement(questionButton).getText();
        // Проверяем текст вопросов и ответов
        assertEquals("Текст вопроса не совпадает для кнопки с ID" + buttonId, expectedQuestionText, actualQuestionTest);
        String actualAnswerText = driver.findElement(answerText).getText();
        assertEquals("Текст ответа не совпадает для кнопки с ID " + buttonId, expectedAnswerText, actualAnswerText);

    }

}
