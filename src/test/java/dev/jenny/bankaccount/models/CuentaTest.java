package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CuentaTest {

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new Cuenta(15000f, 3f);
    }

    @Test
    void testConstructor_ValidValues_ShouldInitializeFields() {
        assertThat(cuenta.getSaldo(), is(equalTo(15000f)));
        assertThat(cuenta.getTasaAnual(), is(equalTo(3f)));
        assertThat(cuenta.getNumeroConsignaciones(), is(equalTo(0)));
        assertThat(cuenta.getNumeroRetiros(), is(equalTo(0)));
        assertThat(cuenta.getComisionMensual(), is(equalTo(0f)));
    }

    @Test
    void testConsignar_ValidAmount_ShouldIncreaseSaldoAndCount() {
        cuenta.consignar(500f);
        assertThat(cuenta.getSaldo(), is(equalTo(15500f)));
        assertThat(cuenta.getNumeroConsignaciones(), is(equalTo(1)));
    }

    @ParameterizedTest(name = "consignar({0}) should throw exception")
    @ValueSource(floats = { 0f, -100f })
    void testConsignar_InvalidAmount_ShouldThrowException(float cantidad) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> cuenta.consignar(cantidad));
        assertThat(exception.getMessage(), is(equalTo("La cantidad debe ser mayor que cero")));
    }

    @Test
    void testRetirar_ValidAmount_ShouldDecreaseSaldoAndCount() {
        cuenta.retirar(500f);
        assertThat(cuenta.getSaldo(), is(equalTo(14500f)));
        assertThat(cuenta.getNumeroRetiros(), is(equalTo(1)));
    }

    @ParameterizedTest(name = "retirar({0}) should throw exception")
    @ValueSource(floats = { 0f, -100f })
    void testRetirar_InvalidAmount_ShouldThrowException(float cantidad) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> cuenta.retirar(cantidad));
        assertThat(exception.getMessage(), is(equalTo("La cantidad debe ser mayor que cero")));
    }
}
