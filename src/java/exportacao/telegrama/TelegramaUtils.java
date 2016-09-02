/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exportacao.telegrama;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Viviane
 */
public class TelegramaUtils {

    public static String gerarNomeDoArquivoExportacao() {
        Date data = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        return "TelegramaExportacao_" + dateFormat.format(data) + ".txt";
    }
}
