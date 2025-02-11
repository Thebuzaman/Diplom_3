package praktikum.pageobjects;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private By emailField = By.xpath(".//input[@type='text']");
    private By passwordField = By.xpath(".//input[@type='password']");
    private By submitButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Ввод в поле 'Email'")
    public void setEmailField (String email) {
        driver.findElement(emailField).sendKeys(email);
    }
    @Step("Ввод в поле 'Password'")
    public void setPasswordField (String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    @Step("Нажатие на кнопу 'Войти' на странице авторизации")
    public void clickSubmitButton() {
        driver.findElement(submitButton).click();
    }

    public void loginForm(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
        clickSubmitButton();

    }
    @Step("Ожидаем кнопу 'Войти' на странице авторизации")
    public void waitLoginPage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(submitButton));
    }
    @Step("Появилась кнопка 'Войти' на странице авторизации")
    public boolean LoginButtonIsVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(submitButton));
        return driver.findElement(submitButton).isDisplayed();
    }
}
