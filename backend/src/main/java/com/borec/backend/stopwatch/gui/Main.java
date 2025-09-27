package com.borec.backend.stopwatch.gui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.borec.backend.BackendApplication;
import com.borec.backend.entity.Action;
import com.borec.backend.entity.ActionType;
import com.borec.backend.service.ActionService;
import com.borec.backend.stopwatch.StopWatch;
import com.borec.backend.stopwatch.StopWatchListener;

import cz.borec.stopwatch.util.TimeUtils;
import jakarta.annotation.PostConstruct;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application implements StopWatchListener {

	private StopWatch stopWatch ; //= StopWatch.createInstance();

	// @Autowired StopWatch stopWatch;

	Label stopWatchValue;
	Button startButton;
	Button stopButton;
	Button resetButton;


	@org.springframework.beans.factory.annotation.Autowired
	public void setStopWatch(StopWatch stopWatch) {
		this.stopWatch = stopWatch;
	}
	


	public void start(Stage s) {

		// set title for the stage
		s.setTitle("Stopwatch 1.0");

		stopWatchValue = new Label("00:00:00.000");
		stopWatchValue
				.setStyle("-fx-font-size: 48px; -fx-font-family: 'Courier New', monospace; -fx-text-fill: #2c3e50;");
		// stopWatchValue.textProperty().bindBidirectional(result);


		// create a button
		startButton = new Button("Start");
		stopButton = new Button("Stop");
		resetButton = new Button("Reset");

		startButton.setOnAction(e -> startStopwatch());
		stopButton.setOnAction(e -> stopStopwatch());
		resetButton.setOnAction(e -> resetStopwatch());

		setStarted(false);

		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));

		// Button container
		HBox buttonBox = new HBox(15);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(startButton, stopButton, resetButton);

		// Keyboard shortcuts info
		Label shortcutsLabel = new Label("Keyboard shortcuts: Space = Start/Stop, S = Stop, R = Reset");
		shortcutsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

		root.getChildren().addAll(stopWatchValue, buttonBox, shortcutsLabel);

		// create a stack pane
		StackPane r = new StackPane();

		// add button
		r.getChildren().add(root);

		// create a scene
		Scene sc = new Scene(r, 560, 200);

		// set the scene
		s.setScene(sc);

		if(stopWatch == null) {
		    final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BackendApplication.class);
			stopWatch = (StopWatch) applicationContext.getBean("StopWatchBean");
			stopWatch.addStopWatchListener(this);
		}

		s.show();
		
	}

	private void resetStopwatch() {
		stopWatch.reset();
	}

	private void stopStopwatch() {
		stopWatch.stop();
		setStarted(false);
	}

	private void startStopwatch() {
		stopWatch.start();
		setStarted(true);
	}

	public static void main(String args[]) { 
		launch(args);
	}

	@PostConstruct
	public void init2() {
		launch(new String[0]);
		
	}

	private void setStarted(boolean b) {
		startButton.setDisable(b);
		stopButton.setDisable(!b);
		resetButton.setDisable(b);
	}

	@Override
	public void onTimeChangedIn100MillisecondsInterval(long time) {
		Platform.runLater(() -> {
			// result.set(TimeUtils.displayTime(time));
			stopWatchValue.setText(TimeUtils.displayTime(time));
		});
	}

}
