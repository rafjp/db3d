package ifb.db3d.der6.javafx.control;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import ifb.db3d.der6.javafx.itens.PropriedadeItem;
import ifb.db3d.der6.object.Bovino;
import ifb.db3d.der6.object.Campo;
import ifb.db3d.der6.object.ImagemInfo;
import ifb.db3d.der6.object.Propriedade;
import ifb.db3d.der6.object.Regiao;
import ifb.db3d.der6.object.Sensor;
import ifb.db3d.der6.persistence.BovinoCRUD;
import ifb.db3d.der6.persistence.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class ImagemControl {

	public static List<Regiao> lastQueryRegiao;
	public static List<Sensor> lastQuerySensor;
	public static List<Campo> lastQueryCampo;
	public static List<Propriedade> imageProp = new ArrayList<>();
	public static List<File> files = new ArrayList<>();
	public static ImagemInfo imagem;

	@FXML
	private ListView<String> fileListView;

	@FXML
	private ComboBox<String> regiaoComboBox;

	@FXML
	private ComboBox<String> sensorComboBox;

	@FXML
	private Button registrarBtn;

	@FXML
	private Button carregarArquivosBtn;

	@FXML
	private TextField bovinoIdEntry;

	@FXML
	private TableView<PropriedadeItem> propTableView;

	@FXML
	private ComboBox<String> propriedadeComboBox;

	@FXML
	private Button addPropriedadeBtn;

	@FXML
	private TextField valorEntry;

	@FXML
	void onAddProp(ActionEvent event) {
		float value;
		try {
			value = Float.parseFloat(valorEntry.getText());
		} catch (Exception e) {
			MsgPopupControl.showNewPopup("Erro no valor da propriedade!", "Erro");
			return;
		}
		Propriedade prop = new Propriedade(null, value, getSelectedProp(), null);
		PropriedadeItem propItem = new PropriedadeItem(prop.getCampo().getNome(), prop.getValora().toString());
		propTableView.getItems().add(propItem);
		imageProp.add(prop);
	}

	@FXML
	void onCarregarArquivos(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		if (files != null)
			files.clear();
		files = fileChooser.showOpenMultipleDialog(carregarArquivosBtn.getScene().getWindow());
		if (files == null)
			return;
		fileListView.getItems().clear();
		for (File file : files)
			fileListView.getItems().add(file.getName());
	}

	@FXML
	void onRegister(ActionEvent event) {
		try {
			ImagemInfo imagemInfo = new ImagemInfo(new Date(), getSelectedRegiao(), getSelectedSensor());
			List<Bovino> bovinosList = new ArrayList<>();
			if (bovinoIdEntry.getText().length() > 0) {
				String bovinos[] = bovinoIdEntry.getText().split(",");
				for (String bovino_id : bovinos) {
					Bovino bovino = BovinoCRUD.get(Integer.parseInt(bovino_id));
					bovinosList.add(bovino);
				}
				imagemInfo.setBovinos(bovinosList);
			}
			imagem = imagemInfo;
			UploadImgControl.showNewPopup();
		} catch (Exception e) {
			e.printStackTrace();
			MsgPopupControl.showNewPopup("Erro ao criar imagem: " + e.getMessage(), "Erro");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void initialize() {
		TableColumn campoColunm = new TableColumn("Campo");
		campoColunm.setCellValueFactory(new PropertyValueFactory<>("campo"));

		TableColumn propColumn = new TableColumn("Propriedade");
		propColumn.setCellValueFactory(new PropertyValueFactory<>("propriedade"));

		propTableView.getColumns().clear();
		propTableView.getColumns().addAll(campoColunm, propColumn);

		imageProp = new ArrayList<>();
		updateComboBoxInfo();
	}

	Campo getSelectedProp() {
		if (propriedadeComboBox.getSelectionModel().getSelectedItem() == null)
			return null;
		int id = Integer.parseInt(propriedadeComboBox.getSelectionModel().getSelectedItem().split(" - ")[0]);
		for (Campo campo : lastQueryCampo)
			if (campo.getCampo_id().equals(id))
				return campo;
		return null;
	}

	Regiao getSelectedRegiao() {
		if (regiaoComboBox.getSelectionModel().getSelectedItem() == null)
			return null;
		int id = Integer.parseInt(regiaoComboBox.getSelectionModel().getSelectedItem().split(" - ")[0]);
		for (Regiao regiao : lastQueryRegiao)
			if (regiao.getRegiao_id().equals(id))
				return regiao;
		return null;
	}

	Sensor getSelectedSensor() {
		if (sensorComboBox.getSelectionModel().getSelectedItem() == null)
			return null;
		int id = Integer.parseInt(sensorComboBox.getSelectionModel().getSelectedItem().split(" - ")[0]);
		for (Sensor sensor : lastQuerySensor)
			if (sensor.getSensor_id().equals(id))
				return sensor;
		return null;
	}

	@SuppressWarnings("unchecked")
	public void updateComboBoxInfo() {
		Query query = ConnectionFactory.getEntityManager().createQuery("select prop from Campo prop");
		lastQueryCampo = (List<Campo>) query.getResultList();
		propriedadeComboBox.getItems().clear();
		for (Campo campo : lastQueryCampo) {
			propriedadeComboBox.getItems().add(campo.getCampo_id() + " - " + campo.getNome());
		}

		if (propriedadeComboBox.getItems().size() > 0)
			propriedadeComboBox.getSelectionModel().select(0);

		query = ConnectionFactory.getEntityManager().createQuery("select sens from Sensor sens");
		lastQuerySensor = (List<Sensor>) query.getResultList();
		sensorComboBox.getItems().clear();
		for (Sensor sensor : lastQuerySensor) {
			sensorComboBox.getItems().add(sensor.getSensor_id() + " - " + sensor.getNome());
		}

		if (sensorComboBox.getItems().size() > 0)
			sensorComboBox.getSelectionModel().select(0);

		query = ConnectionFactory.getEntityManager().createQuery("select reg from Regiao reg");
		lastQueryRegiao = (List<Regiao>) query.getResultList();
		regiaoComboBox.getItems().clear();
		for (Regiao regiao : lastQueryRegiao) {
			regiaoComboBox.getItems().add(regiao.getRegiao_id() + " - " + regiao.getCaracteristica());
		}

		if (regiaoComboBox.getItems().size() > 0)
			regiaoComboBox.getSelectionModel().select(0);
	}
}
