import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SetupTest {
    WebDriver driver;
    @Before
    public void setup() {
        setupChrome();
//        setupYandex();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    public void setupChrome(){
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
    }

    public void setupYandex(){
        System.setProperty("webdriver.chrome.driver", "drivers/yandexdriver.exe");
        driver = new ChromeDriver();
    }
}