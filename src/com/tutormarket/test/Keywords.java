package com.tutormarket.test;


import static com.tutormarket.test.DriverScript.CONFIG;
import static com.tutormarket.test.GetOSName.OsUtils.isWindows;

import static com.tutormarket.test.DriverScript.APP_LOGS;
import static com.tutormarket.test.DriverScript.OR;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tutormarket.test.Constants;

public class Keywords {

	public WebDriver driver; 
	
	public String openBrowser(String object,String data){

        // Chrome Driver Path

          System.setProperty("webdriver.chrome.driver", "/Users/anandmahajan/Downloads/chromedriver 2");
        // Internet Explorer Path
        if (isWindows()) {
            File file = new File("IEDriver/IEDriver.exe");
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        }

        APP_LOGS.debug("Opening browser");
        if(data.equals("Mozilla"))
            driver=new FirefoxDriver();
        else if(data.equals("IE"))
            driver=new InternetExplorerDriver();
        else if(data.equals("Chrome"))
            driver=new ChromeDriver();

        long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        return Constants.KEYWORD_PASS;

    }
	
	
	//This function navigate browser to the URL
	 public String navigate(String object,String data){
	        APP_LOGS.debug("Navigating to URL");
	        try{
	            driver.navigate().to(data);
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+" -- Not able to navigate";
	        }
	        return Constants.KEYWORD_PASS;
	    }
	 
	 //This function clear Inputbox
	 public String ClearInputid(String object,String data){
		 try{
			 driver.findElement(By.id(OR.getProperty(object))).clear();
			 
		 }catch(Exception e){
			 return Constants.KEYWORD_FAIL+" Unable to clear "+e.getMessage();
		 }
		 return Constants.KEYWORD_PASS;
	 }
	 
	 public String ClearInput(String object,String data){
		 try{
			 driver.findElement(By.xpath(OR.getProperty(object))).clear();
			 
		 }catch(Exception e){
			 return Constants.KEYWORD_FAIL+" Unable to clear "+e.getMessage();
		 }
		 return Constants.KEYWORD_PASS;
	 }
	 
	 //This function write input in Text field
	 public  String writeInInput(String object,String data){
	        APP_LOGS.debug("Writing in text box");

	        try{
	            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

	        }
	        return Constants.KEYWORD_PASS;

	    }
	 
	 //This function is used for write value by ID 
	 public  String writeInInputId(String object,String data){
	        APP_LOGS.debug("Writing in text box");

	        try{
	            driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

	        }
	        return Constants.KEYWORD_PASS;

	    }
	 
	 
	 //this function is used for Click link
	 
	 public String clickLink(String object,String data){
	        APP_LOGS.debug("Clicking on link ");
	        try{
	            driver.findElement(By.xpath(OR.getProperty(object))).click();
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
	        }

	        return Constants.KEYWORD_PASS;
	    }
	  
	    	

	 	//this function is used for click button xpath 
	    public  String clickButton(String object,String data){
	        APP_LOGS.debug("Clicking on Button");
	        try{
	            driver.findElement(By.xpath(OR.getProperty(object))).click();
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
	        }
	        return Constants.KEYWORD_PASS;
	    }
	    
	    
	    //this function is used for Click button by ID
	    public  String clickButtonId(String object,String data){
	    	APP_LOGS.debug("Clicking on Button");
	    	try{
	    		driver.findElement(By.id(OR.getProperty(object))).click();
	    	}catch(Exception e){
	    		return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
	    	}
	    	
	    	
	    	return Constants.KEYWORD_PASS;
	    }

	    //This function is used for Wait
	    public String pause(String object, String data) throws NumberFormatException, InterruptedException{
	        long time = (long)Double.parseDouble(object);
	        Thread.sleep(time*1000L);
	        return Constants.KEYWORD_PASS;
	    }

	    
	    
	    
	    //This function is used for verify Text
	    public String verifyText(String object, String data){
	        APP_LOGS.debug("Verifying the text");
	        try{
	            String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
	            System.out.println(actual);
	            String expected=data;

	            if(actual.equals(expected))
	                return Constants.KEYWORD_PASS;
	            else
	                return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
	        }

	    }
	    
	    
	    public  String closeBrowser(String object, String data){
	        APP_LOGS.debug("Closing the browser");
	        try{
	            driver.close();
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
	        }
	        return Constants.KEYWORD_PASS;

	    }
	    
	    
	    //This function us used for Add image 
	    public  String Addprofileimage(String object,String data){
	        APP_LOGS.debug("Adding/change image ");

	        try{
	            driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
	        }catch(Exception e){
	            return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

	        }
	        return Constants.KEYWORD_PASS;

	    }
	    
	    
	    //This function is used for Select value from dropdown
	    public String SelectFromDropdown(String object,String data){
	    	 APP_LOGS.debug("Select value form dropdown menu");
	    	try{
	    		Select value= new Select(driver.findElement(By.id(OR.getProperty(object))));
	    		value.selectByVisibleText(data);
	    	}catch(Exception e){
	    		return Constants.KEYWORD_FAIL+" Unable to select value form dropdown "+e.getMessage();
	    	}
	    	return Constants.KEYWORD_PASS;
	    }
	    
	    
	    //Scroll Webpage till element not visible
	    public String ScrolltoElement(String object,String data){
	    	try{
	    		WebElement element=driver.findElement(By.id(OR.getProperty(object)));
	    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	    	   }catch(Exception e){
	    		return Constants.KEYWORD_FAIL+"Unable to find Element to scroll"+e.getMessage();
	    	}
	    	return Constants.KEYWORD_PASS;
	    }
	    
	    
	    //Scroll webpage till End 
	    public String scrollingToBottomofAPage(String object,String data){
	    	try{
	    		 ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	    	}catch(Exception e){
	    		return Constants.KEYWORD_FAIL+"Unable to fine Element to scroll"+e.getMessage();
	    	}
	    	return Constants.KEYWORD_PASS;
	    }
	    
	    
	    public String maximizeBrowser(String object,String data){
	    	try{
	    		driver.manage().window().maximize();
	    	}catch(Exception e){
	    		return Constants.KEYWORD_FAIL+"Unable to maximize browser"+e.getMessage();
	    	}
	    	return Constants.KEYWORD_PASS;
	    }
	    
	    
	    
	    ///This code is related to direct login functionality 
	    
	    
	    public void waitbyxpath(WebDriver driver, String Locator) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Locator)));
		}

		public void waitbyid(WebDriver driver, String Locator) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(Locator)));
		}
		
		public String loginforpremium(String object,String data){
			try{
			driver.findElement(By.xpath(".//*[@id='menu']/nav/ul[2]/li/a[2]")).click();
			driver.findElement(By.xpath(".//*[@id='logins-login']/div/div/div[2]/p[4]/button")).click();
			waitbyid(driver, "form-btn");
			driver.findElement(By.id("logins-email-field")).sendKeys("anoop@vivino.com");
			driver.findElement(By.id("password")).sendKeys("anoop");
			driver.findElement(By.id("form-btn")).click();
			waitbyxpath(driver, ".//*[@id='menu']/nav/ul[2]/li/a/div");
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+"Unable to login with premium user"+e.getMessage();	
			}
			return Constants.KEYWORD_PASS;
			}
			 
			public String loginforfree(String object,String data){
				try{
				driver.findElement(By.xpath(".//*[@id='menu']/nav/ul[2]/li/a[2]")).click();
				driver.findElement(By.xpath(".//*[@id='logins-login']/div/div/div[2]/p[4]/button")).click();
				waitbyid(driver, "form-btn");
				driver.findElement(By.id("logins-email-field")).sendKeys("sittu3@vivino.com");
				driver.findElement(By.id("password")).sendKeys("12345");
				driver.findElement(By.id("form-btn")).click();
				waitbyxpath(driver, ".//*[@id='menu']/nav/ul[2]/li/a/div");
				}catch(Exception e){
					return Constants.KEYWORD_FAIL+"Unable to login with free user"+e.getMessage();	
				}
				return Constants.KEYWORD_PASS;
				}
	    
	    //not a keyword
			
			public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
		        // take screen shots
		        if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
		            // capturescreen

		            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));

		        }else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
		            // capture screenshot
		            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		        }
		    }
	    

}
