import com.github.javafaker.Faker;
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

public class RegistrationTest extends SetupTest {
    Faker faker = new Faker();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String name = faker.name().username();
    String passwordNotValid = faker.internet().password(1, 5);
    Response response;

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

    @Test
    @DisplayName("Проверка регистрации с валидными данными")
    public void checkRegistrationWithCorrectData() {
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
        driver.get(EndPoints.REGISTRATION_URL);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.registrationForm(name, email, passwordNotValid);
        Assert.assertEquals("Требование к паролю меньше 6 символов не выполнено",
                "Некорректный пароль",
                registrationPage.getPasswordMessage());
    }
}
