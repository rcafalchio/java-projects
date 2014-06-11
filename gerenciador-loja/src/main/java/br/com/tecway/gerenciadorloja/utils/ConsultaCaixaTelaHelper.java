package br.com.tecway.gerenciadorloja.utils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import br.com.tecway.gerenciadorloja.common.CaixaTableVO;

/**
 * Monta a tela central
 * 
 * @author Ricardo Cafalchio
 * @since 06/12/2013
 */
public class ConsultaCaixaTelaHelper extends MontadorTelaHelper {

	public ConsultaCaixaTelaHelper(Node nodeRaiz) {
		super(nodeRaiz);
	}

	@Override
	public Node montarTela() {
		final BorderPane borderPaneCaixa = (BorderPane) nodeRaiz;
		TableView<CaixaTableVO> caixaTableView = null;
		if (SegurancaUtils.getUsuarioEntity().isFlagAdministrador()) {
			final HBox hBox = (HBox) ((VBox) borderPaneCaixa.getBottom()).getChildren().get(0);
			caixaTableView = (TableView) hBox.getChildren().get(0);
		} else {
			final HBox hBox = (HBox) borderPaneCaixa.getBottom();
			caixaTableView = (TableView) hBox.getChildren().get(0);
		}

		montarTableView(caixaTableView);
		return borderPaneCaixa;
	}

	private void montarTableView(TableView<CaixaTableVO> caixaTableView) {

		// Cria a coluna data
		final TableColumn<CaixaTableVO, String> columnData = new TableColumn<CaixaTableVO, String>("Data");
		columnData.setCellValueFactory(new Callback<CellDataFeatures<CaixaTableVO, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(final CellDataFeatures<CaixaTableVO, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getValorData());
			}
		});

		columnData.setPrefWidth(175.0);

		// Cria a coluna dinheiro
		final TableColumn<CaixaTableVO, String> columnDinheiro = new TableColumn<CaixaTableVO, String>(
				"Valor em dinheiro");
		columnDinheiro
				.setCellValueFactory(new Callback<CellDataFeatures<CaixaTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<CaixaTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getValorDinheiro());
					}
				});

		columnDinheiro.setPrefWidth(175.0);

		// Cria a coluna debito
		final TableColumn<CaixaTableVO, String> columnDebito = new TableColumn<CaixaTableVO, String>("Cartão Débito");
		columnDebito
				.setCellValueFactory(new Callback<CellDataFeatures<CaixaTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<CaixaTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getValorDebito());
					}
				});

		columnDebito.setPrefWidth(175.0);

		// Cria a coluna credito
		final TableColumn<CaixaTableVO, String> columnCredito = new TableColumn<CaixaTableVO, String>("Cartão Crédito");
		columnCredito
				.setCellValueFactory(new Callback<CellDataFeatures<CaixaTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<CaixaTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getValorCredito());
					}
				});

		columnCredito.setPrefWidth(175.0);

		caixaTableView.getColumns().addAll(columnData, columnDinheiro, columnDebito, columnCredito);
	}
}
