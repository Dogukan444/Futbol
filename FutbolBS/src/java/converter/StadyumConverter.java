package converter;

import dao.StadyumDAO;
import entity.Stadyum;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "stadyumConverter")
public class StadyumConverter implements Converter {

    private StadyumDAO sdao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return this.getSdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Stadyum s = (Stadyum) o;
        return String.valueOf(s.getStadyum_id());
    }

    public StadyumDAO getSdao() {
        if (this.sdao == null) {
            this.sdao = StadyumDAO.getstadyumDao();
        }
        return sdao;
    }

}
