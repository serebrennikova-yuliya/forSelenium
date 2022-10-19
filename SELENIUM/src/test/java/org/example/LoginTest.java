package org.example;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import java.util.List;
import org.openqa.selenium.By;
public class LoginTest {
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static WebDriver driver;

    /**
     * осуществление первоначальной настройки
     */
    @BeforeClass
    public static void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpage")); }
    /**
     * тестовый метод для осуществления аутентификации
     */
    @Test
    public void loginTest() {
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //значение login/password берутся из файла настроек по аналогии с chromedriver
        //и loginpage
        //вводим логин
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        //нажимаем кнопку входа
        //loginPage.clickLoginBtn();
        //вводим пароль
        loginPage.inputPasswd(ConfProperties.getProperty("password"));
        //нажимаем кнопку входа
        loginPage.clickLoginBtn();
        //получаем отображаемый логин
//        String user = profilePage.getUserName();
        //и сравниваем его с логином из файла настроек
        //       Assert.assertEquals(ConfProperties.getProperty("login"), user);
        List<WebElement> menuItems = driver.findElements(By.cssSelector("li#app- span.name"));
        for (int i = 0; i < menuItems.size(); i++) {
            driver.findElement(By.cssSelector("li#app-:nth-child(" + (i + 1) + ")")).click();

            if (driver.findElements(By.cssSelector("ul.docs")).size() == 0) {
                Assert.assertTrue("The title is not shown on the page.", driver.findElement(By.cssSelector("h1")).isDisplayed());
            } else {
                List<WebElement> subMenuItems = driver.findElements(By.cssSelector("ul.docs li"));
                for (int j = 0; j < subMenuItems.size(); j++) {
                    driver.findElement(By.cssSelector("ul.docs li:nth-child(" + (j + 1) + ")")).click();
                    Assert.assertTrue("The title is not shown on the page.", driver.findElement(By.cssSelector("h1")).isDisplayed());
                }
            }
        }
    }
    /**
     * осуществление выхода из аккаунта с последующим закрытием окна браузера
     */
    /**
     * осуществление выхода из аккаунта с последующим закрытием окна браузера
     */
    @AfterClass
    public static void tearDown() {
//        profilePage.entryMenu();
        profilePage.userLogout();
        driver.quit(); } }