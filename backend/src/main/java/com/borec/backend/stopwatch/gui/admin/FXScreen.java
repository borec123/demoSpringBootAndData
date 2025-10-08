package com.borec.backend.stopwatch.gui.admin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.borec.backend.entity.Zprava;
import com.borec.backend.pojo.ZpravyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXScreen extends Application {

	private static final String OK = "Ok                                                        ";
	private TableView<MessageFx> table;
	private TextField idField;
	private DatePicker validFromPicker;
	private DatePicker validToPicker;
	private CheckBox activeCheckBox;
	private TextField titleField;
	private TextArea messageTextArea;
	private List<Control> formControls;

	// Buttons
	private Button createBtn = new Button("CREATE");
	private Button editBtn = new Button("EDIT");
	private Button deleteBtn = new Button("DELETE");
	private Button okBtn = new Button("SAVE");
	private Button cancelBtn = new Button("CANCEL");
	private Button loadlBtn = new Button("LOAD");

	private ObservableList<MessageFx> data;
	private Label statusLabel = new Label(OK);
	private DataLoader dataLoader = new DataLoader();
	private MessageFx savedMessageFx;

	class DataLoader {

		private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
				.connectTimeout(Duration.ofSeconds(10)).build();
		static final String HOST_ = "localhost";
		static final String PORT_ = "8194";
		private static Throwable throwable;

		void sendLoadDataRequestAsync() {

			throwable = null;

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/listzprava")).timeout(Duration.ofSeconds(10))
					.header("Content-Type", "application/json").GET().build();
			httpClient.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body)
					.thenAccept(this::processResponse).exceptionally(e -> handleError(e));
		}

		void processResponse(String response) {
			processResponseFX(response);
		}

		void processResponseSave(String response) {
			processResponseSaveFX(response);
		}

		void processResponseDelete(String response) {
			processResponseDeleteFX(response);
		}

		private Void handleError(Throwable e) {
			throwable = e;
			error(e);
			return null;
		}

		public void save(Zprava zprava) throws JsonProcessingException {
			throwable = null;

			ObjectMapper om = new ObjectMapper();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/saveZprava")).timeout(Duration.ofSeconds(10))
					.header("Content-Type", "application/json")
					.PUT(BodyPublishers.ofString(om.writeValueAsString(zprava))).build();
			httpClient.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body)
					.thenAccept(this::processResponseSave).exceptionally(e -> handleError(e));
		}

		public void delete(Zprava zprava) throws JsonProcessingException {
			throwable = null;

			ObjectMapper om = new ObjectMapper();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/deleteZprava")).timeout(Duration.ofSeconds(10))
					.header("Content-Type", "application/json")
					.PUT(BodyPublishers.ofString(om.writeValueAsString(zprava))).build();
			httpClient.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body)
					.thenAccept(this::processResponseDelete).exceptionally(e -> handleError(e));
		}
	}

	void processResponseFX(String response) {
		// --- zpracovat response ...

		String result = "Ok, loaded.";

		ObjectMapper om = new ObjectMapper();
		try {
			ZpravyResponse zpravyResponse = om.readValue(response, ZpravyResponse.class);
			List<Zprava> list = zpravyResponse.getList();
			List<MessageFx> fxList = list.stream().map(MessageConverter::toFx).collect(Collectors.toList());
			data.clear();
			data.addAll(fxList);
			table.setItems(data);

		} catch (JsonMappingException e) {
			result = "Json error: " + e;
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			result = "Json error: " + e;
			e.printStackTrace();
		}

		final String label = result;

		Platform.runLater(() -> {
			statusLabel.setText(label);
		});
	}

	public void processResponseSaveFX(String response) {
		String result = "Ok, saved.";

		ObjectMapper om = new ObjectMapper();
		try {
			Zprava zprava = om.readValue(response, Zprava.class);

			LongProperty idProp = savedMessageFx.idProperty();
			long id = idProp.get();

			if (id == MessageFx.NEWLY_CREATED_ID) {
				MessageFx newMessageFx = MessageConverter.toFx(zprava);
				data.add(newMessageFx);
			}
			table.refresh();
			setEditable(false); 

		} catch (JsonMappingException e) {
			result = "Json error: " + e;
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			result = "Json error: " + e;
			e.printStackTrace();
		}

		final String label = result;

		Platform.runLater(() -> {
			statusLabel.setText(label);
		});
	}

	public void processResponseDeleteFX(String response) {
		String result = "Ok, deleted.";

		MessageFx selected = table.getSelectionModel().getSelectedItem();
		if (selected != null) {
			data.remove(selected);
		}
		
		final String label = result;

		Platform.runLater(() -> {
			statusLabel.setText(label);
		});
	}

	public void error(Throwable e) {
		Platform.runLater(() -> {
			statusLabel.setText("Network error: " + e);
		});
	}

	@Override
	public void start(Stage primaryStage) {
		table = new TableView<>();

		TableColumn<MessageFx, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<MessageFx, LocalDate> validFromCol = new TableColumn<>("Valid From");
		validFromCol.setCellValueFactory(new PropertyValueFactory<>("validFrom"));

		TableColumn<MessageFx, LocalDate> validToCol = new TableColumn<>("Valid To");
		validToCol.setCellValueFactory(new PropertyValueFactory<>("validTo"));

		TableColumn<MessageFx, Boolean> activeCol = new TableColumn<>("Active");
		activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));

		TableColumn<MessageFx, String> titleCol = new TableColumn<>("Title");
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

		table.getColumns().addAll(idCol, validFromCol, validToCol, activeCol, titleCol);

		data = FXCollections.observableArrayList();
		table.setItems(data);

		// Detail form
		idField = new TextField();
		idField.setDisable(true); // id usually generated
		validFromPicker = new DatePicker();
		validToPicker = new DatePicker();
		activeCheckBox = new CheckBox("Active");
		titleField = new TextField();
		messageTextArea = new TextArea();

		formControls = List.of(validFromPicker, validToPicker, activeCheckBox, titleField, messageTextArea);

		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(10);

		form.add(new Label("ID:"), 0, 0);
		form.add(idField, 1, 0);

		form.add(new Label("Valid From:"), 0, 1);
		form.add(validFromPicker, 1, 1);

		form.add(new Label("Valid To:"), 0, 2);
		form.add(validToPicker, 1, 2);

		form.add(new Label("Active:"), 0, 3);
		form.add(activeCheckBox, 1, 3);

		form.add(new Label("Title:"), 0, 4);
		form.add(titleField, 1, 4);

		form.add(new Label("Message Text:"), 0, 5);
		form.add(messageTextArea, 1, 5);

		HBox buttons = new HBox(10, createBtn, editBtn, deleteBtn, okBtn, cancelBtn, loadlBtn);
		buttons.setAlignment(Pos.CENTER);

		HBox status = new HBox(10, new Label("Status: "), statusLabel);
		status.setAlignment(Pos.BOTTOM_LEFT);

		VBox root = new VBox(10, table, form, buttons, status);
		root.setPadding(new Insets(20));

		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("Zprávy 1.0 - admin aplikace");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Bind selection
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
			if (newSel != null) {
				idField.setText(String.valueOf(newSel.getId()));
				validFromPicker.setValue(newSel.getValidFrom());
				validToPicker.setValue(newSel.getValidTo());
				activeCheckBox.setSelected(newSel.isActive());
				titleField.setText(newSel.getTitle());
				messageTextArea.setText(newSel.getMessageText());
				this.savedMessageFx = newSel;
			}
		});

		// Handlers
		createBtn.setOnAction(e -> handleCreate());
		editBtn.setOnAction(e -> handleEdit());
		deleteBtn.setOnAction(e -> handleDelete());
		okBtn.setOnAction(e -> save());
		cancelBtn.setOnAction(e -> cancel());
		loadlBtn.setOnAction(e -> load());

		setEditable(false);

		load();
	}

	private void cancel() {
		setEditable(false);
	}

	private void save() {
		try {
			if (savedMessageFx != null) {
				savedMessageFx.setValidFrom(validFromPicker.getValue());
				savedMessageFx.setValidTo(validToPicker.getValue());
				savedMessageFx.setActive(activeCheckBox.isSelected());
				savedMessageFx.setTitle(titleField.getText());
				savedMessageFx.setMessageText(messageTextArea.getText());

				statusLabel.setText("Saving entity ...");

				Zprava zprava = MessageConverter.toEntity(savedMessageFx);
				dataLoader.save(zprava);
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void load() {
		statusLabel.setText("Loading ...");
		dataLoader.sendLoadDataRequestAsync();
	}

	private void setEditable(boolean yes) {
		createBtn.setDisable(yes);
		editBtn.setDisable(yes);
		deleteBtn.setDisable(yes);
		okBtn.setDisable(!yes);
		cancelBtn.setDisable(!yes);
		table.setDisable(yes);
		formControls.forEach(c -> c.setDisable(!yes));
	}

	private void handleCreate() {
		setEditable(true);

		idField.setText(null);
		validFromPicker.setValue(null);
		validToPicker.setValue(null);
		activeCheckBox.setSelected(true);
		titleField.setText(null);
		messageTextArea.setText(null);

		this.savedMessageFx = new MessageFx();
	}

	private void handleEdit() {
		this.savedMessageFx = table.getSelectionModel().getSelectedItem();
		setEditable(true);

		/*
		 * MessageFx selected = table.getSelectionModel().getSelectedItem(); if
		 * (selected != null) { selected.setValidFrom(validFromPicker.getValue());
		 * selected.setValidTo(validToPicker.getValue());
		 * selected.setActive(activeCheckBox.isSelected());
		 * selected.setTitle(titleField.getText());
		 * selected.setMessageText(messageTextArea.getText()); table.refresh(); }
		 */

	}

	private void handleDelete() {
		
		// TODO: if dialog == yes ...
		
		this.savedMessageFx = table.getSelectionModel().getSelectedItem();
		Zprava zprava = MessageConverter.toEntity(savedMessageFx);
		statusLabel.setText("Deleting entity ...");
		try {
			dataLoader.delete(zprava);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * JavaFX wrapper for Message entity (with properties for TableView).
	 */
	public static class MessageFx {
		static final long NEWLY_CREATED_ID = -1;
		private final LongProperty id = new SimpleLongProperty();
		private final ObjectProperty<LocalDate> validFrom = new SimpleObjectProperty<>();
		private final ObjectProperty<LocalDate> validTo = new SimpleObjectProperty<>();
		private final BooleanProperty active = new SimpleBooleanProperty();
		private final StringProperty title = new SimpleStringProperty();
		private final StringProperty messageText = new SimpleStringProperty();

		public MessageFx(Long id, LocalDate validFrom, LocalDate validTo, boolean active, String title,
				String messageText) {
			if (id != null)
				this.id.set(id);
			this.validFrom.set(validFrom);
			this.validTo.set(validTo);
			this.active.set(active);
			this.title.set(title);
			this.messageText.set(messageText);
		}

		public MessageFx() {
			this.id.set(NEWLY_CREATED_ID);
		}

		// Getters and setters
		public long getId() {
			return id.get();
		}

		public void setId(Long id) {
			this.id.set(id);
		}

		public LongProperty idProperty() {
			return id;
		}

		public LocalDate getValidFrom() {
			return validFrom.get();
		}

		public void setValidFrom(LocalDate validFrom) {
			this.validFrom.set(validFrom);
		}

		public ObjectProperty<LocalDate> validFromProperty() {
			return validFrom;
		}

		public LocalDate getValidTo() {
			return validTo.get();
		}

		public void setValidTo(LocalDate validTo) {
			this.validTo.set(validTo);
		}

		public ObjectProperty<LocalDate> validToProperty() {
			return validTo;
		}

		public boolean isActive() {
			return active.get();
		}

		public void setActive(boolean active) {
			this.active.set(active);
		}

		public BooleanProperty activeProperty() {
			return active;
		}

		public String getTitle() {
			return title.get();
		}

		public void setTitle(String title) {
			this.title.set(title);
		}

		public StringProperty titleProperty() {
			return title;
		}

		public String getMessageText() {
			return messageText.get();
		}

		public void setMessageText(String messageText) {
			this.messageText.set(messageText);
		}

		public StringProperty messageTextProperty() {
			return messageText;
		}
	}
}
