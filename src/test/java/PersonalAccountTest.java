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
import praktikum.pageobjects.PersonalAccountPage;
import praktikum.pojo.User;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@RunWith(Parameterized.class)
public class PersonalAccountTest {
    private final WebDriver driver;
    private final String browserType;
    Faker faker = new Faker();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String name = faker.name().username();
    Response response;

    public PersonalAccountTest( String browserType) {
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

    @Parameterized.Parameters(name = "Browser is: {0}")
    public static Object[][] getDriver() {
        return new Object[][]{
                {"Chrome"},
                {"Yandex"},
        };
    }

    @Test
    @DisplayName("Проверка входа в 'Личный кабинет' на главной странице")
    public void checkPersonaAccountVialButtonOnHomePage() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.LOGIN_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);

        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        homePage.waitHomePageWithOrderButton();
        homePage.clickPersonalButton();
        Assert.assertTrue("Пользователь не прошел авторизацию", personalAccountPage.exitButtonIsVisible());
    }

    @Test
    @DisplayName("Проверка выхода из 'Личного кабинета'")
    public void checkExitButtonOfPersonalAccount() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.LOGIN_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);

        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        homePage.waitHomePageWithOrderButton();
        homePage.clickPersonalButton();
        personalAccountPage.waitPersonalAccountPage();
        personalAccountPage.clickExitButton();
        Assert.assertTrue("Пользователь не вышел из аккаунта", loginPage.LoginButtonIsVisible());
    }

    @Test
    @DisplayName("Проверка перехода в конструктор из 'Личного кабинета'")
    public void checkGetConstructorFromPersonalAccount() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.LOGIN_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);

        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        homePage.waitHomePageWithOrderButton();
        homePage.clickPersonalButton();
        personalAccountPage.waitPersonalAccountPage();
        personalAccountPage.clickConstructorButton();
        Assert.assertTrue("Пользователь не перешел в конструктор заказов", homePage.orderButtonIsVisible());
    }

    @Test
    @DisplayName("Проверка перехода на главную страницу нажатием на логотип 'Stellar Burgers' из 'Личного кабинета'")
    public void checkHomePageFromPersonalAccount() {
        Allure.parameter("Браузер окружения", browserType);
        driver.get(EndPoints.LOGIN_URL);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);

        loginPage.waitLoginPage();
        loginPage.loginForm(email, password);
        homePage.waitHomePageWithOrderButton();
        homePage.clickPersonalButton();
        personalAccountPage.waitPersonalAccountPage();
        personalAccountPage.clickStellarLogo();
        Assert.assertTrue("Пользователь не перешел главную страницу", homePage.orderButtonIsVisible());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
