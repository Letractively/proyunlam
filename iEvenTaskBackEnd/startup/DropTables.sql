drop table iet_permiso;
drop table iet_perfil;
ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_permiso;

ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_perfil;

DROP TABLE iet_permiso_perfil;
