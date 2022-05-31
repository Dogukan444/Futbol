package converter;

import dao.YetkiDAO;
import entity.Yetki;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "yetkiConverter")
public class YetkiConverter implements Converter {

    private YetkiDAO ydao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return this.getYdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Yetki y = (Yetki) o;
        return String.valueOf(y.getYetki_id());
    }

    public YetkiDAO getYdao() {
        if (this.ydao == null) {
            this.ydao = YetkiDAO.getyetkiDao();
        }
        return ydao;
    }

    public void setYdao(YetkiDAO ydao) {
        this.ydao = ydao;
    }

}
