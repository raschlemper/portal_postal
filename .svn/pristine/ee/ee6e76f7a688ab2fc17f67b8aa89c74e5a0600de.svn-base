
package br.com.portalpostal.providers;

import java.util.ArrayList;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class Provider {

    protected Object getSingleResult(TypedQuery query){
        Object object = null;
         try {
            object =   query.getSingleResult();
        } catch (NoResultException resultException) {
            object = null;
        }
        
         return object;
    }

    protected Object getResultList(TypedQuery query){
        Object object;
         try {
            object =  query.getResultList();
        } catch (NoResultException resultException) {
            object = new ArrayList<>();
        }

         return object;
    }
   
}
