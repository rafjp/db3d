package ifb.db3d.der6.object;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Bovino {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bovino_id;
	private Boolean sexo;
	private String raca;

	@ManyToMany
	@JoinTable(
			name = "bovino_imagem_info",
			joinColumns = @JoinColumn(name = "bovino_id", referencedColumnName = "bovino_id"),
			inverseJoinColumns = @JoinColumn(name = "imagem_info_id", referencedColumnName = "imagem_info_id")
			)
	List<ImagemInfo> imagens;
	
	@Override
	public String toString() {
		return "Bovino [bovino_id=" + bovino_id + ", sexo=" + sexo + ", raca=" + raca
				+ ", nascimento=" + nascimento + "]";
	}

	@Temporal(TemporalType.DATE)
	private Date nascimento;

	public Bovino() {
		super();
	}

	public Bovino(Integer bovino_id, Boolean sexo, String raca, Date nascimento) {
		super();
		this.bovino_id = bovino_id;
		this.sexo = sexo;
		this.raca = raca;
		this.nascimento = nascimento;
	}
	
	public List<ImagemInfo> getImagens() {
		return imagens;
	}

	public void setImagens(List<ImagemInfo> imagens) {
		this.imagens = imagens;
	}

	public Integer getBovino_id() {
		return bovino_id;
	}

	public void setBovino_id(Integer bovino_id) {
		this.bovino_id = bovino_id;
	}

	public Boolean getSexo() {
		return sexo;
	}

	public void setSexo(Boolean sexo) {
		this.sexo = sexo;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
}
