/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package posapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author andreidb
 */
public class dashboardController implements Initializable {
    
    @FXML
    private Button addProducts_addBtn;
    
    @FXML
    private TextField addProducts_brand;
    
    @FXML
    private Button addProducts_btn;
    
    @FXML
    private Button addProducts_deleteBtn;
    
    @FXML
    private AnchorPane addProducts_form;
    
    @FXML
    private ImageView addProducts_imageView;
    
    @FXML
    private Button addProducts_importBtn;
    
    @FXML
    private TextField addProducts_price;
    
    @FXML
    private TextField addProducts_productId;
    
    @FXML
    private TextField addProducts_productName;
    
    @FXML
    private ComboBox<?> addProducts_productType;
    
    @FXML
    private Button addProducts_resetBtn;
    
    @FXML
    private TextField addProducts_search;
    
    @FXML
    private ComboBox<?> addProducts_status;
    
    @FXML
    private TableView<ProductData> addProducts_tableView;
    
    @FXML
    private TableColumn<ProductData, String> addProducts_col_brand;
    
    @FXML
    private TableColumn<ProductData, Double> addProducts_col_price;
    
    @FXML
    private TableColumn<ProductData, Integer> addProducts_col_productId;
    
    @FXML
    private TableColumn<ProductData, String> addProducts_col_productName;
    
    @FXML
    private TableColumn<ProductData, String> addProducts_col_productType;
    
    @FXML
    private TableColumn<ProductData, String> addProducts_col_status;
    
    @FXML
    private Button addProducts_updateBtn;
    
    @FXML
    private Button close;
    
    @FXML
    private Label home_availableProducts;
    
    @FXML
    private Button home_btn;
    
    @FXML
    private AnchorPane home_form;
    
    @FXML
    private AreaChart<?, ?> home_incomeChart;
    
    @FXML
    private Label home_numberOrder;
    
    @FXML
    private BarChart<?, ?> home_orderChart;
    
    @FXML
    private Label home_totalIncome;
    
    @FXML
    private Button logout;
    
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private Button minimize;
    
    @FXML
    private TextField orders_amount;
    
    @FXML
    private Label orders_balance;
    
    @FXML
    private ComboBox<?> orders_brand;
    
    @FXML
    private Button orders_btn;
    
    @FXML
    private TableColumn<?, ?> orders_col_brand;
    
    @FXML
    private TableColumn<?, ?> orders_col_price;
    
    @FXML
    private TableColumn<?, ?> orders_col_productName;
    
    @FXML
    private TableColumn<?, ?> orders_col_quantity;
    
    @FXML
    private TableColumn<?, ?> orders_col_type;
    
    @FXML
    private AnchorPane orders_form;
    
    @FXML
    private Button orders_payBtn;
    
    @FXML
    private ComboBox<?> orders_productName;
    
    @FXML
    private ComboBox<?> orders_productType;
    
    @FXML
    private Spinner<?> orders_quantity;
    
    @FXML
    private Button orders_receiptBtn;
    
    @FXML
    private Button orders_resetBtn;
    
    @FXML
    private TableView<?> orders_tableView;
    
    @FXML
    private Label orders_total;
    
    @FXML
    private Label usernameLabel;
    
    @FXML
    private Button orders_addBtn;
    
    private double x = 0;
    private double y = 0;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private Image image;
    
    public void addProductsAdd() {
        String sql = "INSERT INTO product (product_id,type,brand,productName,price,status,image,date) VALUES ()";
    }

    public void addProductsImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));
        
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        
        if (file != null) {
            image = new Image(file.toURI().toString(), 146, 137, false, true);
            addProducts_imageView.setImage(image);
            GetData.path=file.getAbsolutePath();
        }
    }

    public ObservableList<ProductData> addProductsListData() {
        ObservableList<ProductData> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ProductData productData;
            while (result.next()) {
                productData = new ProductData(
                        result.getInt("product_id"),
                        result.getString("type"),
                        result.getString("brand"),
                        result.getString("productName"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date")
                );
                productList.add(productData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productList;
    }
    
    private ObservableList<ProductData> addProductsList;
    
    public void addProductShowListData() {
        addProductsList = addProductsListData();
        addProducts_col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProducts_col_productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProducts_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        addProducts_tableView.setItems(addProductsList);
    }
    
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addProducts_form.setVisible(false);
            orders_form.setVisible(false);
            
            home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#de6262   ,#ffb88c);");
            addProducts_btn.setStyle("-fx-background-color:transparent;");
            orders_btn.setStyle("-fx-background-color:transparent;");
        } else if (event.getSource() == addProducts_btn) {
            home_form.setVisible(false);
            addProducts_form.setVisible(true);
            orders_form.setVisible(false);
            
            home_btn.setStyle("-fx-background-color:transparent;");
            addProducts_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#de6262   ,#ffb88c);");
            orders_btn.setStyle("-fx-background-color:transparent;");
            
            addProductShowListData();
        } else if (event.getSource() == orders_btn) {
            home_form.setVisible(false);
            addProducts_form.setVisible(false);
            orders_form.setVisible(true);
            
            home_btn.setStyle("-fx-background-color:transparent;");
            addProducts_btn.setStyle("-fx-background-color:transparent;");
            orders_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#de6262   ,#ffb88c);");
        }
    }
    
    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);;
            
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                
                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });
                
                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });
                
                stage.initStyle(StageStyle.TRANSPARENT);
                
                stage.setScene(scene);
                stage.show();
                
            } else {
                return;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    public void close() {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addProductShowListData();
    }
    
}
