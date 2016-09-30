ALTER TABLE favorecido
CHARACTER SET latin1 COLLATE latin1_swedish_ci;

ALTER TABLE plano_conta
CHARACTER SET latin1 COLLATE latin1_swedish_ci;

ALTER TABLE centro_custo
CHARACTER SET latin1 COLLATE latin1_swedish_ci;

update favorecido set nome = ( select cliente.nome from cliente where cliente.codigo = favorecido.idCliente ) where idCliente is not null;