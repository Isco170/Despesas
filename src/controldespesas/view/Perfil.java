package controldespesas.view;

import controldespesas.dao.EmpresaDAO;
import controldespesas.dao.MoradaDAO;
import controldespesas.dao.UsuarioDAO;
import controldespesas.domain.Empresa;
import controldespesas.domain.Morada;
import controldespesas.domain.Usuario;
import controldespesas.view.FormatarNumeros.TextFieldFormatter;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class Perfil extends Application {

    public Perfil(Usuario us) {
        user = us;
        empDao = new EmpresaDAO();
        try {
            emp = empDao.listarEmpresa(user);

        } catch (SQLException ex) {
            System.out.println("Problemas listando empresa");
            Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        morDAO = new MoradaDAO();
        try {
            mor = morDAO.listarMorada(user);
        } catch (SQLException ex) {
            System.out.println("problemas listando morada");
            Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Stage janela;
    BorderPane raiz;
    Scene cena;
    HBox box;
    StackPane pane;
    VBox cima;
    AnchorPane dadosPessoais, botoes;
    Text text, text2, texto3, texto4, msg;
    TextField nomeField, apelidoField, teleField, emailField, userField, senhaField,
            avenidaField, bairroField, quarteiraoField, casaField, ruaField, empresaField,
            enderecoField, empresaMailField, telefoneField;
    Separator separador1, separador2, separador3, separador4, separador5, separador6;
    GridPane dados;

    Button btnDados, dPessoais, dEmpresa, cancelar, salvar, btnEditar, btnVoltar;

    TabPane dadosAdicionais;
    Tab moradaTab, empregoTab;

    int tel;
    Usuario usuario, user;
    Morada morada, mor;
    Empresa empresa, emp;
    EmpresaDAO empDao;
    UsuarioDAO userDAO;
    MoradaDAO morDAO;

    Image save, voltar, cancelarImage, editarImage;
    ImageView imageView, voltarView, cancelarView, editarView;

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

    public void initsComponents() throws FileNotFoundException {
        raiz = new BorderPane();

        dadosPessoais = new AnchorPane();

        dadosAdicionais = new TabPane();

        moradaTab = new Tab("Morada");
        empregoTab = new Tab("Emprego");

        btnVoltar = new Button("", voltarView);
        btnVoltar.setLayoutX(0);
        btnVoltar.setLayoutY(-70);
        btnVoltar.setId("btnVoltar");

        btnDados = new Button();
        box = new HBox();

        dPessoais = new Button("Dados Pessoais");
        msg = new Text();
        msg.setVisible(false);

        dEmpresa = new Button("Detalhes do Emprego");
        text = new Text("Sistema de gerenciamento de despesas");
        text.setStyle("-fx-background-color: yellow;");
        pane = new StackPane();
        dados = new GridPane();
        cima = new VBox();

        nomeField = TextFields.createClearableTextField();
        nomeField.setText(user.getNome());
        nomeField.setEditable(false);
        Tooltip.install(nomeField, new Tooltip("Digite o seu nome aqui"));
        separador1 = new Separator();

        apelidoField = TextFields.createClearableTextField();
        apelidoField.setText(user.getApelido());
        apelidoField.setEditable(false);
        Tooltip.install(apelidoField, new Tooltip("Digite o seu apelido aqui"));
        separador2 = new Separator();

        teleField = TextFields.createClearableTextField();
        teleField.setText(Integer.toString(user.getTelefone()));
        teleField.setEditable(false);
        Tooltip.install(teleField, new Tooltip("Digite o seu número de telefone aqui"));
        separador3 = new Separator();

        emailField = TextFields.createClearableTextField();
        emailField.setText(user.getEmail());
        emailField.setEditable(false);
        Tooltip.install(emailField, new Tooltip("Digite o seu e-mail aqui"));
        separador4 = new Separator();

        userField = TextFields.createClearableTextField();
        userField.setText(user.getUsuario());
        userField.setEditable(false);
        Tooltip.install(userField, new Tooltip("Digite o nome de usuário, a sua escolha"));
        separador5 = new Separator();

        senhaField = TextFields.createClearableTextField();
        senhaField.setText(user.getSenha());
        senhaField.setEditable(false);
        Tooltip.install(senhaField, new Tooltip("Digite uma senha para o seu usuário"));
        separador6 = new Separator();

    }

    public void initsLayout() throws FileNotFoundException {
        cena = new Scene(raiz, 1000, 600);
        cena.getStylesheets().add("controldespesas/view/css/estilo.css");

        dadosPessoais.setPrefWidth(100);
        dadosPessoais.setPrefHeight(500);
        dadosPessoais.setBackground(Background.EMPTY);
        dadosPessoais.setId("dadosPessoais");

        btnDados.setPrefWidth(300);
        btnDados.setPrefHeight(500);
        btnDados.setId("btn");
        btnDados.setFocusTraversable(false);

        box.setPadding(new Insets(0, 0, 0, 20));
        box.setSpacing(50);

        dPessoais.setPrefWidth(200);
        dPessoais.setPrefHeight(50);
        dPessoais.setFocusTraversable(false);
        dPessoais.setOpacity(0.6);
        dPessoais.setContentDisplay(ContentDisplay.CENTER);
        dPessoais.setId("dPessoais");

        dEmpresa.setPrefHeight(50);
        dEmpresa.setFocusTraversable(false);
        dEmpresa.setOpacity(0.6);
        dEmpresa.setContentDisplay(ContentDisplay.CENTER);
        dEmpresa.setId("dPessoais");

        pane.setId("dp");
        pane.setPrefWidth(150);
        pane.setPrefHeight(50);
        pane.getChildren().addAll(dPessoais);

        dados.setVgap(20);
        dados.setId("dados");

        cima.setPrefHeight(80);
        cima.setAlignment(Pos.CENTER);

        Label lab = new Label("");
        lab.setPrefWidth(1000);
        lab.setPrefHeight(40);
        //lab.setStyle("-fx-background-color: #FFFFFF");

        cima.getChildren().addAll(text, lab);
        cima.setId("cima");

        nomeField.setPrefWidth(270);
        nomeField.setPromptText("Primeiro Nome");
        nomeField.setBackground(Background.EMPTY);
        nomeField.setFocusTraversable(false);

        apelidoField.setPromptText("Apelido");
        apelidoField.setBackground(Background.EMPTY);
        apelidoField.setFocusTraversable(false);

        teleField.setPromptText("Telefone");
        teleField.setBackground(Background.EMPTY);
        teleField.setFocusTraversable(false);

        emailField.setPromptText("E-mail");
        emailField.setBackground(Background.EMPTY);
        emailField.setFocusTraversable(false);

        userField.setPromptText("Usuário");
        userField.setBackground(Background.EMPTY);
        userField.setFocusTraversable(false);

        senhaField.setPromptText("Senha");
        senhaField.setBackground(Background.EMPTY);
        senhaField.setFocusTraversable(false);

        GridPane.setConstraints(nomeField, 0, 1, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador1, 0, 1, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(apelidoField, 0, 2, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador2, 0, 2, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(teleField, 0, 3, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador3, 0, 3, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(emailField, 0, 4, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador4, 0, 4, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(userField, 0, 5, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador5, 0, 5, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(senhaField, 0, 6, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador6, 0, 6, 1, 1, HPos.LEFT, VPos.BOTTOM);

        dados.getChildren().addAll(nomeField, separador1, apelidoField, separador2,
                teleField, separador3, emailField, separador4, userField, separador5,
                senhaField, separador6);

        dadosPessoais.getChildren().addAll(btnVoltar, btnDados, dPessoais, dados, msg);

        msg.setLayoutY(55);
        msg.setLayoutX(30);

        dPessoais.setLayoutY(-20);
        dPessoais.setLayoutX(45);
        dados.setLayoutY(65);
        dados.setLayoutX(10);

        moradaTab.setClosable(false);
        moradaTab.setId("tab");

        //MORADA
        GridPane layMorada = new GridPane();
        layMorada.setPadding(new Insets(40, 0, 0, 10));
        layMorada.setVgap(10);
        layMorada.setHgap(40);

        Text tAvenida = new Text("Avenida");
        avenidaField = TextFields.createClearableTextField();
        avenidaField.setText(mor.getAvenida());
        avenidaField.setEditable(false);
        avenidaField.setPrefWidth(250);
        avenidaField.setBorder(Border.EMPTY);
        avenidaField.setPromptText("Avenida");
        String avenidas[] = {"Av. de Moçambique", "Av. de Angola", "Av. Samora Machel", "Av. Filipe Samuel Magaia", "Av. Emília Daússe"};
        TextFields.bindAutoCompletion(avenidaField, avenidas);

        Text tBairro = new Text("Bairro");
        bairroField = TextFields.createClearableTextField();
        bairroField.setText(mor.getBairro());
        bairroField.setEditable(false);
        bairroField.setPrefWidth(250);
        bairroField.setBorder(Border.EMPTY);
        bairroField.setPromptText("Bairro");
        String bairros[] = {"Inhagoia", "Jardim", "Benfica", "Alto-Maé", "Magoanine", "Laulane", "Choupal"};
        TextFields.bindAutoCompletion(bairroField, bairros);

        Text tQuarteirao = new Text("Quarteirão");
        quarteiraoField = TextFields.createClearableTextField();
        quarteiraoField.setText(Integer.toString(mor.getQuarteirao()));
        quarteiraoField.setEditable(false);
        quarteiraoField.setPrefWidth(250);
        quarteiraoField.setBorder(Border.EMPTY);
        quarteiraoField.setPromptText("Quarteirão");

        Text tCasa = new Text("Nr. Casa");
        casaField = TextFields.createClearableTextField();
        casaField.setText(Integer.toString(mor.getNrCasa()));
        casaField.setEditable(false);
        casaField.setBorder(Border.EMPTY);
        casaField.setPromptText("Nr. Casa");

        String nrCasa[] = {"01", "11", "02", "22", "03", "33", "04", "44", "05", "55", "06", "66", "07", "77", "08", "88", "09", "99", "10"};
        TextFields.bindAutoCompletion(casaField, nrCasa);

        Text tRua = new Text("Rua");
        ruaField = TextFields.createClearableTextField();
        ruaField.setText(mor.getRua());
        ruaField.setEditable(false);
        ruaField.setBorder(Border.EMPTY);
        ruaField.setPromptText("Rua");

        String ruas[] = {"01", "11", "02", "22", "03", "33", "04", "44", "05", "55", "06", "66", "07", "77", "08", "88", "09", "99", "10"};
        TextFields.bindAutoCompletion(ruaField, ruas);

        GridPane.setConstraints(tAvenida, 0, 0);
        GridPane.setConstraints(avenidaField, 0, 1);

        GridPane.setConstraints(tBairro, 1, 0);
        GridPane.setConstraints(bairroField, 1, 1);

        GridPane.setConstraints(tQuarteirao, 0, 2);
        GridPane.setConstraints(quarteiraoField, 0, 3);

        GridPane.setConstraints(tCasa, 1, 2);
        GridPane.setConstraints(casaField, 1, 3);

        GridPane.setConstraints(tRua, 0, 4);
        GridPane.setConstraints(ruaField, 0, 5);

        layMorada.getChildren().addAll(tAvenida, avenidaField, tBairro, bairroField, quarteiraoField,
                tQuarteirao, tCasa, casaField, tRua, ruaField);

        moradaTab.setContent(layMorada);
        //FIM MORADA

        //EMPRESA
        GridPane layEmprego = new GridPane();
        layEmprego.setPadding(new Insets(40, 0, 0, 10));
        layEmprego.setVgap(10);
        layEmprego.setHgap(40);

        Text tNomeEmpresa = new Text("Nome da Empresa");
        empresaField = TextFields.createClearableTextField();
        empresaField.setText(emp.getNome());
        empresaField.setEditable(false);
        empresaField.setPrefWidth(250);
        empresaField.setBorder(Border.EMPTY);
        empresaField.setPromptText("Nome da empresa");

        Text tEndereco = new Text("Endereço");
        enderecoField = TextFields.createClearableTextField();
        enderecoField.setText(emp.getEndereco());
        enderecoField.setEditable(false);
        enderecoField.setPrefWidth(250);
        enderecoField.setBorder(Border.EMPTY);
        enderecoField.setPromptText("Endereço");

        Text tEmail = new Text("E-mail");
        empresaMailField = TextFields.createClearableTextField();
        empresaMailField.setText(emp.getEmail());
        empresaMailField.setEditable(false);
        empresaMailField.setPrefWidth(250);
        empresaMailField.setBorder(Border.EMPTY);
        empresaMailField.setPromptText("E-mail");

        Text tTelefone = new Text("Telefone");
        telefoneField = TextFields.createClearableTextField();
        telefoneField.setText(Integer.toString(emp.getTelefone()));
        telefoneField.setEditable(false);
        telefoneField.setPrefWidth(250);
        telefoneField.setBorder(Border.EMPTY);
        telefoneField.setPromptText("Telefone");

        GridPane.setConstraints(tNomeEmpresa, 0, 0, 1, 1);
        GridPane.setConstraints(empresaField, 0, 1, 1, 1);

        GridPane.setConstraints(tEndereco, 1, 0, 1, 1);
        GridPane.setConstraints(enderecoField, 1, 1, 1, 1);

        GridPane.setConstraints(tEmail, 0, 2, 1, 1);
        GridPane.setConstraints(empresaMailField, 0, 3, 1, 1);

        GridPane.setConstraints(tTelefone, 1, 2, 1, 1);
        GridPane.setConstraints(telefoneField, 1, 3, 1, 1);

        layEmprego.getChildren().addAll(tNomeEmpresa, empresaField,
                tEndereco, enderecoField, tEmail, empresaMailField, tTelefone, telefoneField);

        empregoTab.setClosable(false);
        empregoTab.setContent(layEmprego);
        empregoTab.setId("tab");
        //FIM EMPREGO

        dadosAdicionais.setId("pane");
        dadosAdicionais.getTabs().addAll(moradaTab, empregoTab);
        //dadosAdicionais.getStylesheets().add("controldespesas/view/css/style.css");
        dadosAdicionais.setPrefWidth(500);
        dadosAdicionais.setPrefHeight(420);

        //BOTOES DE SALVAR E CANCELAR
        botoes = new AnchorPane();
        botoes.setPrefWidth(500);

        cancelar = new Button("Cancelar", cancelarView);
        //cancelar.setPrefWidth(100);
        cancelar.setLayoutX(270);
        cancelar.setVisible(false);
        cancelar.setId("Cadastro_cancelar");

        salvar = new Button("Salvar", imageView);
        //salvar.setPrefWidth(100);
        salvar.setLayoutX(100);
        salvar.setVisible(false);
        salvar.setId("Cadastro_salvar");

        btnEditar = new Button("Editar", editarView);
        btnEditar.setId("btnEditar");
        //btnEditar.setPrefWidth(100);
        btnEditar.setLayoutX(100);

        botoes.getChildren().addAll(cancelar, salvar, btnEditar);

        VBox direita = new VBox();
        direita.getChildren().addAll(dadosAdicionais, botoes);

        box.getChildren().addAll(dadosPessoais, direita);

        raiz.setTop(cima);
        raiz.setCenter(box);
    }

    public void initImage() {

        try {
            save = new Image(getClass().getResourceAsStream("img/save.png"));
            imageView = new ImageView(save);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        try {
            voltar = new Image(getClass().getResourceAsStream("img/back.png"));
            voltarView = new ImageView(voltar);
            voltarView.setFitWidth(25);
            voltarView.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        try {
            cancelarImage = new Image(getClass().getResourceAsStream("img/cancelar.png"));
            cancelarView = new ImageView(cancelarImage);
            cancelarView.setFitWidth(25);
            cancelarView.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar img");
        }

        try {
            editarImage = new Image(getClass().getResourceAsStream("img/editar.png"));
            editarView = new ImageView(editarImage);
            editarView.setFitWidth(25);
            editarView.setFitHeight(25);

        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar imagem");
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initImage();
        initsComponents();
        initsLayout();

        janela.setOnCloseRequest(e -> {
            Principal.janela.show();
        });

        nomeField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador1.setStyle("-fx-background-color: blue;");

            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador3.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
        });

        apelidoField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador2.setStyle("-fx-background-color: blue");

            separador1.setStyle("-fx-background-color: #FFFFFF");
            separador3.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
        });

        teleField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador3.setStyle("-fx-background-color: blue");

            separador1.setStyle("-fx-background-color: #FFFFFF");
            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
        });

        emailField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador4.setStyle("-fx-background-color: blue");

            separador1.setStyle("-fx-background-color: #FFFFFF");
            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador3.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
        });
        userField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador5.setStyle("-fx-background-color: blue");

            separador1.setStyle("-fx-background-color: #FFFFFF");
            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador3.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
        });
        senhaField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador6.setStyle("-fx-background-color: blue");

            separador1.setStyle("-fx-background-color: #FFFFFF");
            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador3.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
        });

        teleField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("(###)-#########");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(teleField);
            tff.formatter();
        });
        quarteiraoField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("####");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(quarteiraoField);
            tff.formatter();
        });
        casaField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("####");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(casaField);
            tff.formatter();
        });
        ruaField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("####");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(ruaField);
            tff.formatter();
        });
        telefoneField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("(###)-#########");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(telefoneField);
            tff.formatter();
        });

        btnVoltar.setOnAction(e -> {
            janela.close();
            Principal.janela.show();
        });
        btnEditar.setOnAction(e -> {
            salvar.setVisible(true);
            cancelar.setVisible(true);
            btnEditar.setVisible(false);

            nomeField.setEditable(true);
            apelidoField.setEditable(true);
            teleField.setEditable(true);
            emailField.setEditable(true);
            userField.setEditable(true);
            senhaField.setEditable(true);
            avenidaField.setEditable(true);
            bairroField.setEditable(true);
            quarteiraoField.setEditable(true);
            casaField.setEditable(true);
            ruaField.setEditable(true);
            empresaField.setEditable(true);
            enderecoField.setEditable(true);
            empresaMailField.setEditable(true);
            telefoneField.setEditable(true);
        });

        cancelar.setOnAction(e -> {
            janela.close();
            Principal.janela.show();

        });

        salvar.setOnAction(e -> {
            boolean vazio = false;
            boolean prontos = true;

            if (("".equals(nomeField.getText()) || nomeField.getText() == null) || ("".equals(apelidoField.getText()) || apelidoField.getText() == null)
                    || ("".equals(teleField.getText()) || teleField.getText() == null) || ("".equals(emailField.getText()) || emailField.getText() == null)
                    || ("".equals(userField.getText()) || userField.getText() == null) || ("".equals(senhaField.getText()) || senhaField.getText() == null)
                    || ("".equals(avenidaField.getText()) || avenidaField.getText() == null) || ("".equals(bairroField.getText()) || bairroField.getText() == null)
                    || ("".equals(quarteiraoField.getText()) || quarteiraoField.getText() == null) || ("".equals(casaField.getText()) || casaField.getText() == null)
                    || ("".equals(ruaField.getText()) || ruaField.getText() == null) || ("".equals(empresaField.getText()) || empresaField.getText() == null)
                    || ("".equals(enderecoField.getText()) || enderecoField.getText() == null) || ("".equals(empresaMailField.getText()) || empresaMailField.getText() == null)
                    || ("".equals(telefoneField.getText()) || telefoneField.getText() == null)) {

                vazio = true;

            }

            if (vazio == true) {
                //JOptionPane.showMessageDialog(null, "Preeencher todos os DADOS", "Falha!", JOptionPane.WARNING_MESSAGE);
                notificacao(Pos.TOP_LEFT, graphic, "Preeencher todos os DADOS");
                notificacaoBuilder.showWarning();

                dPessoais.setStyle("-fx-background-color: #ff0000");
                dPessoais.setOpacity(1);

//                msg.setLayoutX(30);
//                msg.setText("Preencher todos os DADOS!");
//                msg.setVisible(true);
            }
            int cas = 0, q = 0, tel2 = 0;
            if (vazio == false) {
                try {
                    tel = Integer.parseInt(teleField.getText());
                    cas = Integer.parseInt(casaField.getText());
                    q = Integer.parseInt(quarteiraoField.getText());
                    tel2 = Integer.parseInt(telefoneField.getText());
                    prontos = true;
                } catch (NumberFormatException er) {

                    //JOptionPane.showMessageDialog(null, "Números de casa, telefone e quarteirão não podem conter letras", "Falha!", JOptionPane.WARNING_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic, "Números de casa, telefone e quarteirão não podem conter letras");
                    notificacaoBuilder.showWarning();

                    dPessoais.setStyle("-fx-background-color: #ff0000");
                    dPessoais.setOpacity(1);
                    //msg.setVisible(true);
                    prontos = false;
                }

            }

            if (prontos == true && vazio == false) {

                userDAO = new UsuarioDAO();

                user.setNome(nomeField.getText());
                user.setApelido(apelidoField.getText());
                user.setUsuario(userField.getText());
                user.setSenha(senhaField.getText());
                user.setTelefone(tel);
                user.setEmail(emailField.getText());

                mor = new Morada(avenidaField.getText(), bairroField.getText(), ruaField.getText(), q, cas);
                emp = new Empresa(empresaField.getText(), enderecoField.getText(), empresaMailField.getText(), tel2);
                boolean sucesso = true;
                try {
                    userDAO.editarUsuario(user);
                } catch (SQLException ex) {
                    sucesso = false;
                    Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    morDAO.editarMorada(mor, user);
                } catch (SQLException ex) {
                    sucesso = false;
                    Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    empDao.editarEmpresa(emp, user);
                } catch (SQLException ex) {
                    sucesso = false;
                    Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (sucesso == true) {
                    //JOptionPane.showMessageDialog(null, "Editado com sucesso", "Tudo certo!", JOptionPane.INFORMATION_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic, "Editado com sucesso");
                    notificacaoBuilder.showWarning();

                    nomeField.setEditable(false);
                    apelidoField.setEditable(false);
                    teleField.setEditable(false);
                    emailField.setEditable(false);
                    userField.setEditable(false);
                    senhaField.setEditable(false);
                    avenidaField.setEditable(false);
                    bairroField.setEditable(false);
                    quarteiraoField.setEditable(false);
                    casaField.setEditable(false);
                    ruaField.setEditable(false);
                    empresaField.setEditable(false);
                    enderecoField.setEditable(false);
                    empresaMailField.setEditable(false);
                    telefoneField.setEditable(false);

                    salvar.setVisible(false);
                    cancelar.setVisible(false);
                    btnEditar.setVisible(true);
                }

            }
        });

        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            janela.getIcons().add(logotipo);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        janela.setTitle("GD => Perfil");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.show();
    }
}
