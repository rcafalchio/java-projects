package br.com.leilaopecuario.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import br.com.leilaopecuario.util.LeilaoWebHelper;

import com.leilaopecuario.constantes.Constantes;
import com.leilaopecuario.constantes.ConstantesMensagens;
import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.negocio.GerenciadorLanceLocal;
import com.leilaopecuario.vo.LanceVO;
import com.leilaopecuario.vo.LeilaoVO;

@ManagedBean
@ViewScoped
public class ControladorLanceBean {

	private final static Logger LOGGER = Logger.getLogger(ControladorLanceBean.class);

	@EJB
	private GerenciadorLanceLocal gerenciadorLance;

	@ManagedProperty(value = "#{controladorLoginBean}")
	private ControladorLoginBean controladorLoginBean;

	// Leilão selecionado pelo usuário, deve permanecer na sessão.
	private LeilaoVO leilaoVO = null;
	private LanceVO lanceVO = null;
	private List<String> listaFotos;

	@PostConstruct
	public void init() {

		try {
			if (this.leilaoVO == null) {
				//Recupera o leilão da sessão
				this.leilaoVO = controladorLoginBean.getLeilaoVO();
			}
			final FacesContext aFacesContext = FacesContext.getCurrentInstance();
			final ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
			final String caminhoFotos = LeilaoWebHelper.recuperaWorkSpace(context.getRealPath("/"))
					+ Constantes.DIRETORIO_FOTOS_LEILAO;
			final String caminhoGravarFotos = context.getRealPath("/")
					+ Constantes.DIRETORIO_FOTOS_LEILAO_BARRA_INVERTIDA;
			LeilaoWebHelper.gravarFotos(caminhoFotos, caminhoGravarFotos);
		} catch (Exception e) {
			LOGGER.error("Problema ao iniciar os elementos!", e);
		}

	}

	public String efetuaLance(LeilaoVO leilaoVO) {

		String retorno = "";

		if (leilaoVO != null) {
			// Grava o Leilão na sessão
			controladorLoginBean.setLeilaoVO(leilaoVO);
			retorno = "paginalance";
		}

		return retorno;
	}

	public String enviarLance() {

		String retorno = "";

		try {
			lanceVO.setLeilaoVO(leilaoVO);
			lanceVO.setUsuarioVO(controladorLoginBean.getUsuario());
			String fraseErro = gerenciadorLance.efetuarLance(lanceVO);
			if (fraseErro != null) {
				FacesContext.getCurrentInstance().addMessage("msg_cadastro",
						new FacesMessage(FacesMessage.SEVERITY_WARN, fraseErro, ""));
			} else {
				FacesContext.getCurrentInstance().addMessage("msg_cadastro",
						new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantesMensagens.INFO_LANCE_SUCESSO, ""));
				retorno = "index";
			}

		} catch (LeilaoException e) {
			FacesContext.getCurrentInstance().addMessage("msg_cadastro",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ConstantesMensagens.FALHA_GENERICA, ""));

			LOGGER.error("Erro no método enviarLance ", e);
		}

		return retorno;
	}

	private List<String> carregaListaFotos() {

		final List<String> lista = new ArrayList<String>();

		final FacesContext aFacesContext = FacesContext.getCurrentInstance();
		final ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
		final String caminhoFotos = context.getRealPath("/") + Constantes.DIRETORIO_FOTOS_LEILAO_BARRA_INVERTIDA + "//"
				+ leilaoVO.getCodigo();

		final File arquivos = new File(caminhoFotos);
		File[] arquivosArray = arquivos.listFiles();
		List<File> listaFile = Arrays.asList(arquivosArray);
		for (File foto : listaFile) {
			if (!Constantes.SEM_IMAGEM.equals(foto.getName())
					|| (Constantes.SEM_IMAGEM.equals(foto.getName()) && Constantes.SEM_IMAGEM.equals(leilaoVO
							.getCaminhosFotos().getFotoPrincipal()))) {
				lista.add(foto.getName());
			}
		}
		return lista;
	}

	public LeilaoVO getLeilaoVO() {
		return leilaoVO;
	}

	public void setLeilaoVO(LeilaoVO leilaoVO) {
		this.leilaoVO = leilaoVO;
	}

	public LanceVO getLanceVO() {
		if (lanceVO == null) {
			lanceVO = new LanceVO();
		}
		return lanceVO;
	}

	public void setLanceVO(LanceVO lanceVO) {
		this.lanceVO = lanceVO;
	}

	public List<String> getListaFotos() {
		if (listaFotos == null) {
			this.listaFotos = carregaListaFotos();
		}
		return listaFotos;
	}

	public void setListaFotos(List<String> listaFotos) {
		this.listaFotos = listaFotos;
	}

	public ControladorLoginBean getControladorLoginBean() {
		return controladorLoginBean;
	}

	public void setControladorLoginBean(ControladorLoginBean controladorLoginBean) {
		this.controladorLoginBean = controladorLoginBean;
	}

}
