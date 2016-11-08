update lancamento_programado 
   set situacao = 0 
 where lancamento_programado.frequencia = 0 
   and lancamento_programado.quantidadeParcela is null 
   and lancamento_programado.situacao = 2
   and exists(select 1 from lancamento 
               where lancamento.idLancamentoProgramado = lancamento_programado.idLancamentoProgramado);

update lancamento_programado 
   set situacao = 0 
 where lancamento_programado.frequencia > 0 
   and lancamento_programado.quantidadeParcela is not null
   and lancamento_programado.situacao = 2 
   and lancamento_programado.quantidadeParcela = (select max(lancamento.numeroParcela) 
                                                    from lancamento 
                                                   where lancamento.idLancamentoProgramado = lancamento_programado.idLancamentoProgramado);


