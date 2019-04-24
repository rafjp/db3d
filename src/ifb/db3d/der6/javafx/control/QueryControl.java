package ifb.db3d.der6.javafx.control;

import java.util.List;

import javax.persistence.Query;

import ifb.db3d.der6.object.Bovino;
import ifb.db3d.der6.object.Campo;
import ifb.db3d.der6.object.Imagem;
import ifb.db3d.der6.object.ImagemInfo;
import ifb.db3d.der6.object.Regiao;
import ifb.db3d.der6.object.Sensor;
import ifb.db3d.der6.persistence.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class QueryControl {

	public static List<Regiao> lastQueryRegiao;

	public static List<Sensor> lastQuerySensor;

	public static List<Campo> lastQueryCampo;

	ToggleGroup radioButtonSexoGroup;

	@FXML
	private CheckBox idCheckButton;

	@FXML
	private CheckBox sexoCheckButton;

	@FXML
	private Button consultarBovinos;

	@FXML
	private TextField idDeTextEntry;

	@FXML
	private TextField idAteTextEntry;

	@FXML
	private RadioButton radioMacho;

	@FXML
	private RadioButton radioFemea;

	@FXML
	private CheckBox curraleiroCheckButton;

	@FXML
	private CheckBox pantaneiroCheckButton;

	@FXML
	private CheckBox neloreCheckButton;

	@FXML
	private ComboBox<String> caracteristicaComboBox;

	@FXML
	private ComboBox<String> valorComboBox;

	@FXML
	private TextField specPropDeTextEntry;

	@FXML
	private TextField specPropAteTextEntry;

	@FXML
	private Button addFiltro;

	@FXML
	private ListView<String> propListView;

	@FXML
	private Button consultarImagens;

	@FXML
	private Button removerFiltro;

	public QueryControl() {
		radioButtonSexoGroup = new ToggleGroup();
	}

	@FXML
	void onAddFiltro(ActionEvent event) {

		String filtro = caracteristicaComboBox.getSelectionModel().getSelectedItem() + "; ";
		filtro += valorComboBox.getSelectionModel().getSelectedItem();

		if (caracteristicaComboBox.getSelectionModel().getSelectedIndex() == 2) {
			try {
				filtro += "; " + Float.parseFloat(specPropDeTextEntry.getText()) + " - "
						+ Float.parseFloat(specPropAteTextEntry.getText());
			} catch (Exception e) {
				MsgPopupControl.showExceptionAlert("Erro no valor da propriedade", "Erro", "Value error", e);
				return;
			}
		}

		boolean add = true;
		for (String filtroName : propListView.getItems()) {
			if (filtroName.equals(filtro)) {
				add = false;
				break;
			}
		}

		if (add)
			propListView.getItems().add(filtro);
		else {
			MsgPopupControl.showNewPopup("Filtro já existe!", "Erro");
		}
	}

	String getBovinoQuery(String queryCommand, boolean handle) {
		if (idCheckButton.isSelected()) {
			int idDe, idAte;
			try {
				idDe = Integer.parseInt(idDeTextEntry.getText());
				idAte = Integer.parseInt(idAteTextEntry.getText());
			} catch (Exception e) {
				MsgPopupControl.showExceptionAlert("Erro na identidade do bovino.", "Erro", "Id erro!", e);
				return null;
			}
			queryCommand += " where bov.bovino_id >= " + idDe + " and bov.bovino_id <= " + idAte;
			handle = true;
		}

		if (sexoCheckButton.isSelected()) {
			if (handle)
				queryCommand += " and";
			else
				queryCommand += " where";
			queryCommand += " bov.sexo = ";
			if (radioMacho.isSelected())
				queryCommand += "false";
			else
				queryCommand += "true";
			handle = true;
		}

		if (curraleiroCheckButton.isSelected() || pantaneiroCheckButton.isSelected()
				|| neloreCheckButton.isSelected()) {
			if (handle)
				queryCommand += " and";
			else
				queryCommand += " where";

			queryCommand += " (bov.raca = ";

			boolean handle_2 = false;
			if (curraleiroCheckButton.isSelected()) {
				queryCommand += "'Curraleiro'";
				handle_2 = true;
			}

			if (neloreCheckButton.isSelected()) {
				if (handle_2)
					queryCommand += " or bov.raca = ";
				queryCommand += "'Nelore'";
				handle_2 = true;
			}

			if (pantaneiroCheckButton.isSelected()) {
				if (handle_2)
					queryCommand += " or bov.raca = ";
				queryCommand += "'Pantaneiro'";
			}
			queryCommand += ")";
			handle = true;
		} else {
			// TODO: Nenhuma raça selecionada
		}

		return queryCommand;
	}

	@SuppressWarnings("unchecked")
	@FXML
	void onConsultarBovinos(ActionEvent event) {
		String queryCommand = "select bov as bovino from Bovino bov";
		queryCommand = getBovinoQuery(queryCommand, false);
		if (queryCommand == null)
			return;
		Query query = ConnectionFactory.getEntityManager().createQuery(queryCommand);
		List<Bovino> result = (List<Bovino>) query.getResultList();
		String queryres = "";
		// TODO: to gui
		for (Bovino bov : result) {
			System.out.println(bov);
			queryres += bov.toString() + "\n";
		}
		MsgPopupControl.showNewPopup(queryres, "Query");
	}

	@FXML
	void onConsultarImagens(ActionEvent event) {

		String regioes = "(";
		boolean notFirstReg = false;

		String sensores = "(";
		boolean notFirstSen = false;

		String campos = "(";
		boolean notFirstCam = false;

		String propriedades = "(";

		for (String filtroString : propListView.getItems()) {
			String filtro[] = filtroString.split(";");
			String filtroInfo[] = filtro[1].split("-");

			if (filtro[0].equals("Região")) {
				if (notFirstReg)
					regioes += " or ";
				regioes += "reg.regiao_id = " + Integer.parseInt(filtroInfo[0].trim());
				notFirstReg = true;
			}

			else if (filtro[0].equals("Sensor")) {
				if (notFirstSen)
					sensores += " or ";
				sensores += "sen.sensor_id = " + Integer.parseInt(filtroInfo[0].trim());
				notFirstSen = true;
			}

			else {
				int campoId = Integer.parseInt(filtroInfo[0].trim());
				if (notFirstCam) {
					campos += " or ";
					propriedades += " and ";
				}
				campos += "cam.campo_id = " + campoId;
				String propValues[] = filtro[2].split("-");
				float valueLow = Float.parseFloat(propValues[0].trim());
				float valueHigh = Float.parseFloat(propValues[1].trim());
				propriedades += "(prop.valora >= " + valueLow + " and prop.valora <= " + valueHigh
						+ " and cam.campo_id = " + campoId + ")";
				notFirstCam = true;
			}
		}
		
		regioes += ")";
		sensores += ")";
		campos += ")";
		propriedades += ")";
		
		String commandQuery =
				"select distinct ima.imagem_id, bov.bovino_id, bov_ima_inf.imagem_info_id from Bovino bov\r\n" + 
				"left join Bovino_Imagem_Info bov_ima_inf on bov.bovino_id = bov_ima_inf.bovino_id\r\n" + 
				"left join Imagem_info ima_inf on ima_inf.imagem_info_id = bov_ima_inf.imagem_info_id\r\n" + 
				"left join Imagem ima on ima.imagem_info_id = ima_inf.imagem_info_id\r\n" + 
				"left join Regiao reg on reg.regiao_id = ima_inf.regiao_id\r\n" + 
				"left join Sensor sen on sen.sensor_id = ima_inf.sensor_id\r\n" + 
				"left join Propriedade prop on prop.imagem_info_id = ima_inf.imagem_info_id\r\n" + 
				"left join Campo cam on cam.campo_id = prop.campo_id";

		commandQuery = getBovinoQuery(commandQuery, false);
		
		if (commandQuery == null) {
			MsgPopupControl.showNewPopup("Informações inválidas!", "Erro");
			return;
		}

		boolean handle = false;
		if (!commandQuery.contains("where")) {
			handle = true;
		}

		if (!regioes.equals("()")) {
			if (handle)
				commandQuery += " where ";
			else
				commandQuery += " and ";
			commandQuery += regioes;
			handle = false;
		}

		if (!sensores.equals("()")) {
			if (handle)
				commandQuery += " where ";
			else
				commandQuery += " and ";
			commandQuery += sensores;
			handle = false;
		}

		if (!campos.equals("()")) {
			if (handle)
				commandQuery += " where ";
			else
				commandQuery += " and ";
			commandQuery += campos;
			handle = false;
		}

		if (!propriedades.equals("()")) {
			if (handle)
				commandQuery += " where ";
			else
				commandQuery += " and ";
			commandQuery += propriedades;
			handle = false;
		}

		Query query = ConnectionFactory.getEntityManager().createNativeQuery(commandQuery, "ImageNativeMap");
		@SuppressWarnings("unchecked")
		List<Object[]> img = (List<Object[]>) query.getResultList();

		String imgQuery = "SELECT ima FROM Imagem ima WHERE ima.imagem_id IN (";
		for (Object[] imagem : img) {
			imgQuery += imagem[0] + ", ";
		}
		imgQuery += "0)";
		Query queryImg = ConnectionFactory.getEntityManager().createQuery(imgQuery);
		@SuppressWarnings("unchecked")
		List<Imagem> resultImagem = (List<Imagem>) queryImg.getResultList();

		String imgInfoQuery = "SELECT imainf FROM Imagem_info imainf WHERE imainf.imagem_info_id IN (";
		for (Object[] imagem : img) {
			imgInfoQuery += imagem[2] + ", ";
		}
		imgInfoQuery += "0)";
		Query queryImgInfo = ConnectionFactory.getEntityManager().createQuery(imgInfoQuery, ImagemInfo.class);
		@SuppressWarnings("unchecked")
		List<ImagemInfo> resultImagemInfo = (List<ImagemInfo>) queryImgInfo.getResultList();

		String bovinoQuery = "SELECT bov FROM Bovino bov where bov.bovino_id IN (";
		for (Object[] imagem : img) {
			bovinoQuery += imagem[1] + ", ";
		}
		bovinoQuery += "0)";
		Query queryBovino = ConnectionFactory.getEntityManager().createQuery(bovinoQuery);
		@SuppressWarnings("unchecked")
		List<Bovino> resultBovino = (List<Bovino>) queryBovino.getResultList();

		QueryViewControl.showNewPopup(resultBovino, resultImagemInfo, resultImagem);
	}

	@FXML
	void onRemoverFiltro(ActionEvent event) {
		try {
			propListView.getItems().remove(propListView.getSelectionModel().getSelectedIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		radioMacho.setToggleGroup(radioButtonSexoGroup);
		radioFemea.setToggleGroup(radioButtonSexoGroup);
		radioFemea.setSelected(true);
		specPropDeTextEntry.setDisable(true);
		specPropAteTextEntry.setDisable(true);
		caracteristicaComboBox.getItems().add("Sensor");
		caracteristicaComboBox.getItems().add("Região");
		caracteristicaComboBox.getItems().add("Propriedade");
		caracteristicaComboBox.getSelectionModel().select(0);
		loadComboBoxInfo();
		updateComboBoxInfo();
	}

	@FXML
	void onSelectFeature(ActionEvent event) {
		updateComboBoxInfo();
	}

	@SuppressWarnings("unchecked")
	public void loadComboBoxInfo() {
		Query query = ConnectionFactory.getEntityManager().createQuery("select prop from Campo prop");
		lastQueryCampo = (List<Campo>) query.getResultList();

		query = ConnectionFactory.getEntityManager().createQuery("select sens from Sensor sens");
		lastQuerySensor = (List<Sensor>) query.getResultList();

		query = ConnectionFactory.getEntityManager().createQuery("select reg from Regiao reg");
		lastQueryRegiao = (List<Regiao>) query.getResultList();
	}

	public void updateComboBoxInfo() {
		valorComboBox.getItems().clear();
		specPropDeTextEntry.setDisable(true);
		specPropAteTextEntry.setDisable(true);
		switch (caracteristicaComboBox.getSelectionModel().getSelectedIndex()) {
		case 0:
			for (Sensor sensor : lastQuerySensor)
				valorComboBox.getItems().add(sensor.getSensor_id() + " - " + sensor.getNome());
			break;
		case 1:
			for (Regiao regiao : lastQueryRegiao)
				valorComboBox.getItems().add(regiao.getRegiao_id() + " - " + regiao.getCaracteristica());
			break;
		case 2:
			specPropDeTextEntry.setDisable(false);
			specPropAteTextEntry.setDisable(false);
			for (Campo campo : lastQueryCampo)
				valorComboBox.getItems().add(campo.getCampo_id() + " - " + campo.getNome());
			break;
		}
		valorComboBox.getSelectionModel().select(0);
	}
}
