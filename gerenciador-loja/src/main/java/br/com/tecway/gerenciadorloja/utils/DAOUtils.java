package br.com.tecway.gerenciadorloja.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 01/03/2013
 */
public class DAOUtils {

	private static EntityManager entityManager; 
	
	/**
	 * Recupera o Entity manager da lojaJPA
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		
		if(entityManager==null){
			
			final EntityManagerFactory emf = Persistence.createEntityManagerFactory("lojaJPA");
			entityManager = emf.createEntityManager();
			
		}
		
		return entityManager;

	}
	
	public static void geraBanco(){
		
//		 EntityManager manager = new JPAUtil().createEntityManager();
//		 manager.close();
		Ejb3Configuration cfg = new Ejb3Configuration();   
		  
		// OBS: lojaJPA eh o nome de meu persistence-unit no persistence.xml.   
		// O configure vai procurar o persistence.xml dentro da pasta META-INF que tem que estar no classpath   
		cfg.configure("lojaJPA", null);   
		Configuration hbmcfg = cfg.getHibernateConfiguration();   
		           
		SchemaExport schemaExport = new SchemaExport(hbmcfg);   
		schemaExport.create(true, true);  

	}

}
