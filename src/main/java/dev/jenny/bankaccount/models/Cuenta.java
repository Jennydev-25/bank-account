package dev.jenny.bankaccount.models;

/**
 * Represents a generic bank account with the operations shared by every account
 * type
 */
public class Cuenta {

    protected float saldo;
    protected int numeroConsignaciones;
    protected int numeroRetiros;
    protected float tasaAnual;
    protected float comisionMensual;

    /**
     * Creates an account with the given balance and annual interest rate as a
     * percentage (3 means 3%)
     */
    public Cuenta(float saldo, float tasaAnual) {
        this.saldo = saldo;
        this.tasaAnual = tasaAnual;
        this.numeroConsignaciones = 0;
        this.numeroRetiros = 0;
        this.comisionMensual = 0f;
    }

    public float getSaldo() {
        return saldo;
    }

    public int getNumeroConsignaciones() {
        return numeroConsignaciones;
    }

    public int getNumeroRetiros() {
        return numeroRetiros;
    }

    public float getTasaAnual() {
        return tasaAnual;
    }

    public float getComisionMensual() {
        return comisionMensual;
    }

    public void consignar(float cantidad) {
        saldo += cantidad;
        numeroConsignaciones++;
    }
}
