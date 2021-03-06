package com.orasi.core.interfaces.impl.internal;

import com.orasi.core.interfaces.Element;
import com.orasi.utils.TestReporter;

/**
 * Processes the iface type into a useful class reference for wrapping WebElements.
 */
public final class ImplementedByProcessor {
    private ImplementedByProcessor() {
    }

    /**
     * Gets the wrapper class (descended from ElementImpl) for the annotation @ImplementedBy.
     *
     * @param iface iface to process for annotations
     * @param <T>   type of the wrapped class.
     * @return The class name of the class in question
     */
    @SuppressWarnings("rawtypes")
	public static <T> Class getWrapperClass(Class<T> iface) {
    	TestReporter.logTrace("Entering ImplementedByProcessor#getWrapperClass");
    	TestReporter.logTrace("Validate Element Interface has ImplementedBy annotation ");
        if (iface.isAnnotationPresent(ImplementedBy.class)) {
            ImplementedBy annotation = iface.getAnnotation(ImplementedBy.class);
            Class clazz = annotation.value();
            if (Element.class.isAssignableFrom(clazz)) {
            	TestReporter.logTrace("Validation successful");
            	TestReporter.logTrace("Exiting ImplementedByProcessor#getWrapperClass");
                return annotation.value();
            }
        }
        throw new UnsupportedOperationException("Apply @ImplementedBy interface to your Interface if you want to extend ");
    }

}