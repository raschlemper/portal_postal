'use strict';

app.constant('MESSAGES', {

    conta: { 
        info: {
            CONTA_ENCERRADO: "Esta conta está encerrada!"
        }
    },

    planoConta: { 
        sucesso: {
            INSERIDO_SUCESSO: "Plano de Conta {0} Inserido com sucesso!"
        },
        info: {
            PLANO_CONTA_EXISTENTE: "Este Plano de Conta já existe! Confira se o código já foi utilizado neste grupo.",
            NAO_PERMITE_ADD_GRUPO: "Não é permitido adicionar contas a um plano de conta que possua lançamentos vinculados.",
            NAO_PERMITE_GRUPO: "Só é permitido realizar lançamentos em plano de conta que não possuam planos de conta vinculados!",
            PLANO_CONTA_NAO_CADASTRADO: "Este plano de conta não esta cadastrado!"
        }
    },

    centroCusto: { 
        info: {
            NAO_PERMITE_GRUPO: "Só é permitido realizar lançamentos em centro de custo que não possuam centros de custo vinculados!",
            CENTRO_CUSTO_NAO_CADASTRADO: "Este centro de custo não esta cadastrado!"
        }
    },

    lancamento: { 
        title: {
            INSERIR: "Inserir Lançamento",
            EDITAR: "Editar Lançamento",
            VISUALIZAR: "Visualizar Lançamento",
            GERAR: "Gerar Lançamento",
            PARCELAR: "Parcelar Lançamento",
            LISTA: "LANÇAMENTOS"
        },
        sucesso: {
            INSERIDO_SUCESSO: "Lançamento Inserido com sucesso!",
            ALTERADO_SUCESSO: "Lançamento Alterado com sucesso!",
            REMOVIDO_SUCESSO: "Lançamento Removido com sucesso!",
            REMOVIDO_SUCESSO_TODOS: "Lançamentos Removidos com sucesso!"
        },
        info: {
            ALERT: "Alerta Lançamento",
            ALERT_EXCLUIR: "Excluir Lançamento?",
            ALTERAR_AUTOMATICO: "Este lançamento é automático e não pode ser alterado!",
            CONFIRMAR_EXCLUIR: "Deseja realmente excluir este lançamento?",
            CONFIRMAR_EXCLUIR_TODOS: "Deseja realmente excluir estes lançamentos?",
            CONFIRMAR_EXCLUIR_PROGRAMADOS_TODOS: "Deseja realmente excluir estes lançamentos? <br/> Alguns lançamentos parcelados não poderão ser excluídos. É necessário excluir todos os lançamentos posteriores!",
            EXCLUIR_POSTERIOR: "Este lançamento não pode ser excluído. É necessário excluir todos os lançamentos posteriores!",
            EXCLUIR_AUTOMATICO: "Este lançamento é automático e não pode ser excluído!",
            SEM_LANCAMENTO_SELECIONADO: "Nenhum lançamento foi selecionado!",
            FAVORECIDO_NAO_CADASTRADO: "Este favorecido não esta cadastrado!",
            NENHUM_PERIODO_SELECIONADO: "Selecionar um período para pesquisar os lançamentos!",
            PERIODO_INVALIDO: "O período não pode ser superior a {0} dias!"
        },
        validacao: {
            DATA_LANCAMENTO_REQUERIDA: "Preencha a data do lançamento!",
            DATA_LANCAMENTO_VALIDA: "A data do lançamento não é válida!",
            DATA_COMPETENCIA_REQUERIDA: "Preencha a competência do lançamento!",
            DATA_COMPETENCIA_VALIDA: "A competência do lançamento não é válida!",
            VALOR_REQUERIDA: "Preencha o valor do lançamento!",
            VALOR_VALIDA: "O valor do lançamento não é válido",
            FAVORECIDO_REQUERIDA: "Preencha o favorecido do lançamento!",
            HISTORICO_REQUERIDA: "Preencha o histórico do lançamento!",
            PLANO_CONTA_REQUERIDA: "Preencha o plano de conta do lançamento!",
            CENTRO_CUSTO_REQUERIDA: "Preencha o centro de custo do lançamento!"
        },
        programar: {
            title: {
                INSERIR: "Inserir Lançamento Programado",
                EDITAR: "Editar Lançamento Programado",
                VISUALIZAR: "Visualizar Lançamento Programado",
                LISTA: "PROGRAMAÇÕES"
            },
            sucesso: {
                INSERIDO_SUCESSO: "Lançamento Programado Inserido com sucesso!",
                ALTERADO_SUCESSO: "Lançamento Programado Alterado com sucesso!",
                REMOVIDO_SUCESSO: "Lançamento Programado Removido com sucesso!",
                REMOVIDO_SUCESSO_TODOS: "Lançamentos Programados Removidos com sucesso!"
            },
            info: {
                ALERT_EXCLUIR: "Excluir Lançamento Programado?",
                CONFIRMAR_EXCLUIR: "Deseja realmente excluir este lançamento programado?",
                LANCAMENTO_VINCULADOS: "Este lançamento programado não pode ser excluído! <br/> Existem Lançamentos vinculados a este lançamento programado.",
                CONFIRMAR_EXCLUIR_TODOS: "Deseja realmente excluir estes lançamentos programados?",
                CONFIRMAR_EXCLUIR_LANCAMENTOS_TODOS: "Deseja realmente excluir estes lançamentos programados? <br/> Alguns lançamentos programados não poderão ser excluídos. É necessário excluir todos os lançamentos vínculados!",
                PARCELA_EXISTENTE: "Este lançamento não pode ser inserido, pois esta parcela já existe!",
                SEM_LANCAMENTO_SELECIONADO: "Nenhum lançamento foi selecionado!"     
            },
            validacao: {
                DATA_LANCAMENTO_REQUERIDA: "Preencha a data do lançamento programado!",
                DATA_LANCAMENTO_VALIDA: "A data do lançamento programado não é válida!",
                DATA_COMPETENCIA_REQUERIDA: "Preencha a competência do lançamento programado!",
                DATA_COMPETENCIA_VALIDA: "A competência do lançamento programado não é válida!",
                VALOR_REQUERIDA: "Preencha o valor do lançamento programado!",
                VALOR_VALIDA: "O valor do lançamento programado não é válido",
                FAVORECIDO_REQUERIDA: "Preencha o favorecido do lançamento programado!",
                HISTORICO_REQUERIDA: "Preencha o histórico do lançamento programado!",
                PLANO_CONTA_REQUERIDA: "Preencha o plano de conta do lançamento programado!",
                CENTRO_CUSTO_REQUERIDA: "Preencha o centro de custo do lançamento programado!"
            }            
        },
        transferir: {
            title: {
                INSERIR: "Inserir Lançamento de Transferência",
                EDITAR: "Editar Lançamento de Transferência"
            },
            sucesso: {
                INSERIDO_SUCESSO: "Lançamento Transferido com sucesso!",
                ALTERADO_SUCESSO: "Lançamento Transferido com sucesso!"
            },
            validacao: {
                CONTA_DIFERENTE: "A conta de origem deve ser diferente da conta de destino!",
                DATA_LANCAMENTO_REQUERIDA: "Preencha a data do lançamento!",
                DATA_LANCAMENTO_ORIGEM_REQUERIDA: "Preencha a data de origem do lançamento!",
                DATA_LANCAMENTO_DESTINO_REQUERIDA: "Preencha a data de destino do lançamento!",
                DATA_LANCAMENTO_VALIDA: "A data do lançamento não é válida!",
                DATA_LANCAMENTO_ORIGEM_VALIDA: "A data de origem do lançamento não é válida!",
                DATA_LANCAMENTO_DESTINO_VALIDA: "A data de destino do lançamento não é válida!",
                DATA_COMPETENCIA_REQUERIDA: "Preencha a competência do lançamento!",
                DATA_COMPETENCIA_ORIGEM_REQUERIDA: "Preencha a competência de origem do lançamento!",
                DATA_COMPETENCIA_DESTINO_REQUERIDA: "Preencha a competência de destino do lançamento!",
                DATA_COMPETENCIA_VALIDA: "A competência do lançamento não é válida!",
                DATA_COMPETENCIA_ORIGEM_VALIDA: "A competência de origem do lançamento não é válida!",
                DATA_COMPETENCIA_DESTINO_VALIDA: "A competência de destino do lançamento não é válida!",
                DATA_VENCIMENTO_REQUERIDA: "Preencha o vencimento do lançamento!",
                DATA_VENCIMENTO_ORIGEM_REQUERIDA: "Preencha o vencimento de origem do lançamento!",
                DATA_VENCIMENTO_DESTINO_REQUERIDA: "Preencha o vencimento de destino do lançamento!",
                DATA_VENCIMENTO_VALIDA: "O vencimento do lançamento não é válida!",
                DATA_VENCIMENTO_ORIGEM_VALIDA: "O vencimento de origem do lançamento não é válida!",
                DATA_VENCIMENTO_DESTINO_VALIDA: "O vencimento de destino do lançamento não é válida!",
                VALOR_REQUERIDA: "Preencha o valor do lançamento!",
                VALOR_VALIDA: "O valor do lançamento não é válido",
                HISTORICO_REQUERIDA: "Preencha o histórico do lançamento!"
            }            
        },
        conciliar: {
            title: {
                INSERIR: "Inserir Lançamento Reconciliado"
            },
            sucesso: {
                INSERIDO_SUCESSO: "Lançamento Reconciliado com sucesso!"
            },
            info: {
                CONFIRMAR_INCLUIR: "Você está inserindo um lançamento em um período reconciliado. <br/> Está inclusão irá impactar no lançamento de conciliação! <br/> Deseja continuar?",
                CONFIRMAR_ALTERAR: "Este lançamento está reconciliado. <br/> A alteração irá impactar no lançamentos de reconciliação! <br/> Deseja continuar?",
                SEM_CONTA_SELECIONADO: "Nenhuma conta foi selecionada!"
            },
            validacao: {
                DATA_LANCAMENTO_REQUERIDA: "Preencha a data do lançamento reconciliado!",
                DATA_LANCAMENTO_VALIDA: "A data do lançamento reconciliado não é válida!",
                HISTORICO_REQUERIDA: "Preencha o histórico do lançamento reconciliado!"
            }
        },
        parcelar: {
            title: {
                INSERIR: "Parcelar Lançamento",
            },
            validacao: {
                SALDO_INCORRETO: "A soma dos valores do parcelamento é diferente ao valor do lançamento!"
            }
        },
        ratear: {
            title: {
                INSERIR: "Ratear Lançamento",
            },
            validacao: {
                SALDO_INCORRETO: "A soma dos valores de rateio é superior ao valor do lançamento!"
            }
        },
        anexar: {
            title: {
                INSERIR: "Inserir Anexo"
            },
            sucesso: {
                INSERIDO_SUCESSO: "Anexo Inserido com sucesso!",
                ALTERADO_SUCESSO: "Anexo Alterado com sucesso!",
                REMOVIDO_SUCESSO: "Anexo Removido com sucesso!"
            },
            validacao: {
                ARQUIVO_REQUERIDA: "Selecione o arquivo!",
                ARQUIVO_NAO_PERMITIDO: "Este tipo de arquivo não é permitido!",
                ARQUIVO_ACIMA_TAMANHO_PERMITIDO: "Este arquivo esta acima do tamanho permitido!"
            }
        },
        compensar: {
            sucesso: {
                INSERIDO_SUCESSO: "Lançamento Compensado com sucesso!"
            },
            info: {
                CONFIRMAR_TODOS: "Deseja compensar todos os lançamentos selecionados?"
            }
        }
    }

});
