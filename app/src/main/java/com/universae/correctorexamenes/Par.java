package com.universae.correctorexamenes;

public class Par {
    private double numeroX;
    private double numeroY;

    public Par(double numeroX, double numeroY) {
	this.numeroX = numeroX;
	this.numeroY = numeroY;
    }

    public double getNumeroX() {
	return numeroX;
    }

    public double getNumeroY() {
	return numeroY;
    }

    @Override
    public String toString() {
	return "{" + numeroX + ", " + numeroY + "}";
    }
}
