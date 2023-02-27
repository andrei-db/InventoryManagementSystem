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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    private ComboBox<String> addProducts_productType;

    @FXML
    private Button addProducts_resetBtn;

    @FXML
    private TextField addProducts_search;

    @FXML
    private ComboBox<String> addProducts_status;

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
    private TableColumn<CustomerData, String> orders_col_brand;

    @FXML
    private TableColumn<CustomerData, Double> orders_col_price;

    @FXML
    private TableColumn<CustomerData, String> orders_col_productName;

    @FXML
    private TableColumn<CustomerData, Integer> orders_col_quantity;

    @FXML
    private TableColumn<CustomerData, String> orders_col_type;

    @FXML
    private AnchorPane orders_form;

    @FXML
    private Button orders_payBtn;

    @FXML
    private ComboBox<?> orders_productName;

    @FXML
    private ComboBox<?> orders_productType;

    @FXML
    private Spinner<Integer> orders_quantity;

    @FXML
    private Button orders_receiptBtn;

    @FXML
    private Button orders_resetBtn;

    @FXML
    private TableView<CustomerData> orders_tableView;

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
        String sql = "INSERT INTO product (product_id,type,brand,productName,price,status,image,date)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        connect = Database.connectDb();

        try {

            Alert alert;

            if (addProducts_productId.getText().isEmpty()
                    || addProducts_productType.getSelectionModel().getSelectedItem() == null
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_price.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || GetData.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                String checkData = "SELECT product_id FROM product WHERE product_id='" + addProducts_productId.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + addProducts_productId.getText() + " already exist!");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addProducts_productId.getText());
                    prepare.setString(2, (String) addProducts_productType.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addProducts_brand.getText());
                    prepare.setString(4, addProducts_productName.getText());
                    prepare.setString(5, addProducts_price.getText());
                    prepare.setString(6, (String) addProducts_status.getSelectionModel().getSelectedItem());

                    String uri = GetData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    addProductsShowListData();
                    addProductsReset();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addProductsUpdate() {
        String uri = GetData.path;
        uri.replace("\\", "\\\\");

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "UPDATE product SET type = '" + addProducts_productType.getSelectionModel().getSelectedItem()
                + "',brand='" + addProducts_brand.getText()
                + "',productName='" + addProducts_productName.getText()
                + "',price='" + addProducts_price.getText()
                + "',status='" + addProducts_status.getSelectionModel().getSelectedItem()
                + "',image='" + uri
                + "',date='" + sqlDate + "' WHERE product_id='" + addProducts_productId.getText() + "'";
        connect = Database.connectDb();
        try {
            Alert alert;
            if (addProducts_productId.getText().isEmpty()
                    || addProducts_productType.getSelectionModel().getSelectedItem() == null
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_price.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || GetData.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are youy sure you want to UPDATE Product ID: " + addProducts_productId.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated");
                    alert.showAndWait();

                    addProductsShowListData();
                    addProductsReset();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addProductsDelete() {

        String sql = "DELETE FROM product WHERE product_id='" + addProducts_productId.getText() + "'";
        connect = Database.connectDb();
        try {
            Alert alert;
            if (addProducts_productId.getText().isEmpty()
                    || addProducts_productType.getSelectionModel().getSelectedItem() == null
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_price.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || GetData.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are youy sure you want to DELETE Product ID: " + addProducts_productId.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted");
                    alert.showAndWait();

                    addProductsShowListData();
                    addProductsReset();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addProductsReset() {
        addProducts_productId.setText("");
        addProducts_productType.getSelectionModel().clearSelection();
        addProducts_brand.setText("");
        addProducts_productName.setText("");
        addProducts_price.setText("");
        addProducts_status.getSelectionModel().clearSelection();
        addProducts_imageView.setImage(null);
        GetData.path = "";

    }

    public void addProductsImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 146, 137, false, true);
            addProducts_imageView.setImage(image);
            GetData.path = file.getAbsolutePath();
        }
    }

    private String[] listType = {"Snacks", "Drinks", "Dessert", "Gadgets", "Personal Products", "Others"};

    public void addProductsListType() {
        List<String> listT = new ArrayList<>();
        for (String data : listType) {
            listT.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listT);
        addProducts_productType.setItems(listData);
    }
    private String[] listStatus = {"Available", "Not Available"};

    public void addProductsListStatus() {
        List<String> listS = new ArrayList<>();
        for (String data : listStatus) {
            listS.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listS);
        addProducts_status.setItems(listData);
    }

    public void addProductsSearch() {
        FilteredList<ProductData> filter = new FilteredList<>(addProductsList, e -> true);
        addProducts_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateProductData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateProductData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getBrand().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getProductName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<ProductData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
        addProducts_tableView.setItems(sortList);
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

    public void addProductsShowListData() {
        addProductsList = addProductsListData();
        addProducts_col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProducts_col_productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProducts_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        addProducts_tableView.setItems(addProductsList);
    }

    public void addProductsSelect() {
        ProductData productData = addProducts_tableView.getSelectionModel().getSelectedItem();
        int index = addProducts_tableView.getSelectionModel().getSelectedIndex();

        if (index - 1 < -1) {
            return;
        }

        addProducts_productId.setText(String.valueOf(productData.getProductId()));

        addProducts_brand.setText(productData.getBrand());
        addProducts_productType.setValue(productData.getType());
        addProducts_productName.setText(productData.getProductName());
        addProducts_price.setText(String.valueOf(productData.getPrice()));
        addProducts_status.setValue(productData.getStatus());

        String uri = "file:" + productData.getImage();
        image = new Image(uri, 146, 137, false, true);
        addProducts_imageView.setImage(image);
        GetData.path = productData.getImage();
    }

    public ObservableList<CustomerData> ordersListData() {
        customerId();
        ObservableList<CustomerData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id='" + customerid + "'";
        connect = Database.connectDb();

        try {
            CustomerData customerData;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                customerData = new CustomerData(result.getInt("customer_id"),
                        result.getString("productType"),
                        result.getString("brand"),
                        result.getString("productName"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getDate("date"));
                listData.add(customerData);
            }

        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    private ObservableList<CustomerData> ordersList;

    public void ordersAdd() {
        //customerId();
        String sql = "INSERT INTO customer (customer_id,productType,brand,productName,quantity,price,date) "
                + " VALUES(?,?,?,?,?,?,?)";

        connect = Database.connectDb();

        try {

            String checkData = "SELECT * FROM product WHERE productName ='" + orders_productName.getSelectionModel().getSelectedItem() + "'";

            double priceData = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                priceData = result.getDouble("price");
            }

            double totalPData = (priceData * qty);

            Alert alert;
            if (orders_productType.getSelectionModel().getSelectedItem() == null
                    || orders_brand.getSelectionModel().getSelectedItem() == null
                    || orders_productName.getSelectionModel().getSelectedItem() == null
                    || totalPData == 0) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose product first");
                alert.showAndWait();
            } else {

                prepare = connect.prepareStatement(sql);

                prepare.setString(1, String.valueOf(customerid));
                prepare.setString(2, (String) orders_productType.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) orders_brand.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String) orders_productName.getSelectionModel().getSelectedItem());
                prepare.setString(5, (String) String.valueOf(qty));

                prepare.setString(6, String.valueOf(totalPData));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(7, String.valueOf(sqlDate));

                prepare.executeUpdate();
                ordersShowListData();
                ordersDisplayTotal();

            }

        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ordersPay() {
        String sql = "INSERT INTO customer_receipt (customer_id,total,amount,balance,date)"
                + " VALUES(?,?,?,?,?)";
        connect = Database.connectDb();
        Alert alert;
        try {

            if (totalP > 0|| orders_amount.getText().isEmpty() ||amountP==0) {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerid));
                    prepare.setString(2, String.valueOf(totalP));
                    prepare.setString(3, String.valueOf(amountP));
                    prepare.setString(4, String.valueOf(balanceP));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    
                   
                   
                    ordersShowListData();
                    
                    totalP=0;
                    amountP=0;
                    balanceP=0;
                    
                    orders_balance.setText("$0.0");
                    orders_amount.setText("");
                    orders_total.setText("$0.0");

                } else {
                    return;
                }

            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid");
                alert.showAndWait();
            }

        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private double amountP;
    private double balanceP;

    public void ordersAmount() {

        Alert alert;
        
        amountP=Double.parseDouble(orders_amount.getText());

        if (totalP > 0) {
            if (amountP >= totalP) {
                balanceP = amountP - totalP;

                orders_balance.setText("$"+String.valueOf(balanceP));
                
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid");
                alert.showAndWait();
                
                
                orders_amount.setText("");
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid");
            alert.showAndWait();
        }

    }

    private double totalP;

    public void ordersDisplayTotal() {

        String sql = "SELECT SUM(price) FROM customer WHERE customer_id ='" + customerid + "'";
        connect = Database.connectDb();
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                totalP = result.getDouble("SUM(price)");

            }

            orders_total.setText("$" + String.valueOf(totalP));

        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] orderListType = {"Snacks", "Drinks", "Dessert", "Gadgets", "Personal Products", "Others"};

    public void ordersListType() {
        List<String> listT = new ArrayList<>();
        for (String data : orderListType) {
            listT.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listT);
        orders_productType.setItems(listData);

        ordersListBrand();
    }

    public void ordersListBrand() {
        String sql = "SELECT brand FROM product WHERE type='" + orders_productType.getSelectionModel().getSelectedItem()
                + "' and status='Available'";
        connect = Database.connectDb();
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {

                listData.add(result.getString("brand"));
            }
            orders_brand.setItems(listData);

            ordersListProductName();

        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ordersListProductName() {
        String sql = "SELECT * FROM product WHERE brand='" + orders_brand.getSelectionModel().getSelectedItem() + "'";
        connect = Database.connectDb();
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {

                listData.add(result.getString("productName"));
            }
            orders_productName.setItems(listData);

        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private SpinnerValueFactory<Integer> spinner;

    public void ordersSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        orders_quantity.setValueFactory(spinner);
    }
    private int qty;

    public void ordersShowSpinnerValue() {

        qty = orders_quantity.getValue();

    }

    public void ordersShowListData() {
        ordersList = ordersListData();

        System.out.println(ordersList.size());
        orders_col_type.setCellValueFactory(new PropertyValueFactory<>("productType"));
        orders_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orders_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        orders_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orders_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        orders_tableView.setItems(ordersList);
    }
    private int customerid;

    public void customerId() {

        String customId = "SELECT * FROM customer ";
        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(customId);
            result = prepare.executeQuery();

            int checkId = -1;

            while (result.next()) {
                customerid = result.getInt("customer_id");
                System.out.print(customerid + " CI ");
            }

            String checkData = "SELECT * FROM customer_receipt";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            while (result.next()) {
                checkId = result.getInt("customer_id");
            }

          if (checkId == customerid) {
                customerid+=1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            addProductsShowListData();
            addProductsListType();
            addProductsListStatus();
            addProductsSearch();
        } else if (event.getSource() == orders_btn) {
            home_form.setVisible(false);
            addProducts_form.setVisible(false);
            orders_form.setVisible(true);

            home_btn.setStyle("-fx-background-color:transparent;");
            addProducts_btn.setStyle("-fx-background-color:transparent;");
            orders_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#de6262   ,#ffb88c);");

            ordersShowListData();
            ordersListType();
            ordersListBrand();
            ordersListProductName();
            ordersSpinner();
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
       
        
        addProductsShowListData();
        addProductsListType();
        addProductsListStatus();

        ordersShowListData();
        ordersListType();
        ordersListBrand();
        ordersListProductName();
        ordersSpinner();
        ordersDisplayTotal();
    }

}
