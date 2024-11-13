**Challenge Literalura - Proyecto Java**
- Descripción:
  - Este repositorio contiene el proyecto Challenge Literalura, desarrollado en Java con el objetivo de gestionar datos relacionados con libros y autores. El proyecto emplea la API de Gutendex para cargar información de libros y almacenarla en una base de datos PostgreSQL, permitiendo realizar consultas sobre dichos datos.

- Características
  - Implementación en Java con el framework Spring Boot.
  - Integración con PostgreSQL para la persistencia de datos.
  - Uso de la API de Gutendex para obtener información sobre libros y autores.
  - Aplicación de consola que permite al usuario interactuar con los datos.

- Requisitos del Entorno
  - Java 11 o superior
  - Maven para la gestión de dependencias y construcción del proyecto.
  - PostgreSQL para la base de datos.
  - Spring Boot como framework de desarrollo.

- Instalación y Configuración
  - Clonar el repositorio:

```bash
git clone https://github.com/ChayChaio/challenge-literalura.git
```

  - Configurar la base de datos PostgreSQL:
    - Crear una base de datos local llamada literalura_db.
    - Actualizar el archivo application.properties con las credenciales de acceso a la base de datos.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

  - Compilar y ejecutar el proyecto:

```bash
mvn clean install
mvn spring-boot:run
```

- Estructura del Proyecto
  - src/main/java/ar/com/literalura: Contiene los controladores, servicios y modelos.
  - src/main/resources: Archivos de configuración y recursos estáticos.
  - src/test/java/ar/com/literalura: Archivos para pruebas (si se implementan en el futuro).

- Tecnologías Utilizadas
  - Java
  - Spring Boot
  - PostgreSQL
  - Maven

- Contribuciones
  - Si deseas contribuir a este proyecto, por favor realiza un fork del repositorio, crea una rama con tu función o corrección, y envía un pull request para revisión.

Autor
Este proyecto fue desarrollado por ChayChaio como parte de un desafío de programación.
