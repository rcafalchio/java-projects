package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorUsuario;
import br.com.tecway.gerenciadorloja.business.IGerenciadorUsuario;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.entity.ContatoEntity;
import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.NumberUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;

/**
 * Controller do cadastro de usuário
 * 
 * @author Ricardo Cafalchio
 * @since 24/06/2013
 */
public class CadastroUsuarioController extends AbstractController {

	private static final Logger LOGGER = LogManager.getLogger(CadastroUsuarioController.class);

	@FXML
	private TextField textNomeUsuario;
	@FXML
	private TextField textCpfUsuario;
	@FXML
	private TextField textTelefoneComercialUsuario;
	@FXML
	private TextField textDDDComercialUsuario;
	@FXML
	private TextField textDDDResidencialUsuario;
	@FXML
	private TextField textTelefoneResidencialUsuario;
	@FXML
	private TextField textTelefoneCelular;
	@FXML
	private TextField textDDDCelular;
	@FXML
	private TextField textLoginUsuario;
	@FXML
	private TextField textSenhaUsuario;
	@FXML
	private TextField textReSenhaUsuario;
	@FXML
	private TextField textEmail;
	@FXML
	private RadioButton radioAdministrador;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
	public void customInitialize() throws ApplicationException {

	}

	/**
	 * Método responsável por validar as informações necessárias para o cadastro do usuário
	 * 
	 * @param usuario
	 * @throws ControllerException
	 */
	public void cadastrarUsuario() throws ControllerException {

		try {

			final List<String> listaMensagens = validarDadosUsuario();

			if (listaMensagens.isEmpty()) {

				final StageAlert alert = new StageAlert(AppConstants.AVISO, "Confirma o cadastro do usuário?",
						Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
				final int response = alert.showAndWaitResponse();

				if (response == 0) {

					final UsuarioEntity usuarioEntity = this.criarUsuario();
					final IGerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario();
					gerenciadorUsuario.cadastrarUsuario(usuarioEntity);
					final StageAlert alertS = new StageAlert(AppConstants.AVISO,
							ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
							AppConstants.BOTAO_OK);
					alertS.show();
				}

			} else {
				CadastroHelper.gerarMensagensCadastro(listaMensagens);
			}

		} catch (BusinessException e) {

			final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
					Boolean.TRUE, AppConstants.BOTAO_OK);
			alert.show();
			LOGGER.error(e.getMensagemNegocio(), e);

		} catch (Exception e) {

			ControllerException.registrarErro(LOGGER, e);

		}
	}

	/**
	 * Valida os dados preenchido para o cadastro do usuário
	 */
	private List<String> validarDadosUsuario() {

		final List<String> lista = new ArrayList<String>();

		// Valida os campos obrigatórios
		if (textNomeUsuario == null || textNomeUsuario.getText().isEmpty()) {
			lista.add(ConstantesMensagens.CAMPO_NOME + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}

		if (textCpfUsuario == null || textCpfUsuario.getText().isEmpty()) {
			lista.add(ConstantesMensagens.CAMPO_CPF + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}

		if (textLoginUsuario == null || textLoginUsuario.getText().isEmpty()) {
			lista.add(ConstantesMensagens.CAMPO_LOGIN + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}

		if (textSenhaUsuario == null || textSenhaUsuario.getText().isEmpty()) {
			lista.add(ConstantesMensagens.CAMPO_SENHA + ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO);
		}

		// Valida se digitou a senha corretamente
		final String senha = TelaUtilitarios.recuperarValorTextField(textSenhaUsuario);
		final String reSenha = TelaUtilitarios.recuperarValorTextField(textReSenhaUsuario);

		if (senha != null && !senha.equals(reSenha)) {
			lista.add(ConstantesMensagens.MENSAGEM_RE_SENHA);
		}

		return lista;
	}

	/**
	 * Método responsável por criar o objeto Usuário com os dados vindos da tela
	 * 
	 * @return Usuario
	 */
	private UsuarioEntity criarUsuario() {

		final UsuarioEntity usuario = new UsuarioEntity();

		try {

			// Seta os contatos
			usuario.setContato(new ContatoEntity());
			// CELULAR
			String campo = TelaUtilitarios.recuperarValorTextField(textDDDCelular);
			usuario.getContato().setDddCelular(NumberUtils.toShort(campo));
			campo = TelaUtilitarios.recuperarValorTextField(textTelefoneCelular);
			usuario.getContato().setTelefoneCelular(NumberUtils.toLong(campo));
			// TEL RESIDENCIAL
			campo = TelaUtilitarios.recuperarValorTextField(textDDDResidencialUsuario);
			usuario.getContato().setDddResidencial(NumberUtils.toShort(campo));
			campo = TelaUtilitarios.recuperarValorTextField(textTelefoneResidencialUsuario);
			usuario.getContato().setTelefoneResidencial(NumberUtils.toLong(campo));
			// TEL COMERCIAL
			campo = TelaUtilitarios.recuperarValorTextField(textTelefoneComercialUsuario);
			usuario.getContato().setDddResidencial(NumberUtils.toShort(campo));
			campo = TelaUtilitarios.recuperarValorTextField(textDDDComercialUsuario);
			usuario.getContato().setTelefoneResidencial(NumberUtils.toLong(campo));
			usuario.getContato().setEmail(TelaUtilitarios.recuperarValorTextField(textEmail));
			// Seta o login
			usuario.setLogin(TelaUtilitarios.recuperarValorTextField(textLoginUsuario));
			usuario.setSenha(TelaUtilitarios.recuperarValorTextField(textSenhaUsuario));
			// Seta os dados básicos do usuário
			usuario.setNome(TelaUtilitarios.recuperarValorTextField(textNomeUsuario));
			campo = TelaUtilitarios.recuperarValorTextField(textCpfUsuario);
			usuario.setCpf(NumberUtils.toLong(campo));
			// Seta o usuário do contato
			usuario.getContato().setUsuario(usuario);
			// Seta se o usuário é administrador
			usuario.setFlagAdministrador(radioAdministrador.isSelected());

		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}

		return usuario;
	}


}
