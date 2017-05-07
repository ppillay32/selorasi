package com.orasi.core.by.angular.internal;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.orasi.core.by.angular.ByNG;
import com.orasi.core.by.angular.FindByNG;

import java.lang.reflect.Field;

public class NGAnnotations  {
	private Field field;

	/**
	 * @param field expected to be an element in a Page Object
	 */
	public NGAnnotations(Field field) {
		this.field = field;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return true if @CacheLookup annotation exists on a field
	 */
	public boolean isLookupCached() {
		return (field.getAnnotation(CacheLookup.class) != null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Looks for one of {@link org.openqa.selenium.support.FindBy},
	 * {@link org.openqa.selenium.support.FindBys} or
	 * {@link org.openqa.selenium.support.FindAll} field annotations. In case
	 * no annotaions provided for field, uses field name as 'id' or 'name'.
	 * @throws IllegalArgumentException when more than one annotation on a field provided
	 */
	public ByNG buildBy() {
		//assertValidAnnotations();

		ByNG ans = null;

	/*	FindBys findBys = field.getAnnotation(FindBys.class);
		if (findBys != null) {
			ans = buildByFromFindBys(findBys);
		}
*/
	
		FindByNG findByNG = field.getAnnotation(FindByNG.class);
		if (ans == null && findByNG != null) {
			ans = buildByNGFindBy(findByNG);
		}

		if (ans == null) {
			throw new IllegalArgumentException("Cannot determine how to locate element " + field);
		}

		return ans;
	}

	protected Field getField() {
		return field;
	}

	protected By buildByFromDefault() {
		return new ByIdOrName(field.getName());
	}

	protected void assertValidAnnotations() {
		FindBys findBys = field.getAnnotation(FindBys.class);
		
		FindBy findBy = field.getAnnotation(FindBy.class);
		if (findBys != null && findBy != null) {
			throw new IllegalArgumentException("If you use a '@FindBys' annotation, " +
					"you must not also use a '@FindBy' annotation");
		}
	}
	  protected ByNG buildByNGFindBy(FindByNG findByNG) {
		  //  HowNG how = findByNG.howNG();
		   // String using = findByNG.using();
		    String types = findByNG.toString().substring(findByNG.toString().indexOf("(")+1, findByNG.toString().length()-1);
		    String foundType = "";
		    for(String type : types.split(",")){
		    	if(type.length()-1 != type.indexOf("=")) {
		    		foundType = type;
		    		break;
		    	}
		    }
		    String how = foundType.split("=")[0];
		    String using = foundType.split("=")[1];
		    switch (how.toUpperCase().trim()) {
		      
		      case "NGBUTTONTEXT":
		        return ByNG.buttonText(using);

		      case "NGCONTROLLER":
		        return ByNG.controller(using);

		      case "NGMODEL":
		        return ByNG.model(using);

		      case "NGREPEAT":
		        return ByNG.repeater(using);

		      case "NGSHOW":
		        return ByNG.show(using);

		      default:
		        // Note that this shouldn't happen (eg, the above matches all
		        // possible values for the How enum)
		        throw new IllegalArgumentException("Cannot determine how to locate element ");
		    }
		  }

}

