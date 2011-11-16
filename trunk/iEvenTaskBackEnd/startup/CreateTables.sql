------------------------------------------------------------
---                  Creates                             ---
------------------------------------------------------------
create table iet_perfil (
	id_perfil int primary key, 
	grupo_ldap char(150) unique,
	eliminado datetime
);

create table iet_permiso (
	id_permiso int primary key, 
	descripcion varchar(255) not null
);

create table iet_permiso_perfil (
	id_permiso int not null, 
	id_perfil int not null
);

create table iet_tarea (
	id_tarea int primary key,
	nombre_tarea varchar(255),
	fecha_comienzo datetime,
	fecha_fin datetime,
	--duracion varchar(255),
    descripcion varchar(255),
	id_usuario varchar(255),
	horas_asignadas int,
	fecha_creacion datetime,
	fecha_modificacion datetime,
	estado varchar(10),
	cumplimiento int,
	tipo_tarea int,
	id_objetivo int,
	peso int,
	eliminado datetime,
	id_tarea_padre int
);

create table iet_subtarea (
	id_tarea int not null,
	id_subtarea int not null
);

create table iet_comentario (
	id_comentario int not null,
	id_tarea int not null,
	comentario varchar(8000) not null,
	id_usuario varchar(255) not null,
	fecha datetime not null
);

create table iet_visualiza (
	id_tarea int not null,
	id_usuario varchar(255) not null
);

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

create table iet_evento_logs (
	id_evento int primary key,
	ruta varchar(255) not null,
	patron varchar(255) not null
);

create table iet_evento_archivos (
	id_evento int primary key,
	tipo int not null,
	ruta varchar(255) not null,
	modificacion datetime
);

create table iet_evento_servicios (
	id_evento int primary key,
	servidor varchar(255) not null,
	port varchar(10) not null
);

create table iet_relacion (
	id_relacion int primary key,
	id_evento int not null,
	eliminado datetime
);

create table iet_relacion_nueva_tarea (
	id_relacion int primary key,
	id_user varchar(255) not null,
	nombre varchar(255) not null,
	id_tarea int 
);

create table iet_relacion_modifica_estado (
	id_relacion int primary key,
	estado_inicial varchar(10) not null,
	estado_final varchar(10) not null
);

create table iet_relacion_modifica_estado_tarea (
	id_relacion int not null,
	id_tarea int not null,
);

create table iet_objetivo (
	id_objetivo int primary key,
	tipo_objetivo varchar(20),
	nombre_objetivo varchar(255),
	descripcion varchar(255),
	escala_medicion varchar(255),
	fecha_finalizacion datetime,
	ponderacion int,
	id_usuario_asignado varchar(255),
	eliminado datetime
);

create table iet_visible (
	id_visible varchar(1000) primary key,
	id_tarea int not null,
	id_usuario varchar(255)
);

create table iet_obj_visible (
	id_obj_visible varchar(1000) primary key,
	id_objetivo int not null,
	id_usuario varchar(255)
);

create table iet_rel_visible (
	id_rel_visible varchar(1000) primary key,
	id_relacion int not null,
	id_usuario varchar(255)
);

------------------------------------------------------------
---                  Restrictions                        ---
------------------------------------------------------------

ALTER TABLE iet_permiso_perfil 
ADD CONSTRAINT fk_perp_permiso FOREIGN KEY (id_permiso) REFERENCES iet_permiso(id_permiso);

ALTER TABLE iet_permiso_perfil 
ADD CONSTRAINT fk_perp_perfil FOREIGN KEY (id_perfil) REFERENCES iet_perfil(id_perfil);

ALTER TABLE iet_permiso_perfil 
ADD CONSTRAINT pk_perp PRIMARY KEY (id_permiso, id_perfil);

ALTER TABLE iet_tarea 
ADD CONSTRAINT fk_tarea_objetivo FOREIGN KEY (id_objetivo) REFERENCES iet_objetivo(id_objetivo);

ALTER TABLE iet_subtarea 
ADD CONSTRAINT fk_subtarea_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_subtarea 
ADD CONSTRAINT fk_subtarea_subtarea FOREIGN KEY (id_subtarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_subtarea 
ADD CONSTRAINT pk_subtarea PRIMARY KEY (id_tarea, id_subtarea);

ALTER TABLE iet_comentario 
ADD CONSTRAINT fk_comentario_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_comentario 
ADD CONSTRAINT pk_comentario PRIMARY KEY (id_comentario);

ALTER TABLE iet_visualiza 
ADD CONSTRAINT fk_visualiza_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_visualiza 
ADD CONSTRAINT pk_visualiza PRIMARY KEY (id_tarea, id_usuario);

ALTER TABLE iet_evento_ldap 
ADD CONSTRAINT fk_evento_eventoldap FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

ALTER TABLE iet_evento_logs 
ADD CONSTRAINT fk_evento_eventologs FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

ALTER TABLE iet_evento_archivos 
ADD CONSTRAINT fk_evento_eventoarchivos FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

ALTER TABLE iet_evento_servicios 
ADD CONSTRAINT fk_evento_eventoservicios FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

ALTER TABLE iet_relacion 
ADD CONSTRAINT fk_relacion_evento FOREIGN KEY (id_evento) REFERENCES iet_evento(id_evento);

ALTER TABLE iet_relacion_nueva_tarea 
ADD CONSTRAINT fk_relacion_nueva_tarea_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_relacion_nueva_tarea 
ADD CONSTRAINT fk_relacion_nueva_tarea_relacion FOREIGN KEY (id_relacion) REFERENCES iet_relacion(id_relacion);

ALTER TABLE iet_relacion_modifica_estado 
ADD CONSTRAINT fk_relacion_modifica_estado_relacion FOREIGN KEY (id_relacion) REFERENCES iet_relacion(id_relacion);

ALTER TABLE iet_relacion_modifica_estado_tarea 
ADD CONSTRAINT fk_relacion_modifica_estado_tarea_relacion FOREIGN KEY (id_relacion) REFERENCES iet_relacion_modifica_estado(id_relacion);

ALTER TABLE iet_relacion_modifica_estado_tarea 
ADD CONSTRAINT fk_relacion_modifica_estado_tarea_tarea FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_relacion_modifica_estado_tarea 
ADD CONSTRAINT pk_relacion_modifica_estado_tarea PRIMARY KEY (id_relacion, id_tarea);

ALTER TABLE iet_visible 
ADD CONSTRAINT fk_relacion_tarea_visible FOREIGN KEY (id_tarea) REFERENCES iet_tarea(id_tarea);

ALTER TABLE iet_obj_visible 
ADD CONSTRAINT fk_relacion_objetivo_visible FOREIGN KEY (id_objetivo) REFERENCES iet_objetivo(id_objetivo);

ALTER TABLE iet_rel_visible 
ADD CONSTRAINT fk_relacion_relacion_visible FOREIGN KEY (id_relacion) REFERENCES iet_relacion(id_relacion);

------------------------------------------------------------
---                       Data                           ---
------------------------------------------------------------

insert into iet_perfil values (1, 'G_ieventask_administrador', null);

insert into iet_permiso values (1, 'Gestionar perfiles');
insert into iet_permiso values (2, 'Gestionar eventos');
insert into iet_permiso values (3, 'Crear y modificar tareas');
insert into iet_permiso values (4, 'Crear y modificar tareas no asignadas');
insert into iet_permiso values (5, 'Crear y modificar objetivos');
insert into iet_permiso values (6, 'Crear y modificar objetivos no asignadas');
insert into iet_permiso values (7, 'Agregar comentarios');
insert into iet_permiso values (8, 'Agregar comentarios no asignadas');
insert into iet_permiso values (9, 'Asignar Tareas');
insert into iet_permiso values (10, 'Reasignar Tareas');
insert into iet_permiso values (11, 'Subdividir Tareas');
insert into iet_permiso values (12, 'Relacionar con objetivo');

------------------------------------------------------------
---              CARGA DE PERFILES                       ---
------------------------------------------------------------

INSERT INTO dbo.iet_perfil
(id_perfil, grupo_ldap, eliminado)
VALUES
(-2045373390, 'G_ieventask_director', NULL);

INSERT INTO dbo.iet_perfil
(id_perfil, grupo_ldap, eliminado)
VALUES
(-1932950264, 'G_ieventask_gerente_lider', NULL);

INSERT INTO dbo.iet_perfil
(id_perfil, grupo_ldap, eliminado)
VALUES
(-1932893422, 'G_ieventask_analista', NULL);

------------------------------------------------------------
---            CARGA DE PERMISOS PERFILES                ---
------------------------------------------------------------
INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(1, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(2, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(2, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(2, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(2, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(3, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(3, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(3, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(3, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(4, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(4, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(4, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(4, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(5, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(5, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(5, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(6, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(6, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(6, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(7, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(7, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(7, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(7, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(8, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(8, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(8, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(8, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(9, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(9, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(9, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(9, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(10, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(10, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(10, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(10, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(11, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(11, -1932950264);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(11, 1);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(12, -2045373390);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(12, -1932893422);

INSERT INTO dbo.iet_permiso_perfil
(id_permiso, id_perfil)
VALUES
(12, 1);