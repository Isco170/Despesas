package controldespesas.view;

import controldespesas.dao.CategoriaDAO;
import controldespesas.domain.Categoria;
import controldespesas.domain.Usuario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class AdicionarCategoria extends Application {

    public AdicionarCategoria(Usuario user) {
        usuario = user;
    }

    CategoriaDAO catDao;
    Categoria categoria;
    Usuario usuario;

    Stage janela;
    Scene cena;
    VBox layout;
    HBox botoes;
    TextField campo;
    Button btnSalvar, btnCancelar;

    Notifications notificacaoBuilder;
    Node graphic = null;
    
    private void notificacao(Pos pos, Node graphic, String mesag){
        notificacaoBuilder = Notifications.create()
                .text("Gestor Financeiro")
                .text(mesag)
                .graphic(graphic)
                .hideAfter(Duration.seconds(3))
                .position(pos)
                .onAction(e ->{
                    System.out.println("Notificador");
                });
        
    }
    public void initsComponents() {
        layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));

        campo = TextFields.createClearableTextField();

        botoes = new HBox();
        botoes.setSpacing(10);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        btnSalvar = new Button("Salvar");
        btnSalvar.setId("Cadastro_salvar");

        btnCancelar = new Button("Cancelar");
        btnCancelar.setId("Cadastro_cancelar");

        cena = new Scene(layout, 300, 100);
        cena.getStylesheets().add("controldespesas/view/css/estilo.css");

    }

    public void initsLayout() {

        botoes.getChildren().addAll(btnSalvar, btnCancelar);
        layout.getChildren().addAll(campo, botoes);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initsComponents();
        initsLayout();

        btnSalvar.setOnAction(e -> {
            if ((campo.getText().equals("")) || (campo.getText() == null)) {
                System.out.println("Vazio");
                //JOptionPane.showMessageDialog(null, "Nome da categoria não pode estar vazio", "Aviso", JOptionPane.WARNING_MESSAGE);
                
                notificacao(Pos.TOP_LEFT, graphic,"Nome da categoria não pode estar vazio");
                notificacaoBuilder.showWarning();
                
            } else {
                categoria = new Categoria();
                catDao = new CategoriaDAO();
                boolean tem = false;
                try {
                    tem = catDao.verificarCategoria(campo.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(AdicionarCategoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (tem == false) {
                    try {

                        categoria.setNome(campo.getText());
                        categoria.setUser(usuario);

                        catDao.salvarCategoria(categoria);
                        janela.setTitle("Nova categoria salva");
                        campo.setText("");

                    } catch (SQLException ex) {
                        Logger.getLogger(AdicionarCategoria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    //JOptionPane.showMessageDialog(null, "Categoria já existente", "Anteção", JOptionPane.INFORMATION_MESSAGE);
                    notificacao(Pos.TOP_LEFT, graphic,"Categoria já existente");
                notificacaoBuilder.showWarning();
                }

            }
        });

        btnCancelar.setOnAction(e -> {
            janela.close();
        });
        try{
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
        janela.getIcons().add(logotipo);
        }catch(NullPointerException ee){
            System.out.println("Erro carregando imagem");
        }
        
        janela.setTitle("GD => Nova Categoria!");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();
    }

}
