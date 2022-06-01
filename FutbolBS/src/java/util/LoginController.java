package util;

import dao.KullaniciDAO;
import entity.Kullanici;
import entity.Yetki;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class LoginController implements Serializable {

    public LoginController() {
    }

    private Kullanici kullanici;
    private KullaniciDAO kullaniciDao;

    /* YetkiKontrol methodu ile;
        Back_end tarafını seviyelendirdik.Back_end template klasörüne girin ordaki layout'un içindeki butonları rendered ile gizlediğimizi görebilirsiniz
     */
    public boolean yetkiKontrol() {
        Kullanici k = (Kullanici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().getOrDefault("valid_user", null);

        for (Yetki y : k.getKullaniciYetkileri()) {
            if (y.getYetki_turu().equalsIgnoreCase("Admin")) {
                return true;
            }
        }
        return false;
    }

    public String login() {
        Kullanici k = this.getKullaniciDao().kullaniciVarmi(this.kullanici);
        if (k == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Hatalı Kullanıcı adı veya şifre!!"));
            return "/front_end/ortak/login";
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_user", k);
            return "/back_end/admin?faces-redirect=true";
        }
    }

    public Kullanici getKullanici() {
        if (this.kullanici == null) {
            this.kullanici = new Kullanici();
        }
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public KullaniciDAO getKullaniciDao() {
        if (this.kullaniciDao == null) {
            this.kullaniciDao = KullaniciDAO.getKullaniciDao();
        }
        return kullaniciDao;
    }

    public void setKullaniciDao(KullaniciDAO kullaniciDao) {
        this.kullaniciDao = kullaniciDao;
    }

}
