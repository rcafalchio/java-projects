package com.leilaopecuario.negocio;

import java.io.File;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.leilaopecuario.entidades.InformacoesFotos;
import com.leilaopecuario.entidades.Leilao;
import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.persistencia.ControladorPersistenciaLeilaoLocal;
import com.leilaopecuario.util.LeilaoHelper;
import com.leilaopecuario.vo.LeilaoVO;
import com.leilaopecuario.vo.UsuarioVO;

@Stateless
public class GerenciadorLeilao implements GerenciadorLeilaoLocal {

	private final static Logger LOGGER = Logger.getLogger(GerenciadorLeilao.class);

	@EJB
	private ControladorPersistenciaLeilaoLocal controladorPersistenciaLeilao;

	@EJB
	private ValidadorLeilaoLocal validadorLeilao;

	@Override
	public String gravaLeilao(LeilaoVO leilaoVO) throws PersistenciaException {

		String fraseErro = null;
		try {

			fraseErro = validadorLeilao.validaDataCadastroLeilao(leilaoVO);
			if (fraseErro == null) {
				controladorPersistenciaLeilao.inserirLeilao(leilaoVO);
			}
		} catch (Exception e) {
			LOGGER.error("Ocorreu um problema no m�todo gravaUsuario", e);
			fraseErro = "Problema ao gravar";
			throw new PersistenciaException(e);
		}

		return fraseErro;
	}

	@Override
	public List<LeilaoVO> recuperaLeiloesAtivos() throws LeilaoException {

		List<LeilaoVO> listaLeiloes = null;

		try {
			final List<Leilao> leiloes = controladorPersistenciaLeilao.buscarLeiloes();
			listaLeiloes = new ArrayList<LeilaoVO>();
			// Adiciona somente os leil�es ativos
			for (Leilao leilao : leiloes) {
				if (leilao.getDataEHoraFinal() != null) {
					if (verificaLeilaoAtivo(leilao.getDataEHoraFinal())) {
						listaLeiloes.add(LeilaoHelper.copiaLeilaoParaLeilaoVO(leilao));
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro no m�todo recuperaLeiloesAtivos", e);
		}

		return listaLeiloes;

	}

	private boolean verificaLeilaoAtivo(Date dataEHoraFinal) {

		boolean retorno = false;
		
		try {
			final Format format = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String dataFinal = format.format(dataEHoraFinal);
			String dataAtual = format.format(new Date());
			if (dataFinal.equals(dataAtual)) {
				retorno = true;
			}
			if (dataEHoraFinal.after(new Date())) {
				retorno = true;
			}
		} catch (Exception e) {
			retorno = false;
		}
		return retorno;
	}

	@Override
	public List<String> recuperaFotosLeilao(Integer codigo) throws LeilaoException {

		List<String> listaFotos = null;

		try {
			final InformacoesFotos caminhoFotos = controladorPersistenciaLeilao.obtemCaminhoFoto(codigo);
			String[] arrayArquivos = new File(caminhoFotos.getCaminho()).list();
			listaFotos = Arrays.asList(arrayArquivos);
		} catch (PersistenciaException e) {
			LOGGER.error("Ocorreu um erro no m�todo recuperaFotosLeilao", e);
			throw new LeilaoException(e);
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro no m�todo recuperaFotosLeilao", e);
			throw new LeilaoException(e);
		}

		return listaFotos;
	}

	@Override
	public void atualizarLeilao(LeilaoVO leilaoVO) throws LeilaoException {

		try {
			controladorPersistenciaLeilao.atualizarLeilao(leilaoVO);
		} catch (PersistenciaException e) {
			throw new LeilaoException(e);
		}

	}

	@Override
	public List<LeilaoVO> recuperaLeiloesPorUsuario(UsuarioVO usuarioVO) throws LeilaoException {

		List<LeilaoVO> listaLeilaoVO = null;

		try {
			final List<Leilao> listaLeilao = controladorPersistenciaLeilao
					.obtemLeiloesPorUsuario(usuarioVO.getCodigo());
			listaLeilaoVO = new ArrayList<LeilaoVO>();
			for (Leilao leilao : listaLeilao) {
				listaLeilaoVO.add(LeilaoHelper.copiaLeilaoParaLeilaoVO(leilao));
			}

		} catch (Exception e) {
			throw new LeilaoException(e);
		}
		return listaLeilaoVO;
	}
}
