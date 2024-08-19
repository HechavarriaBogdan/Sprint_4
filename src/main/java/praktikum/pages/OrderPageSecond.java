package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static praktikum.EnvConfig.EXPLICIT_WAIT;

public class OrderPageSecond {
    private final WebDriver driver;

    public OrderPageSecond(WebDriver driver) {
        this.driver = driver;
    }

    // Локатор для инпута "Когда привезти самокат"
    private final By inputDateToBring = By.cssSelector("[placeholder='* Когда привезти самокат']");
    // Шаблон локатора для выбора даты в календаре
    private final String datepickerDay = "react-datepicker__day--0";
    // Локатор для инпута выбора срока аренды
    private final By inputRentalPeriod = By.className("Dropdown-root");
    // Локатор для инпута с комментарием
    private final By inputComment = By.cssSelector("[placeholder='Комментарий для курьера']");
    // Локатор для кнопки "Заказать"
    private final By orderFinalButton = By.xpath("//*[@class='Order_Buttons__1xGrp']//*[text()='Заказать']");
    // Локатор для кнопки "Да", которая подтверждает оформление заказа
    private final By confirmOrder = By.xpath("//*[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    // Локатор для модального окна с информацией об успешном создании заказа
    private final By orderModalHeader = By.xpath("//*[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Заказ оформлен')]");

    // Метод устанавливает дату в инпуте "Когда привести самокат"
    public void setDateToBring (LocalDate dateToBring) {
        WebElement inputElement = driver.findElement(inputDateToBring);
        inputElement.click();
        int dayOfMonth = dateToBring.getDayOfMonth();
        String day = String.format("%02d", dayOfMonth);
        driver.findElement(By.className(datepickerDay + day)).click();
    }

    // Метод устанавливает срок аренды
    public void setRentalPeriod (String dropdownOptions) {
        driver.findElement(inputRentalPeriod).click();
        driver.findElement(By.xpath(dropdownOptions)).click();
    }
    // Метод выбирает цвет самоката
    public void setSelectColor (String selectColor) {
        driver.findElement(By.id(selectColor)).click();
    }

    // Метод заполняет поле ввода комментария
    public void addComment (String comment) {
        WebElement inputElement = driver.findElement(inputComment);
        inputElement.click();
        inputElement.clear();
        inputElement.sendKeys(comment);
    }
    // Метод нажимает на кнопку "Заказать"
    public void clickFinalButton () {
        driver.findElement(orderFinalButton).click();
    }
    // Метод подтверждает оформление заказа
    public void confirmOrder() {
        driver.findElement(confirmOrder).click();
    }
    // Метод проверяет что появилось всплывающее окно с сообщением об успешном создании заказа
    public void checkOrderModal () {
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(orderModalHeader));
        assertTrue(driver.findElement(orderModalHeader).isDisplayed());
    }

}
