import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class test {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try{
            driver.get("https://mystore-testlab.coderslab.pl/index.php");
            WebElement signIn = driver.findElement(By.className("user-info"));
            signIn.click();

            Thread.sleep(4000L);

            WebElement createAccount = driver.findElement(By.className("no-account"));
            createAccount.click();

            WebElement home = driver.findElement(By.id("_desktop_logo"));
            home.click();

            Thread.sleep(4000L);




            //} catch (Exception e) {
            // throw new RuntimeException(e);
        }finally {
            driver.close();

        }
    }
}

