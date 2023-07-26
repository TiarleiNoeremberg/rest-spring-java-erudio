package br.com.tiarlei.math;

public class SimpleMath {
	
	public Double sum(Double numberOne, Double numberTwo
			) {
		return numberOne + numberTwo;
	}
	
	public Double sub(Double numberOne, Double numberTwo
			) {
		return numberOne - numberTwo;
	}
	
	public Double mul(Double numberOne, Double numberTwo
			) {
		return numberOne * numberTwo;
	}
	
	public Double div(Double numberOne, Double numberTwo
			) {
		return numberOne / numberTwo;
	}
	
	public Double ave(Double numberOne, Double numberTwo
			) {
		return (numberOne + numberTwo) / 2;
	}
	
	public Double sqr(Double numberOne) {
		return Math.sqrt(numberOne);
	}
}
