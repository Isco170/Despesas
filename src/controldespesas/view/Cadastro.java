package controldespesas.view;

import controldespesas.dao.MoradaDAO;
import controldespesas.dao.UsuarioDAO;
import controldespesas.domain.Empresa;
import controldespesas.domain.Morada;
import controldespesas.domain.Usuario;
import controldespesas.view.FormatarNumeros.TextFieldFormatter;
import java.io.FileInputStream;
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
import javafx.scene.control.PasswordField;
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
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class Cadastro extends Application {

    Login log;
    BorderPane raiz;
    Scene cena;
    HBox box, botoes;
    StackPane pane;
    VBox cima;
    AnchorPane dadosPessoais, anchor;
    Text text, text2, texto3, texto4, msg;
    TextField nomeField, apelidoField, telField, emailField, userField,
            avenidaField, bairroField, quarteiraoField, casaField, ruaField, empresaField,
            enderecoField, empresaMailailField, telefoneField;
    PasswordField senhaField, senhaField2;
    Separator separador1, separador2, separador3, separador4, separador5, separador6, separador7;
    GridPane dados;

    Button btnDados, dPessoais, dEmpresa, cancelar, salvar;

    TabPane dadosAdicionais;
    Tab moradaTab, empregoTab;

    int tel;
    Usuario usuario;
    Morada morada;
    Empresa empresa;
    UsuarioDAO userDAO;
    MoradaDAO morDAO;

    Image save, logo;
    ImageView imageView;

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

        btnDados = new Button();
        box = new HBox();

        dPessoais = new Button("Dados Pessoais");
        msg = new Text();
        msg.setVisible(false);

        dEmpresa = new Button("Detalhes do Emprego");
        text = new Text("Sistema de gerenciamento de despesas");
        text.setStyle("-fx-background-color: yellow;");
        text.setLayoutX(400);
        text.setLayoutY(20);
        text.setId("tex");

        anchor = new AnchorPane();

        pane = new StackPane();
        dados = new GridPane();
        cima = new VBox();

        nomeField = TextFields.createClearableTextField();
        Tooltip.install(nomeField, new Tooltip("Digite o seu nome aqui"));
        separador1 = new Separator();

        apelidoField = TextFields.createClearableTextField();
        Tooltip.install(apelidoField, new Tooltip("Digite o seu apelido aqui"));
        separador2 = new Separator();

        telField = TextFields.createClearableTextField();
        Tooltip.install(telField, new Tooltip("Digite o seu número de telefone aqui"));
        separador3 = new Separator();

        emailField = TextFields.createClearableTextField();
        Tooltip.install(emailField, new Tooltip("Digite o seu e-mail aqui"));
        separador4 = new Separator();

        userField = TextFields.createClearableTextField();
        Tooltip.install(userField, new Tooltip("Digite o nome de usuário, a sua escolha"));
        separador5 = new Separator();

        senhaField = new PasswordField();
        Tooltip.install(senhaField, new Tooltip("Digite uma senha para o seu usuário"));
        separador6 = new Separator();

        senhaField2 = new PasswordField();
        Tooltip.install(senhaField2, new Tooltip("Sua senha deve ser igual a primeira"));
        separador7 = new Separator();

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

        Label lab = new Label();
        lab.setPrefWidth(1000);
        lab.setPrefHeight(40);

        anchor.getChildren().addAll(text);
        cima.getChildren().addAll(anchor, lab);
        cima.setId("cima");

        nomeField.setPrefWidth(270);
        nomeField.setPromptText("Primeiro Nome");
        nomeField.setBackground(Background.EMPTY);
        nomeField.setFocusTraversable(false);

        apelidoField.setPromptText("Apelido");
        apelidoField.setBackground(Background.EMPTY);
        apelidoField.setFocusTraversable(false);

        telField.setPromptText("Telefone");
        telField.setBackground(Background.EMPTY);
        telField.setFocusTraversable(false);

        emailField.setPromptText("E-mail");
        emailField.setBackground(Background.EMPTY);
        emailField.setFocusTraversable(false);

        userField.setPromptText("Usuário");
        userField.setBackground(Background.EMPTY);
        userField.setFocusTraversable(false);

        senhaField.setPromptText("Senha");
        senhaField.setBackground(Background.EMPTY);
        senhaField.setFocusTraversable(false);

        senhaField2.setPromptText("Confirmar senha");
        senhaField2.setBackground(Background.EMPTY);
        senhaField2.setFocusTraversable(false);

        GridPane.setConstraints(nomeField, 0, 1, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador1, 0, 1, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(apelidoField, 0, 2, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador2, 0, 2, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(telField, 0, 3, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador3, 0, 3, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(emailField, 0, 4, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador4, 0, 4, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(userField, 0, 5, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador5, 0, 5, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(senhaField, 0, 6, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador6, 0, 6, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(senhaField2, 0, 7, 1, 1, HPos.CENTER, VPos.TOP);
        GridPane.setConstraints(separador7, 0, 7, 1, 1, HPos.LEFT, VPos.BOTTOM);

        dados.getChildren().addAll(nomeField, separador1, apelidoField, separador2,
                telField, separador3, emailField, separador4, userField, separador5,
                senhaField, senhaField2, separador6, separador7);

        dadosPessoais.getChildren().addAll(btnDados, dPessoais, dados, msg);

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
        avenidaField.setPrefWidth(250);
        avenidaField.setBorder(Border.EMPTY);
        avenidaField.setPromptText("Avenida");
        String avenidas[] = {"Av. de Moçambique", "Av. de Angola", "Av. Samora Machel", "Av. Filipe Samuel Magaia", "Av. Emília Daússe"};
        TextFields.bindAutoCompletion(avenidaField, avenidas);

        Text tBairro = new Text("Bairro");
        bairroField = TextFields.createClearableTextField();
        bairroField.setPrefWidth(250);
        bairroField.setBorder(Border.EMPTY);
        bairroField.setPromptText("Bairro");
        String bairros[] = {"Inhagoia", "Jardim", "Benfica", "Alto-Maé", "Magoanine", "Laulane", "Choupal"};
        TextFields.bindAutoCompletion(bairroField, bairros);

        Text tQuarteirao = new Text("Quarteirão");
        quarteiraoField = TextFields.createClearableTextField();
        quarteiraoField.setPrefWidth(250);
        quarteiraoField.setBorder(Border.EMPTY);
        quarteiraoField.setPromptText("Quarteirão");

        Text tCasa = new Text("Nr. Casa");
        casaField = TextFields.createClearableTextField();
        casaField.setBorder(Border.EMPTY);
        casaField.setPromptText("Nr. Casa");

        String nrCasa[] = {"01", "11", "02", "22", "03", "33", "04", "44", "05", "55", "06", "66", "07", "77", "08", "88", "09", "99", "10"};
        TextFields.bindAutoCompletion(casaField, nrCasa);

        Text tRua = new Text("Rua");
        ruaField = TextFields.createClearableTextField();
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

        //EMPRESO
        GridPane layEmprego = new GridPane();
        layEmprego.setPadding(new Insets(40, 0, 0, 10));
        layEmprego.setVgap(10);
        layEmprego.setHgap(40);

        Text tNomeEmpresa = new Text("Nome da Empresa");
        empresaField = TextFields.createClearableTextField();
        empresaField.setPrefWidth(250);
        empresaField.setBorder(Border.EMPTY);
        empresaField.setPromptText("Nome da empresa");

        Text tEndereco = new Text("Endereço");
        enderecoField = TextFields.createClearableTextField();
        enderecoField.setPrefWidth(250);
        enderecoField.setBorder(Border.EMPTY);
        enderecoField.setPromptText("Endereço");

        Text tEmail = new Text("E-mail");
        empresaMailailField = TextFields.createClearableTextField();
        empresaMailailField.setPrefWidth(250);
        empresaMailailField.setBorder(Border.EMPTY);
        empresaMailailField.setPromptText("E-mail");

        Text tTelefone = new Text("Telefone");
        telefoneField = TextFields.createClearableTextField();
        telefoneField.setPrefWidth(250);
        telefoneField.setBorder(Border.EMPTY);
        telefoneField.setPromptText("Telefone");

        GridPane.setConstraints(tNomeEmpresa, 0, 0, 1, 1);
        GridPane.setConstraints(empresaField, 0, 1, 1, 1);

        GridPane.setConstraints(tEndereco, 1, 0, 1, 1);
        GridPane.setConstraints(enderecoField, 1, 1, 1, 1);

        GridPane.setConstraints(tEmail, 0, 2, 1, 1);
        GridPane.setConstraints(empresaMailailField, 0, 3, 1, 1);

        GridPane.setConstraints(tTelefone, 1, 2, 1, 1);
        GridPane.setConstraints(telefoneField, 1, 3, 1, 1);

        layEmprego.getChildren().addAll(tNomeEmpresa, empresaField,
                tEndereco, enderecoField, tEmail, empresaMailailField, tTelefone, telefoneField);

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
        botoes = new HBox();
        botoes.setPrefWidth(500);
        botoes.setAlignment(Pos.CENTER);
        botoes.setSpacing(50);

        cancelar = new Button("Cancelar");
        cancelar.setPrefWidth(100);
        cancelar.setId("Cadastro_cancelar");

        salvar = new Button("Salvar");
        salvar.setPrefWidth(100);
        salvar.setId("Cadastro_salvar");

        botoes.getChildren().addAll(cancelar, salvar);

        VBox direita = new VBox();
        direita.getChildren().addAll(dadosAdicionais, botoes);

        box.getChildren().addAll(dadosPessoais, direita);

        raiz.setTop(cima);
        raiz.setCenter(box);
    }

    public void initImage() {
        try {
            logo = new Image(getClass().getResourceAsStream("img/Logo.png"));
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        try {
            save = new Image(getClass().getResourceAsStream("img/save.png"));
            imageView = new ImageView(save);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

    }

    public void start(Stage janela) throws Exception {
        // janela.initStyle(StageStyle.UTILITY);
        initImage();
        initsComponents();
        initsLayout();

        nomeField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador1.setStyle("-fx-background-color: blue;");

            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador3.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
            separador7.setStyle("-fx-background-color: #FFFFFF");
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
            separador7.setStyle("-fx-background-color: #FFFFFF");
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
        telefoneField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("(###)-#########");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(telefoneField);
            tff.formatter();
        });

        telField.setOnKeyReleased(e -> {
            TextFieldFormatter tff = new TextFieldFormatter();
            tff.setMask("(###)-#########");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(telField);
            tff.formatter();
        });
        telField.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador3.setStyle("-fx-background-color: blue");

            separador1.setStyle("-fx-background-color: #FFFFFF");
            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
            separador7.setStyle("-fx-background-color: #FFFFFF");
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
            separador7.setStyle("-fx-background-color: #FFFFFF");
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
            separador7.setStyle("-fx-background-color: #FFFFFF");
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
            separador7.setStyle("-fx-background-color: #FFFFFF");
        });
        senhaField2.setOnMouseClicked(e -> {

            dPessoais.setOpacity(0.6);
            dPessoais.setStyle("-fx-background-color: #666666");

            separador7.setStyle("-fx-background-color: blue");

            separador1.setStyle("-fx-background-color: #FFFFFF");
            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador3.setStyle("-fx-background-color: #FFFFFF");
            separador4.setStyle("-fx-background-color: #FFFFFF");
            separador5.setStyle("-fx-background-color: #FFFFFF");
            separador6.setStyle("-fx-background-color: #FFFFFF");
        });

        cancelar.setOnAction(e -> {
            try {
                log = new Login();
                log.start(new Stage());
                janela.close();
            } catch (FileNotFoundException ex) {

            }

        });

        salvar.setOnAction(e -> {
            userDAO = new UsuarioDAO();
            morDAO = new MoradaDAO();
            boolean vazio = false;
            boolean senhaDiferente = false;
            boolean prontos = true;
            boolean user = false;

            if (("".equals(nomeField.getText()) || nomeField.getText() == null) || ("".equals(apelidoField.getText()) || apelidoField.getText() == null)
                    || ("".equals(telField.getText()) || telField.getText() == null) || ("".equals(emailField.getText()) || emailField.getText() == null)
                    || ("".equals(userField.getText()) || userField.getText() == null) || ("".equals(senhaField.getText()) || senhaField.getText() == null)
                    || ("".equals(senhaField2.getText()) || senhaField2.getText() == null)) {
                vazio = true;

            } else if (!senhaField.getText().equals(senhaField2.getText())) {
                senhaDiferente = true;
            }

            if (vazio == true) {
                //JOptionPane.showMessageDialog(null, "Preeencher todos os DADOS PESSOAIS", "Falha!", JOptionPane.WARNING_MESSAGE);
                notificacao(Pos.TOP_LEFT, graphic, "Preeencher todos os DADOS PESSOAIS");
                notificacaoBuilder.show();

                dPessoais.setStyle("-fx-background-color: #ff0000");
                dPessoais.setOpacity(1);
//
//                msg.setLayoutX(30);
//                msg.setText("Preencher todos os DADOS PESSOAIS!");
//                msg.setVisible(true);

            } else if (senhaDiferente == true) {
                //JOptionPane.showMessageDialog(null, "Senhas Diferentes", "Falha!", JOptionPane.WARNING_MESSAGE);
                notificacao(Pos.TOP_LEFT, graphic, "Senhas Diferentes");
                notificacaoBuilder.showWarning();

                dPessoais.setStyle("-fx-background-color: #ff0000");
                dPessoais.setOpacity(1);
//                msg.setLayoutX(70);
//                msg.setText("Senhas devem ser iguais!");
//                msg.setVisible(true);
            }

//            if ((vazio == false) && (senhaDiferente == false)) {
//                try {
//                    tel = Integer.parseInt(telField.getText());
//                    prontos = true;
//                } catch (NumberFormatException er) {
//
//                    //JOptionPane.showMessageDialog(null, "Número de telefone inválido", "Falha!", JOptionPane.WARNING_MESSAGE);
//                    notificacao(Pos.TOP_LEFT, graphic, "Número de telefone inválido");
//                    notificacaoBuilder.showWarning();
//
//                    dPessoais.setStyle("-fx-background-color: #ff0000");
//                    dPessoais.setOpacity(1);
//                    //msg.setVisible(true);
//                    prontos = false;
//                }
//
//            }
            try {
                user = userDAO.verificarUser(userField.getText());
            } catch (SQLException ex) {
                Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (user == true) {
                //JOptionPane.showMessageDialog(null, "Usuário existente, tente outro", "Falha!", JOptionPane.WARNING_MESSAGE);
                notificacao(Pos.TOP_LEFT, graphic, "Usuário existente, tente outro");
                notificacaoBuilder.show();

            } else if (prontos == true && vazio == false && senhaDiferente == false) {
                try {

                    usuario = new Usuario(nomeField.getText(), apelidoField.getText(),
                            userField.getText(), senhaField.getText(), tel, emailField.getText());

                    if (("".equals(avenidaField.getText()) && "".equals(bairroField.getText()) && "".equals(ruaField.getText())
                            && "".equals(quarteiraoField.getText())) && ("".equals(empresaField.getText()) && "".equals(enderecoField.getText())
                            && "".equals(empresaMailailField.getText()) && "".equals(telefoneField.getText()))) {

                        userDAO.salvarUsuario(usuario);

                    } else if ((!"".equals(avenidaField.getText()) || !"".equals(bairroField.getText()) || !"".equals(ruaField.getText())
                            || !"".equals(quarteiraoField.getText())) && ("".equals(empresaField.getText()) && "".equals(enderecoField.getText())
                            && "".equals(empresaMailailField.getText()) && "".equals(telefoneField.getText()))) {

                        morada = new Morada(avenidaField.getText(), bairroField.getText(),
                                ruaField.getText(), Integer.parseInt(quarteiraoField.getText()),
                                Integer.parseInt(casaField.getText()));

                        userDAO.salvarAmbos(usuario, morada);
                    } else {

                        morada = new Morada(avenidaField.getText(), bairroField.getText(),
                                ruaField.getText(), Integer.parseInt(quarteiraoField.getText()),
                                Integer.parseInt(casaField.getText()));

                        empresa = new Empresa(empresaField.getText(), enderecoField.getText(),
                                empresaMailailField.getText(), Integer.parseInt(telefoneField.getText()));

                        userDAO.salvarTodos(usuario, morada, empresa);
                    }

                    //JOptionPane.showMessageDialog(null, "Salvo com sucesso", "Tudo certo!", JOptionPane.INFORMATION_MESSAGE);
                    notificacao(Pos.CENTER, graphic, "Salvo com sucesso");
                    notificacaoBuilder.showInformation();

                    log = new Login();
                    try {
                        log.start(new Stage());
                        janela.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    nomeField.setText(null);
                    apelidoField.setText(null);
                    userField.setText(null);
                    senhaField.setText(null);
                    telField.setText(null);
                    emailField.setText(null);
                    senhaField2.setText(null);
                    avenidaField.setText("");
                    bairroField.setText("");
                    ruaField.setText("");
                    quarteiraoField.setText("");
                    casaField.setText("");
                    empresaField.setText("");
                    enderecoField.setText("");
                    empresaMailailField.setText("");
                    telefoneField.setText("");

                } catch (SQLException ex) {
                    Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            janela.getIcons().add(logotipo);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        janela.setTitle("GD => Cadastro");
        janela.setScene(cena);
        janela.setResizable(false);

        janela.show();
    }
}
