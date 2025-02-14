import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import praktikum.factory.WebDriverFactory;

public class SetupTest {
    WebDriver driver;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriverInstance();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}