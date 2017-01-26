package br.com.tecway.gerenciadorloja.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.tecway.gerenciadorloja.entity.CaixaEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public class CaixaDAOImpl extends GenericDAO<CaixaEntity> implements CaixaDAO {

	public CaixaDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<CaixaEntity> buscarCaixas(Date dataInicial, Date dataFinal) throws DAOException {
		List<CaixaEntity> caixas = new ArrayList<CaixaEntity>();
		try {
			final Query query = entityManager.createNamedQuery("CaixaEntity.buscarCaixas");
			query.setParameter("dataInicial", dataInicial);
			query.setParameter("dataFinal", dataFinal);
			caixas = query.getResultList();
		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}
		return caixas;

	}

	@Override
	public CaixaEntity buscarUltimoCaixa() throws DAOException {
		CaixaEntity caixaEntity = null;
		try {
			final Query query = entityManager.createNamedQuery("CaixaEntity.buscarUltimoCaixa");
			caixaEntity = (CaixaEntity) query.getSingleResult();
		} catch (NoResultException e) {
			caixaEntity = null;
		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}
		return caixaEntity;
	}

}
