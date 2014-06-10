package com.leilaopecuario.negocio;

import java.util.List;

import javax.ejb.Local;

import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.vo.DadosLanceUsuarioVO;
import com.leilaopecuario.vo.LanceVO;
import com.leilaopecuario.vo.UsuarioVO;

@Local
public interface GerenciadorLanceLocal {

	/**
	 * Verifica se o lance enviado é válido e cadastra o lance na tabela
	 * 
	 * @param lanceVO
	 * @return String - Frase de erro de negócio
	 * @throws LeilaoException
	 */
	public String efetuarLance(LanceVO lanceVO) throws LeilaoException;
	
	/**
	 * Através do usuário ele recupera todos os seus lances
	 * 
	 * @param usuarioVO
	 * @return DadosLanceUsuarioVO
	 * @throws LeilaoException 
	 */
	public List<DadosLanceUsuarioVO> recuperaDadosLancesPorUsuario(UsuarioVO usuarioVO) throws LeilaoException;
	
}
