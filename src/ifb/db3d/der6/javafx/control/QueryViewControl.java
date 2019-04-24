package ifb.db3d.der6.javafx.control;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import ifb.db3d.der6.javafx.itens.PropriedadeItem;
import ifb.db3d.der6.object.Bovino;
import ifb.db3d.der6.object.Imagem;
import ifb.db3d.der6.object.ImagemInfo;
import ifb.db3d.der6.object.Propriedade;
import ifb.db3d.der6.object.Regiao;
import ifb.db3d.der6.object.Sensor;
import ifb.db3d.der6.persistence.BovinoCRUD;
import ifb.db3d.der6.persistence.ConnectionFactory;
import ifb.db3d.der6.persistence.ImagemCRUD;
import ifb.db3d.der6.persistence.ImagemInfoCRUD;
import ifb.db3d.der6.persistence.PropriedadeCRUD;
import ifb.db3d.der6.util.DateUtil;
import ifb.db3d.der6.util.FxmlResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class QueryViewControl {
	ToggleGroup radioButtonSexoGroup;
	private List<Sensor> lastQuerySensor;
	private List<Regiao> lastQueryRegiao;
	private ImagemInfo lastImagemInfo;
	private Bovino lastBovinoEdit;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void init(List<Bovino> bovinos, List<ImagemInfo> imagensInfo, List<Imagem> imagens) {

		btnEditarInfoBovino.setDisable(true);
		btnRemoverSelecionado.setDisable(true);
		deletarBtn.setDisable(true);
		btnAdicionar.setDisable(true);
		comboBoxSensor.setDisable(true);
		comboBoxRegiao.setDisable(true);

		updateComboBoxs();

		TableColumn campoColunm = new TableColumn("Campo");
		campoColunm.setCellValueFactory(new PropertyValueFactory<>("campo"));

		TableColumn propColumn = new TableColumn("Propriedade");
		propColumn.setCellValueFactory(new PropertyValueFactory<>("propriedade"));

		propTableView.getColumns().clear();
		propTableView.getColumns().addAll(campoColunm, propColumn);

		radioButtonSexoGroup = new ToggleGroup();

		bovinoRacaCombo.getItems().addAll("Nelore", "Pantaneiro", "Curraleiro");
		bovinoRacaCombo.getSelectionModel().select(0);

		bovinoRadioMacho.setToggleGroup(radioButtonSexoGroup);
		bovinoRadioFemea.setToggleGroup(radioButtonSexoGroup);

		TreeItem<String> root = new TreeItem<String>("Banco de dados", getIcon("puzzle-piece-2x"));

		for (Bovino bovino : bovinos) {
			String bovinoText = String.format("bov %05d %s", bovino.getBovino_id(),
					DateUtil.formatDate(bovino.getNascimento()));
			TreeItem<String> bovinoTreeItem = new TreeItem<String>(bovinoText, getIcon("media-record-2x"));

			for (ImagemInfo imagemInfo : imagensInfo) {
				boolean cont = true;
				for(Bovino bovcmp: imagemInfo.getBovinos()) {
					if(bovcmp.getBovino_id().equals(bovino.getBovino_id())) {
						cont = false;
						break;
					}
				}
				if(cont) continue;
				
				String imagemInfoText = String.format("img %05d %s %s", imagemInfo.getImagem_info_id(),
						imagemInfo.getRegiao().getCaracteristica(), DateUtil.formatDate(imagemInfo.getEnvio()));
				TreeItem<String> imgInfoTreeItem = new TreeItem<String>(imagemInfoText, getIcon("folder-2x"));

				for (Imagem imagem : imagens) {
					if (!imagem.getImagemInfo().getImagem_info_id().equals(imagemInfo.getImagem_info_id()))
						continue;

					String icon = "file-2x";
					if (imagem.getExtencao().toLowerCase().endsWith(".png")
							|| imagem.getExtencao().toLowerCase().endsWith(".jpg")
							|| imagem.getExtencao().toLowerCase().endsWith(".gif")
							|| imagem.getExtencao().toLowerCase().endsWith(".tiff")
							|| imagem.getExtencao().toLowerCase().endsWith(".jpeg")) {
						icon = "image-2x";
					}

					String imageText = String.format("arq %05d %s", imagem.getImagem_id(), imagem.getExtencao());
					TreeItem<String> imgTreeItem = new TreeItem<String>(imageText, getIcon(icon));
					imgInfoTreeItem.getChildren().add(imgTreeItem);
				}

				if (imgInfoTreeItem.getChildren().size() > 0)
					bovinoTreeItem.getChildren().add(imgInfoTreeItem);
			}
			root.getChildren().add(bovinoTreeItem);
		}

		root.setExpanded(true);
		mainTreeView.setRoot(root);

		mainTreeView.getSelectionModel().select(0);
	}

	private ImageView getIcon(String path) {
		return new ImageView(new Image(FxmlResource.getIconPath(path)));
	}

	@FXML
	private Button btnEditarInfoBovino;

	@FXML
	private TextField txtBovinoCod;

	@FXML
	private RadioButton bovinoRadioMacho;

	@FXML
	private RadioButton bovinoRadioFemea;

	@FXML
	private ComboBox<String> bovinoRacaCombo;

	@FXML
	private DatePicker bovinoDataNascimento;

	@FXML
	private Button btnTransferirDados;

	@FXML
	private TreeView<String> mainTreeView;

	@FXML
	private Button btnAdicionar;

	@FXML
	private Button deletarBtn;

	@FXML
	private Button btnRemoverSelecionado;

	@FXML
	private TableView<PropriedadeItem> propTableView;

	@FXML
	private ComboBox<String> comboBoxSensor;

	@FXML
	private ComboBox<String> comboBoxRegiao;

	@FXML
	void onAdicionar(ActionEvent event) {
		String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();
		ImagemInfo imagemInfo = null;

		if (item.startsWith("img")) {
			imagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));
		} else if (item.startsWith("arq")) {
			Imagem down = ImagemCRUD.get(Integer.parseInt(item.split(" ")[1]));
			imagemInfo = down.getImagemInfo();
		} else {
			return;
		}

		MsgPopupControl.showPropAddPopup(imagemInfo);

		propTableView.getItems().clear();
		for (Propriedade prop : imagemInfo.getPropriedades()) {
			PropriedadeItem propItem = new PropriedadeItem(prop.getPropriedade_id() + " - " + prop.getCampo().getNome(),
					prop.getValora().toString());
			propTableView.getItems().add(propItem);
		}
	}

	@FXML
	void onDelete(ActionEvent event) {
		String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();
		if (item.startsWith("img")) {
			ImagemInfo imagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));

			if (!MsgPopupControl.showNewConfirmAlert(
					"A operação é irreversível, todos os dados conectados a imagem serão deletados.",
					"Deletar permanentemente?", "Confirmar"))
				return;

			ImagemInfoCRUD.delete(imagemInfo);
			TreeItem<String> tt = mainTreeView.getSelectionModel().getSelectedItem();
			tt.getParent().getChildren().remove(tt);

		} else if (item.startsWith("arq")) {
			Imagem down = ImagemCRUD.get(Integer.parseInt(item.split(" ")[1]));

			if (!MsgPopupControl.showNewConfirmAlert(
					"A operação é irreversível, todos os dados conectados a imagem serão deletados.",
					"Deletar permanentemente?", "Confirmar"))
				return;

			ImagemCRUD.delete(down);
			TreeItem<String> tt = mainTreeView.getSelectionModel().getSelectedItem();
			tt.getParent().getChildren().remove(tt);
		}
	}

	@FXML
	void onComboChangeRegiao(ActionEvent event) {
		String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();
		ImagemInfo imagemInfo = null;

		if (item.startsWith("img")) {
			imagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));
		} else if (item.startsWith("arq")) {
			Imagem down = ImagemCRUD.get(Integer.parseInt(item.split(" ")[1]));
			imagemInfo = down.getImagemInfo();
		} else {
			return;
		}

		int regiaoId = Integer.parseInt(comboBoxRegiao.getSelectionModel().getSelectedItem().split(" - ")[0]);
		Regiao toChange = null;
		for (Regiao regiao : lastQueryRegiao) {
			if (regiao.getRegiao_id().equals(regiaoId)) {
				toChange = regiao;
				break;
			}
		}

		if (toChange == null) {
			return;
		}

		if (imagemInfo.getRegiao().getRegiao_id() == toChange.getRegiao_id()) {
			return;
		}

		imagemInfo.setRegiao(toChange);
		ImagemInfoCRUD.update(imagemInfo);
		MsgPopupControl.showNewPopup("A região da imagem foi alterada!", "Update");
	}

	@FXML
	void onComboChangeSensor(ActionEvent event) {
		String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();
		ImagemInfo imagemInfo = null;

		if (item.startsWith("img")) {
			imagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));
		} else if (item.startsWith("arq")) {
			Imagem down = ImagemCRUD.get(Integer.parseInt(item.split(" ")[1]));
			imagemInfo = down.getImagemInfo();
		} else {
			return;
		}

		int sensorId = Integer.parseInt(comboBoxSensor.getSelectionModel().getSelectedItem().split(" - ")[0]);
		Sensor toChange = null;
		for (Sensor sensor : lastQuerySensor) {
			if (sensor.getSensor_id().equals(sensorId)) {
				toChange = sensor;
				break;
			}
		}

		if (toChange == null) {
			return;
		}

		if (imagemInfo.getSensor().getSensor_id() == toChange.getSensor_id()) {
			return;
		}

		imagemInfo.setSensor(toChange);
		ImagemInfoCRUD.update(imagemInfo);
		MsgPopupControl.showNewPopup("O sensor da imagem foi alterado!", "Update");
	}

	@FXML
	void onEditarInfoBovino(ActionEvent event) {
		lastBovinoEdit.setRaca(bovinoRacaCombo.getSelectionModel().getSelectedItem());
		lastBovinoEdit.setNascimento(
				Date.from(bovinoDataNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		lastBovinoEdit.setSexo(bovinoRadioFemea.isSelected());
		BovinoCRUD.update(lastBovinoEdit);
		MsgPopupControl.showNewPopup("Informações do bovino atualizadas!", "Update");
	}

	@FXML
	void onRemoverSelecionado(ActionEvent event) {
		PropriedadeItem prop = propTableView.getSelectionModel().getSelectedItem();

		if (prop == null) {
			MsgPopupControl.showNewPopup("Selecione uma propriedade primeiro!", "Erro");
			return;
		}

		if (!MsgPopupControl.showNewConfirmAlert(
				"Deletar propriedade:\n" + prop.getCampo() + ": " + prop.getPropriedade() + "?", "Confirmar",
				"Deletar propriedade?"))
			return;

		int propId = Integer.parseInt(prop.getCampo().split(" - ")[0]);
		for (Propriedade propriedade : lastImagemInfo.getPropriedades()) {
			if (propriedade.getPropriedade_id() == propId) {
				lastImagemInfo.getPropriedades().remove(propriedade);
				propTableView.getItems().remove(propTableView.getSelectionModel().getSelectedIndex());
				PropriedadeCRUD.delete(propriedade);
				ConnectionFactory.getEntityManager().refresh(lastImagemInfo);
				break;
			}
		}
	}

	@FXML
	void onTransferirDados(ActionEvent event) {
		String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();

		if (item.startsWith("bov")) {
			// TODO salvar tudo!

		} else if (item.startsWith("img")) {
			ImagemInfo imagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Selecione uma pasta");
			File selectedDirectory = chooser.showDialog(btnTransferirDados.getScene().getWindow());
			boolean pass = true;
			for (Imagem imagem : imagemInfo.getImagens()) {
				try {
					imagem.salvarArquivo(selectedDirectory.getAbsolutePath(), imagem.getExtencao());
				} catch (IOException e) {
					pass = false;
					e.printStackTrace();
				}
			}
			if (!pass) {
				MsgPopupControl.showNewPopup("Não foi possível salvar o arquivo!", "Erro");
			}
		} else if (item.startsWith("arq")) {
			Imagem down = ImagemCRUD.get(Integer.parseInt(item.split(" ")[1]));
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName(down.getExtencao());
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(btnTransferirDados.getScene().getWindow());
			if (file == null)
				MsgPopupControl.showNewPopup("Caminho inválido!", "Erro");
			try {
				down.salvarArquivo(file.getAbsolutePath());
			} catch (IOException e) {
				MsgPopupControl.showNewPopup("Não foi possível salvar o arquivo!", "Erro");
			}
		}
	}

	@FXML
	void onTreeSelect(MouseEvent event) {

		comboBoxRegiao.setDisable(true);
		comboBoxSensor.setDisable(true);

		String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();

		if (item.startsWith("bov")) {
			lastBovinoEdit = BovinoCRUD.get(Integer.parseInt(item.split(" ")[1]));
			txtBovinoCod.setText(lastBovinoEdit.getBovino_id() + "");
			if (!lastBovinoEdit.getSexo()) {
				bovinoRadioMacho.setSelected(true);
			} else {
				bovinoRadioFemea.setSelected(true);
			}
			if (lastBovinoEdit.getRaca().equalsIgnoreCase("Nelore")) {
				bovinoRacaCombo.getSelectionModel().select(0);
			} else if (lastBovinoEdit.getRaca().equalsIgnoreCase("Pantaneiro")) {
				bovinoRacaCombo.getSelectionModel().select(1);
			} else if (lastBovinoEdit.getRaca().equalsIgnoreCase("Curraleiro")) {
				bovinoRacaCombo.getSelectionModel().select(2);
			}
			bovinoDataNascimento
					.setValue(lastBovinoEdit.getNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			lastBovinoEdit.getNascimento();

			btnEditarInfoBovino.setDisable(false);
		}

		else if (item.startsWith("img")) {
			lastImagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));

			propTableView.getItems().clear();
			for (Propriedade prop : lastImagemInfo.getPropriedades()) {
				PropriedadeItem propItem = new PropriedadeItem(
						prop.getPropriedade_id() + " - " + prop.getCampo().getNome(), prop.getValora().toString());
				propTableView.getItems().add(propItem);
			}

			int select = 0;
			for (Regiao regiao : lastQueryRegiao) {
				if (regiao.getRegiao_id().equals(lastImagemInfo.getRegiao().getRegiao_id()))
					break;
				select++;
			}
			comboBoxRegiao.getSelectionModel().select(select);

			select = 0;
			for (Sensor sensor : lastQuerySensor) {
				if (sensor.getSensor_id().equals(lastImagemInfo.getSensor().getSensor_id()))
					break;
				select++;
			}
			comboBoxSensor.getSelectionModel().select(select);

			comboBoxRegiao.setDisable(false);
			comboBoxSensor.setDisable(false);

			btnEditarInfoBovino.setDisable(false);
			btnRemoverSelecionado.setDisable(false);
			deletarBtn.setDisable(false);
			btnAdicionar.setDisable(false);

		} else if (item.startsWith("arq")) {
			item = mainTreeView.getSelectionModel().getSelectedItem().getParent().getValue();

			lastImagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));

			propTableView.getItems().clear();
			for (Propriedade prop : lastImagemInfo.getPropriedades()) {
				PropriedadeItem propItem = new PropriedadeItem(
						prop.getPropriedade_id() + " - " + prop.getCampo().getNome(), prop.getValora().toString());
				propTableView.getItems().add(propItem);
			}

			comboBoxRegiao.setDisable(false);
			comboBoxSensor.setDisable(false);

			btnEditarInfoBovino.setDisable(false);
			btnRemoverSelecionado.setDisable(false);
			deletarBtn.setDisable(false);
			btnAdicionar.setDisable(false);
		} else {
			btnEditarInfoBovino.setDisable(true);
			btnRemoverSelecionado.setDisable(true);
			deletarBtn.setDisable(true);
			btnAdicionar.setDisable(true);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateComboBoxs() {
		Query query;

		query = ConnectionFactory.getEntityManager().createQuery("select sens from Sensor sens");
		lastQuerySensor = (List<Sensor>) query.getResultList();
		comboBoxSensor.getItems().clear();
		for (Sensor sensor : lastQuerySensor) {
			comboBoxSensor.getItems().add(sensor.getSensor_id() + " - " + sensor.getNome());
		}

		if (comboBoxSensor.getItems().size() > 0)
			comboBoxSensor.getSelectionModel().select(0);

		query = ConnectionFactory.getEntityManager().createQuery("select reg from Regiao reg");
		lastQueryRegiao = (List<Regiao>) query.getResultList();
		comboBoxRegiao.getItems().clear();
		for (Regiao regiao : lastQueryRegiao) {
			comboBoxRegiao.getItems().add(regiao.getRegiao_id() + " - " + regiao.getCaracteristica());
		}

		if (comboBoxRegiao.getItems().size() > 0)
			comboBoxRegiao.getSelectionModel().select(0);
	}

	private static void createPopup(List<Bovino> bovinos, List<ImagemInfo> imagensInfo, List<Imagem> imagens, int w, int h) {
		try {
			FXMLLoader loader = new FXMLLoader(FxmlResource.getFxmlPath("QueryResult"));

			Stage msgStage = new Stage();
			msgStage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
			Pane pane = loader.load();
			QueryViewControl queryViewControl = (QueryViewControl) loader.getController();
			queryViewControl.init(bovinos, imagensInfo, imagens);
			Scene scene = new Scene(pane, w, h);
			scene.getStylesheets().add(FxmlResource.getCssPath("app"));
			msgStage.setTitle("Resultado da consulta");
			msgStage.setScene(scene);
			msgStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void showNewPopup(List<Bovino> bovinos, List<ImagemInfo> imagensInfo, List<Imagem> imagens) {
		createPopup(bovinos, imagensInfo, imagens, 1100, 600);
	}
}
