package praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import praktikum.pages.MainPage;
import praktikum.pages.OrderPageFirst;
import praktikum.pages.OrderPageSecond;

import java.time.LocalDate;


@RunWith(Parameterized.class)
public class TopOrderScooterTest {
    @Rule
    public DriverRule factory = new DriverRule();

    private final String name; // Имя
    private final String surname; // Фамилия
    private final String address; // Адрес
    private final String phoneNumber; // Номер телефона
    private final String comment; // Комментарий
    private final LocalDate dateToBring; // Выбор даты в календаре
    private final String metroStationSelector; // Локатор станции метро
    private final String dropdownOptions; // Локатор срока аренды
    private final String selectColor; // Локатор выбора цвета самоката

    public TopOrderScooterTest(String name, String surname, String address, String phoneNumber, String comment,
                            LocalDate dateToBring, String metroStationSelector, String dropdownOptions,
                            String selectColor) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.comment = comment;
        this.dateToBring = dateToBring;
        this.metroStationSelector = metroStationSelector;
        this.dropdownOptions = dropdownOptions;
        this.selectColor = selectColor;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {"Джон", "Доу", "г.Москва, ул.Ленина 50", "+79028574588", "Тестовый комментарий", LocalDate.now(),
                        "[data-index='0']", "//*[@class='Dropdown-menu']//*[text()='сутки']", "black"},
                {"Смит", "Иванов", "Екатеринбург", "+79028716638", "Еще один комментарий",
                        LocalDate.now().plusDays(1), "[data-index='1']",
                        "//*[@class='Dropdown-menu']//*[text()='двое суток']", "grey"},
        };
    }

    /** Тест проверяет весь флоу позитивного сценария с двумя наборами данных.
     *  Точка входа в сценарий - кнопка "Заказать" вверху страницы */

    @Test
    public void TopOrderScooter() throws Exception {
        WebDriver driver = factory.getDriver();
        var mainPage = new MainPage(driver);
        var orderPageFirst = new OrderPageFirst(driver);
        var orderPageSecond = new OrderPageSecond(driver);
        mainPage.open();
        mainPage.topOrderClick();
        orderPageFirst.addName(name);
        orderPageFirst.addSurname(surname);
        orderPageFirst.addAddress(address);
        orderPageFirst.clickMetro(metroStationSelector);
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



