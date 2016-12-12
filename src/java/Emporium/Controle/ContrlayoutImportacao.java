package Emporium.Controle;

import Emporium.Entity.LayoutImportacao;
import Emporium.Entity.MapeamentoLayout;
import br.com.portalpostal.exception.RegistroNaoEncontradoException;
import caixapostal.componentes.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContrlayoutImportacao {

    private final String nomeDataBase;
    private static Connection CONNECTION;
    private final String layoutNaoEncontrado = "Modelo do layout não encontrado";
    public ContrlayoutImportacao(String nomeDataBase) {
        this.nomeDataBase = nomeDataBase;
    }

    public void cadastrar(List<LayoutImportacao> layout) throws SQLException {
        CONNECTION = Conexao.getConnection(nomeDataBase);
        for (LayoutImportacao valor : layout) {
            PreparedStatement prepare = CONNECTION.prepareStatement(sqlInsertLayout());
            prepare.setString(1, valor.getNome());
            prepare.setString(2,valor.getAtributo());
            prepare.setObject(3, getValueToInsert(valor.getPosicaoInicial()));
            prepare.setObject(4, getValueToInsert(valor.getPosicaoFinal()));
            prepare.setObject(5, getValueToInsert(valor.getPosicao()));
            prepare.setString(6,valor.getTipo());
            prepare.execute();
        }

    }

    public void update(List<LayoutImportacao> layout) throws SQLException{
        CONNECTION = Conexao.getConnection(nomeDataBase);
        for (LayoutImportacao valor : layout) {
            PreparedStatement prepare = CONNECTION.prepareStatement(sqlUpdateLayout());
            prepare.setObject(1, getValueToInsert(valor.getPosicaoInicial()));
            prepare.setObject(2, getValueToInsert(valor.getPosicaoFinal()));
            prepare.setObject(3, getValueToInsert(valor.getPosicao()));
            prepare.setString(4, valor.getNome().toUpperCase());
            prepare.setString(5,valor.getAtributo());
            System.out.println(prepare.toString());
            prepare.executeUpdate();
        }
    }

    public void delete(String layout) throws SQLException{
            CONNECTION = Conexao.getConnection(nomeDataBase);
            PreparedStatement prepare = CONNECTION.prepareStatement(sqlDeleteLayout());
            prepare.setString(1, layout);
            prepare.execute();
    }

    public List<String> findName() throws SQLException{
        List<LayoutImportacao> layouts = find();
        Set<String> setNomeLayout = new HashSet();
        setNomeLayout.add("");
        for (LayoutImportacao layout : layouts) {
            setNomeLayout.add(layout.getNome());
        }
        return new ArrayList(setNomeLayout);
    }

    public List<LayoutImportacao> find() throws SQLException{
        CONNECTION = Conexao.getConnection(nomeDataBase);
        PreparedStatement prepare = CONNECTION.prepareStatement(sqlFindLayout(null));
        ResultSet result =  prepare.executeQuery();
        return carregaLayouts(result);
    }

    private List<LayoutImportacao> carregaLayouts(ResultSet result) throws SQLException {
        List<LayoutImportacao> layouts = new ArrayList<LayoutImportacao>();
        while(result.next()){
            layouts.add(criaLayoutImportacao(result));
        }
        return layouts;
    }

    private LayoutImportacao criaLayoutImportacao(ResultSet result) throws SQLException {
        LayoutImportacao layout = new LayoutImportacao();
        layout.setId(result.getInt("id"));
        layout.setNome(result.getString("nome"));
        layout.setAtributo(result.getString("atributo"));
        layout.setPosicao(result.getString("posicao"));
        layout.setPosicaoInicial(result.getString("posicao_inicial"));
        layout.setPosicaoFinal(result.getString("posicao_final"));
        layout.setTipo(result.getString("tipo"));
        if(layout.getTipo().equals(MapeamentoLayout.FORMATO_CSV)){
            layout.setPosicaoInicial(layout.getPosicao());
        }
        return layout;
    }

    public List<LayoutImportacao> findByName(String layout) throws SQLException{
        CONNECTION = Conexao.getConnection(nomeDataBase);
        PreparedStatement prepare = CONNECTION.prepareStatement(sqlFindLayout(layout));
        prepare.setString(1, layout.toUpperCase());
        ResultSet result =  prepare.executeQuery();
        return carregaLayouts(result);
    }

    public LayoutImportacao findAtribute(String atributo, List<LayoutImportacao> atributosDoLayout) {
        for (LayoutImportacao layout : atributosDoLayout) {
            if (layout.getAtributo().equals(atributo)) {
                return layout;
            }
        }
        return new LayoutImportacao();
    }


    public void validaSeLayoutFoiEncontrado(List<LayoutImportacao> layoutImportacao) throws RegistroNaoEncontradoException {
        if (layoutImportacao.isEmpty()) {
            throw new RegistroNaoEncontradoException(layoutNaoEncontrado);
        }
    }

    public boolean isLayoutCadastrado(String nomeLayout) throws SQLException{
        return findByName(nomeLayout).size()>0;
    }

    private String sqlInsertLayout() {
       StringBuilder builder = new StringBuilder();
       builder.append("INSERT INTO layout_importacao(nome,atributo,posicao_inicial,posicao_final,posicao,tipo) ");
       builder.append(" VALUES(?,?,?,?,?,?)");
       return builder.toString();
    }

    private String sqlFindLayout(String layout) {
        StringBuilder builder = new StringBuilder();
        if(layout==null){
            builder.append("SELECT * FROM layout_importacao");
        }else{
            builder.append("SELECT * FROM layout_importacao WHERE UPPER(NOME) = ?");
        }
        return builder.toString();
    }

    private String sqlUpdateLayout() {
        return "UPDATE layout_importacao SET posicao_inicial=?, posicao_final=?, posicao=? WHERE UPPER(nome)=? AND atributo=?";
    }

    private String sqlDeleteLayout() {
       return "DELETE FROM layout_importacao WHERE UPPER(nome)=?";
    }

    private String getValueToInsert(String valor){
        return valor.isEmpty()?null:valor;
    }

}
