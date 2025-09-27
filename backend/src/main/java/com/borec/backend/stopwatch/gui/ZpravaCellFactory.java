/*
 * package com.borec.backend.stopwatch.gui;
 * 
 * import com.borec.backend.entity.Zprava;
 * 
 * import javafx.scene.control.ListCell; import javafx.scene.control.ListView;
 * import javafx.util.Callback;
 * 
 * 
 * public class ZpravaCellFactory implements Callback<ListView<Zprava>,
 * ListCell<Zprava>> {
 * 
 * @Override public ListCell<Zprava> call(ListView<Zprava> zprava) { return new
 * ListCell<>(){
 * 
 * @Override public void updateItem(Zprava zprava, boolean empty) {
 * super.updateItem(zprava, empty); if (empty || zprava == null) {
 * setText(null); } else { setText(zprava.getTitulek()); } } }; } }
 */