package it.FedeWar.Fractnet.math;

import java.math.BigDecimal;

public class BigComplex
{
	public BigDecimal r;
	public BigDecimal i;
	
	public BigComplex(double real, double imag) {
		r = new BigDecimal(real);
		i = new BigDecimal(imag);
	}
	
	/* La norma al quadrato */
	public BigDecimal sqrdNorm() {
		return r.pow(2).add(i.pow(2));
	}
	
	/* Norma del vettore */
	/*public BigDecimal norm() {
		return sqrdNorm().;
	}*/
	
	/*public void copy(BigComplex to) {
		to.r = new BigDecimal(r.toString());
		to.i = new BigDecimal(i.toString());
	}*/
	
	public String toString() {
		return r + " + " + i + " * i";
	}
	
	public void add(BigComplex a) {
		r = r.add(a.r);
		i = i.add(a.i);
	}
	
	public void sub(BigComplex a) {
		r = r.subtract(a.r);
		i = i.subtract(a.i);
	}
	
	public void mul(BigComplex a) {
		BigDecimal tr = r.multiply(a.r).subtract(i.multiply(a.i));
		BigDecimal ti = r.multiply(a.i).add(i.multiply(a.r));
		r = tr;
		i = ti;
	}
	
	public void pow(int n) {
		for(int i = 1; i < n; i++)
			mul(this);
	}
	
	public void copyFrom(Complex c) {
		r = new BigDecimal(c.r);
		i = new BigDecimal(c.i);
	}
	
	public void copyFrom(BigComplex c) {
		r = c.r;
		i = c.i;
	}
	
	public static BigComplex Parse(String str)
	{
		BigComplex C = new BigComplex(0, 0);
		int commaIndex = str.indexOf(';');
		C.r = new BigDecimal(Double.parseDouble(str.substring(0, commaIndex)));
		C.i = new BigDecimal(Double.parseDouble(str.substring(commaIndex + 1)));
		return C;
	}
}
