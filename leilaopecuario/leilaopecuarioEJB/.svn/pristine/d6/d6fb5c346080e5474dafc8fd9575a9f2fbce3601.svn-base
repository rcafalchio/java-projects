package com.leilaopecuario.persistencia;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.leilaopecuario.constantes.Constantes;
import com.leilaopecuario.entidades.Contato;
import com.leilaopecuario.entidades.Empresa;
import com.leilaopecuario.entidades.Endereco;
import com.leilaopecuario.entidades.Estado;
import com.leilaopecuario.entidades.Telefone;
import com.leilaopecuario.entidades.Usuario;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.vo.ContatoVO;
import com.leilaopecuario.vo.TelefoneVO;
import com.leilaopecuario.vo.UsuarioVO;

@Stateless
public class ControladorPersistenciaUsuario implements ControladorPersistenciaUsuarioLocal,
		ControladorPersistenciaUsuarioRemote {

	private static Logger LOGGER = Logger.getLogger(ControladorPersistenciaUsuario.class);

	@PersistenceContext(name = "leilao")
	private EntityManager entityManager;

	@Override
	public void inserirUsuario(UsuarioVO usuarioVO) throws PersistenciaException {

		try {
			LOGGER.info("Vai persistir o usuário");
			// popula as entidades
			// Usuario
			final Usuario usuario = this.populaUsuario(usuarioVO);
			// Empresa
			final Empresa empresa = this.populaEmpresa(usuarioVO);
			// Endereco
			final Endereco endereco = this.populaEndereco(usuarioVO);
			// Contato
			final Contato contato = this.populaContato(usuarioVO);
			// persiste
			usuario.setEmpresa(empresa);
			empresa.setUsuario(usuario);
			usuario.setEndereco(endereco);
			endereco.setUsuario(usuario);
			usuario.setContato(contato);
			contato.setUsuario(usuario);
			entityManager.persist(usuario);

		} catch (Exception e) {
			LOGGER.error("Erro no método inserirUsuario", e);
			throw new PersistenciaException(e);
		}

	}

	@Override
	public boolean verificaExistenciaLogin(String login) throws PersistenciaException {

		boolean retorno = false;

		try {

			final String queryString = "SELECT u.login FROM Usuario u " + "WHERE u.login = :login";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("login", login);
			final String resultado = (String) query.getSingleResult();
			if (resultado != null) {
				retorno = true;
			}

		} catch (NoResultException e) {
			retorno = false;
		} catch (Exception e) {
			LOGGER.error("Erro no método verificaExistenciaLogin", e);
			throw new PersistenciaException(e);
		}
		return retorno;

	}

	/**
	 * Atribui o contato do usuário
	 * 
	 * @param usuarioVO
	 * @return Contato
	 * @throws PersistenciaException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Contato populaContato(UsuarioVO usuarioVO) throws PersistenciaException, IllegalAccessException,
			InvocationTargetException {
		final ContatoVO contatoVO = usuarioVO.getContato();
		final Contato contato = new Contato();
		BeanUtils.copyProperties(contato, contatoVO);
		contato.setTelefones(new ArrayList<Telefone>());
		for (TelefoneVO telefoneVO : contatoVO.getTelefones()) {
			final Telefone telefone = new Telefone();
			BeanUtils.copyProperties(telefone, telefoneVO);
			contato.getTelefones().add(telefone);
			telefone.setContato(contato);
		}
		return contato;
	}

	/**
	 * Atribui o endereco
	 * 
	 * @param usuarioVO
	 * @return Endereco
	 * @throws PersistenciaException
	 */
	private Endereco populaEndereco(UsuarioVO usuarioVO) throws PersistenciaException {
		final Endereco endereco = new Endereco();
		if (usuarioVO != null && usuarioVO.getEmpresa() != null && usuarioVO.getEndereco() != null) {
			endereco.setBairro(usuarioVO.getEndereco().getBairro());
			endereco.setCep(usuarioVO.getEndereco().getCep());
			endereco.setCidade(usuarioVO.getEndereco().getCidade());
			if (usuarioVO.getEndereco().getUf() != null) {
				endereco.setEstado(new Estado());
				endereco.getEstado().setUf(usuarioVO.getEndereco().getUf());
				final List<Endereco> listaEnderecos = new ArrayList<Endereco>();
				listaEnderecos.add(endereco);
				endereco.getEstado().setEnderecos(listaEnderecos);
			}
			if (usuarioVO.getEndereco().getLogradouro() != null) {
				endereco.setLogradouro(usuarioVO.getEndereco().getLogradouro());
			}
		}
		return endereco;
	}

	/**
	 * Atribui a Empresa
	 * 
	 * @param usuarioVO
	 * @return Empresa
	 * @throws PersistenciaException
	 */
	private Empresa populaEmpresa(UsuarioVO usuarioVO) throws PersistenciaException {
		final Empresa empresa = new Empresa();
		if (usuarioVO != null && usuarioVO.getEmpresa() != null) {
			empresa.setCnpj(usuarioVO.getEmpresa().getCnpj());
			empresa.setDataFundacao(usuarioVO.getEmpresa().getDataFundacao());
			empresa.setNomeFantasia(usuarioVO.getEmpresa().getNomeFantasia());
			empresa.setNomeTitular(usuarioVO.getEmpresa().getNomeTitular());
		}
		return empresa;
	}

	/**
	 * Atribui o Usuario
	 * 
	 * @param usuarioVO
	 * @return Usuario
	 * @throws PersistenciaException
	 */
	private Usuario populaUsuario(UsuarioVO usuarioVO) throws PersistenciaException {
		final Usuario usuario = new Usuario();
		try {
			if (usuarioVO != null) {
				usuario.setCpf(usuarioVO.getNumeroCpf());
				usuario.setNome(usuarioVO.getNome());
				usuario.setLogin(usuarioVO.getLogin());
				usuario.setDataNascimento(usuarioVO.getDataNascimento());
				usuario.setSenha(usuarioVO.getSenha());
				usuario.setPapel(Constantes.PAPEL_USUARIO);
			}
		} catch (Exception e) {
			LOGGER.error("Erro no método populaUsuario", e);
			throw new PersistenciaException(e);
		}
		return usuario;
	}

	@Override
	public Usuario recuperaUsuarioPorLogin(String login) throws PersistenciaException {

		Usuario retorno = null;

		try {

			final String queryString = "SELECT u FROM Usuario u " + "WHERE u.login = :login";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("login", login);
			retorno = (Usuario) query.getSingleResult();

		} catch (Exception e) {
			LOGGER.error("Erro no método verificaExistenciaLogin", e);
			throw new PersistenciaException(e);
		}
		return retorno;

	}

}
