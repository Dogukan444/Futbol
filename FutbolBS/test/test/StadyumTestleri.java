package test;

import dao.StadyumDAO;
import entity.Stadyum;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class StadyumTestleri {

    private StadyumDAO stadyumDao = StadyumDAO.getstadyumDao();

    @Test
    public void StadyumTest() {
        Stadyum stadyum = new Stadyum();
        stadyum.setSehir("Malatya");
        stadyum.setStadyum_adi("Yeni Malatya Stadyumu");
        stadyum.setKapasite(27044);
        stadyum.setYapilis_yili(2017);
        assertTrue(stadyumDao.insert(stadyum));
        List<Stadyum> stadyumlar = stadyumDao.findAll("", 1, 100);
        assertTrue(!stadyumlar.isEmpty());
        stadyum.setKapasite(30000);
        stadyum.setStadyum_id(stadyumlar.get(stadyumlar.size() - 1).getStadyum_id());
        assertTrue(stadyumDao.update(stadyum));
        assertTrue(stadyumDao.delete(stadyumlar.get(stadyumlar.size() - 1)));
    }
}
