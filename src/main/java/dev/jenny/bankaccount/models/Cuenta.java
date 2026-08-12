package dev.jenny.bankaccount.models;

import java.util.Locale;

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
        validarCantidad(cantidad);
        saldo += cantidad;
        numeroConsignaciones++;
    }

    public void retirar(float cantidad) {
        validarCantidad(cantidad);
        if (cantidad > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro");
        }
        saldo -= cantidad;
        numeroRetiros++;
    }

    public void calcularInteresMensual() {
        saldo += saldo * (tasaAnual / 12) / 100;
    }

    public void extractoMensual() {
        saldo -= comisionMensual;
        calcularInteresMensual();
    }

    public String imprimir() {
        return String.format(Locale.US,
                "Saldo: %.2f, Consignaciones: %d, Retiros: %d, Comisión mensual: %.2f, Tasa anual: %.2f%%",
                saldo, numeroConsignaciones, numeroRetiros, comisionMensual, tasaAnual);
    }

    private void validarCantidad(float cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    }
}
