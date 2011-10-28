------------------------------------------------------------
---                  Restrictions                        ---
------------------------------------------------------------

ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_permiso;

ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT fk_perp_perfil;

ALTER TABLE iet_permiso_perfil 
DROP CONSTRAINT pk_perp;

ALTER TABLE iet_tarea 
DROP CONSTRAINT fk_tarea_objetivo 

ALTER TABLE iet_subtarea 
DROP CONSTRAINT fk_subtarea_tarea;

ALTER TABLE iet_subtarea 
DROP CONSTRAINT fk_subtarea_subtarea;

ALTER TABLE iet_subtarea 
DROP CONSTRAINT pk_subtarea;

ALTER TABLE iet_comentario 
DROP CONSTRAINT fk_comentario_tarea;

ALTER TABLE iet_comentario 
DROP CONSTRAINT pk_comentario;

ALTER TABLE iet_visualiza 
DROP CONSTRAINT fk_visualiza_tarea;

ALTER TABLE iet_visualiza 
DROP CONSTRAINT pk_visualiza;

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

ALTER TABLE iet_relacion_nueva_tarea 
DROP CONSTRAINT fk_relacion_nueva_tarea_relacion;

ALTER TABLE iet_relacion_modifica_estado 
DROP CONSTRAINT fk_relacion_modifica_estado_relacion;

ALTER TABLE iet_visible 
DROP CONSTRAINT fk_relacion_tarea_visible;

ALTER TABLE iet_obj_visible 
DROP CONSTRAINT fk_relacion_objetivo_visible;



------------------------------------------------------------
---                  DROPS                               ---
------------------------------------------------------------
DROP TABLE iet_perfil;

DROP TABLE iet_permiso;

DROP TABLE iet_permiso_perfil;

DROP TABLE iet_tarea;

DROP TABLE iet_subtarea;

DROP TABLE iet_comentario;

DROP TABLE iet_visualiza ;

DROP TABLE iet_evento;

DROP TABLE iet_evento_ldap;

DROP TABLE iet_evento_logs;

DROP TABLE iet_evento_archivos;

DROP TABLE iet_evento_servicios;

DROP TABLE iet_relacion;

DROP TABLE iet_relacion_nueva_tarea;

DROP TABLE iet_relacion_modifica_estado;

DROP TABLE iet_objetivo;

DROP TABLE iet_visible;

DROP TABLE iet_obj_visible;