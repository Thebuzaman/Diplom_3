package praktikum.pageobjects;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalAccountPage {
    private WebDriver driver;

    private By exitButton = By.xpath(".//button[text()='Выход']");
    private By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private By stellarLogo = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']/a[@href='/']");
    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Нажатие на кнопу 'Выйти'")
    public void clickExitButton() {
        driver.findElement(exitButton).click();
    }
    @Step("Нажатие на кнопу 'Конструктор'")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }
    @Step("Нажатие на логотип 'Stellar Burgers'")
    public void clickStellarLogo() {
        driver.findElement(stellarLogo).click();
    }
    @Step("Ожидаем кнопу 'Выйти' в личном кабинете")
    public void waitPersonalAccountPage() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(exitButton));
    }
    @Step("Появилась кнопа 'Выйти' в личном кабинете")
    public boolean exitButtonIsVisible() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(exitButton));
        return driver.findElement(exitButton).isDisplayed();
    }

}
