package com.borec.backend.stopwatch.gui;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.borec.backend.entity.Zprava;

import cz.borec.stopwatch.util.TimeUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientApplication extends Application {

	protected static final long INTERVAL = 5000;
	private static final String LABEL = "Zprávy administrátora:";
	ListView<Zprava> listView;
	protected static boolean error;
	VBox bottom;

	@Override
	public void start(Stage stage) throws Exception {
		// set title for the stage
		stage.setTitle("Zprávy 1.0 - klientská aplikace");

		listView = new ListView<>();
		Tooltip tooltip = new Tooltip();
		tooltip.setText(LABEL);
		listView.setTooltip(tooltip);
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					// EventTarget o = event.getTarget();
					Zprava zprava = listView.getSelectionModel().getSelectedItem();

					Dialog<String> dialog = new Dialog<String>();
					// Setting the title
					dialog.setTitle("Zpráva: " + zprava.getTitulek() );
					ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
					// Setting the content of the dialog
					dialog.setContentText(zprava.getZprava());
					// Adding buttons to the dialog pane
					dialog.getDialogPane().getButtonTypes().add(type);
					dialog.showAndWait();
				}
			}
		});

		VBox root = new VBox(0);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(5));

		bottom = new VBox(2);
		bottom.setAlignment(Pos.BOTTOM_LEFT);
		bottom.setPadding(new Insets(0));
		Label l = new Label(LABEL);
		l.setTextFill(Color.color(1, 0, 0));

		bottom.getChildren().add(l);
		bottom.getChildren().add(listView);
		bottom.setMaxHeight(80.0);
		bottom.setVisible(false);

		// Button container
		HBox centerBox = new HBox(15);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setPadding(new Insets(110));

		Label label = new Label("Klientská aplikace");
		label.setStyle("-fx-font-size: 48px; -fx-font-family: 'Courier New', monospace; -fx-text-fill: #2c3e50;");
		centerBox.getChildren().add(label);

		// buttonBox.getChildren().addAll(startButton, stopButton, resetButton);

		root.getChildren().addAll(centerBox, bottom);

		// create a stack pane
		StackPane r = new StackPane();

		// add button
		r.getChildren().add(root);

		// create a scene
		Scene sc = new Scene(r, 1000, 400);

		// set the scene
		stage.setScene(sc);

		/*
		 * Initialize a spring bean
		 * 
		 * if(stopWatch == null) { final ApplicationContext applicationContext = new
		 * AnnotationConfigApplicationContext(BackendApplication.class); stopWatch =
		 * (StopWatch) applicationContext.getBean("StopWatchBean");
		 * stopWatch.addStopWatchListener(this); }
		 */

		stage.show();

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						List<Zprava> data = DataLoader.loadDtata();
						Platform.runLater(() -> {
							listView.getItems().clear();
							if (!data.isEmpty()) {
								listView.getItems().addAll(data);
							}
							bottom.setVisible(!data.isEmpty());
						});
						Thread.sleep(INTERVAL);

					} catch (InterruptedException e) {
						error = true;
						e.printStackTrace();
					} catch (IOException e) {
						error = true;
						e.printStackTrace();
					}
				}
			}
		};

		Thread.startVirtualThread(runnable);

	}

	public static void main(String args[]) {
		launch(args);
	}
}
