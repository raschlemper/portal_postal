import com.portalpostal.importacao.componentes.ConversorDestinatarioModeloVip;
import com.portalpostal.importacao.componentes.DestinatarioModeloVip;
import com.portalpostal.importacao.componentes.ReadFile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author Viviane
 */
public class ConversorDestinatarioModeloVipTest {

    private  List<DestinatarioModeloVip> destinatarios;
    private  String ARQUIVO = "importacaoVip.txt";
    public ConversorDestinatarioModeloVipTest() throws FileNotFoundException, IOException  {
       URL url = getClass().getResource(ARQUIVO);
       FileInputStream stream = new FileInputStream(url.getPath());
       destinatarios = new ConversorDestinatarioModeloVip().converter(new ReadFile().read(stream,"ISO-8859-1"));
    }

   
//     @Test
//     public void confereColunasDoArquivoImportado() {
//         DestinatarioModeloVip destinatario = destinatarios.get(0);
//         Assert.assertEquals("VANEIDE DA SILVA ME",destinatario.getNome());
//         Assert.assertEquals("AVENIDA 27 DE OUTUBRO",destinatario.getEndereco());
//         Assert.assertEquals("498",destinatario.getNumero());
//         Assert.assertEquals("A",destinatario.getComplemento());
//         Assert.assertEquals("CENTRO",destinatario.getBairro());
//         Assert.assertEquals("SANTANA DO MATOS",destinatario.getCidade());
//         Assert.assertEquals("RN",destinatario.getEstado());
//         Assert.assertEquals("59520-000",destinatario.getCep());
//         Assert.assertEquals("8434342499",destinatario.getTelefone());
//         Assert.assertEquals("81019",destinatario.getCodigoFinanceiro());
//         Assert.assertEquals("",destinatario.getNumeroObjeto());
//         Assert.assertEquals("005250",destinatario.getPeso());
//         Assert.assertEquals("33",destinatario.getAltura());
//         Assert.assertEquals("28",destinatario.getLargura());
//         Assert.assertEquals("39",destinatario.getComprimento());
//         Assert.assertEquals("000000",destinatario.getCubico());
//         Assert.assertEquals("131639",destinatario.getNota());
//         Assert.assertEquals("2",destinatario.getSerieNota());
//         Assert.assertEquals("00000000000000096515",destinatario.getValorDeclarado());
//         Assert.assertEquals("",destinatario.getValorCobrar());
//         Assert.assertEquals("",destinatario.getServicosAdicionais());
//         Assert.assertEquals("9912328399",destinatario.getContrato());
//         Assert.assertEquals("0013263420",destinatario.getAdministrativo());
//         Assert.assertEquals("70799920",destinatario.getCartao());
//         Assert.assertEquals("vaneidemodas@hotmail.com",destinatario.getEmail());
//         Assert.assertEquals("RECUSAR A ENTREGA CASO A EMBALAGEM ESTEJA COM",destinatario.getObservacao());
//         Assert.assertEquals("",destinatario.getConteudoDoObjeto());
//         Assert.assertEquals("0000",destinatario.getCodigoVolume());
//         Assert.assertEquals("0001",destinatario.getQuantidadeVolumes());
//         Assert.assertEquals("",destinatario.getCodigoClienteVisual());
//         Assert.assertEquals("8499360051",destinatario.getCelular());
//     }
}
