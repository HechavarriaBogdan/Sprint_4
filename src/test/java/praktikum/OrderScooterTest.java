package praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import praktikum.pages.MainPage;
import praktikum.pages.OrderPageFirst;
import praktikum.pages.OrderPageSecond;

@RunWith(Parameterized.class)
public class OrderScooterTest {
    @Rule
    public DriverRule factory = new DriverRule();
    private WebDriver driver;

    private final String name; // Имя
    private final String surname; // Фамилия
    private final String address; // Адрес
    private final String phoneNumber; // Номер телефона
    private final String comment; // Комментарий
    private final String dateToBring; // Выбор даты в календаре
    private final String indexStationSelector; // Индекс станции метро
    private final String dropdownOptions; // Срок аренды
    private final String selectColor; // Цвет самоката
    private final String orderButtonPosition; // Выбор одной из двух кнопок "Заказать"

    public OrderScooterTest(String name, String surname, String address, String phoneNumber, String comment,
                            String dateToBring, String indexStationSelector, String dropdownOptions,
                            String selectColor, String orderButtonPosition) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.comment = comment;
        this.dateToBring = dateToBring;
        this.indexStationSelector = indexStationSelector;
        this.dropdownOptions = dropdownOptions;
        this.selectColor = selectColor;
        this.orderButtonPosition = orderButtonPosition;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {"Джон", "Доу", "г.Москва, ул.Ленина 50", "+79028574588", "Тестовый комментарий", "21.10.2024",
                        "0", "сутки", "black", "Кнопка 1"},
                {"Смит", "Иванов", "Екатеринбург", "+79028716638", "Еще один комментарий", "25.09.2024",
                        "1", "двое суток", "grey", "Кнопка 2"},
        };
    }

    @Before
    public void setUp () {
        driver = factory.getDriver();
    }

    /** Тест проверяет весь флоу позитивного сценария с двумя наборами данных.
     *  Две точки входа в сценарий: кнопка Заказать вверху страницы и кнопка Заказать внизу страницы */

    @Test
    public void orderScooter() throws Exception {
        var mainPage = new MainPage(driver);
        var orderPageFirst = new OrderPageFirst(driver);
        var orderPageSecond = new OrderPageSecond(driver);
        mainPage.open();
        mainPage.bottomOrderClick(orderButtonPosition);
        orderPageFirst.addName(name);
        orderPageFirst.addSurname(surname);
        orderPageFirst.addAddress(address);
        orderPageFirst.clickMetro(indexStationSelector);
        orderPageFirst.addPhoneNumber(phoneNumber);
        orderPageFirst.clickNextButton();
        orderPageSecond.setDateToBring(dateToBring);
        orderPageSecond.setRentalPeriod(dropdownOptions);
        orderPageSecond.setSelectColor(selectColor);
        orderPageSecond.addComment(comment);
        orderPageSecond.clickFinalButton();
        orderPageSecond.confirmOrder();
        orderPageSecond.checkOrderModal();
    }
}
