package praktikum.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;

public class HomePage {
    private WebDriver driver;
    private By personalAccount = By.xpath(".//a[@href='/account']");
    private By enterAccount = By.xpath(".//button[text()='Войти в аккаунт']");
    private By createOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    private By groupBun = By.xpath(".//span[text()='Булки']");
    private By groupSauce = By.xpath(".//span[text()='Соусы']");
    private By groupFilling = By.xpath(".//span[text()='Начинки']");
    private By currentMenu = By.xpath(".//div[contains(@class, 'current')]/span");
    private By bunItem = By.xpath(".//img[@alt='Флюоресцентная булка R2-D3']");
    private By sauceItem = By.xpath(".//img[@alt='Соус традиционный галактический']");
    public By fillingItem = By.xpath(".//img[@alt='Биокотлета из марсианской Магнолии']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Нажатие на кнопку 'Личный кабинет'")
    public void clickPersonalButton() {
        driver.findElement(personalAccount).click();
    }
    @Step("Нажатие на кнопку 'Войти в аккаунт'")
    public void clickEnterAccount() {
        driver.findElement(enterAccount).click();
    }
    @Step("Появилась кнопка 'Оформить заказ' на главной странице")
    public boolean orderButtonIsVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(createOrderButton));
        return driver.findElement(createOrderButton).isDisplayed();
    }
    @Step("Ожидаем кнопу 'Оформить заказ'")
    public void waitHomePageWithOrderButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(createOrderButton));
    }
    @Step("Нажатие на вкладку 'Булки'")
    public void clickBun() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(groupBun));
        driver.findElement(groupBun).click();
    }
    @Step("Нажатие на вкладку 'Соусы'")
    public void clickSauce() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(groupSauce));
        driver.findElement(groupSauce).click();
    }
    @Step("Нажатие на вкладку 'Начинки'")
    public void clickFilling() {
        driver.findElement(groupFilling).click();
    }
    @Step("Получаем название актуальной вкладки для сравнения с ожидаемой")
    public String selectedMenuGroup() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(currentMenu));
        return driver.findElement(currentMenu).getText();
    }
    @Step("Ожидаем появления актуальной вкладки 'Соусы'")
    public void waitSelectedSauce() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(sauceItem));
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(sauceItem));
    }
    @Step("Ожидаем появления актуальной вкладки 'Начинки'")
    public void waitSelectedFilling() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(fillingItem));
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(fillingItem));
    }
    @Step("Ожидаем появления актуальной вкладки 'Булки'")
    public void waitSelectedBun() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(bunItem));
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(bunItem));
    }
}
