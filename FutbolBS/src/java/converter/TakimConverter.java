package converter;

import dao.TakimDAO;
import entity.Takim;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "takimConverter")
public class TakimConverter implements Converter {

    private TakimDAO tdao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return this.getTdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Takim t = (Takim) o;
        return String.valueOf(t.getTakim_id());
    }

    public TakimDAO getTdao() {
        if (this.tdao == null) {
            this.tdao = TakimDAO.gettakimDao();
        }
        return tdao;
    }

}
