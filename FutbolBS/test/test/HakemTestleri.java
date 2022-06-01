package test;

import dao.HakemDAO;
import entity.Hakem;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class HakemTestleri {

    private HakemDAO hakemDao = HakemDAO.gethakemDao();

    @Test
    public void HakemTest() {
        Hakem hakem = new Hakem();
        hakem.setIsim("Cuneyt Çakır");
        hakem.setFifa_kokarti("Var");
        hakem.setOrtalama_puan(8);
        hakem.setYas(44);
        assertTrue(hakemDao.insert(hakem));
        List<Hakem> hakemler = hakemDao.findAll("", 1, 100);
        assertTrue(!hakemler.isEmpty());
        hakem.setOrtalama_puan(9);
        hakem.setHakem_id(hakemler.get(hakemler.size()-1).getHakem_id());
        assertTrue(hakemDao.update(hakem));
        assertTrue(hakemDao.delete(hakemler.get(hakemler.size()-1)));
        
    }
}
