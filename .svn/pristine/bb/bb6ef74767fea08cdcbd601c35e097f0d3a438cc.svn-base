ALTER TABLE plano_conta ADD `nivel` INT NULL;
ALTER TABLE plano_conta ADD `estrutura` VARCHAR(254);

INSERT INTO plano_conta
SELECT idPlanoConta, tipo, codigo, nome, grupo, 0, '' FROM plano_conta;


UPDATE plano_conta SET nivel = 1 
 WHERE grupo IS NULL;

UPDATE plano_conta SET estrutura = codigo 
 WHERE grupo IS NULL;


CREATE TABLE plano_conta_id AS SELECT idPlanoConta, estrutura FROM plano_conta WHERE nivel = 1;
UPDATE plano_conta SET plano_conta.nivel = 2 
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);

UPDATE plano_conta SET plano_conta.estrutura = (SELECT CONCAT(pcid.estrutura, '.', plano_conta.codigo)
                                                            FROM plano_conta_id pcid 
                                                           WHERE plano_conta.grupo = pcid.idPlanoConta)
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);


DROP TABLE plano_conta_id;
CREATE TABLE plano_conta_id AS SELECT idPlanoConta, estrutura FROM plano_conta WHERE nivel = 2;
UPDATE plano_conta SET plano_conta.nivel = 3 
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);

UPDATE plano_conta SET plano_conta.estrutura = (SELECT CONCAT(pcid.estrutura, '.', plano_conta.codigo)
                                                            FROM plano_conta_id pcid 
                                                           WHERE plano_conta.grupo = pcid.idPlanoConta)
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);


DROP TABLE plano_conta_id;
CREATE TABLE plano_conta_id AS SELECT idPlanoConta, estrutura FROM plano_conta WHERE nivel = 3;	
UPDATE plano_conta SET plano_conta.nivel = 4 
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);

UPDATE plano_conta SET plano_conta.estrutura = (SELECT CONCAT(pcid.estrutura, '.', plano_conta.codigo)
                                                            FROM plano_conta_id pcid 
                                                           WHERE plano_conta.grupo = pcid.idPlanoConta)
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);


DROP TABLE plano_conta_id;
CREATE TABLE plano_conta_id AS SELECT idPlanoConta, estrutura FROM plano_conta WHERE nivel = 4;	
UPDATE plano_conta SET plano_conta.nivel = 5
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);

UPDATE plano_conta SET plano_conta.estrutura = (SELECT CONCAT(pcid.estrutura, '.', plano_conta.codigo)
                                                            FROM plano_conta_id pcid 
                                                           WHERE plano_conta.grupo = pcid.idPlanoConta)
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);


DROP TABLE plano_conta_id;
CREATE TABLE plano_conta_id AS SELECT idPlanoConta, estrutura FROM plano_conta WHERE nivel = 5;	
UPDATE plano_conta SET plano_conta.nivel = 6
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);

UPDATE plano_conta SET plano_conta.estrutura = (SELECT CONCAT(pcid.estrutura, '.', plano_conta.codigo)
                                                            FROM plano_conta_id pcid 
                                                           WHERE plano_conta.grupo = pcid.idPlanoConta)
 WHERE plano_conta.grupo IN(SELECT plano_conta_id.idPlanoConta FROM plano_conta_id);


DROP TABLE plano_conta_id;

