package dk.bot.bwinservice.model;

import java.io.Serializable;

public class BWinTeam implements Serializable{

	private String name;
	private double odd;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getOdd() {
		return odd;
	}
	public void setOdd(double odd) {
		this.odd = odd;
	}
	
	
}
