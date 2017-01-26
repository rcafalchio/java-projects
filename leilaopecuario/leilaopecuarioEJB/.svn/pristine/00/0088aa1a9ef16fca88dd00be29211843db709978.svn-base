package com.leilaopecuario.negocio;

import java.util.List;

import javax.ejb.Local;

import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.vo.VacinaVO;

@Local           
public interface GerenciadorVacinaLocal {

	/**
	 * Recupera todas as vacinas cadastradas no banco
	 * 
	 * @return lista de vacinas
	 * @throws LeilaoException
	 */
	public List<VacinaVO> obtemTodasVacinas() throws LeilaoException;

	/**
	 * Recupera o nome das vacinas cadastradas no banco
	 * 
	 * @return lista de nomes de vacinas
	 */
	public List<String> obtemTodasNomesVacinas() throws LeilaoException;
	
	/**
	 * recupera vacina por nome
	 * 
	 * @param nome
	 * @return VacinaVO
	 * @throws LeilaoException
	 */
	public VacinaVO obtemVacinaPorNome(String nome) throws LeilaoException;
}
