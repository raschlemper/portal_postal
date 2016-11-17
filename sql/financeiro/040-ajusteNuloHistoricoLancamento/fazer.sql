update lancamento lanc
   set historico = (select REPLACE(lanc.historico, 'null', ''))
 where lanc.historico is not null 
   and LOCATE('null', lanc.historico) > 0 ;
