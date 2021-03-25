package controldespesas.view;

import controldespesas.dao.UsuarioDAO;
import controldespesas.domain.Usuario;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class Login extends Application {

    Cadastro cadast = new Cadastro();

    Stage janela;
    HBox layout;
    StackPane esquerda;
    VBox conteudoEsquerda;
    Label msg, /*login,*/ esqSenha, texto;
    Text login, userRequired, passRequired;
    Button cadastro, entrar;
    GridPane direita;
    TextField userField;
    PasswordField passField;
    Separator separador, separador2;
    Image log, cadastr, logo;
    ImageView imageView, imageCadastro;
    Scene cena;
    UsuarioDAO dao;
    Cadastro cadastroTela;

    Notifications notificacaoBuilder;
    Node graphic = null;

    private void notificacao(Pos pos, Node graphic, String mesag) {
        notificacaoBuilder = Notifications.create()
                .text("Gestor Financeiro")
                .text(mesag)
                .graphic(graphic)
                .hideAfter(Duration.seconds(3.5))
                .position(pos)
                .onAction(e -> {
                    System.out.println("Notificador");
                });
    }

    public void initsComponents() throws FileNotFoundException {
        layout = new HBox();
        layout.setSpacing(20);

        esquerda = new StackPane();
        esquerda.setId("esquerda");
        esquerda.setPrefSize(350, 300);
        esquerda.setPadding(new Insets(40, 10, 10, 20));

        conteudoEsquerda = new VBox();
        conteudoEsquerda.setSpacing(20);

        msg = new Label("Bem vindo ao GD");
        msg.setId("msg");

        texto = new Label();
        texto.setText("A ferramenta que você precisa\npara estar no controle dos seus gastos.\n\nAdicione novas despesas e,\nsuas respectivas categorias.");
        texto.setId("texto");

        cadastro = new Button("Cadastro");
        cadastro.setContentDisplay(ContentDisplay.RIGHT);
        //cadastro.setPrefWidth(100);
        cadastro.setFocusTraversable(false);
        cadastro.setId("btnCadastro");

        cadastroTela = new Cadastro();

        direita = new GridPane();
        direita.setId("direita");
        direita.setPrefSize(350, 300);
        direita.setPadding(new Insets(20, 10, 10, 80));
        direita.setVgap(10);

        login = new Text("Login");
        login.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        login.setFill(Color.WHITE);
        login.setUnderline(true);

        userField = TextFields.createClearableTextField();
        userField.setId("userField");
        userField.setPromptText("Usuário");
        userField.setBackground(Background.EMPTY);
        userField.setFocusTraversable(false);
        userRequired = new Text("Campo obrigatório                             *");
        userRequired.setFill(Color.WHITE);
        userRequired.setFont(Font.font("verdana", FontWeight.THIN, FontPosture.REGULAR, 10));
        userRequired.setVisible(false);

        passField = TextFields.createClearablePasswordField();
        passField.setId("passField");
        passField.setPromptText("Password");
        passField.setBackground(Background.EMPTY);
        passField.setFocusTraversable(false);
        passRequired = new Text("Campo obrigatório                             *");
        passRequired.setFill(Color.WHITE);
        passRequired.setFont(Font.font("verdana", FontWeight.THIN, FontPosture.REGULAR, 10));
        passRequired.setVisible(false);

        separador = new Separator();
        separador.setId("separador");

        separador2 = new Separator();
        separador2.setId("separador");

        esqSenha = new Label("Esqueceu a senha?");
        esqSenha.setId("esqSenha");
        esqSenha.setUnderline(true);

        entrar = new Button(" Entrar ");
        entrar.setContentDisplay(ContentDisplay.RIGHT);
        entrar.setFocusTraversable(false);
        //entrar.setPrefWidth(80);
        entrar.setId("btnLogin");

        dao = new UsuarioDAO();

        cena = new Scene(layout, 700, 300);
        cena.getStylesheets().add("controldespesas/view/css/estilo.css");
    }

    public void initsLayout() {
        conteudoEsquerda.getChildren().addAll(msg, texto, cadastro);
        esquerda.getChildren().add(conteudoEsquerda);
        GridPane.setConstraints(login, 0, 0, 1, 1, HPos.CENTER, VPos.TOP);

        GridPane.setConstraints(userField, 0, 1, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador, 0, 1, 1, 1, HPos.LEFT, VPos.BOTTOM);
        GridPane.setConstraints(userRequired, 0, 1, 1, 1, HPos.LEFT, VPos.BOTTOM);

        GridPane.setConstraints(userRequired, 0, 2, 1, 1, HPos.LEFT, VPos.TOP);

        GridPane.setConstraints(passField, 0, 3, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(separador2, 0, 3, 1, 1, HPos.LEFT, VPos.BOTTOM);
        GridPane.setConstraints(entrar, 0, 6, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(esqSenha, 0, 7, 1, 1, HPos.CENTER, VPos.BOTTOM);

        GridPane.setConstraints(passRequired, 0, 4, 1, 1, HPos.LEFT, VPos.TOP);

        direita.getChildren().addAll(login, separador, userField, userRequired, separador2, passField, passRequired, entrar, esqSenha);
        layout.getChildren().addAll(esquerda, direita);
    }

    public void initImage() {
        try {
            logo = new Image(getClass().getResourceAsStream("img/Logo.png"));
            graphic = new ImageView(logo);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        try {
            log = new Image(getClass().getResourceAsStream("img/login.png"));
            imageView = new ImageView(log);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        try {
            cadastr = new Image(getClass().getResourceAsStream("img/registro.png"));
            imageCadastro = new ImageView(cadastr);
            imageCadastro.setFitWidth(25);
            imageCadastro.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        janela = primaryStage;
        initImage();
        initsComponents();
        initsLayout();

        cadastro.setOnAction(e -> {
            try {
                cadast.start(new Stage());
                janela.close();
            } catch (Exception ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        userField.setOnMouseClicked(e -> {
            separador2.setStyle("-fx-background-color: #FFFFFF");
            separador.setStyle("-fx-background-color: blue");
            userRequired.setVisible(true);
            passRequired.setVisible(false);
        });

        passField.setOnMouseClicked(e -> {
            separador.setStyle("-fx-background-color: #FFFFFF");
            separador2.setStyle("-fx-background-color: blue");
            passRequired.setVisible(true);
            userRequired.setVisible(false);
        });
        esqSenha.setOnMouseClicked(e -> {
            System.out.println("Esqueci a minha senha");
        });

        entrar.setOnAction(e -> {
            try {
                Usuario usuario = dao.login(userField.getText(), passField.getText());
                if (usuario != null) {
                    Principal principal = new Principal(usuario);
                    try {
                        principal.start(new Stage());
                        janela.close();
                    } catch (Exception ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {

                    //JOptionPane.showMessageDialog(null, "Login incorreto");
                    notificacao(Pos.CENTER, graphic, "Login incorreto");
                    notificacaoBuilder.show();
                }

            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, "Verifica conexão com base de dados");
                notificacao(Pos.CENTER, graphic, "Não foi possível fazer login");
                notificacaoBuilder.show();
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            janela.getIcons().add(logotipo);
        } catch (NullPointerException ee) {
            System.out.println("Erro carregando imagem");
        }

        janela.setTitle("GD => Login!");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
