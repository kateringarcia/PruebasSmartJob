# Proyecto de Creación de Usuarios

Este es un proyecto de ejemplo para la creación de usuarios con los campos nombre, correo, contraseña y un listado de teléfonos, utilizando Spring Boot. La API permite registrar un usuario, y realizar verificaciones de formato de correo y contraseña.

## Prerrequisitos

Antes de empezar, asegurarse de tener lo siguiente instalado:

- **Java 17** o superior.
- **Maven** para construir el proyecto.
- **Postman** o cualquier otra herramienta para probar la API.
- **Git** para clonar el repositorio.
- IDE como IntelliJ IDEA, Eclipse o Visual Studio Code
- Conexión a Internet para descargar dependencias

##Descripción

##Características principales:

-Endpoint de registro de usuario: Permite crear un usuario proporcionando los campos nombre, correo, contraseña y un listado de teléfonos.
-Validación de formato de correo electrónico: Asegura que el correo tenga un formato válido.
-Validación de contraseña: Verifica que la contraseña cumpla con una expresión regular configurable.
-Generación de token JWT: Se genera un token JWT único para el usuario en el momento del registro.
-Base de datos en memoria H2: Utiliza H2 para almacenar usuarios de manera temporal.
-Respuestas en formato JSON: Todos los datos y mensajes de error se retornan en formato JSON.

##Respuestas en caso de éxito:
-En caso de éxito al registrar un usuario, la respuesta incluye el ID del usuario, las fechas de creación y última modificación, el último ingreso, el token JWT, y si el usuario sigue activo.

##Respuestas en caso de error:
-Si el correo ya está registrado, se devuelve un mensaje de error.
-Si el correo o la contraseña no cumplen con el formato esperado, se devuelve un mensaje de error adecuado.

##Tecnologías

Este proyecto está basado en Spring Boot y utiliza las siguientes tecnologías:

-Spring Web para exponer la API RESTful.
-Spring Data JPA para la persistencia en la base de datos.
-H2 Database para almacenamiento en memoria.
-JWT para generación de tokens de acceso.
-JUnit para pruebas unitarias.
-ModelMapper para mapear objetos de request a entidades.

## Endpoints
### POST /api/usuarios

- Este endpoint permite registrar un nuevo usuario.
- **Request Body**:
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [{"number": "1234567", "citycode": "1", "countrycode": "57"}]
}
- **Response (en caso de éxito)**:
{
  "id": "UUID",
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "countrycode": "57"
    }
  ],
  "created": "2025-04-14T00:00:00",
  "modified": "2025-04-14T00:00:00",
  "last_login": "2025-04-14T00:00:00",
  "token": "jwt-token",
  "isactive": true
}

- **Response (en caso de error)**:
Si ocurre un error, por ejemplo, si el correo ya está registrado, la respuesta será:

{
  "mensaje": "El correo ya registrado"
}

##Validaciones
- **Correo electrónico**:
El correo debe seguir el formato: ^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$

- **Contraseña**
La contraseña debe cumplir con la siguiente expresión regular: ^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d]{8,}$

### Instrucciones para ejecutar el proyecto

## Clonar el proyecto
-git clone https://github.com/tu-usuario/PruebasSmartJob.git
-cd PruebasSmartJob
-git checkout master

##Construir el proyecto con Maven
-mvn clean install

##Ejecutar el proyecto
-mvn spring-boot:run
La aplicación se ejecutará en el puerto 8080 de manera predeterminada.

Una vez que el proyecto esté en ejecución, se puede: 

###Acceder al swagger de la API en la siguiente URL:
http://localhost:8080/swagger-ui/index.html#/Usuarios/register

### Pruebas de la API
probar la API utilizando herramientas como Postman utilizando el siguiente curl:

curl --location 'http://localhost:8080/api/users' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Lucía Fernández",
  "email": "Kath@fernandez.cl",
  "password": "Aa123456$",
  "phones": [
    {
      "number": "9876543",
      "citycode": "1",
      "contrycode": "56"
    }
  ]
}
'

###Base de Datos en Memoria
La aplicación utiliza H2 Database en memoria para almacenar los usuarios. Se puede acceder a la consola de H2 a través de la siguiente URL:

http://localhost:8080/h2-console

##Credenciales:
-URL: jdbc:h2:mem:testdb
-Usuario: sa
-password:

###Pruebas Unitarias
Se incluyen pruebas unitarias para validar el comportamiento del registro de usuarios. Se pueden ejecutar con el siguiente comando:
-mvn test