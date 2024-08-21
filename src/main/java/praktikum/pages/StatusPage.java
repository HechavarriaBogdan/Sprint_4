package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class StatusPage {
    private final WebDriver driver;

    private final By img = By.cssSelector("img[alt='Not found']");

    public StatusPage(WebDriver driver) {
        this.driver = driver;
    }

    public void checkErrorMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(img));

        assertTrue(driver.findElement(img).isDisplayed());
    }
}
