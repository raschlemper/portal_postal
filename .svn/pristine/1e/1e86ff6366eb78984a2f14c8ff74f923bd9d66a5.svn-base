update lancamento_programado 
   set dataVencimento = (select MIN(lancamento_programado_parcela.dataVencimento)
                           from lancamento_programado_parcela 
                           where not exists (select 1 from lancamento where lancamento.idLancamento = lancamento_programado_parcela.idLancamento))
 where lancamento_programado.quantidadeParcela > 0;