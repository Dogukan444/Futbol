package converter;

import dao.Teknik_direktorDAO;
import entity.Teknik_direktor;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "teknikDirektorConverter")
public class Teknik_direktorConverter implements Converter {

    private Teknik_direktorDAO tdao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return this.getTdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Teknik_direktor t = (Teknik_direktor) o;
        return String.valueOf(t.getTeknik_direktor_id());
    }

    public Teknik_direktorDAO getTdao() {
        if (this.tdao == null) {
            this.tdao = Teknik_direktorDAO.getteknik_direktorDao();
        }

        return tdao;
    }

}
