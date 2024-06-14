package Cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class MyStoreTest {
    private final WebDriver driver = new ChromeDriver();


    @Given("the user visits login page")
    public void theUserVisitsLoginPage() {
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=authentication&back=my-account");
        driver.manage().window().maximize();
    }

    @When("the user type email and password")
    public void theUserTypeEmailAndPassword() {
        driver.findElement(By.id("field-email")).sendKeys("hankmoody+19@onet.pl");
        driver.findElement(By.id("field-password")).sendKeys("xizwuc-huxnov-3Wajfy");
    }

    @And("the user click button sign in")
    public void theUserClickButtonSignIn() {
        driver.findElement(By.id("submit-login")).click();
    }

    @And("the user click [Addresses]")
    public void theUserClickAddresses() {
        driver.findElement(By.id("addresses-link")).click();
    }

    @When("the user click Create new address")
    public void theUserClickCreateNewAddress() {
        driver.findElement(By.cssSelector("#content > div.addresses-footer > a > span")).click();
    }

    @And("the user fill form data:")
    public void theUserFillFormData(DataTable dataTable) {
        List<Map<String, String>> addressData = dataTable.asMaps(String.class, String.class);
        Map<String, String> addressMap = (Map<String, String>) addressData.get(0);

        WebElement aliasInput = driver.findElement(By.id("field-alias"));
        aliasInput.sendKeys(addressMap.get("Alias"));

        WebElement addressInput = driver.findElement(By.id("field-address1"));
        addressInput.sendKeys(addressMap.get("Address"));

        WebElement cityInput = driver.findElement(By.id("field-city"));
        cityInput.sendKeys(addressMap.get("City"));

        WebElement postalCodeInput = driver.findElement(By.id("field-postcode"));
        postalCodeInput.sendKeys(addressMap.get("Postal Code"));

        WebElement countryDropdown = driver.findElement(By.id("field-id_country"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText(addressMap.get("Country"));

        WebElement phoneInput = driver.findElement(By.id("field-phone"));
        phoneInput.sendKeys(addressMap.get("Phone"));
    }

    @And("the user submit form")
    public void theUserSubmitForm() {
        driver.findElement(By.cssSelector("#content > div > div > form > footer > button")).click();
    }

    @Then("user confirmation message")
    public void userConfirmationMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("controller=addresses"));
        WebElement confirmationMessage = driver.findElement(By.cssSelector("#notifications > div > article > ul > li"));
        if (confirmationMessage.isDisplayed()){
            System.out.println("Confirmed");

        }else {
            System.out.println("ERROR");
        }

    }

    @And("the user delete new address")
    public void theUserDeleteNewAddress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".address")));

        List<WebElement> addresses = driver.findElements(By.cssSelector(".address"));
        WebElement addressToDelete = null;

        for (WebElement address : addresses) {
            WebElement titleElement = address.findElement(By.tagName("h4"));
            String title = titleElement.getText();
            if (title.equals("Bear")) {
                WebElement editButton = address.findElement(By.cssSelector("a[data-link-action='edit-address']"));
                String editHref = editButton.getAttribute("href");
                String idAddress = editHref.split("id_address=")[1];
                WebElement deleteButton = address.findElement(By.cssSelector("a[href*='controller=address&id_address=" + idAddress + "'][data-link-action='delete-address']"));
                deleteButton.click();
                wait.until(ExpectedConditions.stalenessOf(address));
                break;
            }
            if (addressToDelete != null) {
                try {
                    wait.until(ExpectedConditions.stalenessOf(addressToDelete));
                    System.out.println("Adres nie został znaleziony.");
                } catch (TimeoutException e) {
                    System.out.println("Adres nie został usunięty.");
                }
            } else {
                System.out.println("Adres usunięty.");

            }
        }
    }
}
