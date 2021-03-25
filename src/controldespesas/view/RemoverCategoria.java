package controldespesas.view;

import controldespesas.dao.CategoriaDAO;
import controldespesas.dao.DespesaDAO;
import controldespesas.domain.Categoria;
import controldespesas.domain.Usuario;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class RemoverCategoria extends Application {

    public RemoverCategoria(Usuario user) {
        usuario = user;
    }

    CategoriaDAO catDao;
    DespesaDAO despDao;
    Usuario usuario;

    Stage janela;
    Scene cena;

    VBox layout;
    HBox botoes;
    Button btnRemover, btnCancelar;
    Label nota;
    ComboBox<String> categorias;
    ObservableList<Categoria> listaCategoria;

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
        layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));

        categorias = new ComboBox();
        categorias.setPrefWidth(300);
        categorias.setPromptText("Escolha a categoria a remover");
        try {
            removerCategoria();
        } catch (SQLException ex) {
            Logger.getLogger(RemoverCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnRemover = new Button("Remover");
        btnRemover.setId("Cadastro_cancelar");

        btnCancelar = new Button("Cancelar");
        btnCancelar.setId("btnEditar");
        botoes = new HBox();
        botoes.setSpacing(10);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        nota = new Label("Note que removendo uma categoria, todas as\ndepesas ancoradas"
                + " a ela, por padrao passarão a ser,\nda categoria 'Outras', que não é usada nas estatísticas");
        nota.setId("nota");

        cena = new Scene(layout, 300, 170);
        cena.getStylesheets().add("controldespesas/view/css/estilo.css");
    }

    public void initsLayout() {
        botoes.getChildren().addAll(btnRemover, btnCancelar);
        layout.getChildren().addAll(categorias, botoes, nota);
    }

    public void removerCategoria() throws SQLException {
        catDao = new CategoriaDAO();
        listaCategoria = (ObservableList<Categoria>) catDao.listarCategoria(usuario);
        for (int i = 0; i < listaCategoria.size(); i++) {
            categorias.getItems().add(i, listaCategoria.get(i).getNome());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;
        initsComponents();
        initsLayout();

        btnRemover.setOnAction(e -> {
            if (categorias.getSelectionModel().getSelectedIndex() >= 0) {
                despDao = new DespesaDAO();
                try {

                    String deletada = listaCategoria.get(categorias.getSelectionModel().getSelectedIndex()).getNome();
                    catDao.excluirCategoria(listaCategoria.get(categorias.getSelectionModel().getSelectedIndex()));

                    Date hora = new Date();
                    SimpleDateFormat formatar = new SimpleDateFormat("YYYY-MM-dd HH:mm:s");
                    String dataFormatada = formatar.format(hora);
                    catDao.dataRemovido(dataFormatada, listaCategoria.get(categorias.getSelectionModel().getSelectedIndex()));

                    listaCategoria.remove(categorias.getSelectionModel().getSelectedIndex());
                    despDao.atribuirCategoria("Outra", deletada);

                    categorias.getItems().clear();
                    for (int i = 0; i < listaCategoria.size(); i++) {
                        categorias.getItems().add(i, listaCategoria.get(i).getNome());
                    }
                    categorias.getItems().add(listaCategoria.size(), "Outra");

                    //JOptionPane.showMessageDialog(null, "Removida", "Categoria removida", JOptionPane.INFORMATION_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic, "Categoria removida");
                    notificacaoBuilder.showInformation();
                    Principal.tabela.setItems(Principal.listarDespesas());

                } catch (SQLException ex) {
                    //JOptionPane.showMessageDialog(null, "Nao foi possivel remover", "Falha ao remover", JOptionPane.WARNING_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic, "Nao foi possivel remover categoria");
                    notificacaoBuilder.showWarning();
                    Logger.getLogger(RemoverCategoria.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IndexOutOfBoundsException er) {
                    //JOptionPane.showMessageDialog(null, "Não pode remover categoria padrão!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic, "Nao foi possivel remover categoria");
                    notificacaoBuilder.showWarning();
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Selecione a categoria", "Falha", JOptionPane.WARNING_MESSAGE);
                notificacao(Pos.TOP_LEFT, graphic, "Selecione categoria");
                notificacaoBuilder.showInformation();
            }
        });

        btnCancelar.setOnAction(e -> {
            janela.close();
        });

        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            janela.getIcons().add(logotipo);
        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar imagem");
        }

        janela.setTitle("GD => Remover Despesa");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();
    }

}
