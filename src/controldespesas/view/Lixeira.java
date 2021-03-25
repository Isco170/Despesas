package controldespesas.view;

import controldespesas.dao.CategoriaDAO;
import controldespesas.dao.DespesaDAO;
import controldespesas.domain.Categoria;
import controldespesas.domain.Despesa;
import controldespesas.domain.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Lixeira extends Application {

    Principal principal;

    DespesaDAO despDao;
    CategoriaDAO catDao;

    Stage janela;
    Scene cena;
    BorderPane border;
    VBox layout;
    HBox botoes;
    AnchorPane anchor;

    Button btnVoltar, btnRecuperar, btnLimpar;
    ComboBox<String> limpar;

    TableView<Categoria> cateTabela;
    TableView<Despesa> despTabela;
    TabPane tabPane;
    Tab cateTab, despTab;

    TableColumn<Despesa, String> despesaNomeColuna;
    TableColumn<Despesa, String> despesaCategoriaColuna;
    TableColumn<Despesa, Float> despesaValorColuna;
    TableColumn<Despesa, String> despesaDataColuna;
    TableColumn<Despesa, String> despesaDataRemocaoColuna;

    TableColumn<Categoria, String> categoriaNomeColuna;
    TableColumn<Categoria, String> categoriaDataRemocaoColuna;

    Usuario usuario;

    Image voltarImage;
    ImageView voltarView;

    public Lixeira(Usuario user) {
        usuario = user;
    }

    Notifications notificacaoBuilder;
    Node graphic = null;

    private void notificacao(Pos pos, Node graphic, String mesag) {
        notificacaoBuilder = Notifications.create()
                .text("Gestor Financeiro")
                .text(mesag)
                .graphic(graphic)
                .hideAfter(Duration.seconds(3))
                .position(pos)
                .onAction(e -> {
                    System.out.println("Notificador");
                });
    }

    public void initsComponents() {
        border = new BorderPane();
        border.setPadding(new Insets(8, 0, 0, 10));

        btnVoltar = new Button("", voltarView);
        btnVoltar.setId("btnVoltar");

        anchor = new AnchorPane();
        anchor.getChildren().addAll(btnVoltar);

        layout = new VBox();
        layout.setPadding(new Insets(10, 0, 0, 0));

        tabPane = new TabPane();
        cateTab = new Tab("Categorias removidas");
        cateTab.setClosable(false);

        btnRecuperar = new Button("Recuperar");
        btnRecuperar.setId("btnRecuperar");
        btnRecuperar.setVisible(false);

        ObservableList l = FXCollections.observableArrayList("Selecionada", "Todas");
        limpar = new ComboBox(l);
        limpar.setPromptText("Limpar");
        limpar.setVisible(false);
        limpar.setId("limpar");

        botoes = new HBox();
        botoes.setSpacing(10);
        botoes.setPadding(new Insets(10));
        botoes.setAlignment(Pos.CENTER_RIGHT);

        despTab = new Tab("Despesas removidas");
        despTab.setClosable(false);

        //INICIOTABELAS
        /////////
        despesaCategoriaColuna = new TableColumn<>("Categoria");
        despesaCategoriaColuna.setMinWidth(180);
        despesaCategoriaColuna.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        despesaNomeColuna = new TableColumn<>("Nome");
        despesaNomeColuna.setMinWidth(180);
        despesaNomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));

        despesaValorColuna = new TableColumn<>("Valor");
        despesaValorColuna.setMinWidth(150);
        despesaValorColuna.setCellValueFactory(new PropertyValueFactory<>("valor"));

        despesaDataColuna = new TableColumn<>("Data");
        despesaDataColuna.setPrefWidth(180);
        despesaDataColuna.setCellValueFactory(new PropertyValueFactory<>("data"));

        despesaDataRemocaoColuna = new TableColumn<>("Data da remoção");
        despesaDataRemocaoColuna.setPrefWidth(180);
        despesaDataRemocaoColuna.setCellValueFactory(new PropertyValueFactory<>("dataremovido"));

        despTabela = new TableView();
        despTabela.setPrefHeight(900);
        despTabela.setPlaceholder(new Text("Sem despesas"));
        despTabela.setPadding(new Insets(10));
        despTabela.getColumns().addAll(despesaCategoriaColuna, despesaNomeColuna,
                despesaValorColuna, despesaDataColuna, despesaDataRemocaoColuna);
        try {
            despTabela.setItems(listarDespesas());

        } catch (SQLException ex) {
            System.out.println("Problemas ao listar despesas na lixeira");
            Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////
        categoriaNomeColuna = new TableColumn<>("Nome");
        categoriaNomeColuna.setPrefWidth(100);
        categoriaNomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));

        categoriaDataRemocaoColuna = new TableColumn<>("Data da remoção");
        categoriaDataRemocaoColuna.setPrefWidth(180);
        categoriaDataRemocaoColuna.setCellValueFactory(new PropertyValueFactory<>("dataremovido"));

        cateTabela = new TableView();
        cateTabela.setPrefHeight(990);
        cateTabela.setPlaceholder(new Text("Sem Categorias"));
        cateTabela.setPadding(new Insets(10));
        cateTabela.getColumns().addAll(categoriaNomeColuna, categoriaDataRemocaoColuna);
        try {
            cateTabela.setItems(listarCategorias());
        } catch (SQLException ex) {
            Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
        }

        ///////////
        //FIM TABELAS
        cena = new Scene(border, 1000, 600);
        cena.getStylesheets().add("controldespesas/view/css/estilo.css");
    }

    public void initsLayout() {
        border.setTop(anchor);

        cateTab.setContent(cateTabela);
        despTab.setContent(despTabela);

        tabPane.getTabs().addAll(despTab, cateTab);

        botoes.getChildren().addAll(btnRecuperar, limpar);
        layout.getChildren().addAll(tabPane, botoes);

        border.setCenter(layout);
    }

    public ObservableList<Despesa> listarDespesas() throws SQLException {
        despDao = new DespesaDAO();
        ObservableList<Despesa> lista = (ObservableList<Despesa>) despDao.listarLixeira(usuario);
        return lista;
    }

    public ObservableList<Categoria> listarCategorias() throws SQLException {
        catDao = new CategoriaDAO();
        ObservableList<Categoria> lista = (ObservableList<Categoria>) catDao.listarLixeira(usuario);
        return lista;
    }

    public void initsImage() {

        try {
            voltarImage = new Image(getClass().getResourceAsStream("img/back.png"));
            voltarView = new ImageView(voltarImage);
            voltarView.setFitWidth(25);
            voltarView.setFitHeight(25);

        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar imagem");
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;
        initsImage();
        initsComponents();
        initsLayout();

        janela.setOnCloseRequest(e -> {
            Principal.janela.show();
        });
        btnVoltar.setOnAction(e -> {
            try {
                principal = new Principal(usuario);
                janela.close();
                //principal.start(new Stage());
                Principal.janela.show();
            } catch (Exception ex) {
                Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnRecuperar.setOnAction(e -> {
            if (cateTab.isSelected()) {
                catDao = new CategoriaDAO();
                ObservableList<Categoria> selecionada, todas;
                todas = cateTabela.getItems();
                selecionada = cateTabela.getSelectionModel().getSelectedItems();

                Categoria categoria = cateTabela.getSelectionModel().getSelectedItem();
                try {
                    catDao.recuperarCategoria(categoria);
                    selecionada.forEach(todas::remove);
                    //JOptionPane.showMessageDialog(null, "Categoria recuperada");
                    notificacao(Pos.TOP_LEFT, graphic, "Categoria recuperada");
                    notificacaoBuilder.showInformation();

                } catch (SQLException ex) {
                    //JOptionPane.showMessageDialog(null, "Erro ao recuperar a categoria", "Recuperar categoria", JOptionPane.WARNING_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic, "Não foi possível recuperar categoria");
                    notificacaoBuilder.showError();
                    Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NullPointerException er) {
                    //JOptionPane.showMessageDialog(null, "Nenhuma categoria selecionada");
                    notificacao(Pos.TOP_LEFT, graphic, "Nenhuma categoria selecionada");
                    notificacaoBuilder.showWarning();
                }

            } else if (despTab.isSelected()) {
                despDao = new DespesaDAO();
                ObservableList<Despesa> selecionada, todas;
                todas = despTabela.getItems();
                selecionada = despTabela.getSelectionModel().getSelectedItems();
                Despesa despesa = despTabela.getSelectionModel().getSelectedItem();
                try {
                    despDao.recuperarDespesa(despesa);
                    selecionada.forEach(todas::remove);
                    //JOptionPane.showMessageDialog(null, "Despesa recuperada");
                    notificacao(Pos.TOP_LEFT, graphic, "Despesa recuperada");
                    notificacaoBuilder.showWarning();

                } catch (SQLException ex) {
                    //JOptionPane.showMessageDialog(null, "Erro ao recuperar despesa", "Recuperar despesa", JOptionPane.WARNING_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic, "Não foi possível recuperar categoria");
                    notificacaoBuilder.showError();

                    Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NullPointerException er) {
                    //JOptionPane.showMessageDialog(null, "Nenhuma despesa selecionada");
                    notificacao(Pos.TOP_LEFT, graphic, "Nenhuma despesa selecionada");
                    notificacaoBuilder.showError();
                }
            }
        });

        cateTabela.setOnMouseClicked(e -> {
            if ((!cateTabela.getItems().isEmpty()) && (cateTabela.getSelectionModel().getSelectedIndex() >= 0)) {
                btnRecuperar.setVisible(true);
                limpar.setVisible(true);
            }
        });
        despTabela.setOnMouseClicked(e -> {
            if ((!despTabela.getItems().isEmpty()) && (despTabela.getSelectionModel().getSelectedIndex() >= 0)) {
                btnRecuperar.setVisible(true);
                limpar.setVisible(true);
            }
        });

        limpar.setOnAction(e -> {
            if (limpar.getSelectionModel().getSelectedIndex() == 0) {
                if (despTab.isSelected()) {

                    ObservableList<Despesa> selecionada, todas;
                    todas = despTabela.getItems();
                    selecionada = despTabela.getSelectionModel().getSelectedItems();

                    Despesa despesa = despTabela.getSelectionModel().getSelectedItem();
                    try {
                        despDao.limparDespesa(despesa);

                        selecionada.forEach(todas::remove);

                        System.out.println("Limpou despesa selecionada");
                    } catch (SQLException ex) {
                        //JOptionPane.showMessageDialog(null, "Erro ao limpar despesa");
                        notificacao(Pos.TOP_LEFT, graphic, "Não foi possível limpar despesa");
                        notificacaoBuilder.showError();

                        Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NullPointerException er2) {
                        //JOptionPane.showMessageDialog(null, "Nenhuma despesa selecionada!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                        notificacao(Pos.TOP_LEFT, graphic, "Nenhuma despesa selecionada!");
                        notificacaoBuilder.showWarning();
                    }

                } else if (cateTab.isSelected()) {

                    ObservableList<Categoria> selecionada, todas;
                    todas = cateTabela.getItems();
                    selecionada = cateTabela.getSelectionModel().getSelectedItems();

                    Categoria categoria = cateTabela.getSelectionModel().getSelectedItem();
                    try {
                        catDao.limparCategoria(categoria);

                        selecionada.forEach(todas::remove);

                        System.out.println("Limpou categoria selecionada");
                    } catch (SQLException ex) {
                        //JOptionPane.showMessageDialog(null, "Erro ao limpar categoria");
                        notificacao(Pos.TOP_LEFT, graphic, "Não foi possível limpar categoria!");
                        notificacaoBuilder.showError();
                        Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NullPointerException er2) {
                        //JOptionPane.showMessageDialog(null, "Nenhuma categoria selecionada!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                        notificacao(Pos.TOP_LEFT, graphic, "Nenhuma categoria selecionada!");
                        notificacaoBuilder.showWarning();
                    }
                }

            } else if (limpar.getSelectionModel().getSelectedIndex() == 1) {
                if (despTab.isSelected()) {
                    ObservableList<Despesa> lista = despTabela.getItems();
                    int ii = 0;
                    try {
                        for (int i = 0; i < lista.size(); i++) {
                            ii = i;
                            Despesa atual = lista.get(i);
                            despDao.limparDespesa(atual);
                        }
                        despTabela.getItems().clear();

                    } catch (SQLException ex) {
                        //JOptionPane.showMessageDialog(null, "Erro ao limpar despesas", "Aviso!", JOptionPane.WARNING_MESSAGE);
                        notificacao(Pos.TOP_LEFT, graphic, "Não foi possível limpar despesas");
                        notificacaoBuilder.showError();

                        System.out.println("posicao " + ii);
                        Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NullPointerException er2) {
                        //JOptionPane.showMessageDialog(null, "Nenhuma despesa selecionada!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                        notificacao(Pos.TOP_LEFT, graphic, "Nenhuma despesa selecionada");
                        notificacaoBuilder.showWarning();
                    }

                } else if (cateTab.isSelected()) {
                    ObservableList<Categoria> lista = cateTabela.getItems();
                    int ii = 0;
                    try {
                        for (int i = 0; i < lista.size(); i++) {
                            ii = i;
                            Categoria atual = lista.get(i);
                            catDao.limparCategoria(atual);
                        }
                        cateTabela.getItems().clear();

                    } catch (SQLException er) {
                        //JOptionPane.showMessageDialog(null, "Erro ao limpar categorias", "Aviso!", JOptionPane.WARNING_MESSAGE);
                        notificacao(Pos.TOP_LEFT, graphic, "Não foi possível limpar despesas");
                        notificacaoBuilder.showWarning();

                        System.out.println("posicao " + ii);
                        Logger.getLogger(Lixeira.class.getName()).log(Level.SEVERE, null, er);
                    } catch (NullPointerException er2) {
                        //JOptionPane.showMessageDialog(null, "Nenhuma categoria selecionada!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                        notificacao(Pos.TOP_LEFT, graphic, "Nenhuma categoria selecionada!");
                        notificacaoBuilder.showWarning();
                    }
                }
            }
        });
        tabPane.setOnMouseClicked(e -> {
            if (despTab.isSelected()) {

                btnRecuperar.setVisible(false);
                limpar.setVisible(false);
                limpar.getSelectionModel().clearSelection();
            } else if (cateTab.isSelected()) {

                btnRecuperar.setVisible(false);
                limpar.setVisible(false);
                limpar.getSelectionModel().clearSelection();
            }
        });

        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            janela.getIcons().add(logotipo);
        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar imagem");
        }

        janela.setTitle("GD => Lixeira");
        janela.setScene(cena);
        //janela.setResizable(false);
        //janela.initModality(Modality.APPLICATION_MODAL);
        janela.show();
    }
}
