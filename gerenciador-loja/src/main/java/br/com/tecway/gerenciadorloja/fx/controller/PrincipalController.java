package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.constants.TipoEstoqueEnum;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.fx.launcher.GerenciadorLojaApplication;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.ConsultaCaixaTelaHelper;
import br.com.tecway.gerenciadorloja.utils.CaixaTelaHelper;
import br.com.tecway.gerenciadorloja.utils.DataUtils;
import br.com.tecway.gerenciadorloja.utils.EstoqueTelaHelper;
import br.com.tecway.gerenciadorloja.utils.MarcaTelaHelper;
import br.com.tecway.gerenciadorloja.utils.MenuHelper;
import br.com.tecway.gerenciadorloja.utils.ModalLoadUtils;
import br.com.tecway.gerenciadorloja.utils.ProdutoTelaHelper;
import br.com.tecway.gerenciadorloja.utils.SegurancaUtils;
import br.com.tecway.gerenciadorloja.utils.VisualUtils;

/**
 * Controller da tela principal do sistema
 * 
 * @author Ricardo Cafalchio
 * @since 19/06/2013
 */
public class PrincipalController extends AbstractController {

	private static final Logger LOGGER = LogManager.getLogger(PrincipalController.class);

	@FXML
	private VBox homeVbox;

	@FXML
	private MenuBar principalMenuBar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Monta o Header
		if (SegurancaUtils.getUsuarioEntity() != null) {
			if (homeVbox != null) {
				final Label tituloLabel = new Label("TecWay");
				tituloLabel.setId("welcome-text");
				final Label dataLabel = new Label("Data :" + DataUtils.dataSistemaAgora());
				final Label usuarioLabel = new Label("Usuário: " + SegurancaUtils.getUsuarioEntity().getNome());
				dataLabel.setId("label-padrao");
				usuarioLabel.setId("label-padrao");
				dataLabel.setContentDisplay(ContentDisplay.LEFT);
				usuarioLabel.setContentDisplay(ContentDisplay.LEFT);
				final GridPane gridPane = new GridPane();
				gridPane.addRow(0, tituloLabel);
				gridPane.addRow(1, dataLabel);
				gridPane.addRow(2, usuarioLabel);
				homeVbox.getChildren().addAll(gridPane);
			}
		}

		if (principalMenuBar != null) {
			// montarMenu
			MenuHelper.montarMenuSistema(principalMenuBar, this);
		}

	}

	@Override
	public void customInitialize() throws ApplicationException {

	}

	/**
	 * Sai do sistema
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void sair(final ActionEvent event) {
		SegurancaUtils.limparUsuario();
		getApplication().requestCloseApp(event);
	}

	/**
	 * Efetua o logoff
	 * 
	 * @param event
	 */
	public void efetuarLogoff(final ActionEvent event) {

		final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja efetuar logoff?", Severity.WARN,
				Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
		final int response = alert.showAndWaitResponse();

		if (response == 0) {

			SegurancaUtils.limparUsuario();
			getApplication().chamarLogin();

		}
	}

	/**
	 * Chama a tela de cadastro de usuário
	 * 
	 * @param event
	 */
	public void carregarTelaCadastroUsuario(final ActionEvent event) {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPane = ((BorderPane) nodeBorderPane);
					// Carrega a página de cadastro do usuário
					final BorderPane borderPaneCadastro = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CADASTRO_USUARIO, PrincipalController.this.getClass(), getApplication());
					// Adiciona os itens na tela
					borderPane.setCenter(borderPaneCadastro);
					// Adiciona o botão
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
	 * Chama a tela de fluxo de caixa
	 * 
	 * @param event
	 */
	public void carregarTelaFluxoCaixa(final ActionEvent event) {
		ModalLoadUtils.getInstance().showModalLoad();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de caixa
					final BorderPane borderPaneCaixa = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_FLUXO_CAIXA, PrincipalController.this.getClass(), getApplication());
					final CaixaTelaHelper caixaTelaHelper = new CaixaTelaHelper(borderPaneCaixa);
					// Adiciona os itens na tela principal
					borderPaneCadastro.setCenter(caixaTelaHelper.montarTela());
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
	 * Chama a tela de controle de caixa
	 * 
	 * @param event
	 */
	public void carregarTelaCaixa(final ActionEvent event) {
		ModalLoadUtils.getInstance().showModalLoad();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de caixa
					final BorderPane borderPaneCaixa = (BorderPane) VisualUtils.loaderFXML(AppConstants.PAGINA_CAIXA,
							PrincipalController.this.getClass(), getApplication());
					// final ConsultaCaixaTelaHelper caixaTelaHelper = new ConsultaCaixaTelaHelper(borderPaneCaixa);
					// Adiciona os itens na tela principal
					// borderPaneCadastro.setCenter(caixaTelaHelper.montarTela());
					borderPaneCadastro.setCenter(borderPaneCaixa);
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
	 * Chama a tela de controle de caixa
	 * 
	 * @param event
	 */
	public void carregarTelaCaixaAdministrativo(final ActionEvent event) {
		ModalLoadUtils.getInstance().showModalLoad();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de caixa
					final BorderPane borderPaneCaixa = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CAIXA_ADMINISTRATIVO, PrincipalController.this.getClass(),
							getApplication());
					// final ConsultaCaixaTelaHelper caixaTelaHelper = new ConsultaCaixaTelaHelper(borderPaneCaixa);
					// Adiciona os itens na tela principal
					// borderPaneCadastro.setCenter(caixaTelaHelper.montarTela());
					borderPaneCadastro.setCenter(borderPaneCaixa);
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

	public void carregarTelaConsultaCaixa(ActionEvent event) {
		ModalLoadUtils.getInstance().showModalLoad();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de caixa
					final BorderPane borderPaneCaixa = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CONSULTA_CAIXA, PrincipalController.this.getClass(), getApplication());
					final ConsultaCaixaTelaHelper caixaTelaHelper = new ConsultaCaixaTelaHelper(borderPaneCaixa);
					// Adiciona os itens na tela principal
					borderPaneCadastro.setCenter(caixaTelaHelper.montarTela());
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
	 * Chama a tela de cadastro de marca
	 * 
	 * @param event
	 */
	public void carregarTelaCadastroMarca(final ActionEvent event) {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {

					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de cadastro de marcas
					final BorderPane borderPaneMarca = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CADASTRO_MARCA, PrincipalController.this.getClass(), getApplication());
					final MarcaTelaHelper marcaTelaHelper = new MarcaTelaHelper(borderPaneMarca);
					// Adiciona os itens na tela
					borderPaneCadastro.setCenter(marcaTelaHelper.montarTela());

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
	 * Chama a tela do estoque
	 * 
	 * @param event
	 */
	public void carregarTelaEstoque(final ActionEvent event) {
		ModalLoadUtils.getInstance().showModalLoad();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de estoque
					final BorderPane borderPaneEstoque = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_ESTOQUE, PrincipalController.this.getClass(), getApplication());
					final VBox vBox = (VBox) borderPaneEstoque.getCenter();
					final HBox hBox = (HBox) vBox.getChildren().get(0);
					final VBox VBoxTableView = (VBox) hBox.getChildren().get(0);
					final TableView<EstoqueTableVO> estoqueTableView = (TableView<EstoqueTableVO>) VBoxTableView
							.getChildren().get(1);
					EstoqueTelaHelper.criarTableView(TipoEstoqueEnum.LOJA, estoqueTableView);
					// Adiciona os itens na tela
					borderPaneCadastro.setCenter(borderPaneEstoque);
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
	 * Chama a tela do estoque
	 * 
	 * @param event
	 */
	public void carregarTelaEstoqueCasa(final ActionEvent event) {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de estoque
					final BorderPane borderPaneEstoque = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_ESTOQUE_CASA, PrincipalController.this.getClass(), getApplication());
					final VBox vBox = (VBox) borderPaneEstoque.getCenter();
					final HBox hBox = (HBox) vBox.getChildren().get(0);
					final VBox VBoxTableView = (VBox) hBox.getChildren().get(0);
					final TableView<EstoqueTableVO> estoqueTableView = (TableView<EstoqueTableVO>) VBoxTableView
							.getChildren().get(1);
					EstoqueTelaHelper.criarTableView(TipoEstoqueEnum.CASA, estoqueTableView);
					// Adiciona os itens na tela
					borderPaneCadastro.setCenter(borderPaneEstoque);
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
	 * Chama a tela de cadastro de produto
	 * 
	 * @param event
	 */
	public void carregarTelaCadastroProduto(final ActionEvent event) {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {

					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPaneCadastro = ((BorderPane) nodeBorderPane);
					// Carrega a página de cadastro do usuário
					final BorderPane borderPaneProdutos = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CADASTRO_PRODUTO, PrincipalController.this.getClass(), getApplication());
					final ProdutoTelaHelper produtoTelaHelper = new ProdutoTelaHelper(borderPaneProdutos);
					// Adiciona os itens na tela
					borderPaneCadastro.setCenter(produtoTelaHelper.montarTela());


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
	 * Chama a tela de relatorio de estoque
	 * 
	 * @param event
	 */
	public void carregarTelaConsultaEstoque(final ActionEvent event) {
		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPanePrincipal = ((BorderPane) nodeBorderPane);
					// Carrega a página de cadastro do usuário
					final BorderPane borderPaneProdutos = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CONSULTA_ESTOQUE, PrincipalController.this.getClass(), getApplication());
					// Adiciona os itens na tela
					borderPanePrincipal.setCenter(borderPaneProdutos);
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
	 * Chama a tela de relatorio de vendas
	 * 
	 * @param event
	 */
	public void carregarTelaConsultaVendas(final ActionEvent event) {
		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPanePrincipal = ((BorderPane) nodeBorderPane);
					// Carrega a página de cadastro do usuário
					final BorderPane borderPaneProdutos = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CONSULTA_VENDAS, PrincipalController.this.getClass(), getApplication());
					// Adiciona os itens na tela
					borderPanePrincipal.setCenter(borderPaneProdutos);
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

	public void carregarTelaCadastroUsuarioNovo(final ActionEvent event) {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPane = ((BorderPane) nodeBorderPane);
					// Carrega a página de cadastro do usuário
					final BorderPane borderPaneCadastro = (BorderPane) VisualUtils.loaderFXML(
							AppConstants.PAGINA_CADASTRO_USUARIO_NOVO, PrincipalController.this.getClass(),
							getApplication());
					// Adiciona os itens na tela
					borderPane.setCenter(borderPaneCadastro);
					// Adiciona o botão
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

	public void carregarTelaTrocas(ActionEvent event) {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot())
							.getChildren().get(0);
					final BorderPane borderPane = ((BorderPane) nodeBorderPane);
					// Carrega a página de cadastro do usuário
					final BorderPane borderPaneTroca = (BorderPane) VisualUtils.loaderFXML(AppConstants.PAGINA_TROCAS,
							PrincipalController.this.getClass(), getApplication());
					// Adiciona os itens na tela
					borderPane.setCenter(borderPaneTroca);
					// Adiciona o botão
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


}
