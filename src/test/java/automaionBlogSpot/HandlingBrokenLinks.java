package automaionBlogSpot;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HandlingBrokenLinks {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://testautomationpractice.blogspot.com/");
		driver.manage().window().maximize();
	}

	@Test
	public void brokenLinks() {
		// Capture all link elements in webpage
		List<WebElement> links = driver.findElements(By.xpath("//div[@id=\"broken-links\"]//a"));

		for (WebElement linkElement : links) {
			String href = linkElement.getAttribute("href");
			if (href == null || href.isEmpty()) {
				System.out.println("href attribute is empty, not possible to check");
				continue;
			}

			// Hit URL to the server
			try {
				URL url = new URL(href);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.connect();

				if (con.getResponseCode() >= 400) {
					System.out.println(href + "========> Broken Links");
				} else {
					System.out.println(href + "========> Not a Broken Links");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
