package br.com.tecway.gerenciadorloja.utils;

import java.util.List;

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
import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.MarcaProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.MarcaProdutoEntity;

public class MarcaTelaHelper extends MontadorTelaHelper {

	public MarcaTelaHelper(Node nodeRaiz) {
		super(nodeRaiz);
	}

	@Override
	public Node montarTela() {

		final BorderPane borderPane = (BorderPane) nodeRaiz;
		final VBox vBox = (VBox) borderPane.getCenter();
		final HBox hBox = (HBox) vBox.getChildren().get(2);
		final MarcaProdutoDAO marcaProdutoDAO = new MarcaProdutoDAOImpl(DAOUtils.getEntityManager());
		final List<MarcaProdutoEntity> lista = marcaProdutoDAO.findAll();
		final TableView<MarcaProdutoEntity> tableView = (TableView<MarcaProdutoEntity>) hBox.getChildren().get(0);
		montarTableViewMarcas(tableView, lista);

		return borderPane;
	}

	private void montarTableViewMarcas(TableView<MarcaProdutoEntity> tableView, List<MarcaProdutoEntity> lista) {

		// Cria a coluna Marca
		final TableColumn<MarcaProdutoEntity, String> columnMarca = new TableColumn<MarcaProdutoEntity, String>("Marca");
		columnMarca
				.setCellValueFactory(new Callback<CellDataFeatures<MarcaProdutoEntity, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(final CellDataFeatures<MarcaProdutoEntity, String> p) {
						return new ReadOnlyObjectWrapper(p.getValue());
					}
				});
		columnMarca.setPrefWidth(401.0);

		tableView.getColumns().addAll(columnMarca);

		for (MarcaProdutoEntity marcaProdutoEntity : lista) {
			tableView.getItems().add(marcaProdutoEntity);
		}


	}

}
