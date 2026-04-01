package demoblazeSTproject;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.TakesScreenshot;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoBlazeTesting {
	
	WebDriver driver;
	
@BeforeMethod

public void setUp() {
	WebDriverManager.chromedriver().setup();
	driver=new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("https://www.demoblaze.com");
}


@Test(priority=1)
public void validLogin() throws InterruptedException{
	
	driver.findElement(By.id("login2")).click();
	Thread.sleep(1000);
	driver.findElement(By.id("loginusername")).sendKeys("Saras");
	driver.findElement(By.id("loginpassword")).sendKeys("Sara@123");
	
	takeScreenshot(driver,"validlogin.png");
	Thread.sleep(2000);
	
	driver.findElement(By.xpath("//button[text()='Log in']")).click();
	Thread.sleep(2000);
	
	Assert.assertEquals(driver.getTitle(),"STORE");
	
	
	
}

@Test(priority=2)
public void invalidLogin() throws InterruptedException {
	driver.findElement(By.id("login2")).click();
	Thread.sleep(2000);
	driver.findElement(By.id("loginusername")).sendKeys("Saras");
	Thread.sleep(1000);
	driver.findElement(By.id("loginpassword")).sendKeys("Sara@1");
	Thread.sleep(2000);
	
	takeScreenshot(driver,"invalidlogin.png");
	Thread.sleep(2000);
	
	driver.findElement(By.xpath("//button[text()='Log in']")).click();
	Thread.sleep(1000);
}

@Test(priority=3)
public void blankLogin() throws InterruptedException {
	
	driver.findElement(By.id("login2")).click();
	Thread.sleep(1000);
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	driver.findElement(By.xpath("//button[text()='Log in']")).click();
	
	Thread.sleep(1000);
	
	Alert alert=wait.until(ExpectedConditions.alertIsPresent());
	
	Assert.assertEquals(alert.getText(), "Please fill out Username and Password.");
	alert.accept();	
	
	takeScreenshot(driver,"blanklogin.png");
	Thread.sleep(2000);
	
}




@Test(priority=4)
public void productSearch() throws InterruptedException {
	
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Laptops']"))).click();
	Thread.sleep(2000);
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='MacBook air']"))).click();
	
	takeScreenshot(driver,"productsearch.png");
	Thread.sleep(3000);
	
}

@Test(priority=5)
public void AddToCart() throws InterruptedException {
	
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Phones']"))).click();
	Thread.sleep(3000);
  
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Nexus 6']"))).click();
    Thread.sleep(3000);
   
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();      
    Thread.sleep(3000);
    
    takeScreenshot(driver,"addtocart.png");
	Thread.sleep(2000);

}

@Test(priority=6)
public void productAdded() throws InterruptedException {
	
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Phones']"))).click();
	Thread.sleep(2000);
    wait.until(ExpectedConditions.elementToBeClickable( By.xpath("//a[text()='Nokia lumia 1520']"))).click();
    
    Thread.sleep(2000);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();
    
    Alert alert=wait.until(ExpectedConditions.alertIsPresent());
    Assert.assertEquals(alert.getText(),"Product added");
    alert.accept();
    
    wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
    
    Thread.sleep(1000);
    
    boolean productPresent= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Nokia lumia 1520']"))).isDisplayed();
    
    Assert.assertTrue(productPresent,"Product was not found!");
    Thread.sleep(4000);
    
    takeScreenshot(driver,"productadded.png");
	Thread.sleep(2000);
}




@Test(priority = 7)
public void placeOrder() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Click Monitors
    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Monitors"))).click();

    // Wait for products to load (IMPORTANT)
    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[text()='Apple monitor 24']")));

    // Click product
    driver.findElement(By.xpath("//a[text()='Apple monitor 24']")).click();

    // Add to cart
    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))).click();

    // Handle alert properly
    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    System.out.println(alert.getText());
    alert.accept();

    // Go to cart
    wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

    // Wait for cart page
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Products']")));

    // Place order
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();

    // Fill form
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Saras");
    driver.findElement(By.id("country")).sendKeys("India");
    driver.findElement(By.id("city")).sendKeys("Kochi");
    driver.findElement(By.id("card")).sendKeys("142567893456");
    driver.findElement(By.id("month")).sendKeys("12");
    driver.findElement(By.id("year")).sendKeys("2027");

    // Purchase
    driver.findElement(By.xpath("//button[text()='Purchase']")).click();

    // Validate success
    WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[text()='Thank you for your purchase!']")));

    Assert.assertTrue(success.isDisplayed());
    
    Thread.sleep(3000);
    
    takeScreenshot(driver,"placeorder.png");
	Thread.sleep(2000);
}



@Test(priority = 8)
public void LogoutTest() {
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Click Login button
    wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

    // Enter username
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")))
        .sendKeys("user@15");

    // Enter password
    driver.findElement(By.id("loginpassword")).sendKeys("Pass@1234");

    // Click Login
    driver.findElement(By.xpath("//button[text()='Log in']")).click();

    // Wait until Logout button is visible (login success)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));

    // Click Logout
    driver.findElement(By.id("logout2")).click();

    // Validate logout (Login button should be visible again)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));

    // Take screenshot
    takeScreenshot(driver, "logout.png");
}


@Test(priority=9)	    

public void verifyOrderConfirmation() throws InterruptedException {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Laptops']"))).click();
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='MacBook air']"))).click();
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();      
		        Thread.sleep(1000);
		        
		        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		        Assert.assertEquals(alert.getText(), "Product added");
		        alert.accept();
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='name']"))).sendKeys("Sara");      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='country']"))).sendKeys("India");      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city']"))).sendKeys("Kochi");      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='card']"))).sendKeys("142567893456");      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='month']"))).sendKeys("12");      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='year']"))).sendKeys("2027");      
		        Thread.sleep(1000);
		        
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Purchase']"))).click();     
		        Thread.sleep(1000);
		        
		    String confirmationMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Thank you for your purchase!']"))).getText();

		        Assert.assertEquals(confirmationMsg, "Thank you for your purchase!");
		        
		        takeScreenshot(driver,"confirmorder.png");
		    	Thread.sleep(2000);

		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='OK']"))).click();  
		        
		        
		    } 

public static void takeScreenshot(WebDriver driver,String filename)
{
	try
	{
		TakesScreenshot ts= (TakesScreenshot) driver;
		File scource=ts.getScreenshotAs(OutputType.FILE);
		Files.copy(scource.toPath(),Paths.get("Screenshots/"+filename));
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	
}


@AfterMethod
public void tearDown() {
	if(driver!=null){
		driver.quit();
	}
}
}

























