package test;

import dao.YetkiDAO;
import entity.Yetki;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class YetkiTest {

    YetkiDAO yetkiDao = YetkiDAO.getyetkiDao();

    @Test
    public void yetkiTest() {
        Yetki yetki = new Yetki();
        yetki.setYetki_turu("Admin");
        assertTrue(yetkiDao.insert(yetki));
    }
}
