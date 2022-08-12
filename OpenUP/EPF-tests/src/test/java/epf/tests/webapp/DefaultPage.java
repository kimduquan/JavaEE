package epf.tests.webapp;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
public class DefaultPage {

	private final WebDriver driver;
	
	private final URL url;
	
	@Inject
	public DefaultPage(WebDriver driver, @Named(Naming.WebApp.WEB_APP_URL) URL url) {
		this.driver = driver;
		this.url = url;
	}
	
	@PostConstruct
	void navigateTo() {
		driver.navigate().to(url);
	}
}
