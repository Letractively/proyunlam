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