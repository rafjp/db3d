package ifb.db3d.der6.object;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Sensor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer sensor_id;
	String nome;

	public Sensor() {
		super();
	}

	public Sensor(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Sensor [sensor_id=" + sensor_id + ", nome=" + nome + ", descricao=" + descricao + "]";
	}

	public Integer getSensor_id() {
		return sensor_id;
	}

	public void setSensor_id(Integer sensor_id) {
		this.sensor_id = sensor_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	String descricao;
}
