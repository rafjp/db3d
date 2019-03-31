package ifb.db3d.der6.javafx.control;

import java.util.List;

import javax.persistence.Query;

import ifb.db3d.der6.object.Campo;
import ifb.db3d.der6.object.ImagemInfo;
import ifb.db3d.der6.object.Propriedade;
import ifb.db3d.der6.persistence.ConnectionFactory;
import ifb.db3d.der6.persistence.ImagemInfoCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NovaPropControl {

	private List<Campo> lastQueryCampo;

	@FXML
	private ComboBox<String> propCombo;

	@FXML
	private TextField propValue;

	@FXML
	private Button addBtn;

	public ImagemInfo imagemInfo;

	@SuppressWarnings("unchecked")
	@FXML
	void initialize() {
		Query query = ConnectionFactory.getEntityManager().createQuery("select prop from Campo prop");
		lastQueryCampo = (List<Campo>) query.getResultList();
		propCombo.getItems().clear();
		for (Campo campo : lastQueryCampo) {
			propCombo.getItems().add(campo.getCampo_id() + " - " + campo.getNome());
		}
		propCombo.getSelectionModel().select(0);
	}

	@FXML
	void onAdd(ActionEvent event) {
		float valor = 0;

		try {
			valor = Float.parseFloat(propValue.getText());
		} catch (Exception e) {
			MsgPopupControl.showExceptionAlert("Somente valores númericos válidos!", "Erro", "Erro de conversão!", e);
			return;
		}

		int campo_id = Integer.parseInt(propCombo.getSelectionModel().getSelectedItem().split(" - ")[0]);
		Campo campo = null;
		for (Campo c : lastQueryCampo) {
			if (c.getCampo_id().equals(campo_id)) {
				campo = c;
				break;
			}
		}

		Propriedade prop = new Propriedade(null, valor, campo, imagemInfo);
		imagemInfo.getPropriedades().add(prop);
		ImagemInfoCRUD.update(imagemInfo);

		Stage stage = (Stage) addBtn.getScene().getWindow();
		stage.close();
	}
}
