package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;
/*Задание 7. Сделайте сценарий, проверяющий наличие стикеров у товаров*/
public class Task7 {
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
        driver.get(ConfProperties.getProperty("mainpage")); }
    /**
     * тестовый метод для осуществления аутентификации
     */
    @Test
    public void Task7() {
        // creating a list of products on mainpage
        List<WebElement> elements = driver.findElements(By.cssSelector(".product"));
        System.out.println(elements.size());
        // checking of each elemnt have only one sticker
        for (WebElement el :elements)
        {
            Assert.assertTrue(el.findElements(By.cssSelector(".sticker")).size() == 1);
        }
    }
    /**
     * осуществление закрытия окна браузера
     */
    @AfterClass
    public static void tearDown() {
        driver.quit(); } }