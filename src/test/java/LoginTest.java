import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import praktikum.client.BaseHttpClient;
import praktikum.constants.EndPoints;
import praktikum.pageobjects.ForgotPasswordPage;
import praktikum.pageobjects.HomePage;
import praktikum.pageobjects.LoginPage;
import praktikum.pageobjects.RegistrationPage;
import praktikum.pojo.User;import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@RunWith(Parameterized.class)
public class LoginTest {
    private final WebDriver driver;
    private final String browserType;
    Faker faker = new Faker();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String name = faker.name().username();
    Response response;

    public LoginTest( String browserType) {
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

    @Before
    @Step("Создание пользователя посредством API")
    public  void setData() {
        User user = new User(email, password, name);
        response = BaseHttpClient.postRequest(EndPoints.USER_CREATE_POST, user);
    }
    @After
    @Step("Удаление созданного пользователя посредством API")
    public  void cleanData() {
        String accessToken;
        int statusCode = response.then().extract().statusCode();
        if (statusCode == 200) {
            accessToken = response.then().extract().path("accessToken").toString();
            BaseHttpClient.deleteRequest(EndPoints.ACTIONS_WITH_USER, accessToken);
        }
    }

    @Test
    @DisplayName("Проверка входа через кнопку 'Личный кабинет' на главной странице")
    public void checkLoginViaPersonalButtonOnHomePage() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.HOME_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.clickPersonalButton();
        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        Assert.assertTrue("Пользователь не прошел авторизацию", homePage.orderButtonIsVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку 'Войти в аккаунт' на главной странице")
    public void checkLoginViaEnterButtonOnHomePage() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.HOME_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.clickEnterAccount();
        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        Assert.assertTrue("Пользователь не прошел авторизацию", homePage.orderButtonIsVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку 'Войти' на странице регистрации")
    public void checkLoginViaEnterButtonOnRegistrationPage() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.REGISTRATION_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.clickSubmitButton();
        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        Assert.assertTrue("Пользователь не прошел авторизацию", homePage.orderButtonIsVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку 'Войти' на странице восстановления пароля")
    public void checkLoginViaEnterButtonOnForgotPasswordPage() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.FORGOT_PASSWORD_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

        forgotPasswordPage.clickSubmitButton();
        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        Assert.assertTrue("Пользователь не прошел авторизацию", homePage.orderButtonIsVisible());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
