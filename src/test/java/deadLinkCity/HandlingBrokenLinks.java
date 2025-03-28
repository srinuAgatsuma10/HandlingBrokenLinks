package deadLinkCity;

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
		driver.get("http://www.deadlinkcity.com/");
		driver.manage().window().maximize();
	}

	@Test
	public void brokenLinks() {
		// Capture all link elements in webpage
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Number of links : " + links.size());

		int brokenLinks = 0;

		// Access links from the elements
		for (WebElement linkElement : links) {
			String hrefValue = linkElement.getAttribute("href");
			if (hrefValue == null || hrefValue.isEmpty()) {
				System.out.println("href attribute is empty, not possible to check");
				continue;
			}

			// Hit URL to the Server
			try {
				URL url = new URL(hrefValue); // convert href value from string to URL
				HttpURLConnection con = (HttpURLConnection) url.openConnection(); // open connection to the server
				con.connect(); // connect to the server and send request to the server

				if (con.getResponseCode() >= 400) {
					System.out.println(hrefValue + "========> Broken Link");
					brokenLinks++;
				} else {
					System.out.println(hrefValue + "========> Not a Broken Link");
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
