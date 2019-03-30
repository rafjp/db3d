package ifb.db3d.der6.javafx.control;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import ifb.db3d.der6.object.Bovino;
import ifb.db3d.der6.persistence.BovinoCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class BovinosControl {
	@FXML
	private TextField codigoEntry;

	@FXML
	private RadioButton machoCheckButton;

	@FXML
	private RadioButton femeaCheckButton;

	@FXML
	private ComboBox<String> racaComboBox;
	
    @FXML
    private DatePicker dataDeNascimento;

	@FXML
	private Button bovinoRegistrar;
	
	ToggleGroup radioButtonSexoGroup;
	
	public BovinosControl() {
		radioButtonSexoGroup = new ToggleGroup();
	}

	@FXML
	void registrarBovino(ActionEvent event) {
		try {
			int bovinoCod = Integer.parseInt(codigoEntry.getText());
			boolean sexo = femeaCheckButton.isSelected();
			String raca = racaComboBox.getSelectionModel().getSelectedItem();
			LocalDate dataNascimento = dataDeNascimento.getValue();
			Bovino bovino = new Bovino(bovinoCod, sexo, raca, Date.from(dataDeNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			BovinoCRUD.create(bovino);
			System.out.println("Novo registro [bovinoCod: " + bovinoCod + ", sexo: " + sexo + ", raca: " + raca +", dataNascimento: " + dataNascimento);
			MsgPopupControl.showNewPopup("Novo registro [bovinoCod: " + bovinoCod + ", sexo: " + sexo + ", raca: " + raca +", dataNascimento: " + dataNascimento, "Novo registro");
		} catch(Exception e)
		{
			MsgPopupControl.showExceptionAlert("Erro ao registrar bovino", "Erro", "Erro de registro", e);
		}
		
	}
	
	@FXML
	public void initialize()
	{
		racaComboBox.getItems().addAll(
				"Nelore",
				"Pantaneiro",
				"Curraleiro");
		racaComboBox.getSelectionModel().select(0);
		
		machoCheckButton.setToggleGroup(radioButtonSexoGroup);
		femeaCheckButton.setToggleGroup(radioButtonSexoGroup);
		femeaCheckButton.setSelected(true);
		
		dataDeNascimento.setValue(LocalDate.now());
	}

}
