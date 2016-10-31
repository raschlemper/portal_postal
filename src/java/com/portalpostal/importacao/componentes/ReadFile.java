
package com.portalpostal.importacao.componentes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;


public class ReadFile {

    public List<String> read(InputStream inputStream,String charset) throws IOException{
        return IOUtils.readLines(inputStream,charset);
    }

}
