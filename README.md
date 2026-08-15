# 🏦 Cuenta Bancaria en Java

> En esta cuenta bancaria, la herencia no llega con el testamento, llega con `extends`

Proyecto en **Java 21** con **Maven** que modela una cuenta bancaria genérica y dos variantes con comportamiento propio, cuenta de ahorros y cuenta corriente, mediante herencia y polimorfismo. Desarrollado siguiendo **TDD** (JUnit 5 + Hamcrest), con cobertura de tests medida con **JaCoCo**.

---

## 📑 Índice

- [Descripción](#-descripción)
- [Cómo reproducir el proyecto](#-cómo-reproducir-el-proyecto)
- [Estructura del repositorio](#-estructura-del-repositorio)
- [Tecnologías](#-tecnologías)
- [Autora](#-autora)

---

## 📋 Descripción

**Cuenta Bancaria** es un proyecto de Programación Orientada a Objetos (**POO**) que simula la gestión básica de una entidad financiera: `Account` es la clase padre, y `CheckingAccount` y `SavingsAccount` son sus clases hijas que heredan de ella el comportamiento común (consignar, retirar, calcular el interés, generar el extracto), reutilizando ese código y añadiendo solo la regla propia de cada tipo de cuenta.

La separación de responsabilidades se resuelve a nivel de clase, no de capas: cada modelo (`Account` y sus hijas) contiene toda su propia lógica de negocio y devuelve `String` en vez de imprimir, y `App` es la única clase que escribe en consola. Según el enunciado que tenemos, esto no es una aplicación con una fuente de datos externa (una base de datos, una API) que justifique separar Controller/Service/Repository ni aplicar MVC, así que no considero que esa arquitectura aplique aquí, y por eso lo he desarrollado de esta manera:

### Clase padre: Cuenta (`Account`)

**Atributos protegidos:**

- `balance` (float)
- `depositCount` (int, inicial 0)
- `withdrawalCount` (int, inicial 0)
- `annualRate` (float)
- `monthlyFee` (float, inicial 0)

**Constructor:**

Inicializa `balance` y `annualRate` a partir de los parámetros recibidos

**Métodos:**

- `deposit(amount)`: aumenta el saldo
- `withdraw(amount)`: reduce el saldo, sin permitir superar el saldo disponible
- `calculateMonthlyInterest()`: aplica el interés mensual derivado de la tasa anual
- `generateMonthlyStatement()`: resta la comisión mensual y calcula el interés (invocando a `calculateMonthlyInterest()`)
- `print()`: devuelve los valores de todos los atributos

### Clase hija: Cuenta de ahorros (`SavingsAccount`)

**Atributos propios:**

- `active` (boolean) — la cuenta se activa/desactiva automáticamente según si el saldo supera los $10.000
- `withdrawalsAtLastStatement` (int, privado) — guarda cuántos retiros había en el extracto anterior, para calcular la comisión solo sobre los retiros nuevos

**Métodos redefinidos:**

- `deposit` / `withdraw`: solo se ejecutan si la cuenta está activa, invocando al método heredado
- `generateMonthlyStatement()`: cobra $1.000 de comisión por cada retiro por encima de 4 desde el último extracto, y reevalúa si la cuenta sigue activa
- `print()` (redefinido): saldo, comisión mensual y número total de transacciones (consignaciones + retiros)

### Clase hija: Cuenta corriente (`CheckingAccount`)

**Atributo propio:**

- `overdraft` (float, inicial 0)

**Métodos redefinidos:**

- `withdraw`: permite retirar más saldo del disponible; el excedente queda como sobregiro
- `deposit`: aplica la cantidad consignada primero a cancelar el sobregiro pendiente; solo el sobrante aumenta el saldo
- `generateMonthlyStatement()`: invoca al método heredado sin lógica adicional
- `print()` (redefinido): saldo, comisión mensual, transacciones totales y sobregiro

El enunciado no concretaba del todo estos dos comportamientos, así que estas fueron las decisiones que tomé:

- `CheckingAccount.deposit()`: cuando hay sobregiro, la cantidad consignada se aplica primero a cancelarlo, y solo lo que sobra aumenta el saldo, para que un depósito no genere saldo de la nada
- `SavingsAccount.generateMonthlyStatement()`: la comisión por exceso de retiros se calcula sobre los retiros desde el último extracto, no sobre el total histórico de la cuenta, para que un mismo retiro no se cobre dos veces

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

## 🚀 Cómo reproducir el proyecto

### Requisitos previos

| Herramienta                                                   | Requisito                  | Guía de instalación                                                                                       |
| ------------------------------------------------------------- | -------------------------- | --------------------------------------------------------------------------------------------------------- |
| [JDK 21](https://www.oracle.com/java/technologies/downloads/) | Instalado y en el `PATH`   | [Ver guía](https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html)              |
| [Apache Maven](https://maven.apache.org/download.cgi)         | Instalado y en el `PATH`   | [Ver guía](https://maven.apache.org/install.html)                                                         |
| [Git](https://git-scm.com/downloads)                          | Para clonar el repositorio | [Ver guía](https://git-scm.com/book/es/v2/Inicio---Sobre-el-Control-de-Versiones-Instalaci%C3%B3n-de-Git) |

### Pasos

**1. Comprueba que tienes Java y Maven instalados** (si algún comando no se reconoce, instálalo desde los enlaces de _Requisitos previos_):

```bash
java --version
mvn --version
```

**2. Clona el repositorio:**

```bash
git clone https://github.com/Jennydev-25/bank-account.git
```

**3. Entra en la carpeta del proyecto:**

```bash
cd bank-account
```

**4. Ejecuta los tests** (compila y genera el reporte de cobertura de JaCoCo):

```bash
mvn test
```

El reporte de cobertura se genera en `target/site/jacoco/index.html`, que puedes abrir en el navegador.

**5. Ejecuta la aplicación** e imprime las tres cuentas demo por consola:

```bash
mvn exec:java
```

---

## 📁 Estructura del repositorio

```text
bank-account/
├── src/
│   ├── main/java/dev/jenny/bankaccount/
│   │   ├── App.java
│   │   └── models/
│   │       ├── Account.java
│   │       ├── CheckingAccount.java
│   │       └── SavingsAccount.java
│   └── test/java/dev/jenny/bankaccount/
│       ├── AppTest.java
│       └── models/
│           ├── AccountTest.java
│           ├── CheckingAccountTest.java
│           └── SavingsAccountTest.java
├── .gitignore
├── pom.xml
└── README.md
```

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
