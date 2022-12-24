package com.example.supplychainsandeep17dec;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SupplyChain extends Application {

    public static final int width = 700, height = 600, headerBar = 50, footerBar = 20;

    Pane bodyPane = new Pane();
    Login login = new Login();
    ProductDetails productDetails = new ProductDetails();

    Button globalLoginButton;
    Label customerEmailLabel = null;

    String customerEmail = null;

    private  GridPane headerBar(){



        TextField searchText = new TextField();
        Button searchButton = new Button("search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName = searchText.getText();

                // clear body and put this new page in the body
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(productName));
            }
        });
        globalLoginButton = new Button("Log in");
        globalLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear(); // after clicking on login button product information page will disappear and
                bodyPane.getChildren().add(loginPage()); // login page will come to enter details..
                globalLoginButton.setDisable(true); // disabling this login button after login..

            }
        });

        customerEmailLabel = new Label("Welcome User");


        GridPane gridPane = new GridPane();
//        gridPane.setStyle("-fx-background-color: #C0C0C0");
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
//        gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(searchText, 0, 0);
        gridPane.add(searchButton, 1, 0);
        gridPane.add(globalLoginButton, 2, 0);
        gridPane.add(customerEmailLabel, 3, 0);

        return gridPane;
    }

    private GridPane loginPage() {
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label messageLabel = new Label("Enter Details");

        TextField emailTextField = new TextField();
        PasswordField passwordField = new PasswordField();

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
//        gridPane.setStyle("-fx-background-color: #C0C0C0");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordField.getText();

//                messageLabel.setText("Successful");
                if(login.customerLogin(email, password)){
                    messageLabel.setText("Successful");
                    customerEmail = email;
                    globalLoginButton.setDisable(true);
                    customerEmailLabel.setText("Welcome : " + customerEmail);
                    bodyPane.getChildren().clear(); // after successful login the login window will be disappeared and
                    bodyPane.getChildren().add(productDetails.getAllProducts()); // product window will appear
                }
                else{
                    messageLabel.setText("Login Failed");
                }

            }
        });

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailTextField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(messageLabel, 1, 2);

        return gridPane;
    }


    private  GridPane footerBar(){
        Button addToCartButton = new Button("Add to Cart");
        Button buyNowButton = new Button("Buy Now");

        Label messageLabel = new Label();

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product selectedProduct = productDetails.getSeletectedProduct();
                if(Order.placeOrder(customerEmail, selectedProduct)){
                    messageLabel.setText("Ordered");
                }
                else{
                    messageLabel.setText("Order Failed");
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), footerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(50);
        gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setTranslateY(footerBar+height+50);

        gridPane.add(addToCartButton, 0, 0);
        gridPane.add(buyNowButton, 1, 0);
        gridPane.add(messageLabel, 2, 0);

        return gridPane;
    }

    private Pane createContent(){

        InputStream stream = null;
        try{
            stream = new FileInputStream("C:\\Users\\Sandeep\\IdeaProjects\\supplyChainSandeep17Dec\\src\\main\\Amazon_logo.svg.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = new Image(stream);
        ImageView logo = new ImageView();
        logo.setImage(image);

        logo.setX(10);
        logo.setY(1);
        logo.setFitWidth(100);
        logo.setFitHeight(39);

        Pane root = new Pane();

        root.setPrefSize(width, height+3*footerBar+50);

        bodyPane.setMinSize(width, height);
        bodyPane.setTranslateY(headerBar);

        bodyPane.getChildren().addAll(productDetails.getAllProducts());

        root.getChildren().addAll(headerBar(), bodyPane, footerBar(), logo);

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());

        Image image = new Image("C:\\Users\\Sandeep\\IdeaProjects\\supplyChainSandeep17Dec\\src\\main\\java\\com\\example\\supplychainsandeep17dec\\amazon-icon-2.png");
        stage.getIcons().add(image);

        stage.setTitle("Amazon Clone!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}