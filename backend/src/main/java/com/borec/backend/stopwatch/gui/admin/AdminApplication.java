package com.borec.backend.stopwatch.gui.admin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;

import com.borec.backend.entity.Zprava;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminApplication extends Application  {

	ListView<Zprava> listView ;

	@Override
	public void start(Stage stage) throws Exception {
		// set title for the stage
		stage.setTitle("Zprávy 1.0 - administrátorská aplikace");

		listView = new ListView<>();
		Tooltip tooltip  = new Tooltip();
		tooltip.setText("Seznam zpráv");
		listView.setTooltip(tooltip);
		
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));

		VBox top = new VBox(20);
		top.setAlignment(Pos.CENTER);
		top.setPadding(new Insets(0));
		top.getChildren().add(new Label("Seznam zpráv"));
		top.getChildren().add(listView);

		// Button container
		HBox buttonBox = new HBox(15);
		buttonBox.setAlignment(Pos.CENTER);
		//buttonBox.getChildren().addAll(startButton, stopButton, resetButton);

		// Keyboard shortcuts info
		Label shortcutsLabel = new Label("Keyboard shortcuts: Space = Start/Stop, S = Stop, R = Reset");
		shortcutsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

		root.getChildren().addAll(top, buttonBox, shortcutsLabel);

		// create a stack pane
		StackPane r = new StackPane();

		// add button
		r.getChildren().add(root);

		// create a scene
		Scene sc = new Scene(r, 1000, 400);

		// set the scene
		stage.setScene(sc);

		stage.show();
		
	}
	
	public static void main(String args[]) { 
		launch(args);
	}


}
