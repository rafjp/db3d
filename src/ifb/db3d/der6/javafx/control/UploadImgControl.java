package ifb.db3d.der6.javafx.control;

import java.io.File;

import ifb.db3d.der6.object.Imagem;
import ifb.db3d.der6.object.Propriedade;
import ifb.db3d.der6.persistence.ImagemCRUD;
import ifb.db3d.der6.persistence.ImagemInfoCRUD;
import ifb.db3d.der6.persistence.PropriedadeCRUD;
import ifb.db3d.der6.util.FxmlResource;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UploadImgControl {

	public static boolean uploadingState = false;

	private Thread uploadThread;
	private boolean uploading;

	@FXML
	private ProgressBar propLoadBar;

	@FXML
	private ProgressBar arquivoLoadBar;

	@FXML
	private Button okButton;

	@FXML
	private Label propAtual;

	@FXML
	private Label titleLabel;

	@FXML
	private Label arquivoAtual;

	@FXML
	void onOk(ActionEvent event) {

		uploadingState = false;
		uploading = false;
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}

	void updatePropUp(String prop, double porcentagem) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				propLoadBar.setProgress(porcentagem);
				propAtual.setText(prop);
			}
		});
	}

	void updateFileUP(String file, double porcentagem) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				arquivoLoadBar.setProgress(porcentagem);
				arquivoAtual.setText(file);
			}
		});
	}

	@FXML
	void initialize() {
		uploadingState = true;
		uploading = true;
		uploadThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ImagemInfoCRUD.create(ImagemControl.imagem);

					float progress = 0;
					for (Propriedade prop : ImagemControl.imageProp) {
						progress++;
						prop.setImagem(ImagemControl.imagem);
						PropriedadeCRUD.create(prop);
						updatePropUp(prop.getCampo().getNome(), progress / ImagemControl.imageProp.size());
						Thread.sleep(500);
						if (!uploading)
							return;
					}

					progress = 0;
					for (File file : ImagemControl.files) {
						progress++;
						Imagem imagem = new Imagem(file.getName(), ImagemControl.imagem);
						imagem.carregarArquivo(file.getAbsolutePath());
						ImagemCRUD.create(imagem);
						updateFileUP(file.getName(), progress / ImagemControl.files.size());
						Thread.sleep(500);
						if (!uploading)
							return;
					}

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							titleLabel.setText("Completo!");
							okButton.setText("Ok");
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							MsgPopupControl.showNewPopup("Erro ao enviar imagem: " + e.getMessage(), "Erro");
						}
					});
				}
			}
		});

		uploadThread.start();
	}

	private static void createPopup() {
		if (uploadingState) {
			MsgPopupControl.showNewPopup("Uma operação de envio já está sendo executada!", "Erro");
			return;
		}

		try {
			Stage msgStage = new Stage();
			msgStage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
			Pane pane = FXMLLoader.load(FxmlResource.getFxmlPath("ImagemUpload"));
			Scene scene = new Scene(pane, 400, 300);
			scene.getStylesheets().add(FxmlResource.getCssPath("app"));
			msgStage.setTitle("Upload");
			msgStage.setScene(scene);
			msgStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void showNewPopup() {
		createPopup();
	}
}
