package demoQA;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class URL_Validation {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://demoqa.com/broken");
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void linkValidationValidLink() {
		// Capture URL
		WebElement url1 = driver.findElement(By.xpath("//a[normalize-space()='Click Here for Valid Link']"));

		String href = url1.getAttribute("href");

		// Hit the Url to the server
		try {
			URL url = new URL(href);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			if (con.getResponseCode() >= 400) {
				System.out.println("This is Broken Link");
			} else {
				System.out.println("Valid Link");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 2)
	public void linkValidationBrokenLink() {
		// Capture URL
		WebElement url2 = driver.findElement(By.xpath("//a[normalize-space()='Click Here for Broken Link']"));
		String hrefUrl = url2.getAttribute("href");

		// Hit the Url to the server
		try {
			URL url = new URL(hrefUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			if (con.getResponseCode() >= 400) {
				System.out.println("This is Broken Link");
			} else {
				System.out.println("Valid Link");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
