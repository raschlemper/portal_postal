ALTER TABLE `conta` ADD codigoIntegracao INTEGER NULL;

ALTER TABLE `conta` 
ADD UNIQUE INDEX `u_codigointegracao` (`codigoIntegracao`);

INSERT INTO conta VALUES
(null, null, null, 'Balcão', 0, 0, now(), 0, 1),
(null, null, null, 'Tesouraria', 0, 0, now(), 0, 2);