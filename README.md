# 🏦 Cuenta Bancaria en Java

> En esta cuenta bancaria, la herencia no llega con el testamento, llega con `extends`

Proyecto en **Java 21** con **Maven** que modela una cuenta bancaria genérica y dos variantes con comportamiento propio, cuenta de ahorros y cuenta corriente, mediante herencia y polimorfismo. Desarrollado siguiendo **TDD** (JUnit 5 + Hamcrest), con cobertura de tests medida con **JaCoCo**.

---

## 📑 Índice

- [Descripción](#-descripción)
- [Tecnologías](#-tecnologías)
- [Autora](#-autora)

---

## 📋 Descripción

**Cuenta Bancaria** es un proyecto de Programación Orientada a Objetos (**POO**) que simula la gestión básica de una entidad financiera: `Cuenta` es la clase padre, y `CuentaAhorros` y `CuentaCorriente` son sus clases hijas que heredan de ella el comportamiento común (consignar, retirar, calcular el interés, generar el extracto), reutilizando ese código y añadiendo solo la regla propia de cada tipo de cuenta.

<details>
<summary><strong>Enunciado completo</strong></summary>

Desarrollar un programa que modele una cuenta bancaria que tiene los siguientes atributos, que deben ser de acceso protegido:

- Saldo, de tipo float.
- Número de consignaciones con valor inicial cero, de tipo int.
- Número de retiros con valor inicial cero, de tipo int.
- Tasa anual (porcentaje), de tipo float.
- Comisión mensual con valor inicial cero, de tipo float.

La clase Cuenta tiene un constructor que inicializa los atributos saldo y tasa anual con valores pasados como parámetros. La clase Cuenta tiene los siguientes métodos:

- Consignar una cantidad de dinero en la cuenta actualizando su saldo.
- Retirar una cantidad de dinero en la cuenta actualizando su saldo. El valor a retirar no debe superar el saldo.
- Calcular el interés mensual de la cuenta y actualiza el saldo correspondiente.
- Extracto mensual: actualiza el saldo restándole la comisión mensual y calculando el interés mensual correspondiente (invoca el método anterior).
- Imprimir: retorno los valores de los atributos.

La clase Cuenta tiene dos clases hijas:

**Cuenta de ahorros:** posee un atributo para determinar si la cuenta de ahorros está activa (tipo boolean). Si el saldo es menor a $10000, la cuenta está inactiva, en caso contrario se considera activa. Los siguientes métodos se redefinen:

- Consignar: se puede consignar dinero si la cuenta está activa. Debe invocar al método heredado.
- Retirar: es posible retirar dinero si la cuenta está activa. Debe invocar al método heredado.
- Extracto mensual: si el número de retiros es mayor que 4, por cada retiro adicional, se cobra $1000 como comisión mensual. Al generar el extracto, se determina si la cuenta está activa o no con el saldo.
- Un nuevo método imprimir que retorna el saldo de la cuenta, la comisión mensual y el número de transacciones realizadas (suma de cantidad de consignaciones y retiros).

**Cuenta corriente:** posee un atributo de sobregiro, el cual se inicializa en cero. Se redefinen los siguientes métodos:

- Retirar: se retira dinero de la cuenta actualizando su saldo. Se puede retirar dinero superior al saldo. El dinero que se debe queda como sobregiro.
- Consignar: invoca al método heredado. Si hay sobregiro, la cantidad consignada reduce el sobregiro.
- Extracto mensual: invoca al método heredado.
- Un nuevo método imprimir que retorna el saldo de la cuenta, la comisión mensual, el número de transacciones realizadas (suma de cantidad de consignaciones y retiros) y el valor de sobregiro.

**Requisitos:**

- Diagrama UML de clases
- Tests unitarios obligatorios (cobertura mínima 70%)

**Entregables:**

- Repositorio de Github
- Captura de pantalla del diagrama de clase o enlace público al archivo
- Captura de pantalla de la sección testing de VSCode que muestre que se ha cumplido con la cobertura de tests

</details>

---

## 🛠️ Tecnologías

- **[Java 21](https://www.oracle.com/java/technologies/downloads/)** — Lenguaje de programación del proyecto
- **[Apache Maven](https://maven.apache.org/)** — Gestor de dependencias y construcción del proyecto
- **[JUnit 5](https://junit.org/junit5/)** — Framework de tests unitarios
- **[Hamcrest](https://hamcrest.org/JavaHamcrest/)** — Librería de matchers para aserciones legibles
- **[JaCoCo](https://www.jacoco.org/jacoco/)** — Medición de la cobertura de tests
- **[Visual Studio Code](https://code.visualstudio.com/)** — Editor usado para desarrollar y gestionar el proyecto
- **[Markdown](https://www.markdownguide.org/)** — Lenguaje de marcado para el README
- **[Git](https://git-scm.com/)** / **[GitHub](https://github.com/)** — Control de versiones y alojamiento del proyecto

---

## 👩‍💻 Autora

**[Jenny Sánchez Requejo](https://github.com/Jennydev-25)**

[Volver arriba](#-cuenta-bancaria-en-java)
