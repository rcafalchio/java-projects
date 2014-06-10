package br.com.leilaopecuario.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.leilaopecuario.negocio.GerenciadorLeilaoLocal;
import com.leilaopecuario.vo.LeilaoVO;
import com.leilaopecuario.vo.UsuarioVO;

@ManagedBean
public class ControladorMeusLeiloesBean {

	private final static Logger LOGGER = Logger.getLogger(ControladorMeusLeiloesBean.class);
	
	@EJB
	private GerenciadorLeilaoLocal gerenciadorLeilao;
	
	private List<LeilaoVO> meusLeiloes = null;
	private LeilaoVO leilaoVO = null;
	
	@PostConstruct
	public void init() {

		try {
			HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			final UsuarioVO usuarioVO = (UsuarioVO) httpSession.getAttribute("usuario");
			if (usuarioVO != null) {
				meusLeiloes = gerenciadorLeilao.recuperaLeiloesPorUsuario(usuarioVO);
			}
		} catch (Exception e) {
			LOGGER.error("Problema ao Recuperar Leilões do usuário", e);
		}

	}
	
	public List<LeilaoVO> getMeusLeiloes() {
		return meusLeiloes;
	}
	public void setMeusLeiloes(List<LeilaoVO> meusLeiloes) {
		this.meusLeiloes = meusLeiloes;
	}
	public LeilaoVO getLeilaoVO() {
		return leilaoVO;
	}
	public void setLeilaoVO(LeilaoVO leilaoVO) {
		this.leilaoVO = leilaoVO;
	}
	
}
