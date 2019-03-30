package ifb.db3d.der6.object;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="imagem_info")
public class ImagemInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer imagem_info_id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "envio")
	Date envio;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "regiao_id")
	Regiao regiao;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "sensor_id")
	Sensor sensor;
	
	@ManyToMany
	@JoinTable(
			name = "bovino_imagem_info",
			joinColumns = @JoinColumn(name = "imagem_info_id", referencedColumnName = "imagem_info_id"),
			inverseJoinColumns = @JoinColumn(name = "bovino_id", referencedColumnName = "bovino_id")
			)
	List<Bovino> bovinos;
	
	@OneToMany
	@JoinColumn(name = "imagem_info_id")
	List<Propriedade> propriedades;
	
	@OneToMany
	@JoinColumn(name = "imagem_info_id")
	List<Imagem> imagens;

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public List<Propriedade> getPropriedades() {
		return propriedades;
	}

	public void setPropriedades(List<Propriedade> propriedades) {
		this.propriedades = propriedades;
	}

	public List<Bovino> getBovinos() {
		return bovinos;
	}

	public void setBovinos(List<Bovino> bovinos) {
		this.bovinos = bovinos;
	}

	public ImagemInfo() {
		super();
	}
	
	public ImagemInfo(Date envio, Regiao regiao, Sensor sensor) {
		super();
		this.envio = envio;
		this.regiao = regiao;
		this.sensor = sensor;
	}

	public Integer getImagem_info_id() {
		return imagem_info_id;
	}

	public void setImagem_info_id(Integer imagem_info_id) {
		this.imagem_info_id = imagem_info_id;
	}

	public Date getEnvio() {
		return envio;
	}

	public void setEnvio(Date envio) {
		this.envio = envio;
	}

	public Regiao getRegiao() {
		return regiao;
	}
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	@Override
	public String toString() {
		return "ImagemInfo [imagem_info_id=" + imagem_info_id + ", envio=" + envio + ", regiao=" + regiao + ", sensor="
				+ sensor + "]";
	}
	
}
