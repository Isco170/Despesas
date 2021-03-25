package controldespesas.view;

import controldespesas.dao.CategoriaDAO;
import controldespesas.dao.DespesaDAO;
import controldespesas.domain.Categoria;
import controldespesas.domain.Despesa;
import controldespesas.domain.Usuario;
import controldespesas.view.FormatarNumeros.TextFieldFormatter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class Despesa_Adicionar extends Application {

    public Despesa_Adicionar(Usuario user) {
        usuario = user;
    }

    public Despesa_Adicionar() {
        usuario = new Usuario();
    }

    Usuario usuario;
    Despesa despesa;
    DespesaDAO despDao;
    CategoriaDAO catDao;

    Stage janela;
    Scene cena;

    AnchorPane layout;
    GridPane grid;
    Button btnBackground, header, cancelar, guardar;
    HBox botoes;
    TextField nomeField, valorField;
    Label msg;
    Separator nomeSeparator, valorSeparator;
    TextArea descricaoArea;
    ComboBox<String> categoria;

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

        layout = new AnchorPane();

        header = new Button("Nova Despesa");
        header.setPrefSize(200, 50);
        header.setLayoutX(150);
        header.setLayoutY(10);
        header.setId("dPessoais");

        msg = new Label("Por favor, preencha todos campos");
        msg.setLayoutX(150);
        msg.setLayoutY(65);
        msg.setVisible(false);
        msg.setId("texto");

        btnBackground = new Button();
        btnBackground.setFocusTraversable(false);
        btnBackground.setPrefSize(500, 670);
        btnBackground.setLayoutY(40);
        btnBackground.setId("btn");

        grid = new GridPane();
        grid.setLayoutX(100);
        grid.setLayoutY(90);
        grid.setVgap(30);

        nomeField = TextFields.createClearableTextField();
        nomeField.setPrefWidth(270);
        nomeField.setPromptText("Nome");
        nomeField.setBackground(Background.EMPTY);
        nomeField.setFocusTraversable(false);
        nomeSeparator = new Separator();

        valorField = TextFields.createClearableTextField();
        valorField.setPrefWidth(270);
        valorField.setPromptText("Valor");
        valorField.setBackground(Background.EMPTY);
        valorField.setFocusTraversable(false);
        valorSeparator = new Separator();

        categoria = new ComboBox();
        categoria.setPrefWidth(270);
        categoria.setPromptText("Categorias");
        try {
            preencherCategoria(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(Despesa_Adicionar.class.getName()).log(Level.SEVERE, null, ex);
        }

        descricaoArea = new TextArea();
        descricaoArea.setPrefWidth(270);
        descricaoArea.setPromptText("Descrição");

        cancelar = new Button("Cancelar");
        cancelar.setId("Cadastro_cancelar");

        guardar = new Button("Guardar");
        guardar.setId("Cadastro_salvar");

        botoes = new HBox();
        botoes.setSpacing(39);
        botoes.setLayoutX(150);
        botoes.setLayoutY(640);
        botoes.getChildren().addAll(cancelar, guardar);

        cena = new Scene(layout, 500, 700);
        cena.getStylesheets().add("controldespesas/view/css/estilo.css");
    }

    public void initsLayout() {

        GridPane.setConstraints(nomeField, 0, 1, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(nomeSeparator, 0, 1, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(valorField, 0, 2, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(valorSeparator, 0, 2, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(categoria, 0, 3, 1, 1, HPos.LEFT, VPos.TOP);

        GridPane.setConstraints(descricaoArea, 0, 4, 1, 1, HPos.LEFT, VPos.TOP);

        grid.getChildren().addAll(nomeField, nomeSeparator, valorField, valorSeparator, categoria, descricaoArea);

        layout.getChildren().addAll(btnBackground, grid, header, msg, botoes);
    }

    public void preencherCategoria(Usuario usuario) throws SQLException {
        CategoriaDAO catDao = new CategoriaDAO();
        ObservableList<Categoria> listaCategoria = (ObservableList<Categoria>) catDao.listarCategoria(usuario);
        for (int i = 0; i < listaCategoria.size(); i++) {
            categoria.getItems().add(i, listaCategoria.get(i).getNome());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initsComponents();
        initsLayout();

        cancelar.setOnAction(e -> {
            janela.close();
        });

        guardar.setOnAction(e -> {
            if ((!nomeField.getText().equals("") || nomeField.getText() != null) && (!valorField.getText().equals("") || valorField.getText() != null)
                    && (!descricaoArea.getText().equals("") || descricaoArea.getText() != null) && categoria.getValue() != null) {

                //INICIO CASO TODOS OS CAMPOS ESTAJAM PREENCHIDOS
                //////////////////////////////////////////////
                System.out.println("Preenchidos");
                boolean certo = false;
                float valor = 0;
                try {
                    valor = Float.parseFloat(valorField.getText());
                    certo = true;
                } catch (NumberFormatException er) {
                    certo = false;
                }

                if (certo == true) {
                    try {
                        Date hora = new Date();
                        SimpleDateFormat formatar = new SimpleDateFormat("YYYY-MM-dd HH:mm:s");
                        String dataFormatada = formatar.format(hora);

                        despesa = new Despesa();
                        despDao = new DespesaDAO();
                        catDao = new CategoriaDAO();

                        despesa.setNome(nomeField.getText());
                        despesa.setCategoria(categoria.getValue());
                        despesa.setDescricao(descricaoArea.getText());
                        despesa.setValor(valor);

                        despesa.setData(dataFormatada);
                        despesa.setUsuario(usuario);

                        despDao.salvarDespesa(despesa);
                        catDao.categoriaUsada(categoria.getValue(), usuario);

                        //JOptionPane.showMessageDialog(null, "Salvo", "Confirmado", JOptionPane.PLAIN_MESSAGE);
                        notificacao(Pos.TOP_CENTER, graphic, "Salvo com sucesso");
                        notificacaoBuilder.showInformation();

                        nomeField.setText("");
                        valorField.setText("");
                        descricaoArea.setText("");
                        Principal.tabela.setItems(Principal.listarDespesas());

                    } catch (SQLException ex) {
                        Logger.getLogger(Despesa_Adicionar.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    //JOptionPane.showMessageDialog(null, "Valor da Despesa está incorrecto", "Falha", JOptionPane.WARNING_MESSAGE);
                    notificacao(Pos.TOP_CENTER, graphic, "Valor da Despesa está incorrecto");
                    notificacaoBuilder.showError();
                }
                /////////////////////////////////////////////
                //FIM CASO TODOS OS CAMPOS ESTAJAM PREENCHIDOS
            } else {
                //JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos", "Aviso", JOptionPane.WARNING_MESSAGE);
                notificacao(Pos.TOP_CENTER, graphic, "Por favor, preencha todos os campos");
                notificacaoBuilder.showWarning();
            }

        });

        valorField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("######");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(valorField);
            tff.formatter();
        });

        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            janela.getIcons().add(logotipo);
        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar imagem");
        }

        janela.setTitle("GD => Nova Despesa");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();

    }

}
