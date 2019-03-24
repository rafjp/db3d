package ifb.db3d.der6.javafx;

import java.io.IOException;

import ifb.db3d.der6.javafx.control.ImagemControl;
import ifb.db3d.der6.javafx.control.QueryControl;
import ifb.db3d.der6.persistence.ConnectionFactory;
import ifb.db3d.der6.util.FxmlResource;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainFx extends Application{
	
	@FXML
    private Tab bovinosTab;
	
	@FXML
    private Tab sqlTab;
	
	@FXML
    private Tab imgTab;

    @FXML
    private Tab propTab;

    @FXML
    private Tab sensoresTab;

    @FXML
    private Tab animalTab;
    
    @FXML
    private Tab consultaTab;
    
    FXMLLoader imageLoader;
    FXMLLoader queryLoader;
    ImagemControl imagemControl;
    QueryControl queryControl;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
		Pane pane = FXMLLoader.load(FxmlResource.getFxmlPath("MainWindows"));
		Scene scene = new Scene(pane, 1100, 600);
		scene.getStylesheets().add(FxmlResource.getCssPath("app"));
		primaryStage.setTitle("Der6");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@FXML
	public void initialize()
	{
		ConnectionFactory.open();
		AnchorPane pane;
		try {
			pane = FXMLLoader.load(FxmlResource.getFxmlPath("BovinoForm"));
			bovinosTab.setContent(pane);
			
			pane = FXMLLoader.load(FxmlResource.getFxmlPath("SqlView"));
			sqlTab.setContent(pane);
			
			pane = FXMLLoader.load(FxmlResource.getFxmlPath("Regioes"));
			animalTab.setContent(pane);
			
			pane = FXMLLoader.load(FxmlResource.getFxmlPath("Sensores"));
			sensoresTab.setContent(pane);
			
			pane = FXMLLoader.load(FxmlResource.getFxmlPath("Propriedades"));
			propTab.setContent(pane);
			
			imageLoader = new FXMLLoader(FxmlResource.getFxmlPath("ImagemForm"));
			pane = imageLoader.load();
			imagemControl = (ImagemControl) imageLoader.getController();
			imgTab.setContent(pane);
			imgTab.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
				public void handle(Event event) { 
					imagemControl.updateComboBoxInfo();
				}
			});
			queryLoader = new FXMLLoader(FxmlResource.getFxmlPath("BovinoQueryView"));
			pane = queryLoader.load();
			queryControl = (QueryControl) queryLoader.getController();
			consultaTab.setContent(pane);
			consultaTab.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
				public void handle(Event event) { 
					queryControl.updateComboBoxInfo();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@FXML
	public void stop() {
		ConnectionFactory.close(); // TODO
	}
}
