create table iet_perfil (
	id_perfil int primary key, 
	nombre varchar(50) not null, 
	ruta_conexion varchar(255) not null, 
	grupo_ldap char(50) not null
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

