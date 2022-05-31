package converter;

import dao.UlkeDAO;
import entity.Ulke;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "ulkeConverter")
public class UlkeConverter implements Converter {

    private UlkeDAO udao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return this.getUdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Ulke u = (Ulke) o;
        return String.valueOf(u.getUlke_id());
    }

    public UlkeDAO getUdao() {
        if (this.udao == null) {
            this.udao = UlkeDAO.getulkeDao();
        }
        return udao;
    }

}
