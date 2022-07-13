package com.ae.qa.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ae.qa.base.TestBase;
import com.ae.qa.util.Messages;

public class SysadminPolicyNew extends TestBase {
	public LoginPage loginpage = new LoginPage();
	public static WebDriverWait wait = new WebDriverWait(driver, 60);
	public InformationPage informationpage=new InformationPage();
	public SecurityQuestionsPage securityquestionpage=new SecurityQuestionsPage();
	@FindBy(xpath = "//span[(text()='Settings')]")
	WebElement settingsTab;
	@FindBy(xpath = "//a[text()='Sysadmin Policy']")
	WebElement sysadminPolicyTab;
	@FindBy(xpath = "//input[@id='attemptsInput']")
	WebElement noOfAttempts;
	@FindBy(xpath = "//button[@name='submit']")
	WebElement saveBtn;
	@FindBy(xpath = "//div/ul/li[1]/b")
	WebElement verifyAttempts;
	@FindBy(id = "popup-button-ok")
	WebElement okBtn;
	@FindBy(xpath = "//p[@class='alert-message-text']")
	WebElement PopUpMsg;
	@FindBy(name = "sign-out")
	WebElement signOutBtn;
	@FindBy(id = "uname")
	WebElement username;
	@FindBy(id = "pswd")
	WebElement password;
	@FindBy(xpath = "//span[@class='fa fa-caret-right']")
	WebElement pswdPolicyDrpDwn;
	@FindBy(id = "passwordHistoryInput")
	WebElement passwordHistoryInput;
	@FindBy(xpath = "//div[@class='card-body']/ul/li[3]/b")
	WebElement verifyHistory;
	@FindBy(id = "change-pswd")
	WebElement changePswd;
	@FindBy(xpath = "//div[@id='login-username']")
	WebElement UserNameTab;
	@FindBy(id = "oldpswd")
	WebElement oldPswd;
	@FindBy(id = "newpswd")
	WebElement newPswd;
	@FindBy(id = "confirmpswd")
	WebElement newConfirmPswd;
	@FindBy(xpath = "//button[text()='Change']")
	WebElement changeBtn;
	@FindBy(id = "minLengthInput")
	WebElement minLengthInput;
	@FindBy(id = "maxLengthInput")
	WebElement maxLengthInput;
	@FindBy(id = "uppercaseInput")
	WebElement uppercaseInput;
	@FindBy(id = "lowercaseInput")
	WebElement lowercaseInput;
	@FindBy(id = "specialCharInput")
	WebElement specialCharInput;
	@FindBy(id = "digitsInput")
	WebElement digitsInput;
	@FindBy(xpath="//div[@class='title-div']/h2")
	WebElement pageTitle;

	public SysadminPolicyNew() {
		// this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public void validatePswdComplexity(String minLength, String maxLength,String upperCase,String lowerCase,String specialChar,
			String digitsIp, String invalidPswd) throws Exception {
		loginpage.login("Sysadmin","Akola@123");
		Reporter.log("User log in Successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(sysadminPolicyTab));
		js.executeScript("arguments[0].click();", sysadminPolicyTab);
		System.out.println("Sysadmin Policy tab clicked");
		Thread.sleep(2000);
		pswdPolicyDrpDwn.click();
		// wait.until(ExpectedConditions.visibilityOf(noOfAttempts));
		//clear all criteria first
		for (int i = 1; i <= 2; i++) {
			minLengthInput.sendKeys(Keys.BACK_SPACE);
			maxLengthInput.sendKeys(Keys.BACK_SPACE);
			uppercaseInput.sendKeys(Keys.BACK_SPACE);
			lowercaseInput.sendKeys(Keys.BACK_SPACE);
			specialCharInput.sendKeys(Keys.BACK_SPACE);
			digitsInput.sendKeys(Keys.BACK_SPACE);
		}
		//set new criteria
		minLengthInput.sendKeys(minLength);
		maxLengthInput.sendKeys(maxLength);
		uppercaseInput.sendKeys("" + upperCase);
		lowercaseInput.sendKeys("" + lowerCase);
		specialCharInput.sendKeys("" + specialChar);
		digitsInput.sendKeys("" + digitsIp);
		Thread.sleep(2000);
		saveBtn.click();
		List<WebElement> elements = driver.findElements(By.xpath("//div[@class='card-body']/ul/ul/li"));
		ArrayList<String> Output_Attempts = new ArrayList<String>();
		for (WebElement element : elements) {
			String element_value = element.getText();
			// System.out.println(element_value);
			Output_Attempts.add(element_value);
		}
		System.out.println("Expected Arraylist is:" + Output_Attempts);
		okBtn.click();
		Thread.sleep(3000);
		String Actual_SuccessMsg = PopUpMsg.getText();
		String Expected_SuccessMsg = Messages.updatePasswordPolicy;
		System.out.println("Actual Message after password policy update:" + Actual_SuccessMsg);
		System.out.println("Expected Message after password policy update:" + Expected_SuccessMsg);
		Assert.assertEquals(Actual_SuccessMsg, Expected_SuccessMsg, "Password policy not updated.");
		System.out.println(
				"Till now we set the criteria for password and now we are verifying the same while changing pwd");
		wait.until(ExpectedConditions.visibilityOf(UserNameTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", UserNameTab);
		Thread.sleep(2000);
		changePswd.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		oldPswd.sendKeys(Password);
		Thread.sleep(3000);
		newPswd.sendKeys(invalidPswd);
		Thread.sleep(3000);
		newConfirmPswd.click();
		List<WebElement> cp_elements = driver.findElements(By.xpath("//div[@class='error']/div/ul/li"));
		wait.until(ExpectedConditions.visibilityOfAllElements(cp_elements));
		ArrayList<String> error_Attempts = new ArrayList<String>();
		for (WebElement cp_element : cp_elements) {
			String cpelement_value = cp_element.getText();
			// System.out.println(cpelement_value);
			error_Attempts.add(cpelement_value);
			Thread.sleep(2000);
		}
		System.out.println("Values in arrayList Before:" + error_Attempts);
		// WebElement cp_psw =
		// driver.findElement(By.xpath("//div[@class='error']/div"));
		// String pswd_criteria = cp_psw.getText();
		String pswd_criteria = "Password length should be between " + minLength + "-" + maxLength;
		error_Attempts.add(4, pswd_criteria);
		System.out.println("Values in arrayList After:" + error_Attempts);
		System.out.println(
				"Compare thye arraylist in confirm policy popup with arraylist having error msg on change password");
		if (Output_Attempts.equals(error_Attempts)) {
			Assert.assertTrue(true, "Validation done");
		} else {
			Assert.assertTrue(false, "Validation Fail");
		}
	}

}
