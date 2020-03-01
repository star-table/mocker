package org.nico.mocker.exception;

import org.nico.mocker.consts.RespCode;

public class MockerException extends Exception{

	private static final long serialVersionUID = -4285570151529511652L;

	private int code;
	
	private String message;
	
	private RespCode respCode;

	public MockerException(RespCode respCode) {
		this.code = respCode.getCode();
		this.message = respCode.getMsg();
		this.respCode = respCode;
	}
	
	public MockerException() {
		super();
	}

	public MockerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MockerException(String message, Throwable cause) {
		super(message, cause);
	}

	public MockerException(String message) {
		super(message);
	}

	public MockerException(Throwable cause) {
		super(cause);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RespCode getRespCode() {
        return respCode;
    }

    public void setRespCode(RespCode respCode) {
        this.respCode = respCode;
    }
	
}
