# PagoService API

Esta API RESTful permite gestionar pagos a través de distintos endpoints.

## Funcionalidades:

> - Generar nuevos pagos.
> - Buscar pagos individuales o consultar la lista completa.
> - Actualizar el estatus de un pago.

## Endpoints:

> - POST /api/v1/pagos: Genera un nuevo pago.
> - GET /api/v1/pagos/{id}: Busca un pago por su identificador.
> - GET /api/v1/pagos: Obtiene la lista de todos los pagos.
> - PATCH /api/v1/pagos/{id}/estatus: Actualiza el estatus de un pago.

## Cómo ejecutar el proyecto:

1. Requisitos:

- Java 17 
- Spring boot
- MySQL 
- Kafka 
- Docker

2. Instrucciones:

Clona el repositorio:

```
git clone https://github.com/jormartinezl/prueba-tecnica-pago.git
```

Entra dentro del proyecto:

```
cd prueba-tecnica-pago
```

Levartar imagenes

```docker
Empaqueta el proyecto sin las pruebas unitarias
./mvnw clean package -DskipTests

Genera la imagen en Docker
docker-compose build java_app

Ejecuta el docker-compose.yml del proyecto
docker-compose up
```

Insertar en base datos

```sql
#Entra a la consola de mysql
docker exec -it mysql2 mysql -uuser -p

Introducir la contraseña: password123#

#Selecciona la base de datos
USE pagos_db;

#Crea la tabla pago
CREATE TABLE pago (
id bigint not null auto_increment,
beneficiario varchar(255) not null,
cantidad integer not null,
concepto varchar(255) not null,
emisor varchar(255) not null,
estatus varchar(255) not null,
monto decimal(20,2) not null,
primary key (id));
```

Tecnologías utilizadas:

- Spring Boot
- Spring Web
- Spring Data JPA
- Swagger UI
- Kafka
- Docker
- Junit
- Mokito