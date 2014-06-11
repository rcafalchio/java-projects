package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorUsuario;
import br.com.tecway.gerenciadorloja.business.GerenciadorVenda;
import br.com.tecway.gerenciadorloja.business.IGerenciadorUsuario;
import br.com.tecway.gerenciadorloja.business.IGerenciadorVenda;
import br.com.tecway.gerenciadorloja.common.UsuarioVO;
import br.com.tecway.gerenciadorloja.common.VendaVO;
import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.fx.components.StagePopup;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.DataUtils;
import br.com.tecway.gerenciadorloja.utils.ModalLoadUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;
import br.com.tecway.gerenciadorloja.utils.VendaTelaHelper;

public class ConsultaVendasController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(ConsultaVendasController.class);

	@FXML
	private ListView<UsuarioVO> vendedoresListView;
	@FXML
	private ListView<UsuarioVO> selecionadosListView;
	@FXML
	protected TableView<VendaVO> vendasTableView;
	@FXML
	private TextField dataInicialText;
	@FXML
	private TextField dataFinalText;
	@FXML
	private Accordion vendasAccordion;
	@FXML
	protected StagePopup popupConsultarProduto = null;
	@FXML
	protected StagePopup popupExportarRelatório = null;
	@FXML
	private CheckBox agruparCheckBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataInicialText.setText(DataUtils.dataSistemaAgora());
		dataFinalText.setText(DataUtils.dataSistemaAgora());
		final IGerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario();
		final List<UsuarioEntity> listaUsuariosEntity = gerenciadorUsuario.buscarTodosUsuarios();
		UsuarioVO usuarioVO = null;
		for (UsuarioEntity usuarioEntity : listaUsuariosEntity) {
			usuarioVO = new UsuarioVO();
			usuarioVO.setCodigo(usuarioEntity.getCodigo());
			usuarioVO.setNome(usuarioEntity.getNome());
			vendedoresListView.getItems().add(usuarioVO);
		}
		vendasTableView.setTooltip(new Tooltip("Clique duas vezes para ver os produtos desta venda!"));
		
		vendasTableView.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2
							&& vendasTableView.getSelectionModel().getSelectedItem() != null) {
						carregarPopupProdutos();
					}
				}
			}
		});
	}

	@Override
	public void customInitialize() throws ApplicationException {
	}

	public void pesquisar() {
		ModalLoadUtils.getInstance().showModalLoad();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final IGerenciadorVenda gerenciadorVenda = new GerenciadorVenda();
					final List<VendaVO> listaVendas = gerenciadorVenda.buscarVendas(selecionadosListView.getItems(),
							TelaUtilitarios.recuperarDataTextField(dataInicialText),
							TelaUtilitarios.recuperarDataTextField(dataFinalText));
					if (listaVendas == null || listaVendas.isEmpty()) {
						vendasTableView.getItems().clear();
						final StageAlert alert = new StageAlert(AppConstants.AVISO,
								"Não foi encontrado vendas neste período!", Severity.INFO, Boolean.TRUE,
								AppConstants.BOTAO_OK);
						alert.show();
					} else {
						vendasTableView.getItems().clear();
						vendasTableView.getColumns().clear();
						if (agruparCheckBox.isSelected()) {
							agruparPorData(listaVendas);
							VendaTelaHelper.montarVendaTableView(vendasTableView, listaVendas, Boolean.TRUE);
						} else {
							VendaTelaHelper.montarVendaTableView(vendasTableView, listaVendas, Boolean.FALSE);
						}
					}

				} catch (BusinessException e) {
					final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
							Boolean.TRUE, AppConstants.BOTAO_OK);
					alert.show();
					LOGGER.error(e.getMensagemNegocio(), e);
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

			private void agruparPorData(List<VendaVO> listaVendas) {
				final List<VendaVO> listaAgrupada = new ArrayList<VendaVO>();
				for (VendaVO vendaVO : listaVendas) {
					agrupar(vendaVO, listaAgrupada);
				}
				listaVendas.clear();
				listaVendas.addAll(listaAgrupada);
			}

			private void agrupar(VendaVO vendaVO, List<VendaVO> listaAgrupada) {
				boolean achou = false;
				for (VendaVO vendaAgrupada : listaAgrupada) {
					if (vendaVO.getDataVenda().equals(vendaAgrupada.getDataVenda())) {
						somarValores(vendaVO, vendaAgrupada);
						achou = true;
					}
				}
				if (!achou) {
					listaAgrupada.add(vendaVO);
				}
			}

			private void somarValores(VendaVO vendaVO, VendaVO vendaAgrupada) {
				vendaAgrupada.setValorBruto(vendaAgrupada.getValorBruto() + vendaVO.getValorBruto());
				vendaAgrupada.setValorLiquido(vendaAgrupada.getValorLiquido() + vendaVO.getValorLiquido());
			}
		});
	}

	private void carregarPopupProdutos() {
		try {
			popupConsultarProduto = new StagePopup("Finalizar compra", TelaUtilitarios.loaderPopupFXML(
					AppConstants.PAGINA_POPUP_PRODUTO_VENDA, this), Modality.APPLICATION_MODAL, 800.0, 310.0, Boolean.FALSE,
					Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
			popupConsultarProduto.show();
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}

	}

	public void exportar() {
		try {
			this.popup = new StagePopup("Exportar Dados", TelaUtilitarios.loaderPopupFXML(
					AppConstants.PAGINA_POPUP_GERAR_ARQUIVO, this), Modality.APPLICATION_MODAL, 800.0, 310.0,
					Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
			super.popup = this.popup;
			this.popup.show();
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	public void adiconarVendedor() {
		if (this.vendedoresListView.getSelectionModel().getSelectedItem() != null
				&& !selecionadosListView.getItems().contains(
						(this.vendedoresListView.getSelectionModel().getSelectedItem()))) {
			this.selecionadosListView.getItems().add(this.vendedoresListView.getSelectionModel().getSelectedItem());
			this.vendedoresListView.getItems().remove(this.vendedoresListView.getSelectionModel().getSelectedItem());
			this.vendedoresListView.getSelectionModel().selectNext();
		}
	}

	public void removerVendedor() {
		if (this.selecionadosListView.getSelectionModel().getSelectedItem() != null
				&& !vendedoresListView.getItems().contains(
						this.selecionadosListView.getSelectionModel().getSelectedItem())) {
			this.vendedoresListView.getItems().add(this.selecionadosListView.getSelectionModel().getSelectedItem());
			this.selecionadosListView.getItems()
					.remove(this.selecionadosListView.getSelectionModel().getSelectedItem());
			this.selecionadosListView.getSelectionModel().selectNext();
		}
	}

}
