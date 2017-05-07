package com.orasi.core.by.angular;

import java.lang.reflect.Field;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.orasi.core.by.angular.internal.ByAngular;
import com.orasi.core.by.angular.internal.NGAnnotations;
@SuppressWarnings("unused")
public class AngularElementLocator implements ElementLocator {
	private final WebDriver driver;
	private final ByNG by;	  
	private static ByAngular locator;


	public AngularElementLocator(final WebDriver driver, final Field field) {
		this(driver, new NGAnnotations(field));
	}
	public AngularElementLocator(final WebDriver driver, final NGAnnotations field) {		
		this.driver =  driver;
		this.by = field.buildBy();
		locator = new ByAngular(driver);
	}
	//@Override
	@Override
	public WebElement findElement() {
		RemoteWebElement element = null;
	/*	if (!by.ngModel().toString().isEmpty()){
			element = (RemoteWebElement) driver.findElement(ByAngular.model(by.ngModel()));
		}else if(!by.ngRepeater().toString().isEmpty()){
			element = (RemoteWebElement) driver.findElement(ByAngular.repeater(by.ngRepeater()));
		}else if(!by.ngButtonText().toString().isEmpty()){
			element = (RemoteWebElement) driver.findElement(ByAngular.buttonText(by.ngButtonText()));
		}else if(!by.ngController().toString().isEmpty()){
			element = (RemoteWebElement) driver.findElement(ByAngular.controller(by.ngController()));
		}else if(!by.ngShow().toString().isEmpty()){
			element = (RemoteWebElement) driver.findElement(ByAngular.show(by.ngShow()));
		}*/
		// return element.findElement(ng.model(ngLocator.ngModel()));
		return element;
	}

	@Override
	@SuppressWarnings({ })// @Override
	public List<WebElement> findElements() {

		List<WebElement> elements = null;
		/*if (!by.ngModel().toString().isEmpty()){
			elements = driver.findElements(ByAngular.model(by.ngModel()));
		}else if(!by.ngRepeater().toString().isEmpty()){
			elements = driver.findElements(ByAngular.repeater(by.ngRepeater()));
		}else if(!by.ngButtonText().toString().isEmpty()){
			elements = driver.findElements(ByAngular.buttonText(by.ngButtonText()));
		}else if(!by.ngController().toString().isEmpty()){
			elements = driver.findElements(ByAngular.controller(by.ngController()));
		}else if(!by.ngShow().toString().isEmpty()){
			elements =  driver.findElements(ByAngular.show(by.ngShow()));
		}*/

		return elements;
	}
}
