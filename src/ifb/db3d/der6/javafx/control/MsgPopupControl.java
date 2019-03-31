package ifb.db3d.der6.javafx.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import ifb.db3d.der6.object.ImagemInfo;
import ifb.db3d.der6.util.FxmlResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class MsgPopupControl {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private Label msgLabel;

	@FXML
	private Button closeButton;

	@FXML
	void onClose(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	public void init(String msg) {
		msgLabel.setText(msg);
	}

	private static void createPopup(String msg, String title, int w, int h) {
		try {
			FXMLLoader loader = new FXMLLoader(FxmlResource.getFxmlPath("MsgPopup"));
			Stage msgStage = new Stage();
			msgStage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
			Pane pane = loader.load();
			MsgPopupControl msgPopupControl = (MsgPopupControl) loader.getController();
			msgPopupControl.init(msg);
			Scene scene = new Scene(pane, w, h);
			scene.getStylesheets().add(FxmlResource.getCssPath("app"));
			msgStage.setTitle(title);
			msgStage.setScene(scene);
			msgStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void showPropAddPopup(ImagemInfo imagemInfo) {
		try {
			FXMLLoader loader = new FXMLLoader(FxmlResource.getFxmlPath("NovaProp"));
			Stage msgStage = new Stage();
			msgStage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
			Pane pane = loader.load();
			NovaPropControl msgPopupControl = (NovaPropControl) loader.getController();
			msgPopupControl.imagemInfo = imagemInfo;
			Scene scene = new Scene(pane);
			scene.getStylesheets().add(FxmlResource.getCssPath("app"));
			msgStage.setTitle("Adicionar nova propriedade");
			msgStage.setScene(scene);
			msgStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void showNewPopup(String msg, String title) {
		createPopup(msg, title, 400, 165);
	}

	public static void showNewPopup(String msg, String title, int width, int height) {
		createPopup(msg, title, width, height);
	}

	public static void showNewInfoAlert(String msg, String title, String header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public static boolean showNewConfirmAlert(String msg, String title, String header) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
		alert.setTitle(header);
		alert.setHeaderText(title);
		alert.setContentText(msg);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			return true;
		return false;
	}

	public static String showNewInputDialog(String msg, String title, String header) {
		TextInputDialog dialog = new TextInputDialog();
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(msg);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (result.get().length() < 1)
				return null;
			return result.get();
		}

		return null;
	}

	public static void showExceptionAlert(String msg, String title, String header, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
