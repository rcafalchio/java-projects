package br.com.tecway.gerenciadorloja.dao;

import java.util.Date;
import java.util.List;

import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.entity.VendaEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public interface VendaDAO extends IGenericDAO<VendaEntity> {

	/**
	 * 
	 * 
	 * @param usuariosEntity
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @throws DAOException
	 */
	List<VendaEntity> buscarVendasPorUsuarios(List<UsuarioEntity> usuariosEntity, Date dataInicial, Date dataFinal)
			throws DAOException;

}
