package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorUsuario;
import br.com.tecway.gerenciadorloja.business.IGerenciadorUsuario;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.fx.launcher.GerenciadorLojaApplication;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.ModalLoadUtils;
import br.com.tecway.gerenciadorloja.utils.SegurancaUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;


/**
 * Classe que controla as ações de login
 * 
 * @author Ricardo Cafalchio
 * @since 28/02/2013
 */
public class LoginController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	@FXML
	private TextField textUsuario;

	@FXML
	private TextField textSenha;

	/**
	 * Efetua o login
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void efetuarLoginEvent(final ActionEvent event) {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					// Efetua a validação dos campos
					if (validarDados()) {
						// Efetua a autenticação
						final UsuarioEntity usuarioEntity = autenticarLogin();
						if (usuarioEntity != null) {
							SegurancaUtils.setUsuarioEntity(usuarioEntity);
							getApplication().chamarTelaPrincipal();
						}
					} else {
						final StageAlert alert = new StageAlert(AppConstants.AVISO,
								ConstantesMensagens.MENSAGEM_GENERICA_ERRO_PREENCHIMENTO, Severity.ERROR, Boolean.TRUE,
								AppConstants.BOTAO_OK);
						alert.show();
					}
				} catch (Exception e) {
					ControllerException.registrarErro(LOGGER, e);
				} finally {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ModalLoadUtils.getInstance().hideModalLoad();
						}
					});
				}
			}
		});
	}

	/**
	 * Efetua as validações para autenticação do usuário no sistema
	 */
	private UsuarioEntity autenticarLogin() throws ApplicationException {

		UsuarioEntity retorno = null;

		try {
			final IGerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario();
			retorno = gerenciadorUsuario.autenticarUsuario(TelaUtilitarios.recuperarValorTextField(textUsuario),
					TelaUtilitarios.recuperarValorTextField(textSenha));
		} catch (BusinessException e) {
			final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
					Boolean.TRUE, AppConstants.BOTAO_OK);
			alert.show();
			LOGGER.error(e.getMensagemNegocio(), e);
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}


		return retorno;
	}

	private boolean validarDados() {

		final List<String> listaMensagens = new ArrayList<String>();

		boolean retorno = true;

		if ("".equals(TelaUtilitarios.recuperarValorTextField(textUsuario))) {

			listaMensagens.add("Digite o usuário!");
			retorno = false;

		}

		if ("".equals(TelaUtilitarios.recuperarValorTextField(textSenha))) {

			listaMensagens.add("Digite a senha!");
			retorno = false;

		}

		final VBox errosVbox = CadastroHelper.montarGridErros(listaMensagens, Pos.TOP_CENTER);
		final HBox hBox = (HBox) ((AnchorPane) GerenciadorLojaApplication.getScene().getRoot()).getChildren().get(0);
		final VBox vBox = (VBox) hBox.getChildren().get(0);

		// Verifica se já contém o Vbox
		if (vBox.getChildren().size() > 3) {
			vBox.getChildren().remove(3);
		}

		vBox.getChildren().add(errosVbox);

		return retorno;

	}

	public void cadastroNovoUsuario() {
		getApplication().chamarTelaNovoUsuario();
		super.carregarTelaCadastroUsuarioNovo(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void customInitialize() throws ApplicationException {
		// TODO Auto-generated method stub
	}

	/**
	 * Adiciona o produto com o ENTER
	 * 
	 * @param event
	 */
	public void efetuarLogin(final KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			this.efetuarLoginEvent(null);
		}
	}

}
