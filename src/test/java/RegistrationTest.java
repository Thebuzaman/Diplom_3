import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import praktikum.client.BaseHttpClient;
import praktikum.constants.EndPoints;
import praktikum.pageobjects.HomePage;
import praktikum.pageobjects.LoginPage;
import praktikum.pageobjects.RegistrationPage;
import praktikum.pojo.User;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

@RunWith(Parameterized.class)
public class RegistrationTest {
    private final WebDriver driver;
    private final String browserType;
    Faker faker = new Faker();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String name = faker.name().username();
    String passwordNotValid = faker.internet().password(1, 5);
    Response response;

    public RegistrationTest( String browserType) {
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

    @After
    @Step("Удаление созданного пользователя посредством API")
    public  void cleanData() {
        User user = new User(email, password);
        response = BaseHttpClient.postRequest(EndPoints.USER_LOGIN_POST, user);
        String accessToken;
        int statusCode = response.then().extract().statusCode();
        if (statusCode == 200) {
            accessToken = response.then().extract().path("accessToken").toString();
            BaseHttpClient.deleteRequest(EndPoints.ACTIONS_WITH_USER, accessToken);
        }
    }

    @Parameterized.Parameters(name = "Browser is: {0}")
    public static Object[][] getDriver() {
        return new Object[][]{
                {"Chrome"},
                {"Yandex"},
        };
    }

    @Test
    @DisplayName("Проверка регистрации с валидными данными")
    public void checkRegistrationWithCorrectData() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.REGISTRATION_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registrationForm(name, email, password);
        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        Assert.assertTrue("Пользователь не прошел регистрацию и авторизацию", homePage.orderButtonIsVisible());
    }

    @Test
    @DisplayName("Появление ошибки при регистрации с невалидным паролем меньше 6 символов")
    public void checkErrorRegistrationWithIncorrectPassword() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.REGISTRATION_URL);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registrationForm(name, email, passwordNotValid);
        Assert.assertEquals("Требование к паролю меньше 6 символов не выполнено",
                "Некорректный пароль",
                registrationPage.getPasswordMessage());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
