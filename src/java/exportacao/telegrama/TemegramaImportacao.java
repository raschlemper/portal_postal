
package exportacao.telegrama;

import Util.Conexao;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.io.IOUtils;

public class TemegramaImportacao {

    private TelegramaList telegramaList;
    private String nomeBancoDados;
    public TemegramaImportacao(String nomeBancoDados) {
        this.nomeBancoDados = nomeBancoDados;
        
    }

    public void importar(InputStream arquivo) throws IOException, SQLException{
        transformerToObject(transformerToString(arquivo));
        atualizar();
    }

    private void atualizar() throws SQLException {
        String sql = "UPDATE telegrama_postal SET sro=?, valor=?, status=?,dataHoraEnviado=? WHERE id=?";
        Connection conn = (Connection) Conexao.conectar(nomeBancoDados);
        for (Telegrama telegrama : telegramaList.getTelegramas()) {
            if (telegrama.getStatus().equals(TelegramaStatus.ENVIADO)) {
                PreparedStatement prepareStatement = conn.prepareStatement(sql);
                prepareStatement.setString(1, telegrama.getEtiqueta());
                prepareStatement.setDouble(2, telegrama.getValor());
                prepareStatement.setInt(3, 1);
                prepareStatement.setTimestamp(4, new java.sql.Timestamp(telegrama.getDataEnvio().getTime()));
                prepareStatement.setLong(5, telegrama.getId());
                prepareStatement.execute();
            }
        }
        conn.close();
    }

    private String transformerToString(InputStream arquivo) throws IOException {
        return IOUtils.toString(arquivo);
    }

    private void transformerToObject(String json) {
        Gson gson = new Gson();
        telegramaList = gson.fromJson(json, TelegramaList.class);
    }
}
