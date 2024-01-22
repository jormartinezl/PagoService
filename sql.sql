USE pagos_db;

CREATE TABLE pago (id bigint not null auto_increment,
beneficiario varchar(255) not null, cantidad integer not null,
concepto varchar(255) not null, emisor varchar(255) not null,
estatus varchar(255) not null, monto decimal(20,2) not null,
primary key (id));
