package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
