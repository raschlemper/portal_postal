ALTER TABLE lancamento ADD `idFavorecido` INT NULL;
ALTER TABLE lancamento_programado ADD `idFavorecido` INT NULL;

INSERT INTO favorecido
SELECT DISTINCT null, 2, null, null, codigo, nome 
  FROM cliente
UNION
SELECT DISTINCT null, null, null, null, null, favorecido 
  FROM lancamento
 WHERE favorecido IS NOT NULL
UNION
SELECT DISTINCT null, null, null, null, null, favorecido 
  FROM lancamento_programado
 WHERE favorecido IS NOT NULL;

UPDATE lancamento 
   SET lancamento.idFavorecido = (SELECT favorecido.idFavorecido 
                                    FROM favorecido 
                                   WHERE favorecido.nome = lancamento.favorecido);

UPDATE lancamento_programado 
   SET lancamento_programado.idFavorecido = (SELECT favorecido.idFavorecido 
                                               FROM favorecido 
                                              WHERE favorecido.nome = lancamento_programado.favorecido);

ALTER TABLE lancamento DROP `favorecido`;
ALTER TABLE lancamento_programado DROP `favorecido`;