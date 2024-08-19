package praktikum;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.pages.MainPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static praktikum.EnvConfig.EXPLICIT_WAIT;

@RunWith(Parameterized.class)


public class ImportantQuestionsTest {
    // Переменная содержит общий шаблон локаторов для кнопок с вопросами
    private static final String BUTTON_ID_TEMPLATE = "accordion__heading-";
    // Массив с текстом вопросов
    private static final String[] QUESTIONS = {
            "Сколько это стоит? И как оплатить?",
            "Хочу сразу несколько самокатов! Так можно?",
            "Как рассчитывается время аренды?",
            "Можно ли заказать самокат прямо на сегодня?",
            "Можно ли продлить заказ или вернуть самокат раньше?",
            "Вы привозите зарядку вместе с самокатом?",
            "Можно ли отменить заказ?",
            "Я живу за МКАДом, привезёте?"
    };

    // Массив с текстом ответов
    private static final String[] ANSWERS = {
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };

    @Parameterized.Parameter(0)
    public String buttonId;

    @Parameterized.Parameter(1)
    public String expectedQuestionText;

    @Parameterized.Parameter(2)
    public String expectedAnswerText;

    // Нужно для того что бы все тесты выполнялись в одном окне
    @ClassRule
    public static DriverRule driverRule = new DriverRule();

    // Закрываем боттом-шит про куки перед запуском параметризованных тестов
    @BeforeClass
    public static void setUp() {
        WebDriver driver = driverRule.getDriver();
        var mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Parameterized.Parameters(name = "{index}: Проверка кнопки с ID {0} текста {1} и {2}")
    public static Collection<Object[]> data() {
        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < QUESTIONS.length; i++) {
            data.add(new Object[]{BUTTON_ID_TEMPLATE + i, QUESTIONS[i], ANSWERS[i]});
        }
        return data;
    }

    /** Тест проверяет ответы на часто задаваемый вопросы.
     *  Тест выполняется без перезапуска окна браузера */

    @Test
    public void questionsAbout() {
        WebDriver driver = driverRule.getDriver();
        var mainPage = new MainPage(driver);
        // Создание локаторов для кнопки и текста
        By questionButton = By.id(buttonId);
        By answerText = By.id(buttonId.replace("heading", "panel")); // заменяем `heading` на `panel` для текста
        // Прокручиваем страницу до нужного элемента
        mainPage.scrollForElement(questionButton);
        // Добавили явное ожидание
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOfElementLocated(questionButton));
        // Нажимаем на нужный элемент
        mainPage.clickOnQuestion(questionButton);
        // Добавили явное ожидание
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT)).until(ExpectedConditions.visibilityOfElementLocated(answerText));
        // Сравниваем текст вопроса с ожидаемым результатом
        String actualQuestionTest = driver.findElement(questionButton).getText();
        assertEquals("Текст вопроса не совпадает для кнопки с ID" + buttonId, expectedQuestionText, actualQuestionTest);
        // Сравниваем текст ответа на часто задаваемые вопросы с ожидаемым результатом
        String actualAnswerText = driver.findElement(answerText).getText();
        assertEquals("Текст ответа не совпадает для кнопки с ID " + buttonId, expectedAnswerText, actualAnswerText);

    }
}
