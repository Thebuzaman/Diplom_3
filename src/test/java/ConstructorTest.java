import praktikum.pageobjects.HomePage;
import praktikum.constants.EndPoints;
import praktikum.constants.IngredientsMenu;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

public class ConstructorTest extends SetupTest{

    @Test
    @DisplayName("Переход в меню 'Соусы'")
    public void checkSwitchToSauceGroup() {
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
}
