/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Fernando
 */
public class ServicosECT {
    
    private final Integer[] COD_ECT_PAC_PAG = {41262,41238};
    private final Integer[] COD_ECT_PAC = {41068,41106,41211};
    private final Integer[] COD_ECT_PAX = {41300};
    
    private final Integer[] COD_ECT_PPI = {40053,40150};
    
    private final Integer[] COD_ECT_SEDEX = {40843,40010,40096,41432,40436,40444,40568,40819};
    private final Integer[] COD_ECT_SEDEXC = {40045,40126,41432,40630,91537};
    
    private final Integer[] COD_ECT_ESEDEX = {81019, 81833, 81043};
    
    private final Integer[] COD_ECT_SEDEX10 = {1010, 40215};
    private final Integer[] COD_ECT_SEDEX12 = {40169};
    
    private final Integer[] COD_ECT_CARTA = {10014,10707,10154,11851,10138};
    private final Integer[] COD_ECT_SIMPLES = {10014,10715,10065,11843};    
    
    private final Integer[] COD_ECT_MDPB = {14575,14591,14745,14753,14761,14770,15300,15318,15326};    
    
    private final Integer[] COD_ECT_TELEGRAMA = {60070};    
    
    private final Integer[] COD_ECT_LEV_INT = {45209};
    private final Integer[] COD_ECT_EMS_DOC = {45012};
    private final Integer[] COD_ECT_EMS_MER = {45110};
    private final Integer[] COD_ECT_MER_ECO_INT = {45128};
    private final Integer[] COD_ECT_DOC_ECO_INT = {45020};
    private final Integer[] COD_ECT_DOC_PRI_INT = {45039};
    
    public String getTipoServicoByCodECT(String codigoECT){
        try{
            int codECT = Integer.parseInt(codigoECT.replace(" ", "").replace(".", ""));
            List<Integer> pac = Arrays.asList(COD_ECT_PAC);
            List<Integer> pac2 = Arrays.asList(COD_ECT_PAC_PAG);
            List<Integer> pax = Arrays.asList(COD_ECT_PAX);
            if(pac.contains(codECT) || pac2.contains(codECT) || pax.contains(codECT)){
                //PAC
                return "PAC";
            }
            List<Integer> sedex = Arrays.asList(COD_ECT_SEDEX);
            List<Integer> sedex2 = Arrays.asList(COD_ECT_SEDEXC);
            if(sedex.contains(codECT) || sedex2.contains(codECT)){
                //SEDEX
                return "SEDEX";
            }
            List<Integer> esedex = Arrays.asList(COD_ECT_ESEDEX);
            if(esedex.contains(codECT)){
                //SEDEX
                return "E-SEDEX";
            }
            List<Integer> ppi = Arrays.asList(COD_ECT_PPI);
            if(ppi.contains(codECT)){
                //PPI
                return "PPI";
            }
            List<Integer> mdpb = Arrays.asList(COD_ECT_MDPB);
            if(mdpb.contains(codECT)){
                //MDPB
                return "MDPB";
            }
            List<Integer> sedex10 = Arrays.asList(COD_ECT_SEDEX10);
            if(sedex10.contains(codECT)){
                //SEDEX10
                return "SEDEX 10";
            }
            List<Integer> sedex12 = Arrays.asList(COD_ECT_SEDEX12);
            if(sedex12.contains(codECT)){
                //SEDEX12
                return "SEDEX 12";
            }
            List<Integer> carta = Arrays.asList(COD_ECT_CARTA);
            if(carta.contains(codECT)){
                //CARTA REG.
                return "CARTA REG.";
            }
            List<Integer> simples = Arrays.asList(COD_ECT_SIMPLES);
            if(simples.contains(codECT)){
                //CARTA SIMPLES
                return "CARTA SIMPLES";
            }
            List<Integer> telegrama = Arrays.asList(COD_ECT_TELEGRAMA);
            if(telegrama.contains(codECT)){
                //CARTA SIMPLES
                return "TELEGRAMA";
            }
            List<Integer> int1 = Arrays.asList(COD_ECT_LEV_INT);
            List<Integer> int2 = Arrays.asList(COD_ECT_EMS_DOC);
            List<Integer> int3 = Arrays.asList(COD_ECT_EMS_MER);
            List<Integer> int4 = Arrays.asList(COD_ECT_DOC_ECO_INT);
            List<Integer> int5 = Arrays.asList(COD_ECT_DOC_PRI_INT);
            List<Integer> int6 = Arrays.asList(COD_ECT_MER_ECO_INT);
            if(int1.contains(codECT) || int2.contains(codECT) || int3.contains(codECT) || int4.contains(codECT) || int5.contains(codECT) || int6.contains(codECT)){
                //INTERNACIONAL
                return "INTERNACIONAL";
            }
            
            return codigoECT;            
        }catch(NumberFormatException e){
            return "CODIGO ECT INVALIDO";
        }
    }
    
}
