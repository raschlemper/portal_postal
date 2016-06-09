/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.OrderedJSONObject;

public class contrRelatorios {

  
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    public static String montaJson(String sql, SimpleDateFormat sdf, String nomeBd) throws SQLException, JSONException {
        Connection conn = Conexao.conectar(nomeBd);      
        PreparedStatement valores = conn.prepareStatement(sql);
        ResultSet rs = (ResultSet) valores.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        JSONArray json = new JSONArray();

        while (rs.next()) {
            int numColumns = rsmd.getColumnCount();
            Map obj = new LinkedHashMap();
            for (int i = 1; i < numColumns + 1; i++) {
                String column_name = rsmd.getColumnLabel(i);               
                switch (rsmd.getColumnType(i)) {
                    case java.sql.Types.ARRAY:
                        obj.put(column_name, rs.getArray(i));
                        break;
                    case java.sql.Types.BIGINT:
                        obj.put(column_name, rs.getInt(i));
                        break;
                    case java.sql.Types.BOOLEAN:
                        obj.put(column_name, rs.getBoolean(i));
                        break;
                    case java.sql.Types.BLOB:
                        obj.put(column_name, rs.getBlob(i));
                        break;
                    case java.sql.Types.DOUBLE:
                        if(column_name.startsWith("$")){
                            obj.put(column_name, df.format(rs.getDouble(i)));
                        }else{
                             obj.put(column_name, rs.getDouble(i));
                        }                        
                        break;
                    case java.sql.Types.FLOAT:
                        if(column_name.startsWith("$")){
                        obj.put(column_name, df.format(rs.getFloat(i)));
                        }else{
                            obj.put(column_name, rs.getFloat(i));
                        }
                        break;
                    case java.sql.Types.DECIMAL:
                        if(column_name.startsWith("$")){
                        obj.put(column_name, df.format(rs.getObject(i)));
                        }else{
                            obj.put(column_name, rs.getFloat(i));
                        }
                        break;
                    case java.sql.Types.INTEGER:
                        obj.put(column_name, rs.getInt(i));
                        break;
                    case java.sql.Types.NVARCHAR:
                        obj.put(column_name, rs.getNString(i));
                        break;
                    case java.sql.Types.VARCHAR:
                        obj.put(column_name, rs.getString(i));
                        break;
                    case java.sql.Types.TINYINT:
                        obj.put(column_name, rs.getInt(i));
                        break;
                    case java.sql.Types.SMALLINT:
                        obj.put(column_name, rs.getInt(i));
                        break;
                    case java.sql.Types.DATE:
                        obj.put(column_name, sdf.format(rs.getDate(i)));
                        break;
                    case java.sql.Types.TIMESTAMP:
                        obj.put(column_name, sdf.format(rs.getTimestamp(i)));
                        break;
                    default:
                        obj.put(column_name, rs.getObject(i));
                        break;
                }
            }
            JSONObject jsonObject = new OrderedJSONObject(obj);
            json.put(jsonObject);
        }
        return json.toString();
    }

}
