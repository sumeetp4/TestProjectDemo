import io.appium.java_client.MobileBy;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.testproject.sdk.drivers.ios.IOSDriver;
import io.testproject.sdk.drivers.web.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;


public class GBMChat {
    //private final String webToken = "MuCUh6SAKIinxk2RUBXQJDNbECzWrnVSeAqg30VcpKI1";
    private final String mobileToken = "CeGach4lVRwFsO4U35z9YQCx73W812iBltVMA5daUtI1";
    WebDriverWait wait;
    ChromeDriver chromeDriver;
    IOSDriver iosDriver;



    @BeforeClass
    public void setup() throws Exception {
        iosDriver = new IOSDriver(mobileToken,getCapabilities());
        chromeDriver = new ChromeDriver(mobileToken,new ChromeOptions());
        chromeDriver.manage().timeouts().implicitlyWait(15000, TimeUnit.MILLISECONDS);

    }

    public static DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.UDID, "00008030-000A095E3A69802E");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Soumya’s iPhone");
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.google.Maps");
        return capabilities;
    }

    @Test (priority=1)
    public void agentLogin() throws Exception {
        chromeDriver.get("https://agent-west.digital.nuance.com/");
        chromeDriver.findElement(By.id("username")).sendKeys("agt@tc.com");
        chromeDriver.findElement(By.id("password")).sendKeys("123");
        chromeDriver.findElement(By.className("submit")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("menu-select-status-icon")));
        chromeDriver.findElement(By.className("menu-select-status-icon")).click();
        chromeDriver.findElement(By.xpath("//*[@id=\"ember557-options-option-0\"]")).click();
        Assert.assertEquals(chromeDriver.findElement(By.className("status-label")).getText(),"Available");

        //Second Test
        iosDriver.findElement(MobileBy.AccessibilityId("Updates tab")).click();
        iosDriver.findElement(MobileBy.AccessibilityId("Messages")).click();
        iosDriver.findElement(MobileBy.AccessibilityId("STT QA GBM V2 STT Agent 1")).click();
        Assert.assertTrue(iosDriver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"STT QA GBM V2 STT Agent 1\"]")).isDisplayed());

        //Third Test
        By typeMessageBy;
        typeMessageBy = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND label == 'Type message' AND visible == 1");
        iosDriver.findElement(typeMessageBy).click();
        iosDriver.findElement(typeMessageBy).sendKeys("Hello");
        Assert.assertTrue(iosDriver.findElement(MobileBy.AccessibilityId("I am welcome message")).isDisplayed());

        //Four Test
        typeMessageBy = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND label == 'Type message' AND visible == 1");
        iosDriver.findElement(typeMessageBy).sendKeys("Live agent");
        Assert.assertTrue(iosDriver.findElement(MobileBy.AccessibilityId("To assist you better, please tell us your first name.")).isDisplayed());
        iosDriver.findElement(typeMessageBy).sendKeys("Sumeet");

        //Validate GBM chat received at Agent
        Assert.assertEquals(chromeDriver.findElement(By.xpath("//*[@id=\"ember1344\"]/div[1]/div[1]/div[2]")).getText(),"Google's Business Messages Chat");
        chromeDriver.findElement(By.className("ql-editor ql-blank")).sendKeys("Hi Sumeet");
        chromeDriver.findElement(By.className("send-message-button")).click();

        Assert.assertTrue(iosDriver.findElement(MobileBy.AccessibilityId("Hi Sumeet")).isDisplayed());
    }

    @Test (priority=2)
    public void startGBM() {
        iosDriver.findElement(MobileBy.AccessibilityId("Updates tab")).click();
        iosDriver.findElement(MobileBy.AccessibilityId("Messages")).click();
        iosDriver.findElement(MobileBy.AccessibilityId("STT QA GBM V2 STT Agent 1")).click();
        Assert.assertTrue(iosDriver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"STT QA GBM V2 STT Agent 1\"]")).isDisplayed());
    }

    @Test (priority=3)
    public void chatwithVA() {
        By typeMessageBy;
        typeMessageBy = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND label == 'Type message' AND visible == 1");
        iosDriver.findElement(typeMessageBy).click();
        iosDriver.findElement(typeMessageBy).sendKeys("Hello");
        Assert.assertTrue(iosDriver.findElement(MobileBy.AccessibilityId("I am welcome message")).isDisplayed());
    }

    @Test (priority=4)
    public void startChat() {
        By typeMessageBy;
        typeMessageBy = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND label == 'Type message' AND visible == 1");
        iosDriver.findElement(typeMessageBy).sendKeys("Live agent");
        Assert.assertTrue(iosDriver.findElement(MobileBy.AccessibilityId("To assist you better, please tell us your first name.")).isDisplayed());
        iosDriver.findElement(typeMessageBy).sendKeys("Sumeet");

        //Validate GBM chat received at Agent
        Assert.assertEquals(chromeDriver.findElement(By.xpath("//*[@id=\"ember1344\"]/div[1]/div[1]/div[2]")).getText(),"Google's Business Messages Chat");
        chromeDriver.findElement(By.className("ql-editor ql-blank")).sendKeys("Hi Sumeet");
        chromeDriver.findElement(By.className("send-message-button")).click();

        Assert.assertTrue(iosDriver.findElement(MobileBy.AccessibilityId("Hi Sumeet")).isDisplayed());

    }
}
