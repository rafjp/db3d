package ifb.db3d.der6.object;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Regiao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer regiao_id;
	String caracteristica;
	String descricao;

	public Regiao() {
		super();
	}

	public Regiao(String caracteristica, String descricao) {
		super();
		this.caracteristica = caracteristica;
		this.descricao = descricao;
	}

	public Integer getRegiao_id() {
		return regiao_id;
	}

	public void setRegiao_id(Integer regiao_id) {
		this.regiao_id = regiao_id;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Regiao [regiao_id=" + regiao_id + ", caracteristica=" + caracteristica + ", descricao=" + descricao
				+ "]";
	}
}
