package br.com.tecway.gerenciadorloja.utils;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;

/**
 * Monta a tela central
 * 
 * @author Ricardo Cafalchio
 * @since 06/12/2013
 */
@SuppressWarnings("unchecked")
public class CaixaTelaHelper extends MontadorTelaHelper {

	public CaixaTelaHelper(Node nodeRaiz) {
		super(nodeRaiz);
	}

	@Override
	public Node montarTela() {

		final BorderPane borderPaneCaixa = (BorderPane) nodeRaiz;
		final AnchorPane anchorPane = (AnchorPane) borderPaneCaixa.getLeft();
		final VBox vBox = (VBox) anchorPane.getChildren().get(0);
		final TableView<EstoqueTableVO> tableView = (TableView<EstoqueTableVO>) vBox.getChildren().get(0);
		montarTableViewEstoque(tableView);
		return borderPaneCaixa;

	}

	private TableView<EstoqueTableVO> montarTableViewEstoque(TableView<EstoqueTableVO> tableViewEstoque) {

		tableViewEstoque.getColumns().clear();
		tableViewEstoque.getColumns().addAll(CaixaTelaHelper.montarListaColunas());
		return tableViewEstoque;

	}

	public static ObservableList<TableColumn<EstoqueTableVO, String>> montarListaColunas() {

		ObservableList<TableColumn<EstoqueTableVO, String>> listaColunas = FXCollections
				.observableArrayList(new ArrayList<TableColumn<EstoqueTableVO, String>>());

		// Cria a coluna Quantidade
		final TableColumn<EstoqueTableVO, String> columnQuantidade = new TableColumn<EstoqueTableVO, String>("Qtd");
		columnQuantidade
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getQuantidade());
					}
				});

		columnQuantidade.setPrefWidth(30.0);

		final TableColumn<EstoqueTableVO, String> columnMarca = new TableColumn<EstoqueTableVO, String>("Marca");
		columnMarca
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getProdutoTableVO().getMarca());
					}
				});

		columnMarca.setPrefWidth(175.0);

		// Cria a coluna Nome
		final TableColumn<EstoqueTableVO, String> columnNome = new TableColumn<EstoqueTableVO, String>("Nome");
		columnNome
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getProdutoTableVO().getNome());
					}
				});

		columnNome.setPrefWidth(220.0);

		// Cria a coluna código de barras
		final TableColumn<EstoqueTableVO, String> columnCodigoBarras = new TableColumn<EstoqueTableVO, String>(
				"Código de barras");
		columnCodigoBarras
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getCodigoBarras());
					}
				});

		columnCodigoBarras.setPrefWidth(170.0);

		// Cria a coluna Nome
		final TableColumn<EstoqueTableVO, String> columnPreco = new TableColumn<EstoqueTableVO, String>("Preço");
		columnPreco
				.setCellValueFactory(new Callback<CellDataFeatures<EstoqueTableVO, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<EstoqueTableVO, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue().getProdutoTableVO().getPreco());
					}
				});

		columnPreco.setPrefWidth(100.0);

		listaColunas.addAll(columnQuantidade, columnMarca, columnNome, columnCodigoBarras, columnPreco);

		return listaColunas;

	}
}
