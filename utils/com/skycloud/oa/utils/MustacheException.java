//
// $Id: MustacheException.java 2 2010-10-21 06:32:37Z samskivert $

package com.skycloud.oa.utils;

/**
 * An exception thrown when an error occurs parsing or executing a Mustache template.
 */
public class MustacheException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MustacheException (String message) {
        super(message);
    }

    public MustacheException (Throwable cause) {
        super(cause);
    }

    public MustacheException (String message, Throwable cause) {
        super(message, cause);
    }
}
