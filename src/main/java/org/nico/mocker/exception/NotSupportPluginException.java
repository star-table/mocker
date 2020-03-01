package org.nico.mocker.exception;

public class NotSupportPluginException extends MockerException{

	private static final long serialVersionUID = -4549957794122168156L;

	public NotSupportPluginException() {
		super("Not support plugin");
	}

	public NotSupportPluginException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotSupportPluginException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSupportPluginException(String message) {
		super(message);
	}

	public NotSupportPluginException(Throwable cause) {
		super(cause);
	}

}
