package br.com.tecway.gerenciadorloja.fx.components;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StagePopup extends Stage {

	// logger
	private static final Logger LOGGER = LogManager.getLogger(StagePopup.class);

	// static properties
	private static final double MIN_STAGE_WIDTH = 50;
	private static final double MIN_STAGE_HEIGHT = 50;

	// properties
	private final ObjectProperty<Object> answer = new SimpleObjectProperty<Object>();
	private final StringProperty popupTitle = new SimpleStringProperty();
	private final ObjectProperty<Node> content = new SimpleObjectProperty<Node>();
	private final ObjectProperty<Modality> popupModality = new SimpleObjectProperty<Modality>();
	private final DoubleProperty sceneWidth = new SimpleDoubleProperty();
	private final DoubleProperty sceneHeight = new SimpleDoubleProperty();
	private final BooleanProperty resizable = new SimpleBooleanProperty();
	private final BooleanProperty closable = new SimpleBooleanProperty();
	private final BooleanProperty movable = new SimpleBooleanProperty();
	private final BooleanProperty hideOnFocusLost = new SimpleBooleanProperty();
	private final BooleanProperty lightBoxMode = new SimpleBooleanProperty();

	private final DoubleProperty restoreSceneWidth = new SimpleDoubleProperty();
	private final DoubleProperty restoreSceneHeight = new SimpleDoubleProperty();
	private final DoubleProperty restoreSceneX = new SimpleDoubleProperty();
	private final DoubleProperty restoreSceneY = new SimpleDoubleProperty();

	private Pane root = null;
	private BorderPane borderPane = null;

	// mouse Properties
	private double initX;
	private double initY;
	private double initDimX;
	private double initDimY;
	private double initWidth;
	private double initHeight;

	private Boolean isMaximized = Boolean.FALSE;
	private Label minimizeLabel;
	private Label maximizeLabel;
	private Label restoreLabel;
	private Label closeLabel;

	/* Adicionado para poder testar Popups sem dar NullPointer caso ainda não tenha sido instanciado algum popup */
	/**
	 * Construtor
	 */
	public StagePopup() {

	}

	/* default constructors */
	/**
	 * Construtor
	 * 
	 * @param content
	 *            - Conteúdo
	 * @param sceneWidth
	 *            - Largura
	 * @param sceneHeight
	 *            - Altura
	 */
	public StagePopup(final Node content, final Double sceneWidth, final Double sceneHeight) {
		this(null, content, sceneWidth, sceneHeight);
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param content
	 *            - Conteúdo
	 * @param sceneWidth
	 *            - Largura
	 * @param sceneHeight
	 *            - Altura
	 */
	public StagePopup(final String title, final Node content, final Double sceneWidth, final Double sceneHeight) {
		this(title, content, Modality.NONE, sceneWidth, sceneHeight);
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param content
	 *            - Conteúdo
	 * @param modality
	 *            - {@link Modality}
	 * @param sceneWidth
	 *            - Largura
	 * @param sceneHeight
	 *            - Altura
	 */
	public StagePopup(final String title, final Node content, final Modality modality, final Double sceneWidth,
			final Double sceneHeight) {
		this(title, content, modality, sceneWidth, sceneHeight, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE,
				Boolean.FALSE, Boolean.FALSE);
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param content
	 *            - Conteúdo
	 * @param modality
	 *            - {@link Modality}
	 * @param sceneWidth
	 *            - Largura
	 * @param sceneHeight
	 *            - Altura
	 * @param resizable
	 *            - {@link Boolean} pode redimensionar
	 * @param closable
	 *            - {@link Boolean} pode fechar
	 * @param movable
	 *            - {@link Boolean} pode mover
	 * @param hideOnFocusLost
	 *            - {@link Boolean} fecha ao perder foco
	 * @param lightBoxMode
	 *            - {@link Boolean} modo lightbox
	 */
	public StagePopup(final String title, final Node content, final Modality modality, final Double sceneWidth,
			final Double sceneHeight, final Boolean resizable, final Boolean closable, final Boolean movable,
			final Boolean hideOnFocusLost, final Boolean lightBoxMode) {
		this.popupTitle.set(title);
		this.content.set(content);
		this.popupModality.set(modality);
		this.sceneWidth.set(sceneWidth);
		this.sceneHeight.set(sceneHeight);
		this.resizable.set(resizable);
		this.closable.set(closable);
		this.movable.set(movable);
		this.hideOnFocusLost.set(hideOnFocusLost);
		this.lightBoxMode.set(lightBoxMode);

		this.initComponents();
	}

	/**
	 * Método responsável por iniciar os componentes gráficos do 'Popup'
	 */
	private void initComponents() {
		this.initModality(this.popupModality.get());
		this.initStyle(StageStyle.TRANSPARENT);
		this.getIcons().add(new Image("/images/shortcut.png"));

		this.borderPane = this.loadBorderPane();
		this.borderPane.setCenter(this.content.get());
		this.borderPane.setMaxWidth(this.sceneWidth.get());
		this.borderPane.setMaxHeight(this.sceneHeight.get());
		this.borderPane.setMinWidth(this.sceneWidth.get());
		this.borderPane.setMinHeight(this.sceneHeight.get());
		this.borderPane.setPrefWidth(this.sceneWidth.get());
		this.borderPane.setPrefHeight(this.sceneHeight.get());
		// TESTE
		this.borderPane.setStyle("-fx-background-color: SLATEGRAY");

		if (this.lightBoxMode.get()) {
			final Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			this.root = new StackPane();
			this.root.setPrefWidth(visualBounds.getWidth());
			this.root.setPrefHeight(visualBounds.getHeight());
			this.root.setStyle("-fx-background-color: SLATEGRAY");
		} else {
			this.root = new AnchorPane();
			this.root.setStyle("-fx-background-color: SLATEGRAY");
			// this.root.setStyle("-fx-padding: 8 12 0 0;");
			// this.setMaxWidth(this.sceneWidth.get() + 12);
			// this.setMaxHeight(this.sceneHeight.get() + 8);
			// this.setMinWidth(this.sceneWidth.get() + 12);
			// this.setMinHeight(this.sceneHeight.get() + 8);
		}
		this.root.getChildren().add(this.borderPane);
		// AnchorPane.setTopAnchor(this.borderPane, 0D);

		final Scene scene = new Scene(this.root);
		scene.getStylesheets().add("/styles/style.css");
		scene.getStylesheets().add("/styles/style_tecway.css");

		scene.setFill(Color.TRANSPARENT);
		// scene.setFill(Color.SLATEGRAY);

		this.setScene(scene);
		this.setTitle(this.popupTitle.get());
		this.updateTitle(this.popupTitle.get());
	}

	/**
	 * Método responsável por bloquear o fechamento do popup por vias externas (Excluindo stage.close();).
	 */
	public void addIgnoreCloseRequest() {
		// External calls: Example= Alt+F4
		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(final WindowEvent event) {
				event.consume();
			}
		});
	}

	/**
	 * Método responsável por carregar o container principal do 'Popup'
	 * 
	 * @return - {@link BorderPane}
	 */
	private BorderPane loadBorderPane() {
		BorderPane borderPane = null;
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/pages/stagepopup.fxml"));
		try {
			borderPane = (BorderPane) loader.load();
			// Add events
			this.minimizeLabel = (Label) borderPane.lookup("#minimizeLabel");
			((Label) borderPane.lookup("#minimizeLabel")).setOnMouseClicked(this.minimizeStage);
			if (this.hideOnFocusLost.get() || this.lightBoxMode.get()) {
				this.minimizeLabel.setVisible(Boolean.FALSE);
			}
			this.maximizeLabel = (Label) borderPane.lookup("#maximizeLabel");
			((Label) borderPane.lookup("#maximizeLabel")).setOnMouseClicked(this.maximizeRestoreStage);
			this.restoreLabel = (Label) borderPane.lookup("#restoreLabel");
			((Label) borderPane.lookup("#restoreLabel")).setOnMouseClicked(this.maximizeRestoreStage);
			((HBox) borderPane.getTop()).getChildren().remove(this.restoreLabel);
			if (!this.resizable.get() || this.lightBoxMode.get()) {
				((HBox) borderPane.getTop()).getChildren().remove(this.maximizeLabel);
			}
			this.closeLabel = (Label) borderPane.lookup("#closeLabel");
			if (this.closable != null && !this.closable.get()) {
				this.closeLabel.setVisible(Boolean.FALSE);
			}
			((Label) borderPane.lookup("#closeLabel")).setOnMouseClicked(this.closeStage);
			if (this.movable.get() && !this.lightBoxMode.get()) {
				borderPane.getTop().setCursor(Cursor.MOVE);
				borderPane.getTop().setOnMousePressed(this.moveStageStart);
				borderPane.getTop().setOnMouseDragged(this.moveStageEnd);
			}
			if (this.resizable.get() && !this.lightBoxMode.get()) {
				((Label) borderPane.lookup("#resizeLabel")).setVisible(true);
				((Label) borderPane.lookup("#resizeLabel")).setOnMousePressed(this.resizeStageStart);
				((Label) borderPane.lookup("#resizeLabel")).setOnMouseDragged(this.resizeStageEnd);
			}
			if (!Modality.APPLICATION_MODAL.equals(this.popupModality.get()) && !this.lightBoxMode.get()
					&& this.hideOnFocusLost.get()) {
				this.focusedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue,
							final Boolean newValue) {
						if (!newValue) {
							StagePopup.this.close();
						}
					}
				});
			}
		} catch (final IOException e) {
			LOGGER.error("Erro ao carregar o BorderPane principal do popup", e);
		}
		return borderPane;
	}

	/**
	 * Handler responsável por minimizar o 'Popup'
	 */
	private final EventHandler<MouseEvent> minimizeStage = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StagePopup.this.setIconified(Boolean.TRUE);
		}
	};

	/**
	 * Handler responsável por maximizar/restaurar o 'Popup'
	 */
	private final EventHandler<MouseEvent> maximizeRestoreStage = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			if (StagePopup.this.restoreLabel.equals(event.getSource())) {
				// Restore
				StagePopup.this.isMaximized = Boolean.FALSE;
				StagePopup.this.borderPane.getStyleClass().remove("stage-popup-maximized");
				((HBox) StagePopup.this.borderPane.getTop()).getChildren().remove(StagePopup.this.restoreLabel);
				((HBox) StagePopup.this.borderPane.getTop()).getChildren().add(2, StagePopup.this.maximizeLabel);

				final double newWidth = StagePopup.this.restoreSceneWidth.get();
				if (newWidth > MIN_STAGE_WIDTH) {
					StagePopup.this.borderPane.setMaxWidth(newWidth);
					StagePopup.this.borderPane.setMinWidth(newWidth);
					StagePopup.this.borderPane.setPrefWidth(newWidth);
					StagePopup.this.setMaxWidth(newWidth + 12);
					StagePopup.this.setMinWidth(newWidth + 12);
				}
				final double newHeight = StagePopup.this.restoreSceneHeight.get();
				if (newHeight > MIN_STAGE_HEIGHT) {
					StagePopup.this.borderPane.setMaxHeight(newHeight);
					StagePopup.this.borderPane.setMinHeight(newHeight);
					StagePopup.this.borderPane.setPrefHeight(newHeight);
					StagePopup.this.setMaxHeight(newHeight + 8);
					StagePopup.this.setMinHeight(newHeight + 8);
				}
				StagePopup.this.setX(StagePopup.this.restoreSceneX.get());
				StagePopup.this.setY(StagePopup.this.restoreSceneY.get());

				((Label) StagePopup.this.borderPane.lookup("#resizeLabel")).setVisible(Boolean.TRUE);
				// Move Actions
				if (StagePopup.this.movable.get() && !StagePopup.this.lightBoxMode.get()) {
					StagePopup.this.borderPane.getTop().setCursor(Cursor.MOVE);
					StagePopup.this.borderPane.getTop().setOnMousePressed(StagePopup.this.moveStageStart);
					StagePopup.this.borderPane.getTop().setOnMouseDragged(StagePopup.this.moveStageEnd);
				}

			} else if (StagePopup.this.maximizeLabel.equals(event.getSource())) {
				// Maximize
				StagePopup.this.isMaximized = Boolean.TRUE;
				StagePopup.this.borderPane.getStyleClass().add("stage-popup-maximized");
				((HBox) StagePopup.this.borderPane.getTop()).getChildren().remove(StagePopup.this.maximizeLabel);
				((HBox) StagePopup.this.borderPane.getTop()).getChildren().add(2, StagePopup.this.restoreLabel);

				StagePopup.this.restoreSceneWidth.set(StagePopup.this.borderPane.getMinWidth());
				StagePopup.this.restoreSceneHeight.set(StagePopup.this.borderPane.getMinHeight());
				StagePopup.this.restoreSceneX.set(StagePopup.this.getX());
				StagePopup.this.restoreSceneY.set(StagePopup.this.getY());
				// MainApp.this.mainAppPane.setCenter(MainApp.this.appPane);
				final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
				/* Maximized State */
				StagePopup.this.borderPane.setMaxWidth(bounds.getWidth());
				StagePopup.this.borderPane.setMinWidth(bounds.getWidth());
				StagePopup.this.borderPane.setPrefWidth(bounds.getWidth());
				StagePopup.this.borderPane.setMaxHeight(bounds.getHeight());
				StagePopup.this.borderPane.setMinHeight(bounds.getHeight());
				StagePopup.this.borderPane.setPrefHeight(bounds.getHeight());
				StagePopup.this.setX(bounds.getMinX());
				StagePopup.this.setY(bounds.getMinY());
				StagePopup.this.setMaxWidth(bounds.getWidth());
				StagePopup.this.setMinWidth(bounds.getWidth());
				StagePopup.this.setMaxHeight(bounds.getHeight());
				StagePopup.this.setMinHeight(bounds.getHeight());

				((Label) StagePopup.this.borderPane.lookup("#resizeLabel")).setVisible(Boolean.FALSE);
				StagePopup.this.borderPane.getTop().setCursor(Cursor.DEFAULT);
				StagePopup.this.borderPane.getTop().setOnMousePressed(null);
				StagePopup.this.borderPane.getTop().setOnMouseDragged(null);
			}
		}
	};

	/**
	 * Handler responsável por fechar o 'Popup'
	 */
	private final EventHandler<MouseEvent> closeStage = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StagePopup.this.close();
		}
	};

	/**
	 * Handler responsável por controlar o inicio da movimentação do 'Popup'
	 */
	private final EventHandler<MouseEvent> moveStageStart = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StagePopup.this.initX = event.getScreenX() - StagePopup.this.getX();
			StagePopup.this.initY = event.getScreenY() - StagePopup.this.getY();
		}
	};

	/**
	 * Handler responsável por controlar a movimentação do 'Popup'
	 */
	private final EventHandler<MouseEvent> moveStageEnd = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StagePopup.this.setX(event.getScreenX() - StagePopup.this.initX);
			StagePopup.this.setY(event.getScreenY() - StagePopup.this.initY);
		}
	};

	/**
	 * Handler responsável por controlar o inicio do redimensionamento do 'Popup'
	 */
	private final EventHandler<MouseEvent> resizeStageStart = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StagePopup.this.initDimX = event.getScreenX();
			StagePopup.this.initDimY = event.getScreenY();
			StagePopup.this.initWidth = StagePopup.this.borderPane.getWidth();
			StagePopup.this.initHeight = StagePopup.this.borderPane.getHeight();
		}
	};

	/**
	 * Handler responsável por controlar o redimensionamento do 'Popup'
	 */
	private final EventHandler<MouseEvent> resizeStageEnd = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			final double newWidth = StagePopup.this.initWidth + event.getScreenX() - StagePopup.this.initDimX;
			if (newWidth > MIN_STAGE_WIDTH) {
				StagePopup.this.borderPane.setMaxWidth(newWidth);
				StagePopup.this.borderPane.setMinWidth(newWidth);
				StagePopup.this.borderPane.setPrefWidth(newWidth);
				StagePopup.this.setMaxWidth(newWidth + 12);
				StagePopup.this.setMinWidth(newWidth + 12);
			}
			final double newHeight = StagePopup.this.initHeight + event.getScreenY() - StagePopup.this.initDimY;
			if (newHeight > MIN_STAGE_HEIGHT) {
				StagePopup.this.borderPane.setMaxHeight(newHeight);
				StagePopup.this.borderPane.setMinHeight(newHeight);
				StagePopup.this.borderPane.setPrefHeight(newHeight);
				StagePopup.this.setMaxHeight(newHeight + 8);
				StagePopup.this.setMinHeight(newHeight + 8);
			}
		}
	};

	/**
	 * Método responsável por abrir o 'Popup' em uma posição específica.
	 * 
	 * @author F0110415 - Henrique Guedes
	 * @since 02/01/2013 10:25:27
	 * @param x
	 *            - posição X
	 * @param y
	 *            - posição Y
	 */
	public void show(final double x, final double y) {
		super.show();
		this.setX(x);
		this.setY(y);
	}

	/**
	 * @return the root
	 */
	public Pane getRoot() {
		return this.root;
	}

	/**
	 * @return the borderPane
	 */
	public BorderPane getBorderPane() {
		return this.borderPane;
	}

	/**
	 * @return the popupTitle
	 */
	public String getPopupTitle() {
		return this.popupTitle.get();
	}

	/**
	 * @param popupTitle
	 *            the popupTitle to set
	 */
	public void setPopupTitle(final String popupTitle) {
		this.popupTitle.set(popupTitle);
	}

	/**
	 * @return the answer
	 */
	public Object getAnswer() {
		return this.answer.get();
	}

	/**
	 * @param object
	 *            the answer to set
	 */
	public void setAnswer(final Object object) {
		this.answer.set(object);
	}

	/**
	 * @return the content
	 */
	public Node getContent() {
		return this.content.get();
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(final Node content) {
		this.content.set(content);
		if (this.borderPane != null) {
			this.borderPane.setCenter(content);
		}
	}

	/**
	 * @return the modality
	 */
	public Modality getPopupModality() {
		return this.popupModality.get();
	}

	/**
	 * @return the sceneWidth
	 */
	public Double getSceneWidth() {
		return this.sceneWidth.get();
	}

	/**
	 * @return the sceneHeight
	 */
	public Double getSceneHeight() {
		return this.sceneHeight.get();
	}

	/**
	 * @return the resizable
	 */
	public Boolean getResizable() {
		return this.resizable.get();
	}

	/**
	 * @return the closable
	 */
	public Boolean getClosable() {
		return this.closable.get();
	}

	/**
	 * @return the movable
	 */
	public Boolean getMovable() {
		return this.movable.get();
	}

	/**
	 * @return the hideOnFocusLost
	 */
	public Boolean getHideOnFocusLost() {
		return this.hideOnFocusLost.get();
	}

	/**
	 * @return the showLightBoxMode
	 */
	public Boolean getLightBoxMode() {
		return this.lightBoxMode.get();
	}

	/**
	 * @return the closeLabel
	 */
	public Label getCloseLabel() {
		return this.closeLabel;
	}

	/**
	 * @return the minimizeLabel
	 */
	public Label getMinimizeLabel() {
		return this.minimizeLabel;
	}

	/**
	 * Método responsável por executar o método showAndWait do Popup e retornar o objeto de answer
	 */
	public Object showAndWaitObject() {
		super.showAndWait();
		return this.answer.get();
	}

	/**
	 * Método responsável por atualizar título do popup
	 * 
	 * @author F0110415 - Henrique Guedes
	 * @since 02/01/2013 15:23:25
	 * @param popupTitle
	 *            - Título do popup
	 */
	public void updateTitle(final String popupTitle) {
		if (this.borderPane != null) {
			final Label titleLabel = (Label) this.borderPane.lookup("#titleLabel");
			if (titleLabel != null) {
				titleLabel.setText(popupTitle);
			}
		}
	}

	/**
	 * Método responsável por redimensionar o tamanho do popup de acordo com o conteúdo.
	 * 
	 * @author F0110415 - Henrique Guedes
	 * @since 25/04/2013 17:20:43
	 */
	public void autoSize() {
		if (this.getContent() != null) {
			double newWidth = -1;
			double newHeight = -1;
			final HBox top = (HBox) StagePopup.this.borderPane.getTop();
			final HBox left = (HBox) StagePopup.this.borderPane.getLeft();
			final HBox right = (HBox) StagePopup.this.borderPane.getRight();
			final HBox bottom = (HBox) StagePopup.this.borderPane.getBottom();
			if (this.getContent() instanceof Control) {
				newWidth = ((Control) this.getContent()).getWidth() + left.getWidth() + right.getWidth();
				newHeight = ((Control) this.getContent()).getHeight() + top.getHeight() + bottom.getHeight();
			} else if (this.getContent() instanceof Region) {
				newWidth = ((Region) this.getContent()).getWidth() + left.getWidth() + right.getWidth();
				newHeight = ((Region) this.getContent()).getHeight() + top.getHeight() + bottom.getHeight();
			}
			if (newWidth > -1 && newHeight > -1) {
				if (newWidth > MIN_STAGE_WIDTH) {
					StagePopup.this.borderPane.setMaxWidth(newWidth);
					StagePopup.this.borderPane.setMinWidth(newWidth);
					StagePopup.this.borderPane.setPrefWidth(newWidth);
					StagePopup.this.setMaxWidth(newWidth + 12);
					StagePopup.this.setMinWidth(newWidth + 12);
				}
				if (newHeight > MIN_STAGE_HEIGHT) {
					StagePopup.this.borderPane.setMaxHeight(newHeight);
					StagePopup.this.borderPane.setMinHeight(newHeight);
					StagePopup.this.borderPane.setPrefHeight(newHeight);
					StagePopup.this.setMaxHeight(newHeight + 8);
					StagePopup.this.setMinHeight(newHeight + 8);
				}
			}
		}
	}

}
