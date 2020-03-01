package org.nico.mocker.random;

public interface AbstractRandom {

	public String randomKey();
	
	public String randomString();
	
	public int randomInteger();
	
	public long randomLong();
	
	public double randomDouble();
	
	public Object randomDate();
	
	public boolean randomBoolean();
	
	public Object random();
}
