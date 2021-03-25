package controldespesas.view;

import controldespesas.dao.CategoriaDAO;
import controldespesas.dao.DespesaDAO;
import controldespesas.dao.ImagemDAO;
import controldespesas.domain.Categoria;
import controldespesas.domain.Despesa;
import controldespesas.domain.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class Principal extends Application {

    public Principal(Usuario us) {
        Principal.usuario = us;

    }

    Despesa_Adicionar despAd;
    AdicionarCategoria adCat;
    Login login;
    InfoDespesa infoDesp;
    RemoverCategoria removerCat;
    Lixeira lixo;
    Estatistica estatis;
    Perfil perfil;

    static Usuario usuario;
    static DespesaDAO despDao;
    ImagemDAO imgDao;

    static Stage janela;
    Scene cena;
    BorderPane border;

    MenuBar barra;
    Menu ficheiroMenu, adicionarMenu, removerMenu;
    MenuItem perfilItem, sairItem, categoriaAdItem, categoriaReItem, despesaAdItem, despesaReItem, estatisticaItem, lixeiraItem;

    AnchorPane dados, anchorImagem;
    Pane imagemPane;
    Label nome, apelido, contacto;
    boolean ver = false;
    Button btnDados, btnSair, btnPesquisar, btnAdicionar, btnRemover, btnInfo, btnRepor, addImage;

    ComboBox<String> categoriasCombo, valoresCombo;

    VBox centro;
    TextField campo;
    HBox footer, pesquisa, filtros, pesq, botoes, repor;

    public static TableView<Despesa> tabela;
    TableColumn<Despesa, String> nomeColuna;
    TableColumn<Despesa, String> categoriaColuna;
    TableColumn<Despesa, Float> valorColuna;
    TableColumn<Despesa, String> dataColuna;

    Image imagem, search, addFoto, perfilImage, menu, dadosImage;
    ImageView imagemRepor, searchView, fotoView, perfilView, menuView, dadosView;
    FileChooser escolherImagem;
    File ficheiro;

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

        //INICIO MENU
        ficheiroMenu = new Menu("", menuView);
        adicionarMenu = new Menu("Adicionar");
        removerMenu = new Menu("Remover");

        estatisticaItem = new MenuItem("Estatística");
        lixeiraItem = new MenuItem("Lixeira");
        despesaAdItem = new MenuItem("Despesa");
        perfilItem = new MenuItem("Perfil");
        sairItem = new MenuItem("Sair");
        categoriaAdItem = new MenuItem("Categoria");
        categoriaReItem = new MenuItem("Categoria");

        barra = new MenuBar();

        ficheiroMenu.getItems().addAll(perfilItem, estatisticaItem, lixeiraItem, sairItem);
        adicionarMenu.getItems().addAll(despesaAdItem, categoriaAdItem);
        removerMenu.getItems().addAll(categoriaReItem);

        barra.getMenus().addAll(ficheiroMenu, adicionarMenu, removerMenu);
        //FIM MENU

        //INICIO DO LADO ESQUERDO DADOS
        dados = new AnchorPane();
        dados.setPadding(new Insets(8));
        dados.setId("dadosPane");

        anchorImagem = new AnchorPane();
        anchorImagem.setPrefSize(300, 200);
        anchorImagem.setLayoutX(10);
        anchorImagem.setLayoutY(10);

        imagemPane = new Pane(perfilView);
        imagemPane.setPrefSize(300, 200);
        //imagemPane.setLayoutX(10);
        //imagemPane.setLayoutY(10);
        imagemPane.setId("imagemPane");

        addImage = new Button("", fotoView);
        addImage.setLayoutX(120);
        addImage.setLayoutY(80);
        addImage.setFocusTraversable(false);
        addImage.setVisible(false);

        nome = new Label("Nome: " + usuario.getNome());
        nome.setLayoutX(10);
        nome.setLayoutY(240);

        apelido = new Label("Apelido: " + usuario.getApelido());
        apelido.setLayoutX(10);
        apelido.setLayoutY(280);

        contacto = new Label("Contacto: +258 " + usuario.getTelefone());
        contacto.setLayoutX(10);
        contacto.setLayoutY(320);

        btnDados = new Button("", dadosView);
        btnDados.setLayoutX(231);
        btnDados.setLayoutY(450);
        //btnDados.setId("Cadastro_salvar");
        btnDados.setId("btnDados");
        //FIM DO LADO ESQUERDO DADOS

        //INICIO DO FOOTER
        btnSair = new Button("Sair");
        btnSair.setId("btnSair");

        footer = new HBox();
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(8));
        footer.setId("dadosPane");
        //FIM DO FOOTER

        //INCIO DA PARTE CENTRAL
        centro = new VBox();
        //centro.setPadding(new Insets(20, 0, 0, 0));
        centro.setSpacing(8);
        centro.setId("centro");

        categoriasCombo = new ComboBox();
        categoriasCombo.setPromptText("Filtrar categoria");
        categoriasCombo.setId("filtros");
        try {
            preencherCategoria(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList l = FXCollections.observableArrayList("1 - 99Mt", "100 - 199Mt", "200 - 299Mt", "300 - 399Mt", "400 - 499Mt", "500 - 599", "600...");
        valoresCombo = new ComboBox(l);
        valoresCombo.setPromptText("Filtrar valor");
        valoresCombo.setId("filtros");

        campo = TextFields.createClearableTextField();
        Tooltip.install(campo, new Tooltip("Digite o nome ou categoria"));
        campo.setPrefWidth(200);

        btnPesquisar = new Button("", searchView);
        Tooltip.install(btnPesquisar, new Tooltip("Pesquisar"));
        btnPesquisar.setId("btnPesquisar");

        filtros = new HBox();
        filtros.setSpacing(10);

        pesq = new HBox();
        pesq.setSpacing(10);

        btnRepor = new Button("", imagemRepor);
        Tooltip.install(btnRepor, new Tooltip("Atualizar"));
        btnRepor.setId("repor");

        repor = new HBox();
        repor.setAlignment(Pos.CENTER_RIGHT);
        repor.setPadding(new Insets(0, 10, 0, 0));

        pesquisa = new HBox();
        pesquisa.setSpacing(30);
        pesquisa.setAlignment(Pos.CENTER_RIGHT);
        pesquisa.setPadding(new Insets(5, 10, 0, 10));

        //INICIO DA TABELA
        nomeColuna = new TableColumn<>("Nome");
        nomeColuna.setMinWidth(180);
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));

        categoriaColuna = new TableColumn<>("Categoria");
        categoriaColuna.setMinWidth(180);
        categoriaColuna.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        valorColuna = new TableColumn<>("Valor");
        valorColuna.setMinWidth(150);
        valorColuna.setCellValueFactory(new PropertyValueFactory<>("valor"));

        dataColuna = new TableColumn<>("Data e Hora");
        dataColuna.setMinWidth(180);
        dataColuna.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabela = new TableView();
        tabela.setPrefSize(1000, 410);
        tabela.setPlaceholder(new Text("Sem despesas"));
        tabela.setPadding(new Insets(10));
        try {
            tabela.setItems(listarDespesas());
        } catch (SQLException ex) {
            System.out.println("Problemas em listar despesas na tabela");
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabela.getColumns().addAll(categoriaColuna, nomeColuna, valorColuna, dataColuna/*, detalhesColuna*/);

        btnAdicionar = new Button("Adicionar");
        btnAdicionar.setId("adicionar");

        btnRemover = new Button("Remover");
        btnRemover.setVisible(false);
        btnRemover.setId("remover");

        btnInfo = new Button("+ Info");
        btnInfo.setVisible(false);
        btnInfo.setId("info");

        botoes = new HBox();
        botoes.setAlignment(Pos.BOTTOM_RIGHT);
        botoes.setPadding(new Insets(0, 10, 0, 0));
        botoes.setSpacing(10);
        botoes.getChildren().addAll(btnRemover, btnAdicionar, btnInfo);
        //FIM DA TABELA

        //FIM DA PARTE CENTRAL
        cena = new Scene(border, 1220, 650);
        cena.getStylesheets().add("controldespesas/view/css/estilo.css");
    }

    public void initsLayout() {

        border.setTop(barra);

        anchorImagem.getChildren().addAll(imagemPane, addImage);
        dados.getChildren().addAll(anchorImagem, nome, apelido, contacto, btnDados);
        border.setLeft(dados);

        footer.getChildren().addAll(btnSair);
        border.setBottom(footer);

        filtros.getChildren().addAll(categoriasCombo, valoresCombo);

        pesq.getChildren().addAll(campo, btnPesquisar);

        pesquisa.getChildren().addAll(filtros, pesq);

        repor.getChildren().addAll(btnRepor);

        centro.getChildren().addAll(pesquisa, repor, tabela, botoes);

        border.setCenter(centro);
    }

    static ObservableList<Despesa> listarDespesas() throws SQLException {
        despDao = new DespesaDAO();
        ObservableList<Despesa> despesa = (ObservableList<Despesa>) despDao.listarDespesa(usuario);

        return despesa;
    }

    public void preencherCategoria(Usuario usuario) throws SQLException {
        CategoriaDAO catDao = new CategoriaDAO();
        ObservableList<Categoria> listaCategoria = (ObservableList<Categoria>) catDao.listarCategoria(usuario);
        for (int i = 0; i < listaCategoria.size(); i++) {
            categoriasCombo.getItems().add(i, listaCategoria.get(i).getNome());
        }
    }

    public void initImage() {

        try {
            search = new Image(getClass().getResourceAsStream("img/search.png"));
            searchView = new ImageView(search);
            searchView.setFitWidth(25);
            searchView.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        try {
            imagem = new Image(getClass().getResourceAsStream("img/repor.png"));
            imagemRepor = new ImageView(imagem);
            imagemRepor.setFitWidth(25);
            imagemRepor.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        try {
            addFoto = new Image(getClass().getResourceAsStream("img/image.png"));
            fotoView = new ImageView(addFoto);
            fotoView.setFitWidth(24);
            fotoView.setFitHeight(24);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        imgDao = new ImagemDAO();
        try {
            perfilImage = new Image(new FileInputStream(imgDao.listarImagem(usuario)));
        } catch (SQLException ex) {
            System.out.println("Problemas ao ler imagem na base de dados");
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Problemas ao ler imagem, nao na base de dados");
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        perfilView = new ImageView(perfilImage);
        perfilView.setFitWidth(290);
        perfilView.setFitHeight(190);
        perfilView.setLayoutX(5);
        perfilView.setLayoutY(5);
        perfilView.setId("perfilView");

        try {
            menu = new Image(getClass().getResourceAsStream("img/menu.png"));
            menuView = new ImageView(menu);
            menuView.setFitWidth(40);
            menuView.setFitHeight(30);
        } catch (NullPointerException ex) {
            System.out.println("Erro carregando imagem");
        }

        try {
            dadosImage = new Image(getClass().getResourceAsStream("img/user.png"));
            dadosView = new ImageView(dadosImage);
            dadosView.setFitWidth(30);
            dadosView.setFitHeight(50);
        } catch (NullPointerException ex) {
            System.out.println("Erro carregando imagem");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initImage();
        initsComponents();
        initsLayout();

        btnSair.setOnAction(e -> {
            try {
                login = new Login();
                login.start(new Stage());
                janela.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnRemover.setOnAction(e -> {
            if (!tabela.getItems().isEmpty()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que quer apagar a despesa selecionada?", "Apagar despesa", JOptionPane.OK_OPTION);
                if (resposta == 0) {
                    try {
                        //tabela.getItems().get(tabela.getSelectionModel().getSelectedIndex()); 
                        despDao.excluirDespesa(tabela.getItems().get(tabela.getSelectionModel().getSelectedIndex()));

                        Date hora = new Date();
                        SimpleDateFormat formatar = new SimpleDateFormat("YYYY-MM-dd HH:mm:s");
                        String dataFormatada = formatar.format(hora);
                        despDao.dataRemovido(dataFormatada, tabela.getItems().get(tabela.getSelectionModel().getSelectedIndex()));

                        tabela.setItems(listarDespesas());
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

        btnPesquisar.setOnAction(e -> {
            if (campo.getText().equals("") || campo.getText() == null) {
                //JOptionPane.showMessageDialog(null, "Campo de pesquisa vazio", "Campo de pesquisa", JOptionPane.WARNING_MESSAGE);
                notificacao(Pos.TOP_LEFT, graphic, "Campo de pesquisa vazio");
                notificacaoBuilder.showWarning();
            } else if (tabela.getItems().isEmpty()) {
                //JOptionPane.showMessageDialog(null, "Tabela sem despesas!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                notificacao(Pos.TOP_LEFT, graphic, "Tabela sem despesas!");
                notificacaoBuilder.showWarning();
            } else {
                Despesa despesa = new Despesa();

                ObservableList<Despesa> todas, iguais;

                todas = tabela.getItems();
                ArrayList<Despesa> lista = new ArrayList();
                // tabela.getItems().clear();
                despesa.setNome(campo.getText());
                despesa.setCategoria(campo.getText());
                despesa.setDescricao(campo.getText());
                //iguais.clear();
                for (int i = 0; i < todas.size(); i++) {
                    if ((todas.get(i).getNome().equalsIgnoreCase(despesa.getNome())) || (todas.get(i).getCategoria().equalsIgnoreCase(despesa.getCategoria()))
                            || (todas.get(i).getDescricao().equalsIgnoreCase(despesa.getDescricao()))) {
                        lista.add(todas.get(i));
                    }
                }
                todas.clear();
                for (int a = 0; a < lista.size(); a++) {
                    tabela.getItems().add(a, lista.get(a));
                }
            }
        });
        btnRepor.setOnAction(e -> {
            try {
                categoriasCombo.getSelectionModel().clearSelection();
                categoriasCombo.getItems().clear();
                preencherCategoria(usuario);
                valoresCombo.getSelectionModel().clearSelection();
                tabela.setItems(listarDespesas());

            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, "Erro ao atualizar a tabela");
                notificacao(Pos.TOP_LEFT, graphic, "Erro ao atualizar a tabela!");
                notificacaoBuilder.showError();

                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnAdicionar.setOnAction(e -> {
            try {
                despAd = new Despesa_Adicionar(usuario);
                despAd.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnInfo.setOnAction(e -> {
            if (!tabela.getItems().isEmpty()) {
                try {
                    infoDesp = new InfoDespesa(tabela.getItems().get(tabela.getSelectionModel().getSelectedIndex()), usuario);
                    infoDesp.start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        categoriaAdItem.setOnAction(e -> {
            try {
                adCat = new AdicionarCategoria(usuario);
                adCat.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        sairItem.setOnAction(e -> {
            login = new Login();
            try {
                login.start(new Stage());
                janela.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        categoriaReItem.setOnAction(e -> {
            try {
                removerCat = new RemoverCategoria(usuario);
                removerCat.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tabela.setOnMouseClicked(e -> {
            if ((!tabela.getItems().isEmpty()) && (tabela.getSelectionModel().getSelectedIndex() >= 0)) {
                btnRemover.setVisible(true);
                btnInfo.setVisible(true);
            }

        });

        despesaAdItem.setOnAction(e -> {
            try {
                despAd = new Despesa_Adicionar();
                despAd.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        lixeiraItem.setOnAction(e -> {
            System.out.println("lixo");
            try {
                lixo = new Lixeira(usuario);
                lixo.start(new Stage());
                janela.hide();
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        categoriasCombo.setOnAction(e -> {
            try {
                String cat = categoriasCombo.getSelectionModel().getSelectedItem();
                ObservableList<Despesa> lista = (ObservableList<Despesa>) despDao.filtrarDespesas(cat, usuario);
                tabela.setItems(lista);
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        valoresCombo.setOnAction(e -> {
            String valor1 = "0", valor2 = "0";
            if (valoresCombo.getSelectionModel().getSelectedIndex() == 0) {
                valor1 = "1";
                valor2 = "99";
            } else if (valoresCombo.getSelectionModel().getSelectedIndex() == 1) {
                valor1 = "100";
                valor2 = "199";
            } else if (valoresCombo.getSelectionModel().getSelectedIndex() == 2) {
                valor1 = "200";
                valor2 = "299";
            } else if (valoresCombo.getSelectionModel().getSelectedIndex() == 3) {
                valor1 = "300";
                valor2 = "399";
            } else if (valoresCombo.getSelectionModel().getSelectedIndex() == 4) {
                valor1 = "400";
                valor2 = "499";
            } else if (valoresCombo.getSelectionModel().getSelectedIndex() == 5) {
                valor1 = "500";
                valor2 = "599";
            } else if (valoresCombo.getSelectionModel().getSelectedIndex() == 6) {
                valor1 = "600";
                valor2 = "1000000000";
            }

            ObservableList<Despesa> lista;
            try {
                lista = (ObservableList<Despesa>) despDao.filtrarValor(valor1, valor2, usuario);
                tabela.setItems(lista);
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        estatisticaItem.setOnAction(e -> {

            try {
                estatis = new Estatistica(usuario);
                estatis.start(new Stage());
                janela.hide();
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        addImage.setOnMouseExited(e -> {
            ver = true;
        });
        addImage.setOnMouseEntered(e -> {
            ver = false;
        });

        imagemPane.setOnMouseEntered(e -> {
            if (ver == false) {
                addImage.setVisible(true);
                ver = true;
            } else {
                addImage.setVisible(false);
                ver = false;
            }

        });
        addImage.setOnAction(e -> {
            escolherImagem = new FileChooser();
            escolherImagem.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.png", "*.jpg"));
            ficheiro = escolherImagem.showOpenDialog(null);
            if (ficheiro != null) {
                try {
                    imgDao.atualizarImagem(ficheiro.getAbsolutePath(), usuario);

                    perfilImage = new Image(new FileInputStream(imgDao.listarImagem(usuario)));
                    perfilView.setImage(perfilImage);

                    System.out.println(ficheiro.getAbsolutePath());
                } catch (SQLException ex) {
                    System.out.println("Erro ao passar o caminho da imagem");
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Nunhuma imagem foi selecionada!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            }

        });

        perfilItem.setOnAction(e -> {
            perfil = new Perfil(usuario);
            try {
                perfil.start(new Stage());
                janela.hide();
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        btnDados.setOnAction(e -> {
            perfil = new Perfil(usuario);
            try {
                perfil.start(new Stage());
                janela.hide();
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            janela.getIcons().add(logotipo);
        } catch (NullPointerException ex) {
            System.out.println("Erro carregando imagem");
        }

        janela.setTitle("GD => Tela Principal!");
        janela.setScene(cena);
        //janela.setResizable(false);
        janela.show();
    }
}
