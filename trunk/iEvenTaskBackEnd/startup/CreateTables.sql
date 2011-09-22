create table iet_perfil (
	id_perfil int primary key, 
	nombre varchar(50) not null, 
	ruta_conexion varchar(255) not null, 
	grupo_ldap char(50) not null,
	eliminado datetime
);

create table iet_permiso (
	id_permiso int primary key, 
	descripcion varchar(255) not null
);

insert into iet_permiso values (1, 'Gestion de objetivos');
insert into iet_permiso values (2, 'Herramientas de administracion');

create table iet_permiso_perfil (
	id_permiso int not null, 
	id_perfil int not null
);

ALTER TABLE iet_permiso_perfil 
ADD CONSTRAINT fk_perp_permiso FOREIGN KEY (id_permiso) REFERENCES iet_permiso(id_permiso);

ALTER TABLE iet_permiso_perfil 
ADD CONSTRAINT fk_perp_perfil FOREIGN KEY (id_perfil) REFERENCES iet_perfil(id_perfil);

ALTER TABLE iet_permiso_perfil 
ADD CONSTRAINT pk_perp PRIMARY KEY (id_permiso, id_perfil);


create table iet_tarea (
	id_tarea int primary key,
	fecha_inicio datetime,
	fecha_fin datetime,
	horas_asignadas int,
	id_usuario varchar(255),
	fecha_creacion datetime,
	fecha_modificacion datetime,
	estado int,
	cumplimiento int,
	tipo_tarea int
);
create table iet_subtarea (
	id_tarea int not null,
	id_subtarea int not null
);
ALTER TABLE iet_subtarea 
ADD CONSTRAINT fk_subtarea_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_subtarea 
ADD CONSTRAINT fk_subtarea_subtarea FOREIGN KEY (id_subtarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_subtarea 
ADD CONSTRAINT pk_subtarea PRIMARY KEY (id_tarea, id_subtarea);

create table iet_comentario (
	id_comentario int not null,
	id_tarea int,
	comentario varchar(8000)
);

ALTER TABLE iet_comentario 
ADD CONSTRAINT fk_comentario_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_comentario 
ADD CONSTRAINT pk_comentario PRIMARY KEY (id_comentario);

create table iet_visualiza (
	id_tarea int not null,
	id_usuario varchar(255) not null
);

ALTER TABLE iet_visualiza 
ADD CONSTRAINT fk_visualiza_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_visualiza 
ADD CONSTRAINT pk_visualiza PRIMARY KEY (id_tarea, id_usuario);

create table iet_evento (
	id_evento int primary key, 
	nombre varchar(50) not null,
	periodicidad int not null,
	fecha_expiracion datetime,
	iteraciones int,
	ejecuciones int,
	eliminado datetime
);

create table iet_evento_ldap (
	id_evento int primary key,
	codigo varchar(50) not null
);

ALTER TABLE iet_evento_ldap 
ADD CONSTRAINT fk_evento_eventoldap FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

create table iet_evento_logs (
	id_evento int primary key,
	ruta varchar(255) not null,
	patron varchar(255) not null
);

ALTER TABLE iet_evento_logs 
ADD CONSTRAINT fk_evento_eventologs FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

create table iet_evento_archivos (
	id_evento int primary key,
	tipo int not null,
	ruta varchar(255) not null,
	modificacion datetime
);

ALTER TABLE iet_evento_archivos 
ADD CONSTRAINT fk_evento_eventoarchivos FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

create table iet_evento_servicios (
	id_evento int primary key,
	servidor varchar(255) not null,
	port varchar(10) not null
);

ALTER TABLE iet_evento_servicios 
ADD CONSTRAINT fk_evento_eventoservicios FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

create table iet_relacion (
	id_relacion int primary key,
	id_evento int not null,
	eliminado datetime
);

create table iet_relacion_nueva_tarea (
	id_relacion int primary key,
	id_user varchar(255) not null,
	nombre varchar(255) not null
);

create table iet_relacion_modifica_estado (
	id_relacion int primary key,
	id_tarea int not null,
	estado_inicial varchar(10) not null,
	estado_final varchar(10) not null
);

ALTER TABLE iet_relacion 
ADD CONSTRAINT fk_relacion_evento FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

ALTER TABLE iet_relacion_nueva_tarea 
ADD CONSTRAINT fk_relacion_nueva_tarea_relacion FOREIGN KEY (id_relacion) REFERENCES iet_relacion(id_relacion);

ALTER TABLE iet_relacion_modifica_estado 
ADD CONSTRAINT fk_relacion_modifica_estado_relacion FOREIGN KEY (id_relacion) REFERENCES iet_relacion(id_relacion);

