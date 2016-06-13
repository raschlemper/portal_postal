'use strict';

app.constant('MESSAGES', {

    conta: { 
        info: {
            CONTA_ENCERRADO: "Esta conta está encerrada!"
        }
    },

    planoConta: { 
        info: {
            NAO_PERMITE_GRUPO: "Só é permitido realizar lançamentos em plano de conta que não possuam planos de conta vinculados!"
        }
    },

    centroCusto: { 
        info: {
            NAO_PERMITE_GRUPO: "Só é permitido realizar lançamentos em centro de custo que não possuam centros de custo vinculados!"
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
            CONFIRMAR_EXCLUIR: "Deseja realmente excluir este lançamento?",
            CONFIRMAR_EXCLUIR_TODOS: "Deseja realmente excluir estes lançamentos?",
            CONFIRMAR_EXCLUIR_PROGRAMADOS_TODOS: "Deseja realmente excluir estes lançamentos? <br/> Alguns lançamentos parcelados não poderão ser excluídos. É necessário excluir todos os lançamentos posteriores!",
            EXCLUIR_POSTERIOR: "Este lançamento não pode ser excluído. É necessário excluir todos os lançamentos posteriores!",
            SEM_LANCAMENTO_SELECIONADO: "Nenhum lançamento foi selecionado!"
        },
        validacao: {
            DATA_LANCAMENTO_REQUERIDA: "Preencha a data do lançamento!",
            DATA_LANCAMENTO_VALIDA: "A data do lançamento não é válida!",
            DATA_COMPETENCIA_REQUERIDA: "Preencha a competência do lançamento!",
            DATA_COMPETENCIA_VALIDA: "A competência do lançamento não é válida!",
            VALOR_REQUERIDA: "Preencha o valor do lançamento!",
            VALOR_VALIDA: "O valor do lançamento não é válido",
            HISTORICO_REQUERIDA: "Preencha o histórico do lançamento!"
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
                HISTORICO_REQUERIDA: "Preencha o histórico do lançamento programado!"
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
                DATA_LANCAMENTO_VALIDA: "A data do lançamento não é válida!",
                DATA_COMPETENCIA_REQUERIDA: "Preencha a competência do lançamento!",
                DATA_COMPETENCIA_VALIDA: "A competência do lançamento não é válida!",
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
                CONFIRMAR_ALTERAR: "Este lançamento está reconciliado. <br/> A alteração irá impactar no lançamentos de reconciliação! <br/> Deseja continuar?"
            },
            validacao: {
                DATA_LANCAMENTO_REQUERIDA: "Preencha a data do lançamento reconciliado!",
                DATA_LANCAMENTO_VALIDA: "A data do lançamento reconciliado não é válida!",
                HISTORICO_REQUERIDA: "Preencha o histórico do lançamento reconciliado!"
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
            validacao: {
                ARQUIVO_REQUERIDA: "Selecione o arquivo!"
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
