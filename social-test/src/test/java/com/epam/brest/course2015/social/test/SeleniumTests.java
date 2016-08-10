package com.epam.brest.course2015.social.test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexander_borohov on 10.8.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SeleniumConfig.class})
public class SeleniumTests {
    @Autowired
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver.get("http://www.calculator.net/");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void webDriverTest() throws InterruptedException {
        driver.findElement(new By.ByXPath(".//*[@id='sciout']/tbody/tr[2]/td[2]/div/div[3]/span[1]")).click();
        Thread.sleep(500);
        driver.findElement(new By.ByXPath(".//*[@id='sciout']/tbody/tr[2]/td[2]/div/div[4]/span[1]")).click();
        Thread.sleep(500);
        driver.findElement(new By.ByXPath(".//*[@id='sciout']/tbody/tr[2]/td[2]/div/div[1]/span[4]")).click();
        Thread.sleep(500);
        driver.findElement(new By.ByXPath(".//*[@id='sciout']/tbody/tr[2]/td[2]/div/div[3]/span[1]")).click();
        Thread.sleep(500);
        driver.findElement(new By.ByXPath(".//*[@id='sciout']/tbody/tr[2]/td[2]/div/div[3]/span[1]")).click();
        Thread.sleep(500);
        driver.findElement(new By.ByXPath(".//*[@id='sciout']/tbody/tr[2]/td[2]/div/div[5]/span[4]")).click();
        Thread.sleep(500);

        String result = driver.findElement(new By.ByXPath(".//*[@id='sciOutPut']")).getText();

        assertEquals(result, "21.");
    }

    private static void simpleSocialLoginTest() throws InterruptedException {

    }

}
