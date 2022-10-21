package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

/*Задание 8. Проверить сортировку стран и геозон на странице стран*/
public class Task8 {
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
    public void Task8() {
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
        driver.get(ConfProperties.getProperty("countriesnpage"));
        int amountOfCountries = driver.findElements(By.xpath("//td[@id='content']//tbody/tr[@class='row']")).size();
        for (int i = 1; i <= amountOfCountries; i++) {
            String currentCountry = driver.findElement(By.xpath("//td[@id='content']//tbody/tr[" + (i + 1) + "]//a")).getText();
            if (i < amountOfCountries) {
                String nextCountry = driver.findElement(By.xpath("//td[@id='content']//tbody/tr[" + (i + 2) + "]//a")).getText();
                Assert.assertTrue(currentCountry.compareTo(nextCountry) < 0);
            }
            int amountOfZonesInCurrentCountry = Integer.parseInt(
                    driver.findElement(By.xpath("//td[@id='content']//tbody/tr[" + (i + 1) + "]/td[6]")).getText()
            );
            if (amountOfZonesInCurrentCountry != 0) {
                driver.findElement(By.xpath("//td[@id='content']//tbody/tr[" + (i + 1) + "]/td[5]/a")).click();
                for (int j = 1; j <= amountOfZonesInCurrentCountry; j++) {
                    String currentZone = driver.findElement(By.xpath("//table[@id='table-zones']/tbody/tr[" + (j + 1) + "]/td[3]")).getText();
                    if (j < amountOfZonesInCurrentCountry) {
                        String nextZone = driver.findElement(By.xpath("//table[@id='table-zones']/tbody/tr[" + (j + 2) + "]/td[3]")).getText();
                        Assert.assertTrue(currentZone.compareTo(nextZone) < 0);
                    }
                }
                driver.findElement(By.xpath("//ul[@id='box-apps-menu']/li[@class='selected']")).click();
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