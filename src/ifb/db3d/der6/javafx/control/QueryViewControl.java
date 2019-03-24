package ifb.db3d.der6.javafx.control;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private static List<Imagem> imagens;
	ToggleGroup radioButtonSexoGroup;
//	private Bovino lastBovinoEdit;
	private List<Sensor> lastQuerySensor;
	private List<Regiao> lastQueryRegiao;
	private ImagemInfo lastImagemInfo;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @FXML
    void initialize()
    {
		updateComboBoxs();
		
    	TableColumn campoColunm = new TableColumn("Campo");
		campoColunm.setCellValueFactory(new PropertyValueFactory<>("campo"));

		TableColumn propColumn = new TableColumn("Propriedade");
		propColumn.setCellValueFactory(new PropertyValueFactory<>("propriedade"));

		propTableView.getColumns().clear();
		propTableView.getColumns().addAll(campoColunm, propColumn);
    	
    	radioButtonSexoGroup = new ToggleGroup();
    	
    	bovinoRacaCombo.getItems().addAll(
				"Nelore",
				"Pantaneiro",
				"Curraleiro");
    	bovinoRacaCombo.getSelectionModel().select(0);
		
    	bovinoRadioMacho.setToggleGroup(radioButtonSexoGroup);
    	bovinoRadioFemea.setToggleGroup(radioButtonSexoGroup);
		bovinoRadioFemea.setSelected(true);
    	
    	TreeItem<String> root = new TreeItem<String>("Banco de dados", getIcon("puzzle-piece-2x"));
    	
    	// TreeItem<String> bovinoTreeItem = new TreeItem<String>(bovino.toString(), getIcon("media-record-2x.png"));
    	
    	Set<Bovino> bovinos = new HashSet<Bovino>();
    	
    	for(Imagem imagem: imagens)
    	{
    		for(Bovino bovino: imagem.getImagemInfo().getBovinos())
    		{	
    			bovinos.add(bovino);
    		}
    	}
    	
    	for(Bovino bovino: bovinos)
		{	
    		String bovinoText = String.format("bov %05d %s", bovino.getBovino_id(), bovino.getNascimento().toString());
    		TreeItem<String> bovinoTreeItem = new TreeItem<String>(bovinoText, getIcon("media-record-2x"));
    		
    		for(ImagemInfo imagemInfo: bovino.getImagens())
    		{
    			String imagemInfoText = String.format("img %05d %s %s", imagemInfo.getImagem_info_id(), imagemInfo.getRegiao().getCaracteristica(), imagemInfo.getEnvio());
    			TreeItem<String> imgInfoTreeItem = new TreeItem<String>(imagemInfoText, getIcon("folder-2x"));
    			
    			for(Imagem imagem: imagens)
    			{
    				if(imagem.getImagemInfo() == imagemInfo)
    				{
    					String icon = "file-2x";
    					if(imagem.getExtencao().toLowerCase().endsWith(".png") || imagem.getExtencao().toLowerCase().endsWith(".jpg") || imagem.getExtencao().toLowerCase().endsWith(".gif") ||
    							imagem.getExtencao().toLowerCase().endsWith(".tiff") || imagem.getExtencao().toLowerCase().endsWith(".jpeg"))
    					{
    						icon = "image-2x";
    					}
    					
    					String imageText = String.format("arq %05d %s", imagem.getImagem_id(), imagem.getExtencao());
    					TreeItem<String> imgTreeItem = new TreeItem<String>(imageText, getIcon(icon));
    					imgInfoTreeItem.getChildren().add(imgTreeItem);
    				}
    			}
    			
    			bovinoTreeItem.getChildren().add(imgInfoTreeItem);
    		}
    		root.getChildren().add(bovinoTreeItem);
		}
    	
    	root.setExpanded(true);
    	mainTreeView.setRoot(root);
    }
    
    private ImageView getIcon(String path)
    {
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
    private Button btnRemoverSelecionado;

    @FXML
    private TableView<PropriedadeItem> propTableView;

    @FXML
    private ComboBox<String> comboBoxSensor;

    @FXML
    private ComboBox<String> comboBoxRegiao;

    @FXML
    void onAdicionar(ActionEvent event) {

    }

    @FXML
    void onComboChangeRegiao(ActionEvent event) {

    }

    @FXML
    void onComboChangeSensor(ActionEvent event) {

    }

    @FXML
    void onEditarInfoBovino(ActionEvent event) {

    }

    @FXML
    void onRemoverSelecionado(ActionEvent event) {

    }

    @FXML
    void onTransferirDados(ActionEvent event) {
    	String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();
    	
    	if(item.startsWith("bov"))
    	{
    		// TODO salvar tudo!
    	} else if(item.startsWith("img"))
    	{
    		ImagemInfo imagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));
    		DirectoryChooser chooser = new DirectoryChooser();
    		chooser.setTitle("Selecione uma pasta");
    		File selectedDirectory = chooser.showDialog(btnTransferirDados.getScene().getWindow());
    		boolean pass = true;
    		for(Imagem imagem: imagemInfo.getImagens())
    		{
    			try {
					imagem.salvarArquivo(selectedDirectory.getAbsolutePath(), imagem.getExtencao());
				} catch (IOException e) {
					pass = false;
					e.printStackTrace();
				}
    		}
    		if(!pass)
    		{
    			MsgPopupControl.showNewPopup("Não foi possível salvar o arquivo!", "Erro");
    		}
    	} else if(item.startsWith("arq"))
    	{	
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
    	
    	String item = mainTreeView.getSelectionModel().getSelectedItem().getValue();
    	
    	if(item.startsWith("bov"))
    	{
    		Bovino lastBovinoEdit = BovinoCRUD.get(Integer.parseInt(item.split(" ")[1]));
    		txtBovinoCod.setText(lastBovinoEdit.getBovino_id() + "");
    		if(lastBovinoEdit.getSexo())
    		{
    			bovinoRadioMacho.setSelected(true);
    		} else
    		{
    			bovinoRadioFemea.setSelected(true);
    		}
    		if(lastBovinoEdit.getRaca().equalsIgnoreCase("Nelore"))
    		{
    			bovinoRacaCombo.getSelectionModel().select(0);
    		} else if(lastBovinoEdit.getRaca().equalsIgnoreCase("Pantaneiro"))
    		{
    			bovinoRacaCombo.getSelectionModel().select(1);
    		} else if(lastBovinoEdit.getRaca().equalsIgnoreCase("Curraleiro"))
    		{
    			bovinoRacaCombo.getSelectionModel().select(2);
    		}
    		bovinoDataNascimento.setValue(lastBovinoEdit.getNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    		lastBovinoEdit.getNascimento();
    	}
    	
    	else if(item.startsWith("img"))
    	{
    		lastImagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));
    		
    		propTableView.getItems().clear();
    		for(Propriedade prop: lastImagemInfo.getPropriedades())
    		{
    			PropriedadeItem propItem = new PropriedadeItem(prop.getCampo().getNome(), prop.getValora().toString());
    			propTableView.getItems().add(propItem);
    		}
    		
    		int select = 0;
    		for(Regiao regiao: lastQueryRegiao)
    		{
    			if(regiao.getRegiao_id().equals(lastImagemInfo.getRegiao().getRegiao_id()))
    				break;
    			select ++;
    		}
    		comboBoxRegiao.getSelectionModel().select(select);
    		
    		
    		 select = 0;
     		for(Sensor sensor: lastQuerySensor)
     		{
     			if(sensor.getSensor_id().equals(lastImagemInfo.getSensor().getSensor_id()))
     				break;
     			select ++;
     		}
     		comboBoxSensor.getSelectionModel().select(select);
    		
    	} else if(item.startsWith("arq"))
    	{
    		item = mainTreeView.getSelectionModel().getSelectedItem().getParent().getValue();
    		
    		lastImagemInfo = ImagemInfoCRUD.get(Integer.parseInt(item.split(" ")[1]));
    		
    		propTableView.getItems().clear();
    		for(Propriedade prop: lastImagemInfo.getPropriedades())
    		{
    			PropriedadeItem propItem = new PropriedadeItem(prop.getCampo().getNome(), prop.getValora().toString());
    			propTableView.getItems().add(propItem);
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	private void updateComboBoxs()
    {
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
    
    private static void createPopup(List<Imagem> imagens, int w, int h)
    {
    	QueryViewControl.imagens = imagens;
    	
    	try {
    		Stage msgStage = new Stage();
    		msgStage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
	    	Pane pane = FXMLLoader.load(FxmlResource.getFxmlPath("QueryResult"));
			Scene scene = new Scene(pane, w, h);
			scene.getStylesheets().add(FxmlResource.getCssPath("app"));
			msgStage.setTitle("Resultado da consulta");
			msgStage.setScene(scene);
			msgStage.show();
    	} catch(Exception e)
    	{
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
    
    public static void showNewPopup(List<Imagem> imagens) {
    	createPopup(imagens, 1100, 600);
    }
    
    public static void showNewPopup(List<Imagem> imagens, int width, int height) {
    	createPopup(imagens, width, height);
    }
}
