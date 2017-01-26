package br.com.leilaopecuario.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.leilaopecuario.negocio.GerenciadorLanceLocal;
import com.leilaopecuario.vo.DadosLanceUsuarioVO;
import com.leilaopecuario.vo.LeilaoVO;
import com.leilaopecuario.vo.UsuarioVO;

@ManagedBean
public class ControladorMeusLancesBean {

	private final static Logger LOGGER = Logger.getLogger(ControladorMeusLancesBean.class);

	private List<DadosLanceUsuarioVO> meusLances = null;
	private LeilaoVO leilaoVO = null;
	private DadosLanceUsuarioVO dadosLanceUsuarioVO = null;

	@EJB
	private GerenciadorLanceLocal gerenciadorLance;

	@PostConstruct
	public void init() {

		try {
			HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			final UsuarioVO usuarioVO = (UsuarioVO) httpSession.getAttribute("usuario");
			if (usuarioVO != null) {
				meusLances = gerenciadorLance.recuperaDadosLancesPorUsuario(usuarioVO);
			}
		} catch (Exception e) {
			LOGGER.error("Problema ao Recuperar Leilões do usuário", e);
		}

	}

	public List<DadosLanceUsuarioVO> getMeusLances() {
		return meusLances;
	}

	public void setMeusLances(List<DadosLanceUsuarioVO> meusLances) {
		this.meusLances = meusLances;
	}

	public LeilaoVO getLeilaoVO() {
		return leilaoVO;
	}

	public void setLeilaoVO(LeilaoVO leilaoVO) {
		this.leilaoVO = leilaoVO;
	}

	public DadosLanceUsuarioVO getDadosLanceUsuarioVO() {
		return dadosLanceUsuarioVO;
	}

	public void setDadosLanceUsuarioVO(DadosLanceUsuarioVO dadosLanceUsuarioVO) {
		this.dadosLanceUsuarioVO = dadosLanceUsuarioVO;
	}
}
