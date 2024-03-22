package genericutility;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import genericUtility.ExcelFileUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class BaseClass_Practice {
	PropertyFileUtility putil= new PropertyFileUtility();
	ExcelFileUtility eutil= new ExcelFileUtility();
	public WebDriver driver= null;
	public static WebDriver sDriver;// this is for listeneres...which is not implemented yet
	
	@BeforeSuite
	public void beforeSuiteConfiguration() {
		System.out.println("Database Connection established");
	}
	@BeforeClass
	public void beforeClassConfiguration() throws IOException {
		String BROWSER=putil.toReadDataFromPropertyFile("browser");
		String URL= putil.toReadDataFromPropertyFile("url");
		if(BROWSER.equalsIgnoreCase("chrome")) {
			driver= new ChromeDriver();
		}else if(BROWSER.equalsIgnoreCase("edge")) {
			driver= new EdgeDriver();
		}
		driver.manage().window().maximize();//webdriver utility is not created by BALAJI framework architect 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get(URL);
		
	}
	@BeforeMethod
	public void beforeMethodConfiguration() throws IOException {
		String uname= putil.toReadDataFromPropertyFile("username");
		String pwd= putil.toReadDataFromPropertyFile("password");
		driver.findElement(By.name("user_name")).sendKeys(uname);
		driver.findElement(By.name("user_password")).sendKeys(pwd);
		driver.findElement(By.id("submitButton")).click();
		
	}
	@AfterMethod
	public void afterMethodConfiguration() {
		WebElement administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions action= new Actions(driver);
		action.moveToElement(administrator).perform();
		driver.findElement(By.linkText("Sign Out")).click();
	}
	@AfterClass
	public void afterClassConfiguration() {
		driver.quit();
	}
	@AfterSuite
	public void afterSuiteConfiguration() {
		System.out.println("Database Connection disconnected");
	}
}
