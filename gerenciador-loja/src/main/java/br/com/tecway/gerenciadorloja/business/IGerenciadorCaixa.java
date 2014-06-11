package br.com.tecway.gerenciadorloja.business;

import java.util.Date;
import java.util.List;

import br.com.tecway.gerenciadorloja.common.CaixaVO;
import br.com.tecway.gerenciadorloja.entity.CaixaEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public interface IGerenciadorCaixa {

	/**
	 * Recupera os valores do caixa
	 * 
	 * @param date
	 * 
	 * @return CaixaVO
	 * @throws DAOException
	 */
	CaixaVO recuperarCaixa(Date date) throws DAOException;

	/**
	 * Pesquisar Caixa de acordo com o período informado
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return List<CaixaEntity>
	 */
	List<CaixaEntity> pesquisarCaixas(Date dataInicial, Date dataFinal) throws BusinessException, DAOException;

	/**
	 * Realiza a retirada de dinheiro do caixa.
	 * 
	 * @param valorRetirada
	 * @throws BusinessException
	 * @throws DAOException
	 */
	void realizarRetirada(Double valorRetirada) throws BusinessException, DAOException;

	/**
	 * Ajusta o dinheiro do caixa.
	 * 
	 * @param novoValorDinheiro
	 * @throws DAOException
	 */
	void ajustarCaixa(Double novoValorDinheiro) throws DAOException;

}
