package converter;

import dao.KullaniciDAO;
import entity.Kullanici;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "kullaniciConverter")
public class KullaniciConverter implements Converter {

    KullaniciDAO kdao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return this.getKdao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Kullanici k = (Kullanici) o;
        return String.valueOf(k.getKullanici_id());
    }

    public KullaniciDAO getKdao() {
        if (this.kdao == null) {
            this.kdao = KullaniciDAO.getKullaniciDao();
        }
        return kdao;
    }

    public void setKdao(KullaniciDAO kdao) {
        this.kdao = kdao;
    }

}
