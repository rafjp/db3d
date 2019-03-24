package ifb.db3d.der6.javafx.control;

import java.util.List;

import javax.persistence.Query;

import ifb.db3d.der6.object.Regiao;
import ifb.db3d.der6.persistence.ConnectionFactory;
import ifb.db3d.der6.persistence.RegiaoCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegiaoControl {
	
	public static List<Regiao> lastQuery;

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
    	Regiao regiao = new Regiao(caracteristicaEntry.getText(), descricaoText.getText());
    	RegiaoCRUD.create(regiao);
    	MsgPopupControl.showNewPopup("Nova região registrada!", "Novo registro");
    	updateListView();
    }

    @FXML
    void onRemove(ActionEvent event) {
    	Regiao toRemove = getListSelected();
    	if(toRemove != null)
    	{
    		RegiaoCRUD.delete(toRemove);
    		updateListView();
    	}
    }
    
    private Regiao getListSelected(){
    	if(listView.getSelectionModel().getSelectedItem() == null)
    		return null;
    	
    	int id = Integer.parseInt(listView.getSelectionModel().getSelectedItem().split(" - ")[0]);
    	for(Regiao regiao: lastQuery)
    		if(regiao.getRegiao_id().equals(id))
    			return regiao;
    	
    	return null;
    }

    @FXML
    void onShowProp(ActionEvent event) {
    	Regiao toShowProp = getListSelected();
    	if(toShowProp != null)
    		MsgPopupControl.showNewPopup(toShowProp.toString(), "Propriedades de região: " + toShowProp.getRegiao_id(), 700, 300);
    }
    
    @FXML
    void initialize() {
		updateListView();
    }
	
	@SuppressWarnings("unchecked")
	void updateListView()
	{
		listView.getItems().clear();
		Query query = ConnectionFactory.getEntityManager().createQuery("select reg from Regiao reg");
    	lastQuery = (List<Regiao>) query.getResultList();
    	listView.getItems().clear();
    	for(Regiao regiao: lastQuery)
    	{
    		listView.getItems().add(regiao.getRegiao_id() + " - "+ regiao.getCaracteristica());
    	}
	}
}
