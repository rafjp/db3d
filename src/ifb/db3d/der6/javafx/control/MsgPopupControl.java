package ifb.db3d.der6.javafx.control;

import ifb.db3d.der6.util.FxmlResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MsgPopupControl {
	
	@FXML
	private AnchorPane mainPane;
	
	@FXML
    private TextArea msgLabel;

    @FXML
    private Button closeButton;
    
    private static String msg;

    @FXML
    void onClose(ActionEvent event) {
    	Stage stage = (Stage) closeButton.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void initialize()
    {
    	msgLabel.setText(msg);
    }
    
    private static void createPopup(String msg, String title, int w, int h)
    {
    	try {
    		MsgPopupControl.msg = msg;
    		Stage msgStage = new Stage();
    		msgStage.getIcons().add(new Image(FxmlResource.getIconPath("document-4x")));
	    	Pane pane = FXMLLoader.load(FxmlResource.getFxmlPath("MsgPopup"));
			Scene scene = new Scene(pane, w, h);
			scene.getStylesheets().add(FxmlResource.getCssPath("app"));
			msgStage.setTitle(title);
			msgStage.setScene(scene);
			msgStage.show();
    	} catch(Exception e)
    	{
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
    
    public static void showNewPopup(String msg, String title) {
    	createPopup(msg, title, 400, 165);
    }
    
    public static void showNewPopup(String msg, String title, int width, int height) {
    	createPopup(msg, title, width, height);
    }
}
