package ifb.db3d.der6.object;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bovino_imagem_info")
public class BovinoImagemInfo {

	@Id
	Integer bovino_imagem_info_id;
	Integer bovino_id;
	Integer imagem_info_id;

	public BovinoImagemInfo() {
		super();
	}

	public BovinoImagemInfo(Integer bovino_imagem_info_id, Integer bovino_id, Integer imagem_info_id) {
		super();
		this.bovino_imagem_info_id = bovino_imagem_info_id;
		this.bovino_id = bovino_id;
		this.imagem_info_id = imagem_info_id;
	}

	public Integer getBovino_imagem_info_id() {
		return bovino_imagem_info_id;
	}

	public void setBovino_imagem_info_id(Integer bovino_imagem_info_id) {
		this.bovino_imagem_info_id = bovino_imagem_info_id;
	}

	public Integer getBovino_id() {
		return bovino_id;
	}

	public void setBovino_id(Integer bovino_id) {
		this.bovino_id = bovino_id;
	}

	public Integer getImagem_info_id() {
		return imagem_info_id;
	}

	public void setImagem_info_id(Integer imagem_info_id) {
		this.imagem_info_id = imagem_info_id;
	}

	@Override
	public String toString() {
		return "BovinoImagemInfo [bovino_imagem_info_id=" + bovino_imagem_info_id + ", bovino_id=" + bovino_id
				+ ", imagem_info_id=" + imagem_info_id + "]";
	}
}
