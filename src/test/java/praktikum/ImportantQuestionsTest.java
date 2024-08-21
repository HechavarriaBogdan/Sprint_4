package praktikum;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import praktikum.pages.MainPage;
import java.util.Collection;

@RunWith(Parameterized.class)


public class ImportantQuestionsTest {
    private WebDriver driver;
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
    public static void openPageWithoutCookie () {
        WebDriver driver = driverRule.getDriver();
        var mainPage = new MainPage(driver);
        mainPage.open();
    }
    @Before
    public void setUp() {
        driver = driverRule.getDriver();
    }


    @Parameterized.Parameters(name = "{index}: Проверка кнопки с ID {0} текста {1} и {2}")
    public static Collection<Object[]> data() {
        WebDriver driver = driverRule.getDriver();
        MainPage mainPage = new MainPage(driver);
        return mainPage.addDataToArray(QUESTIONS, ANSWERS);
    }

    /**
     * Тест проверяет ответы на часто задаваемый вопросы.
     * Тест выполняется без перезапуска окна браузера
     */

    @Test
    public void questionsAbout() {
        var mainPage = new MainPage(driver);
        mainPage.clickOnQuestionsAndCheckContent(buttonId, expectedQuestionText, expectedAnswerText);
    }
}
