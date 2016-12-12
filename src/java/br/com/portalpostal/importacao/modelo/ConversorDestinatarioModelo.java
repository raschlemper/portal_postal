
package br.com.portalpostal.importacao.modelo;

import java.util.List;


public interface ConversorDestinatarioModelo {
    String getAtributoDaLinhaDoArquivo(String atributo,String linha);
    List<DestinatarioModelo> converter(List<String> linhasDoArquivo);
}
