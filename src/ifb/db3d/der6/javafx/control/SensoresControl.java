package ifb.db3d.der6.javafx.control;

import java.util.List;

import javax.persistence.Query;

import ifb.db3d.der6.object.Sensor;
import ifb.db3d.der6.persistence.ConnectionFactory;
import ifb.db3d.der6.persistence.SensorCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SensoresControl {

	public static List<Sensor> lastQuery;

	@FXML
	private TextField caracteristicaEntry;

	@FXML
	private TextArea descricaoText;

	@FXML
	private Button adicionarButton;

	@FXML
	private ListView<String> listView;

	@FXML
	private Button propriedadesButton;

	@FXML
	private Button removerButton;

	@FXML
	void onAdd(ActionEvent event) {
		Sensor Sensor = new Sensor(caracteristicaEntry.getText(), descricaoText.getText());
		SensorCRUD.create(Sensor);
		MsgPopupControl.showNewPopup("Novo sensor registrado!", "Novo registro");
		updateListView();
	}

	@FXML
	void onRemove(ActionEvent event) {
		Sensor toRemove = getListSelected();
		if (toRemove != null) {
			SensorCRUD.delete(toRemove);
			updateListView();
		}
	}

	private Sensor getListSelected() {
		if (listView.getSelectionModel().getSelectedItem() == null)
			return null;

		int id = Integer.parseInt(listView.getSelectionModel().getSelectedItem().split(" - ")[0]);
		for (Sensor Sensor : lastQuery)
			if (Sensor.getSensor_id().equals(id))
				return Sensor;

		return null;
	}

	@FXML
	void onShowProp(ActionEvent event) {
		Sensor toShowProp = getListSelected();
		if (toShowProp != null)
			MsgPopupControl.showNewPopup(toShowProp.toString(), "Propriedades de região: " + toShowProp.getSensor_id(),
					700, 300);
	}

	@FXML
	void initialize() {
		updateListView();
	}

	@SuppressWarnings("unchecked")
	void updateListView() {
		listView.getItems().clear();
		Query query = ConnectionFactory.getEntityManager().createQuery("select sen from Sensor sen");
		lastQuery = (List<Sensor>) query.getResultList();
		listView.getItems().clear();
		for (Sensor Sensor : lastQuery) {
			listView.getItems().add(Sensor.getSensor_id() + " - " + Sensor.getNome());
		}
	}
}
