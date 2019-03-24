package ifb.db3d.der6.javafx.control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ifb.db3d.der6.persistence.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SqlControl {
	@FXML
	private TextField hostTextShow;

	@FXML
	private TextField portTextShow;

	@FXML
	private TextField userTextShow;

	@FXML
	private TextArea sqlTextEntry;

	@FXML
	private Button executarBtn;

	@FXML
	@SuppressWarnings("unchecked")
	void executarConsulta(ActionEvent event) {
		try {
			Query query = ConnectionFactory.getEntityManager().createQuery(sqlTextEntry.getText());
			List<Object> result = (List<Object>) query.getResultList();
			String buffer = "Query: " + sqlTextEntry.getText() + "\n";
			for (Object object : result) {
				buffer += object.toString() + "\n";
			}
			MsgPopupControl.showNewPopup(buffer, "Consulta", 800, 600);
		} catch(Exception e)
		{
			MsgPopupControl.showNewPopup(e.getMessage(), "Erro ao executar consulta");
		}
		
	}

	@FXML
	public void initialize() {
		if (ConnectionFactory.getEntityManager() != null) {
			EntityManager entityManager = ConnectionFactory.getEntityManager();
			hostTextShow.setText(entityManager.getProperties().get("javax.persistence.jdbc.url").toString());
			portTextShow.setText("5432");
			userTextShow.setText(entityManager.getProperties().get("javax.persistence.jdbc.user").toString());
		}
	}

}
