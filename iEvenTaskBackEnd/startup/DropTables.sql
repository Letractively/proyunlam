ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_permiso;
ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_perfil;
DROP TABLE iet_permiso_perfil;
drop table iet_permiso;
drop table iet_perfil;

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

ALTER TABLE iet_evento_ldap 
DROP CONSTRAINT fk_evento_eventoldap;
ALTER TABLE iet_evento_logs 
DROP CONSTRAINT fk_evento_eventologs;
ALTER TABLE iet_evento_archivos 
DROP CONSTRAINT fk_evento_eventoarchivos;
ALTER TABLE iet_evento_servicios
DROP CONSTRAINT fk_evento_eventoservicios;
ALTER TABLE iet_relacion
DROP CONSTRAINT fk_relacion_evento;


DROP TABLE iet_evento_ldap;
DROP TABLE iet_evento_logs;
DROP TABLE iet_evento_archivos;
DROP TABLE iet_evento_servicios;
DROP TABLE iet_evento;

ALTER TABLE iet_relacion_nueva_tarea
DROP CONSTRAINT fk_relacion_nueva_tarea_relacion;
ALTER TABLE iet_relacion_modifica_estado
DROP CONSTRAINT fk_relacion_modifica_estado_relacion;

DROP TABLE iet_relacion_nueva_tarea;
DROP TABLE iet_relacion_modifica_estado;
DROP TABLE iet_relacion;




