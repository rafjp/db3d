package ifb.db3d.der6.javafx.itens;

public class PropriedadeItem {

	private String campo;
	private String propriedade;
	public PropriedadeItem(String campo, String propriedade) {
		super();
		this.campo = campo;
		this.propriedade = propriedade;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getPropriedade() {
		return propriedade;
	}
	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}
}
