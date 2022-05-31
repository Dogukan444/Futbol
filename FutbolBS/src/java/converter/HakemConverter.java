package converter;

import dao.HakemDAO;
import entity.Hakem;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "hakemConverter")
public class HakemConverter implements Converter {

    private HakemDAO hdao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return this.getHdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Hakem h = (Hakem) o;
        return String.valueOf(h.getHakem_id());
    }

    public HakemDAO getHdao() {
        if (this.hdao == null) {
            this.hdao = HakemDAO.gethakemDao();
        }
        return hdao;
    }

}
