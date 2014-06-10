package br.com.leilaopecuario.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.leilaopecuario.vo.VacinaVO;

@FacesConverter(value = "convertvacina")
public class ConversorVacina implements Converter {

	// @ManagedProperty(value = "#{controladorLeilaoBean}")
	// private ControladorLeilaoBean controladorLeilaoBean;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		// VacinaVO vacinaVO = null;
		// final List<VacinaVO> vacinas =
		// controladorLeilaoBean.getVacinas().getSource();
		// for (VacinaVO vacinaVO2 : vacinas) {
		// vacinaVO = vacinaVO2;
		// }
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String retorno = null;
		if (!(value == null)) {
			VacinaVO vacinaVO = (VacinaVO) value;
			retorno = vacinaVO.getNomeVacina();
		}
		return retorno.toString();
	}
}
