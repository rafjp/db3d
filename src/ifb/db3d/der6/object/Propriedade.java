package ifb.db3d.der6.object;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Propriedade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer propriedade_id;

	Float valora;
	Float valorb;

	@ManyToOne
	@JoinColumn(name = "campo_id")
	Campo campo;

	@ManyToOne
	@JoinColumn(name = "imagem_info_id")
	ImagemInfo imagem;

	public Propriedade() {

	}

	public Propriedade(Integer propriedade_id, Float valora, Campo campo, ImagemInfo imagem) {
		super();
		this.propriedade_id = propriedade_id;
		this.valora = valora;
		this.campo = campo;
		this.imagem = imagem;
	}

	public Propriedade(Integer propriedade_id, Float valora, Float valorb, Campo campo, ImagemInfo imagem) {
		super();
		this.propriedade_id = propriedade_id;
		this.valora = valora;
		this.valorb = valorb;
		this.campo = campo;
		this.imagem = imagem;
	}

	public Integer getPropriedade_id() {
		return propriedade_id;
	}

	public void setPropriedade_id(Integer propriedade_id) {
		this.propriedade_id = propriedade_id;
	}

	public Float getValora() {
		return valora;
	}

	public void setValora(Float valora) {
		this.valora = valora;
	}

	public Float getValorb() {
		return valorb;
	}

	public void setValorb(Float valorb) {
		this.valorb = valorb;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public ImagemInfo getImagem() {
		return imagem;
	}

	public void setImagem(ImagemInfo imagem) {
		this.imagem = imagem;
	}

	@Override
	public String toString() {
		return "Propriedade [propriedade_id=" + propriedade_id + ", valora=" + valora + ", valorb=" + valorb
				+ ", campo=" + campo + ", imagem=" + imagem + "]";
	}
}
