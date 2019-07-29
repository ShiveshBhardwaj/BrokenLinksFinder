package pack1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinks {
	public static void fetchURL(String url) throws MalformedURLException, IOException
	{
		System.setProperty("webdriver.chrome.driver","locationOfChromeDriver");
		WebDriver driver=new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(url);
		List<WebElement> links=driver.findElements(By.tagName("a"));
		links.addAll(driver.findElements(By.tagName("img")));
		ArrayList<String> activeLinks=new ArrayList<String>();
		System.out.println("Total number of links "+links.size());
		for(int i=0;i<links.size();i++)
		{
			if(links.get(i).getAttribute("href")!=null && (!links.get(i).getAttribute("href").contains("javascript") ))
			{
				activeLinks.add(links.get(i).getAttribute("href"));

			}

		}	
		
		System.out.println("Total number of active links "+activeLinks.size());
		for(int j=0;j<activeLinks.size();j++)
		{
			HttpURLConnection connection= (HttpURLConnection)new URL(activeLinks.get(j)).openConnection();

			connection.connect();
			int response=connection.getResponseCode();
			connection.disconnect();
			System.out.println((j+1)+" "+activeLinks.get(j)+"-->"+response);

		}
		driver.quit();

	}
	public static void main(String args[]) throws MalformedURLException, IOException {
		fetchURL("EnterUrl");
	}
}
