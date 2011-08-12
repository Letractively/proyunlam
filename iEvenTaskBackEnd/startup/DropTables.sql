drop table iet_permiso;
drop table iet_perfil;
ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_permiso;

ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_perfil;

DROP TABLE iet_permiso_perfil;

ALTER TABLE iet_visualiza 
DROP CONSTRAINT fk_visualiza_tarea;

DROP TABLE iet_visualiza;

ALTER TABLE iet_comentario 
DROP CONSTRAINT fk_comentario_tarea;

DROP TABLE iet_comentario;

ALTER TABLE iet_subtarea 
DROP CONSTRAINT fk_subtarea_tarea;
ALTER TABLE iet_subtarea 
DROP CONSTRAINT fk_subtarea_subtarea;

DROP TABLE iet_subtarea;

DROP TABLE iet_tarea;