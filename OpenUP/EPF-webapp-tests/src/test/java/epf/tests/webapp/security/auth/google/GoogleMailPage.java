package epf.tests.webapp.security.auth.google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.tests.webapp.Page;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class GoogleMailPage extends Page {

	@Inject
	public GoogleMailPage(RemoteWebDriver driver) throws Exception {
		super(driver);
	}

	@PostConstruct
	@Override
	public void navigateTo() {
		driver.navigate().to("https://mail.google.com");
	}

	public void setIdentifierId(String identifierId) {
		driver.findElement(By.id("identifierId")).sendKeys(identifierId);
	}
	
	public String next() {
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		return "";
	}
	
	public String tryAgain() {
		WebElement link = driver.findElement(By.xpath("//*[text()='Try again']"));
		Actions actions = new Actions(driver).moveToElement(link).click();
		actions.build().perform();
		return "";
	}
}
