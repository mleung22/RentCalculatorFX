package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();

			Scene scene = new Scene(root, 200, 200);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.show();

			root.setCenter(addRootGridPane());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HBox addHBoxRootTitle() {

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);

		Text title = new Text("Rent Calculator");
		title.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 20));

		hbox.getChildren().add(title);

		return hbox;

	}

	public static VBox addVBoxRootButtons() {

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5);

		Button calculateButton = new Button("Calculate New Payment");
		Button viewButton = new Button("View Past Payments");

		vbox.getChildren().addAll(calculateButton, viewButton);

		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Stage calculateButtonStage = new Stage();
				BorderPane cbBorderPane = new BorderPane();

				Scene scene = new Scene(cbBorderPane, 400, 230);
				calculateButtonStage.setScene(scene);

				calculateButtonStage.show();

				try {
					cbBorderPane.setCenter(calculateButtonUI());
				} catch (IOException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		viewButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Stage viewButtonStage = new Stage();
				BorderPane vbBorderPane = new BorderPane();

				Scene scene = new Scene(vbBorderPane, 450, 350);
				viewButtonStage.setScene(scene);

				viewButtonStage.show();

				vbBorderPane.setTop(viewButtonTitle());
				try {
					vbBorderPane.setCenter(viewButtonUI());
				} catch (IOException | SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		return vbox;
	}

	public static GridPane addRootGridPane() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.add(addHBoxRootTitle(), 0, 0);
		grid.add(addVBoxRootButtons(), 0, 1);

		return grid;

	}

	public static HBox addHBoxCalculateButtonTitle() {

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);

		Text title = new Text("Calculate New Payment");
		title.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 20));

		hbox.getChildren().add(title);

		return hbox;
	}

	public static VBox calculateButtonLabelVBox() {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER_RIGHT);
		vbox.setSpacing(10);

		Label date = new Label("Date:");
		Label rent = new Label("Rent:");
		Label water = new Label("Water:");
		Label electricity = new Label("Electricity:");
		Label internet = new Label("Internet:");

		vbox.getChildren().addAll(date, rent, water, electricity, internet);

		return vbox;
	}

	public static VBox calculateButtonNameLabels() {
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		Label mike = new Label("Mike");
		Label patti = new Label("Patti");

		vbox.getChildren().addAll(mike, patti);

		return vbox;
	}

	public static GridPane calculateButtonUI() throws FileNotFoundException,
			IOException, SQLException {

		Database db = new Database();

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(8);
		grid.setVgap(10);

		HBox hboxButtons = new HBox();
		hboxButtons.setAlignment(Pos.CENTER);
		hboxButtons.setSpacing(30);

		VBox vboxTextField = new VBox();
		vboxTextField.setAlignment(Pos.CENTER_LEFT);

		VBox vboxPaymentResultLabels = new VBox();
		vboxPaymentResultLabels.setSpacing(5);

		Label mikeResult = new Label("$0.00");
		Label pattiResult = new Label("$0.00");

		TextField date = new TextField();
		TextField rent = new TextField();
		TextField water = new TextField();
		TextField electricity = new TextField();
		TextField internet = new TextField();

		Button calculate = new Button("Calculate");
		Button exit = new Button("Exit");

		calculate.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				double convertedRent = Double.parseDouble(rent.getText());
				double convertedWater = Double.parseDouble(water.getText());
				double convertedElectricity = Double.parseDouble(electricity
						.getText());
				double convertedInternet = Double.parseDouble(internet
						.getText());

				MonthlyPayments mp = new MonthlyPayments(date.getText(),
						convertedRent, convertedWater, convertedElectricity,
						convertedInternet);

				mikeResult.setText("$" + mp.calculateForM());
				pattiResult.setText("$" + mp.calculateForP());

				date.setText("");
				rent.setText("");
				water.setText("");
				electricity.setText("");
				internet.setText("");

				try {
					db.insertInformation(mp);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				Node source = (Node) e.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			}
		});

		hboxButtons.getChildren().addAll(calculate, exit);
		vboxTextField.getChildren().addAll(date, rent, water, electricity,
				internet);
		vboxPaymentResultLabels.getChildren().addAll(mikeResult, pattiResult);

		grid.add(addHBoxCalculateButtonTitle(), 1, 1);
		grid.add(calculateButtonLabelVBox(), 0, 2);
		grid.add(vboxTextField, 1, 2);
		grid.add(hboxButtons, 1, 3);
		grid.add(calculateButtonNameLabels(), 3, 2);
		grid.add(vboxPaymentResultLabels, 4, 2);
		return grid;

	}

	public static HBox viewButtonTitle() {

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		Text title = new Text("View Past Payments");
		title.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 20));

		hbox.getChildren().add(title);

		return hbox;
	}

	public static VBox viewListViewLayout(ListView lv) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.getChildren().add(lv);

		return vbox;
	}

	public static VBox viewButtonLabelVBox() {

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER_RIGHT);

		Label rent = new Label("Rent:");
		Label water = new Label("Water:");
		Label electricity = new Label("Electricity:");
		Label internet = new Label("Internet:");

		vbox.getChildren().addAll(rent, water, electricity, internet);
		return vbox;
	}

	public static VBox viewButtonNameLabels() {

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.BOTTOM_CENTER);

		Label mike = new Label("Mike:");
		Label patti = new Label("Patti:");

		vbox.getChildren().addAll(mike, patti);

		return vbox;
	}

	public static GridPane viewButtonUI() throws FileNotFoundException,
			IOException, SQLException {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);

		Database db = new Database();
		ListView list = new ListView<>();
		ObservableList paymentDates;

		VBox vbox = new VBox();
		VBox vboxNames = new VBox();
		HBox hbox = new HBox();

		vbox.setAlignment(Pos.CENTER_LEFT);
		vboxNames.setAlignment(Pos.BOTTOM_CENTER);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(10);

		Label rent = new Label("$0.00");
		Label water = new Label("$0.00");
		Label electricity = new Label("$0.00");
		Label internet = new Label("$0.00");

		Label mike = new Label("$0.00");
		Label patti = new Label("$0.00");

		Button delete = new Button("Delete");
		Button exit = new Button("Exit");

		try {
			paymentDates = FXCollections.observableList(db
					.getDateFromDatabase());
			list.setItems(paymentDates);

			delete.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					try {
						db.deleteDataFromDatabaseByDate(list
								.getSelectionModel().getSelectedItem());
						paymentDates.remove(list.getSelectionModel()
								.getSelectedItem());
						rent.setText("$0.00");
						water.setText("$0.00");
						electricity.setText("$0.00");
						internet.setText("$0.00");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});

			list.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {

					try {
						rent.setText("$"
								+ db.getPaymentsFromDatabaseWithDate(
										list.getSelectionModel()
												.getSelectedItem()).getRent());
						water.setText("$"
								+ db.getPaymentsFromDatabaseWithDate(
										list.getSelectionModel()
												.getSelectedItem()).getWater());
						electricity.setText("$"
								+ db.getPaymentsFromDatabaseWithDate(
										list.getSelectionModel()
												.getSelectedItem())
										.getElectricity());
						internet.setText("$"
								+ db.getPaymentsFromDatabaseWithDate(
										list.getSelectionModel()
												.getSelectedItem())
										.getInternet());
						mike.setText("$"
								+ db.getPaymentsFromDatabaseWithDate(
										list.getSelectionModel()
												.getSelectedItem())
										.calculateForM());
						patti.setText("$"
								+ db.getPaymentsFromDatabaseWithDate(
										list.getSelectionModel()
												.getSelectedItem())
										.calculateForP());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			exit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					Node source = (Node) e.getSource();
					Stage stage = (Stage) source.getScene().getWindow();
					stage.close();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		hbox.getChildren().addAll(delete, exit);
		vbox.getChildren().addAll(rent, water, electricity, internet);
		vboxNames.getChildren().addAll(mike, patti);

		grid.add(viewButtonNameLabels(), 1, 0);
		grid.add(vboxNames, 2, 0);
		grid.add(viewListViewLayout(list), 0, 0);
		grid.add(vbox, 2, 0);
		grid.add(viewButtonLabelVBox(), 1, 0);
		grid.add(hbox, 0, 1);

		return grid;
	}
}
