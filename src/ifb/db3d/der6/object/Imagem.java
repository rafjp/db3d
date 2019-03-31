package ifb.db3d.der6.object;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Imagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer imagem_id;
	String extencao;
	byte[] arquivo;
	@ManyToOne
	@JoinColumn(name = "imagem_info_id")
	ImagemInfo imagemInfo;

	public Imagem() {
		super();
	}

	public Imagem(String extencao, ImagemInfo imagemInfo) {
		super();
		this.extencao = extencao;
		this.imagemInfo = imagemInfo;
	}

	public Integer getImagem_id() {
		return imagem_id;
	}

	public void setImagem_id(Integer imagem_id) {
		this.imagem_id = imagem_id;
	}

	public String getExtencao() {
		return extencao;
	}

	public void setExtencao(String extencao) {
		this.extencao = extencao;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public void carregarArquivo(String caminho) throws IOException {
		arquivo = Files.readAllBytes(Paths.get(caminho));
	}

	public void salvarArquivo(String caminho) throws IOException {
		Files.write(Paths.get(caminho), arquivo);
	}

	public void salvarArquivo(String caminho, String extencao) throws IOException {
		Files.write(Paths.get(caminho + File.separator + extencao), arquivo);
	}

	public ImagemInfo getImagemInfo() {
		return imagemInfo;
	}

	public void setImagemInfo(ImagemInfo imagemInfo) {
		this.imagemInfo = imagemInfo;
	}

	@Override
	public String toString() {
		return "Imagem [imagem_id=" + imagem_id + ", extencao=" + extencao + ", imagemInfo=" + imagemInfo + "]";
	}
}
