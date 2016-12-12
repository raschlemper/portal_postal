package br.com.portalpostal.prepostagem.dao;

import Controle.ContrClienteEtiquetas;
import br.com.portalpostal.componentes.ConexaoEntityManager;
import br.com.portalpostal.entity.Cliente;
import br.com.portalpostal.entity.ClienteContrato;
import br.com.portalpostal.entity.ClienteDepartamentos;
import br.com.portalpostal.entity.PreVenda;
import br.com.portalpostal.entity.PreVendaDestinatario;
import br.com.portalpostal.entity.ServicosEct;
import br.com.portalpostal.providers.ProviderCliente;
import br.com.portalpostal.providers.ProviderServicoEct;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class PrePostagemDao {

    private final EntityManager manager;
    private final Map<Integer, ServicosEct> mapServicosEct;
    private Cliente cliente = null;

    public PrePostagemDao(EntityManager manager) {
        this.manager = manager;
        this.mapServicosEct = carregaServicosECT();
    }

    public void persist(List<PreVenda> preVendas) {

        if (!isRegistrosComESemNumeroDeEtiqueta(preVendas)) {
            throw new RuntimeException("Registros com número da etiqueta inválido.");
        }

        if (!isEtiquetaValidas(preVendas)) {
            throw new RuntimeException("Algumas etiquetas são inválidas.");
        }

        if(!isCamposObrigatoriosCarregados(preVendas)){
            throw new RuntimeException("Layout inconsistente com o arquivo selecionado.");
        }

        for (PreVenda preVenda : preVendas) {
            preparaPreVenda(preVenda);
        }

        for (PreVenda preVenda : preVendas) {
            persist(preVenda);
        }
    }

    public void persist(PreVenda preVenda) {
        PreVendaDestinatario preVendaDestinatario = preVenda.getDestinatario();
        manager.getTransaction().begin();
        manager.persist(preVenda);
        manager.persist(preVendaDestinatario);
        manager.getTransaction().commit();
        preVenda.setIdDestinatario(preVendaDestinatario.getPreVendaDestinatarioPK().getIdDestinatario());
        manager.getTransaction().begin();
        manager.persist(preVenda);
        manager.getTransaction().commit();
    }

    private void preparaPreVenda(PreVenda preVenda) {
        carregaCliente(preVenda);
        carregaCodigoFinanceiro(preVenda);
        carregaDepartamento(preVenda);
        carregaNumeroObjeto(preVenda);

    }

    private void carregaCodigoFinanceiro(PreVenda preVenda) {
        Cliente cliente = preVenda.getCliente();
        preVenda.setNomeServico(getNomeServico(preVenda.getNomeServico()));
        preVenda.setCodECT(findGrupoServicoContrato(preVenda, cliente.getContratos()));
    }

    private String getNomeServico(String servico) {
        for (Map.Entry<Integer, ServicosEct> entrySet : mapServicosEct.entrySet()) {
            ServicosEct servicoEct = entrySet.getValue();
            if (String.valueOf(servicoEct.getCodECT()).equals(servico) || servicoEct.getGrupoServico().equals(servico)) {
                return servicoEct.getGrupoServico();
            }
        }
        return null;
    }

    private Integer findGrupoServicoContrato(PreVenda preVenda, List<ClienteContrato> contratos) {
        ClienteContrato clienteContrato = findServicoContratoCliente(preVenda, contratos);
        Integer codigoEct;
        if (clienteContrato != null) {
            codigoEct = clienteContrato.getCodECT();
        } else {
            codigoEct = getServicoVista(preVenda.getNomeServico());
        }
        return codigoEct;
    }

    private Integer getServicoVista(String servico) {
        for (Map.Entry<Integer, ServicosEct> entrySet : mapServicosEct.entrySet()) {
            ServicosEct servicoEct = entrySet.getValue();
            if (isEncontrouServicoVista(servicoEct, servico)) {
                return servicoEct.getCodECT();
            }
        }
        return null;
    }

    private static boolean isEncontrouServicoVista(ServicosEct servicoEct, String servico) {
        return servicoEct.getAvista() > 0 && servicoEct.getGrupoServico().equals(servico);
    }

    private ClienteContrato findServicoContratoCliente(PreVenda preVenda, List<ClienteContrato> contratos) {
        for (ClienteContrato contrato : contratos) {
            if (isServicoVigenteContrato(contrato, preVenda)) {
                return contrato;
            }
        }
        return null;
    }

    private static boolean isServicoVigenteContrato(ClienteContrato contrato, PreVenda preVenda) {
        Cliente cliente = contrato.getCliente();
        return contrato.getClienteContratoPK().getGrupoServico().equals(preVenda.getNomeServico())
                && cliente.getDtVigenciaFimContrato().after(Calendar.getInstance().getTime())
                && cliente.getStatusCartaoPostagem() == 1;
    }

    private Map<Integer, ServicosEct> carregaServicosECT() {
        ProviderServicoEct provider = new ProviderServicoEct(manager);
        return criaMapServicosECT(provider.findAllServicosAtivos());
    }

    private Map<Integer, ServicosEct> criaMapServicosECT(List<ServicosEct> servicosEct) {
        Map<Integer, ServicosEct> map = new HashMap<>();
        for (ServicosEct servicoECT : servicosEct) {
            map.put(servicoECT.getCodECT(), servicoECT);
        }
        return map;
    }

   
    private void carregaNumeroObjeto(PreVenda preVenda) {
        if (preVenda.getPreVendaPK().getNumObjeto().isEmpty()) {
            String etiqueta = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServ(preVenda.getIdCliente(), preVenda.getNomeServico(), getNomeDB());
            preVenda.getPreVendaPK().setNumObjeto(etiqueta);
        }
    }

    private String getNomeDB() {
        String nomeDB = (String) manager.getProperties().get(ConexaoEntityManager.PROPERTIESNOMEDB);
        return nomeDB;

    }

    private void carregaCliente(PreVenda preVenda) {
        if (this.cliente == null) {
            ProviderCliente providerCliente = new ProviderCliente(manager);
            this.cliente = providerCliente.findClienteById(preVenda.getIdCliente());
        }
        preVenda.setCliente(cliente);
    }

    private ClienteDepartamentos findDepartamento(PreVenda preVenda) {
        for (ClienteDepartamentos departamento : cliente.getDepartamentos()) {
            if (departamento.getClienteDepartamentosPK().getIdDepartamento() == preVenda.getIdDepartamento()) {
                return departamento;
            }
        }
        return null;
    }

    private void carregaDepartamento(PreVenda preVenda) {
        ClienteDepartamentos clienteDepartamento = findDepartamento(preVenda);
        if (clienteDepartamento != null) {
            preVenda.setDepartamento(clienteDepartamento.getNomeDepartamento());
            if (clienteDepartamento.getCartaoPostagem() != null && !clienteDepartamento.getCartaoPostagem().isEmpty()) {
                preVenda.setCartaoPostagem(clienteDepartamento.getCartaoPostagem());
            }
        }
    }

    private boolean isRegistrosComESemNumeroDeEtiqueta(List<PreVenda> preVendas) {
        return isTodasEtiquetasPreenchidas(preVendas) || isTotasEtiquetasVazias(preVendas);
    }

    private static boolean isTotasEtiquetasVazias(List<PreVenda> preVendas) {
        return true;// preVendas.size() == preVendas.stream().filter(preVenda -> preVenda.getPreVendaPK().getNumObjeto().isEmpty()).collect(Collectors.toList()).size();
    }

    private boolean isTodasEtiquetasPreenchidas(List<PreVenda> preVendas) {
        return true;// preVendas.size() == preVendas.stream().filter(preVenda -> !preVenda.getPreVendaPK().getNumObjeto().isEmpty()).collect(Collectors.toList()).size();
    }

    private boolean isEtiquetaValidas(List<PreVenda> preVendas) {
        if (isTodasEtiquetasPreenchidas(preVendas)) {
            return isTotasEtiquetasSaoValidas(preVendas);
        }
        return true;
    }

    private static boolean isTotasEtiquetasSaoValidas(List<PreVenda> preVendas) {
        return true;// preVendas.stream().allMatch(preVenda -> CalculoEtiqueta.validaNumObjeto(preVenda.getPreVendaPK().getNumObjeto()));
    }

    private boolean isCamposObrigatoriosCarregados(List<PreVenda> preVendas) {
        return true;// preVendas.stream().allMatch(preVenda->isDadosDestinatarioPreenchido(preVenda.getDestinatario()));
    }

    private boolean isDadosDestinatarioPreenchido(PreVendaDestinatario destinatario){
        return !destinatario.getNome().isEmpty()
                && !destinatario.getCep().isEmpty()
                && !destinatario.getEndereco().isEmpty()
                && !destinatario.getBairro().isEmpty()
                && !destinatario.getCidade().isEmpty()
                && !destinatario.getUf().isEmpty();
    }

}
