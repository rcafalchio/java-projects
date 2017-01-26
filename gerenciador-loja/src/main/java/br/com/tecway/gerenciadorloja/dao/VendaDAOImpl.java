package br.com.tecway.gerenciadorloja.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.entity.VendaEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public class VendaDAOImpl extends GenericDAO<VendaEntity> implements VendaDAO {

	public VendaDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<VendaEntity> buscarVendasPorUsuarios(List<UsuarioEntity> usuariosEntity, Date dataInicial,
			Date dataFinal) throws DAOException {
		List<VendaEntity> vendas = null;
		try {
			final Query query = entityManager.createNamedQuery("VendaEntity.buscarVendasPorUsuarios");
			final List<Integer> chaveUsuarios = new ArrayList<Integer>();
			for (UsuarioEntity usuarioEntity : usuariosEntity) {
				chaveUsuarios.add(usuarioEntity.getCodigo());
			}
			query.setParameter("listaUsuarios", chaveUsuarios);
			query.setParameter("dataInicial", dataInicial);
			query.setParameter("dataFinal", dataFinal);
			vendas = query.getResultList();
		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}
		return vendas;
	}

}
