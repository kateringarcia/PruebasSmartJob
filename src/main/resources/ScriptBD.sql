------------------------------------------------------------------------------------------------
--- Create table usuarios
------------------------------------------------------------------------------------------------
CREATE TABLE usuarios (
    id number PRIMARY KEY,             	-- Campo UUID como clave primaria
    name VARCHAR(255),               	-- Nombre del usuario
    email VARCHAR(255) UNIQUE NOT NULL, -- Correo electrónico, debe ser único
    password VARCHAR(255) NOT NULL,  	-- Contraseña
    created TIMESTAMP,               	-- Fecha de creación
    modified TIMESTAMP,              	-- Fecha de modificación
    lastLogin TIMESTAMP,             	-- Último login
    token VARCHAR(255),              	-- Token de acceso
    isActive BOOLEAN DEFAULT TRUE    	-- Estado del usuario, por defecto activo
);

------------------------------------------------------------------------------------------------
--- Create table phones
------------------------------------------------------------------------------------------------
CREATE TABLE phones (
    id number PRIMARY KEY,             							-- ID único para cada teléfono
    number VARCHAR(20) NOT NULL,     								-- Número de teléfono
    citycode VARCHAR(10),            								-- Código de ciudad
    countrycode VARCHAR(10),         								-- Código de país
    user_id varchar,                    							-- Relación con la tabla `usuarios`
    FOREIGN KEY (user_id) REFERENCES usuarios(id) ON DELETE CASCADE -- Clave foránea a la tabla `usuarios`
);