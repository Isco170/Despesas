package controldespesas.view;

import controldespesas.dao.CategoriaDAO;
import controldespesas.dao.DespesaDAO;
import controldespesas.domain.Categoria;
import controldespesas.domain.Despesa;
import controldespesas.domain.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Estatistica extends Application {

    public Estatistica(Usuario user) {
        usuario = user;
    }

    DespesaDAO despDao;
    CategoriaDAO cateDao;
    Usuario usuario;
    Button voltar;

    TableView<Despesa> tabela;
    TableColumn<Despesa, String> despesaColuna;
    TableColumn<Despesa, Float> valorColuna;

    PieChart chartDespesas;
    BarChart<String, Number> despesasChart;
    ObservableList<PieChart.Data> ol = FXCollections.observableArrayList();
    ObservableList<String> categorias = FXCollections.observableArrayList();
    ObservableList<Float> valorTotal = FXCollections.observableArrayList();
    ObservableList<Despesa> despesas = FXCollections.observableArrayList();
    ObservableList<Despesa> desp = FXCollections.observableArrayList();

    Image volta;
    ImageView imageVoltar;

    float outrasF = 0;

    public void preencherOutra() {
        ObservableList<Despesa> lista = Principal.tabela.getItems();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCategoria().equals("Outra")) {
                outrasF += lista.get(i).getValor();
            }
        }
    }

    public void initBarchart() throws SQLException {
        chartDespesas = new PieChart();
        chartDespesas.setTitle("Análise das despesas");

        //listarCategorias();        
        System.out.println("Total despesa: ");
        for (int a = 0; a < categorias.size(); a++) {
            ol.add(a, new PieChart.Data(categorias.get(a), valorTotal.get(a)));
            desp.add(a, new Despesa(categorias.get(a), valorTotal.get(a)));

            System.out.println(categorias.get(a) + ": " + valorTotal.get(a));
        }

        preencherOutra();

        ol.add(new PieChart.Data("Outras", outrasF));

        desp.add(new Despesa("Outra", outrasF));

        System.out.println("Outras" + ": " + outrasF);
        chartDespesas.setData(ol);
    }

    public void listarCategorias() throws SQLException {
        System.out.println("Listar Categorias");
        cateDao = new CategoriaDAO();
        ObservableList<Categoria> lista = (ObservableList<Categoria>) cateDao.listarUsadas(usuario);
        for (int i = 0; i < lista.size(); i++) {
            categorias.add(i, lista.get(i).getNome());
            valorTotal.add(0F);
        }
        //listarDespesa();
    }

    public void listarDespesa() throws SQLException {
        System.out.println("Listar Despesas");
        despDao = new DespesaDAO();
        despesas = (ObservableList<Despesa>) despDao.listarDespesa(usuario);
        //somarValores();
    }

    public void somarValores() {
        System.out.println("Somar Valores");
        for (int a = 0; a < despesas.size(); a++) {
            for (int i = a + 1; i < despesas.size(); i++) {

                if (despesas.get(a).getCategoria().equals(despesas.get(i).getCategoria())) {
                    System.out.println("Dentro do if");
                    System.out.println("Somando: " + despesas.get(a).getNome() + " com " + despesas.get(i).getNome());
                    System.out.println("");
                    despesas.get(a).setValor(despesas.get(a).getValor() + despesas.get(i).getValor());
                    despesas.get(i).setValor(0);
                }
            }
        }
        for (int aa = 0; aa < despesas.size(); aa++) {
            System.out.println(despesas.get(aa).getNome() + ": " + despesas.get(aa).getValor());

        }
        // atribuirValores();
    }

    public void atribuirValores() {
        int cont = 0;
        while (cont < categorias.size()) {

            System.out.println("Atribuir valores");
            for (int a = 0; a < despesas.size(); a++) {
                //for (int i = a + 1; i < despesas.size(); i++) {
                System.out.println("");
                if ((despesas.get(a).getCategoria().equals(categorias.get(cont))) && (despesas.get(a).getValor() != 0)) {
                    //despesas.get(a).setValor(despesas.get(a).getValor() + despesas.get(i).getValor());
                    //despesas.get(i).setValor(0);
                    //System.out.println(a+ "a Categoria: "+ categoria);
                    valorTotal.add(cont, despesas.get(a).getValor());
                }
                //}
            }

            cont++;
        }
    }

    public void initChart() {
        despesasChart = new BarChart<String, Number>(new CategoryAxis(), new NumberAxis());
        despesasChart.setTitle("Ranking de Despesas");

        ObservableList<BarChart.Data<String, Number>> bc = FXCollections.observableArrayList();

        for (int a = 0; a < categorias.size(); a++) {
            bc.add(a, new XYChart.Data<String, Number>(categorias.get(a), valorTotal.get(a)));

        }
        XYChart.Series<String, Number> sr = new XYChart.Series<String, Number>();
        sr.setName("Valor total por Despesa");
        sr.getData().addAll(bc);
        despesasChart.getData().add(sr);

    }

    public void initImage() throws FileNotFoundException {
        try {
            volta = new Image(getClass().getResourceAsStream("img/back.png"));
            imageVoltar = new ImageView(volta);
            imageVoltar.setFitWidth(25);
            imageVoltar.setFitHeight(25);
        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar imagem");
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        listarCategorias();
        listarDespesa();
        somarValores();
        atribuirValores();
        initBarchart();
        initChart();
        initImage();

        voltar = new Button("", imageVoltar);
        voltar.setFocusTraversable(false);
        voltar.setId("btnVoltar");
        voltar.setOnAction(e -> {
            Principal.janela.show();
            primaryStage.close();
        });

        primaryStage.setOnCloseRequest(e -> {
            Principal.janela.show();
        });

        despesaColuna = new TableColumn<>("Despesa");
        despesaColuna.setMinWidth(200);
        despesaColuna.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        valorColuna = new TableColumn<>("Valor total gasto (Mt)");
        valorColuna.setMinWidth(200);
        valorColuna.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabela = new TableView();
        //tabela.setPrefWidth(600);
        tabela.setPlaceholder(new Text("Sem despesas"));
        tabela.setPadding(new Insets(10));
        tabela.setItems(desp);
        tabela.getColumns().addAll(despesaColuna, valorColuna);

        VBox root = new VBox();
        root.setSpacing(20);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(voltar, chartDespesas, tabela/*, despesasChart*/);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("controldespesas/view/css/estilo.css");

        try {
            Image logotipo = new Image(getClass().getResourceAsStream("img/GD-Logo.png"));
            primaryStage.getIcons().add(logotipo);
        } catch (NullPointerException ee) {
            System.out.println("Erro ao carregar imagem");
        }

        primaryStage.setTitle("GD => Estatísticas");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
