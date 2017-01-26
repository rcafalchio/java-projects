package com.leilaopecuario.negocio;

import java.util.List;

import javax.ejb.Local;

import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.vo.LeilaoVO;
import com.leilaopecuario.vo.UsuarioVO;

@Local
public interface GerenciadorLeilaoLocal {

	/**
	 * Persiste o leilão
	 * 
	 * @param leilaoVO
	 * @throws PersistenciaException
	 */
	public String gravaLeilao(LeilaoVO leilaoVO) throws PersistenciaException;

	/**
	 * Recupera uma lista de leilões ativos
	 * 
	 * @return lista de leilões
	 * @throws LeilaoException
	 */
	public List<LeilaoVO> recuperaLeiloesAtivos() throws LeilaoException;

	/**
	 * Recupera a lista dos caminhos das fotos do leilão
	 * 
	 * @param codigo
	 * @return Lista de fotos
	 * @throws LeilaoException
	 */
	public List<String> recuperaFotosLeilao(Integer codigo) throws LeilaoException;

	/**
	 * Atualiza o leilão
	 * 
	 * @param leilaoVO
	 * @throws PersistenciaException
	 * @throws LeilaoException
	 */
	public void atualizarLeilao(LeilaoVO leilaoVO) throws LeilaoException;

	/**
	 * Recupera os leilões cadastrados pelo usuário
	 * 
	 * @param usuarioVO
	 * @return lista de leilões
	 * @throws LeilaoException 
	 */
	public List<LeilaoVO> recuperaLeiloesPorUsuario(UsuarioVO usuarioVO) throws LeilaoException;

}
