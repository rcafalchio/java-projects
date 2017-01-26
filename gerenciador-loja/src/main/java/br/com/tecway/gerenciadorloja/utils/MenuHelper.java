package br.com.tecway.gerenciadorloja.utils;

import br.com.tecway.gerenciadorloja.fx.controller.PrincipalController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public final class MenuHelper {

	private MenuHelper() {
	}

	public static void montarMenuSistema(final MenuBar principalMenuBar, final PrincipalController controller) {

		// ###########################################
		// MENU SISTEMA
		// ###########################################
		final Menu menuSistema = new Menu("Sistema");
		// Monta o menu logoff
		final MenuItem menuItemLogoff = new MenuItem("Efetuar o logoff");
		menuItemLogoff.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.efetuarLogoff(event);
			}

		});
		// Monta o sair
		final MenuItem menuItemSair = new MenuItem("Sair");
		menuItemSair.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.sair(event);
			}

		});
		// Adiciona ao menu pai
		menuSistema.getItems().addAll(menuItemLogoff, menuItemSair);
		principalMenuBar.getMenus().addAll(menuSistema);

		// Se ele estiver logado
		if (SegurancaUtils.getUsuarioEntity() != null) {


			// ###########################################
			// MENU OPERACOES
			// ###########################################
			final Menu menuOperacoes = new Menu("Operações");
			// Monta o menu caixa
			final MenuItem menuItemCaixa = new MenuItem("Abrir Caixa");
			menuItemCaixa.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaFluxoCaixa(event);
				}

			});

			// Monta o menu caixa
			final MenuItem menuItemCaixaDiario = new MenuItem("Controle de Caixa");
			menuItemCaixaDiario.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (SegurancaUtils.getUsuarioEntity().isFlagAdministrador()) {
						controller.carregarTelaCaixaAdministrativo(event);
					} else {
						controller.carregarTelaCaixa(event);
					}
				}

			});

			// Monta o Estoque
			final MenuItem menuItemEstoque = new MenuItem("Atualizar Estoque Loja");
			menuItemEstoque.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaEstoque(event);
				}

			});
			// Monta o Estoque casa
			final MenuItem menuItemEstoqueCasa = new MenuItem("Atualizar Estoque Casa");
			menuItemEstoqueCasa.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaEstoqueCasa(event);
				}

			});

			// Monta o menu de trocas
			final MenuItem menuItemTrocas = new MenuItem("Efetuar trocas e devoluções");
			menuItemTrocas.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaTrocas(event);
				}

			});


			// ###########################################
			// MENU CADASTROS
			// ###########################################
			final Menu menuCadastros = new Menu("Cadastros");
			// Monta o menu caixa
			final MenuItem menuItemCadastroUsuario = new MenuItem("Cadastro de usuário");
			menuItemCadastroUsuario.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaCadastroUsuario(event);
				}

			});
			// Monta o Estoque
			final MenuItem menuItemCadastroProduto = new MenuItem("Cadastro de produto");
			menuItemCadastroProduto.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaCadastroProduto(event);
				}

			});
			// Monta o Estoque
			final MenuItem menuItemCadastroMarca = new MenuItem("Cadastro de Marca");
			menuItemCadastroMarca.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaCadastroMarca(event);
				}

			});

			// ###########################################
			// MENU CONSULTAS
			// ###########################################
			final Menu menuRelatorios = new Menu("Consultas");
			// Monta o menu caixa
			final MenuItem menuItemRelatorioEstoque = new MenuItem("Estoque");
			menuItemRelatorioEstoque.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaConsultaEstoque(event);
				}

			});
			// Monta o Estoque
			final MenuItem menuItemRelatorioCaixa = new MenuItem("Caixa");
			menuItemRelatorioCaixa.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaConsultaCaixa(event);
				}

			});
			// Monta o Estoque
			final MenuItem menuItemRelatorioVendas = new MenuItem("Vendas");
			menuItemRelatorioVendas.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.carregarTelaConsultaVendas(event);
				}

			});

			// Aplica a segurança
			if (SegurancaUtils.getUsuarioEntity().isFlagAdministrador()) {
				// Adiciona ao menu pai
				menuOperacoes.getItems().addAll(menuItemEstoque, menuItemEstoqueCasa);
				// Adiciona ao menu pai
				menuCadastros.getItems()
						.addAll(menuItemCadastroUsuario, menuItemCadastroProduto, menuItemCadastroMarca);
				// Adiciona ao menu pai
				menuRelatorios.getItems().addAll(menuItemRelatorioEstoque, menuItemRelatorioCaixa,
						menuItemRelatorioVendas);
				// Adiciona todos os menus
				principalMenuBar.getMenus().addAll(menuCadastros);
				principalMenuBar.getMenus().addAll(menuRelatorios);
			}
			// Adiciona ao menu pai
			menuOperacoes.getItems().addAll(menuItemCaixa, menuItemCaixaDiario, menuItemTrocas);


			principalMenuBar.getMenus().addAll(menuOperacoes);


		}

	}

}
