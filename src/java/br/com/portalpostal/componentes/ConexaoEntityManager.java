package br.com.portalpostal.componentes;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexaoEntityManager {


    public static String DBPORTALPOSTAL="portalpostal";
    public static String PROPERTIESNOMEDB="br.com.portalpostal.nomedb";
    private static Map<String, EntityManager> poolConnection;
    private static final String SERVER ="scc4-ricardinho";
    private static final String PORT="3306";
    private static final String USER="root";
    private static final String PASS="1s2c3c4";
    private static final String PREFIXODATABASE = "pp_";
    private static final boolean LOGSQL = false;
    

    public static EntityManager getConnection(String nomeDB) {
        return new ConexaoEntityManager().conexao(nomeDB);
    }

    public  EntityManager conexao(String nomeDB) {
        if (poolConnection == null) {
            poolConnection = new HashMap<>();
        }
        EntityManager entityManager = poolConnection.get(nomeDB);
        
        if (entityManager == null || !entityManager.isOpen()) {
            poolConnection.put(nomeDB,criaEntityManager(nomeDB));
            entityManager = poolConnection.get(nomeDB);
        }
        return entityManager;
    }

    private EntityManager criaEntityManager(String nomeDB) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Portal_Postal",changeDatabase(nomeDB));
        return factory.createEntityManager();
    }

   private Map<String, String> changeDatabase(String dataBase){
        Map<String, String> persistenceMap = new HashMap<>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:mysql://"+SERVER+":"+PORT+"/"+getDataBaseName(dataBase)+"?zeroDateTimeBehavior=convertToNull");
        persistenceMap.put(PROPERTIESNOMEDB,dataBase);
        persistenceMap.put("javax.persistence.jdbc.user", USER);
        persistenceMap.put("javax.persistence.jdbc.password", PASS);
        persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        if (LOGSQL) {
           persistenceMap.put("eclipselink.logging.level", "WARNING");
           persistenceMap.put("eclipselink.logging.parameters", "true");
           persistenceMap.put("eclipselink.logging.level.sql", "ALL");
       }
        return persistenceMap;
    }

   private String getDataBaseName(String bancoDados){
       if(DBPORTALPOSTAL.equals(bancoDados)){
           return DBPORTALPOSTAL;
       }
       return PREFIXODATABASE+bancoDados;

   }

}
