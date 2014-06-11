package br.com.tecway.gerenciadorloja.business;

import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;
import br.com.tecway.gerenciadorloja.common.FechamentoVendaVO;
import br.com.tecway.gerenciadorloja.common.UsuarioVO;
import br.com.tecway.gerenciadorloja.common.VendaVO;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public interface IGerenciadorVenda {


	/**
	 * 
	 */
	void fecharVenda(final FechamentoVendaVO ferchamentoVendaVO) throws BusinessException, DAOException;

	/**
	 * Busca as vendas dos usuários informados
	 * 
	 * @param usuarios
	 * @param dataInicial
	 * @param dataFinal
	 * @return List<VendaVO>
	 * @throws ConverterException
	 * @throws DAOException
	 * @throws BusinessException
	 */
	List<VendaVO> buscarVendas(ObservableList<UsuarioVO> usuarios, Date dataInicial, Date dataFinal)
			throws ConverterException, DAOException, BusinessException;

}
