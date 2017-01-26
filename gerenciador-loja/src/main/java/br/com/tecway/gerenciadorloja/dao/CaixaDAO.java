package br.com.tecway.gerenciadorloja.dao;

import java.util.Date;
import java.util.List;

import br.com.tecway.gerenciadorloja.entity.CaixaEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 13/12/2013
 */
public interface CaixaDAO extends IGenericDAO<CaixaEntity> {

	/**
	 * Busca os caixas de determinado período
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return List<CaixaEntity>
	 * @throws DAOException
	 */
	List<CaixaEntity> buscarCaixas(Date dataInicial, Date dataFinal) throws DAOException;

	/**
	 * Busca o último caixa
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @throws DAOException
	 */
	CaixaEntity buscarUltimoCaixa() throws DAOException;

}
