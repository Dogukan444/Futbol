package converter;

import dao.LigDAO;
import entity.Lig;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "ligConverter")
public class LigConverter implements Converter {

    private LigDAO ldao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        return this.getLdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Lig l = (Lig) o;
        return String.valueOf(l.getLig_id());
    }

    public LigDAO getLdao() {
        if (this.ldao == null) {
            this.ldao = LigDAO.getligDao();
        }
        return ldao;
    }

}
