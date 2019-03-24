package ifb.db3d.der6.object;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Campo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer campo_id;
	
	String nome;
	
	
	public Campo() {
		
	}
	
	public Campo(Integer campo_id, String nome) {
		super();
		this.campo_id = campo_id;
		this.nome = nome;
	}

	public Integer getCampo_id() {
		return campo_id;
	}

	public void setCampo_id(Integer campo_id) {
		this.campo_id = campo_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Campo [campo_id=" + campo_id + ", nome=" + nome + "]";
	}
}
