package ifb.db3d.der6.javafx.control;

import java.util.List;

import javax.persistence.Query;

import ifb.db3d.der6.object.Campo;
import ifb.db3d.der6.persistence.ConnectionFactory;
import ifb.db3d.der6.persistence.CampoCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CamposControl {

	public static List<Campo> lastQuery;

	@FXML
	private TextField caracteristicaEntry;

	@FXML
	private TextArea descricaoText;

	@FXML
	private Button adicionarButton;

	@FXML
	private ListView<String> listView;

	@FXML
	private Button camposButton;

	@FXML
	private Button removerButton;

	@FXML
	void onAdd(ActionEvent event) {
		Campo campo = new Campo(null, caracteristicaEntry.getText());
		CampoCRUD.create(campo);
		MsgPopupControl.showNewPopup("Nova região registrada!", "Novo registro");
		updateListView();
	}

	@FXML
	void onRemove(ActionEvent event) {
		Campo toRemove = getListSelected();
		if (toRemove != null) {
			CampoCRUD.delete(toRemove);
			updateListView();
		}
	}

	private Campo getListSelected() {
		if (listView.getSelectionModel().getSelectedItem() == null)
			return null;

		int id = Integer.parseInt(listView.getSelectionModel().getSelectedItem().split(" - ")[0]);
		for (Campo campo : lastQuery)
			if (campo.getCampo_id().equals(id))
				return campo;

		return null;
	}

	@FXML
	void initialize() {
		updateListView();
	}

	@SuppressWarnings("unchecked")
	void updateListView() {
		listView.getItems().clear();
		Query query = ConnectionFactory.getEntityManager().createQuery("select prop from Campo prop");
		lastQuery = (List<Campo>) query.getResultList();
		listView.getItems().clear();
		for (Campo campo : lastQuery) {
			listView.getItems().add(campo.getCampo_id() + " - " + campo.getNome());
		}
	}
}
