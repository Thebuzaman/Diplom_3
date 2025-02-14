package praktikum.pageobjects;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private WebDriver driver;
    private By nameField = By.xpath("//label[text()='Имя']/..//input[@type='text']");
    private By emailField = By.xpath("//label[text()='Email']/..//input[@type='text']");
    private By passwordField = By.xpath(".//input[@type='password']");
    private By registrationButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private By submitButton = By.xpath(".//a[@href='/login']");
    private By incorrectPassword = By.xpath(".//p[text()='Некорректный пароль']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Ввод в поле 'Name'")
    public void setNameField (String name) {
        driver.findElement(nameField).sendKeys(name);
    }
    @Step("Ввод в поле 'Email'")
    public void setEmailField (String email) {
        driver.findElement(emailField).sendKeys(email);
    }
    @Step("Ввод в поле 'Password'")
    public void setPasswordField (String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    @Step("Нажатие на кнопу 'Зарегестрироваться' на странице регистрации")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }
    @Step("Нажатие на кнопу 'Войти' на странице восстановления пароля")
    public void clickSubmitButton() {
        driver.findElement(submitButton).click();
    }

    public void registrationForm(String name, String email, String password) {
        setNameField(name);
        setEmailField(email);
        setPasswordField(password);
        clickRegistrationButton();
    }
    @Step("Получение ошибки 'Некорректный пароль'")
    public String getPasswordMessage() {
        return driver.findElement(incorrectPassword).getText();
    }

}
