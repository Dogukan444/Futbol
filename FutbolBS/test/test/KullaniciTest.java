package test;

import dao.KullaniciDAO;
import entity.Kullanici;
import entity.Yetki;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class KullaniciTest {

    KullaniciDAO kullaniciDao = KullaniciDAO.getKullaniciDao();

    @Test
    public void test() {
        Kullanici kullanici = new Kullanici();
        kullanici.setLogin_name("admin");
        kullanici.setSifre("admin");
        kullanici.setAd_soyad("Seher Urtekin");
        kullanici.setCinsiyet("Kadın");
        kullanici.setYas(21);
        kullanici.setCep_telefonu("05510167896");
        kullanici.setEmail("urtekinseher@gmail.com");
        List<Yetki> yetkiler = new ArrayList<>();
        yetkiler.add(new Yetki(4, "Admin"));
        kullanici.setKullaniciYetkileri(yetkiler);
        assertTrue(kullaniciDao.insert(kullanici));
    }

}
