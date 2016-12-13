package br.com.portalpostal.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-13T09:46:07")
@StaticMetamodel(Coleta.class)
public class Coleta_ { 

    public static volatile SingularAttribute<Coleta, String> obs;
    public static volatile SingularAttribute<Coleta, Integer> idColeta;
    public static volatile SingularAttribute<Coleta, Date> dataHoraBaixa;
    public static volatile SingularAttribute<Coleta, Integer> idUsuario;
    public static volatile SingularAttribute<Coleta, Date> dataHoraAguardando;
    public static volatile SingularAttribute<Coleta, Integer> idColetador;
    public static volatile SingularAttribute<Coleta, Integer> tipoSolicitacao;
    public static volatile SingularAttribute<Coleta, Integer> idDepartamento;
    public static volatile SingularAttribute<Coleta, Integer> idCliente;
    public static volatile SingularAttribute<Coleta, Integer> statusEntrega;
    public static volatile SingularAttribute<Coleta, Integer> idContato;
    public static volatile SingularAttribute<Coleta, Integer> idTipo;
    public static volatile SingularAttribute<Coleta, Date> dataHoraColeta;
    public static volatile SingularAttribute<Coleta, Date> dataHoraSolicitacao;
    public static volatile SingularAttribute<Coleta, Integer> status;

}