package WSR.WSRTesting;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Resources.Base;
import Resources.ExtentReport;

public class Listeners extends Base implements ITestListener {

	ExtentTest test;
	ExtentReports extent = ExtentReport.getReportObject();

	public void onTestStart(ITestResult result) {
		
		test = extent.createTest(result.getMethod().getMethodName());
	}

	public void onTestFailure(ITestResult result) {
		test.fail("Test failure:" + result.getThrowable());
		String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(screenshot);
		extent.flush();
		driver.quit();
		
	}

	public void onTestSuccess(ITestResult result) {
		extent.flush();
		driver.quit();
	}
}
