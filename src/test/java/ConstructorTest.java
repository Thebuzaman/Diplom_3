import praktikum.pageobjects.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import praktikum.constants.EndPoints;
import praktikum.constants.IngredientsMenu;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

@RunWith(Parameterized.class)
public class ConstructorTest {
    private final WebDriver driver;
    private final String browserType;
    public ConstructorTest( String browserType) {
        this.browserType = browserType;
        switch (browserType) {
            case "Yandex": {
                System.setProperty("webdriver.chrome.driver", "drivers/yandexdriver.exe");
                break;
            }
            case "Chrome": {
                WebDriverManager.chromedriver().setup();
                break;
            }
        }
        this.driver=new ChromeDriver();
    }

    @Parameterized.Parameters(name = "Browser is: {0}")
    public static Object[][] getDriver() {
        return new Object[][]{
                {"Chrome"},
                {"Yandex"},
        };
    }

    @Test
    @DisplayName("Переход в меню 'Соусы'")
    public void checkSwitchToSauceGroup() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.HOME_URL);
        HomePage homePage = new HomePage(driver);
        String expectedText = IngredientsMenu.SAUCE;
        homePage.clickSauce();
        homePage.waitSelectedSauce();
        String actualText = homePage.selectedMenuGroup();
        Assert.assertEquals("Не произошел переход в меню 'Соусы'", expectedText, actualText);
    }

    @Test
    @DisplayName("Переход в меню 'Начинки'")
    public void checkSwitchToFillingGroup() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.HOME_URL);
        HomePage homePage = new HomePage(driver);
        String expectedText = IngredientsMenu.FILLING;
        homePage.clickFilling();
        homePage.waitSelectedFilling();
        String actualText = homePage.selectedMenuGroup();
        Assert.assertEquals("Не произошел переход в меню 'Начинки'", expectedText, actualText);
    }

    @Test
    @DisplayName("Переход в меню 'Булки'")
    public void checkSwitchToBunGroup() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.HOME_URL);
        HomePage homePage = new HomePage(driver);
        String expectedText = IngredientsMenu.BUN;
        homePage.clickSauce();
        homePage.waitSelectedSauce();
        homePage.clickBun();
        homePage.waitSelectedBun();
        String actualText = homePage.selectedMenuGroup();
        Assert.assertEquals("Не произошел переход в меню 'Булки'", expectedText, actualText);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
