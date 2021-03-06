package dao;

import entity.Kullanici;
import entity.Yetki;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KullaniciDAO extends SuperDAO implements DaoArayuz<Kullanici>{

    private static KullaniciDAO kullaniciDao = null;

    private KullaniciDAO() {
    }

    public static KullaniciDAO getKullaniciDao() {
        if (kullaniciDao == null) {
            kullaniciDao = new KullaniciDAO();
        }
        return kullaniciDao;
    }

    PreparedStatement pst = null;
    ResultSet rs = null;

    YetkiDAO ydao;
    
    @Override
    public boolean insert(Kullanici k) {

        try {
            pst = this.getConnection().prepareStatement("insert into kullanici (login_name,sifre,ad_soyad,cinsiyet,yas,cep_telefonu,email) "
                    + "values(?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, k.getLogin_name());
            pst.setString(2, k.getSifre());
            pst.setString(3, k.getAd_soyad());
            pst.setString(4, k.getCinsiyet());
            pst.setInt(5, k.getYas());
            pst.setString(6, k.getCep_telefonu());
            pst.setString(7, k.getEmail());

            pst.executeUpdate();

            int k_id = 0;
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                k_id = gk.getInt(1);
            }
            for (Yetki y : k.getKullaniciYetkileri()) {
                pst = this.getConnection().prepareStatement("insert into kullanici_yetki (kullanici_id,yetki_id) values (?,?)");
                pst.setInt(1, k_id);
                pst.setInt(2, y.getYetki_id());
                if (pst.executeUpdate() == 0) {
                    return false;
                }

            }

        } catch (SQLException ex) {
            System.out.println(" KullaniciDAO HATA(Create): " + ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Kullanici k) {  //kullan??c??_yetki tablosunda kullan??c?? foreign key'i cascade !

        try {
            pst = this.getConnection().prepareStatement("delete from kullanici where kullanici_id=?");
            pst.setInt(1, k.getKullanici_id());
            return pst.executeUpdate() != 0;
            //Kullan??c??y?? silince ili??kili kullan??c??_yetki tablosundan yetkileri silmemize gerek yok otomatik siliniyor.

        } catch (SQLException ex) {
            System.out.println(" KullaniciDAO HATA(Delete): " + ex.getMessage());
        }
        return false;
    }

    @Override
    public int count() {
        int count = 0;
        try {
            pst = this.getConnection().prepareStatement("select count(kullanici_id) as kullanici_count from kullanici ");
            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt("kullanici_count");

        } catch (SQLException ex) {
            System.out.println("KullaniciDAO HATA(Count):" + ex.getMessage());
        }

        return count;
    }

    @Override
    public List<Kullanici> findAll(String deger, int page, int pageSize) {
        List<Kullanici> klist = new ArrayList();
        int start = (page - 1) * pageSize;

        try {
            pst = this.getConnection().prepareStatement("select * from kullanici where login_name ilike ? or ad_soyad ilike ? order by kullanici_id asc limit ? offset ? ");
            pst.setString(1, "%" + deger + "%");
            pst.setString(2, "%" + deger + "%");
            pst.setInt(3, pageSize);
            pst.setInt(4, start);
            rs = pst.executeQuery();
            while (rs.next()) {
                Kullanici temp = new Kullanici(rs.getInt("kullanici_id"), rs.getString("login_name"), rs.getString("sifre"),
                        rs.getString("ad_soyad"), rs.getString("cinsiyet"), rs.getInt("yas"), rs.getString("cep_telefonu"), rs.getString("email"));
                temp.setKullaniciYetkileri(this.getYdao().getKullaniciYetkileri(temp.getKullanici_id()));
                klist.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("KullaniciDAO HATA(FindAll):" + ex.getMessage());
        }
        return klist;
    }

    @Override
    public Kullanici find(int id) {
        Kullanici k = null;
        try {
            pst = this.getConnection().prepareStatement("select * from kullanici where kullanici_id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            rs.next();
            k = new Kullanici();

            k.setKullanici_id(rs.getInt("kullanici_id"));
            k.setLogin_name(rs.getString("login_name"));
            k.setSifre(rs.getString("sifre"));
            k.setAd_soyad(rs.getString("ad_soyad"));
            k.setCinsiyet(rs.getString("cinsiyet"));
            k.setYas(rs.getInt("yas"));
            k.setCep_telefonu(rs.getString("cep_telefonu"));
            k.setEmail(rs.getString("email"));
            //k.setKullaniciYetkileri(this.getYdao().getKullaniciYetkileri(k.getKullanici_id()));  //!
        } catch (SQLException ex) {
            System.out.println("KullaniciDAO HATA(F??ND) :" + ex.getMessage());
        }
        return k;
    }

    @Override
    public boolean update(Kullanici k) {

        try {
            pst = this.getConnection().prepareStatement("update kullanici set login_name=?,sifre=?,ad_soyad=?,cinsiyet=?,"
                    + "yas=?,cep_telefonu=?,email=? where kullanici_id=?");
            pst.setString(1, k.getLogin_name());
            pst.setString(2, k.getSifre());
            pst.setString(3, k.getAd_soyad());
            pst.setString(4, k.getCinsiyet());
            pst.setInt(5, k.getYas());
            pst.setString(6, k.getCep_telefonu());
            pst.setString(7, k.getEmail());
            pst.setInt(8, k.getKullanici_id());

            pst.executeUpdate();

            pst = this.getConnection().prepareStatement("delete from kullanici_yetki where kullanici_id=?");
            pst.setInt(1, k.getKullanici_id());
            pst.executeUpdate();

            for (Yetki y : k.getKullaniciYetkileri()) {
                pst = this.getConnection().prepareStatement("insert into kullanici_yetki(kullanici_id,yetki_id) values (?,?)");
                pst.setInt(1, k.getKullanici_id());
                pst.setInt(2, y.getYetki_id());
                if (pst.executeUpdate() == 0) {
                    return false;
                }
            }

        } catch (SQLException ex) {
            System.out.println("KullaniciDAO HATA(Update): " + ex.getMessage());
        }
        return true;
    }

    public Kullanici kullaniciVarmi(Kullanici k) {
        Kullanici kullanici = null;

        try {
            pst = this.getConnection().prepareStatement("select * from kullanici where login_name=? and sifre=? ");
            pst.setString(1, k.getLogin_name());
            pst.setString(2, k.getSifre());
            rs = pst.executeQuery();

            if (rs.next()) {

                kullanici = new Kullanici();
                kullanici.setKullanici_id(rs.getInt("kullanici_id"));
                kullanici.setLogin_name(rs.getString("login_name"));
                kullanici.setSifre(rs.getString("sifre"));
                kullanici.setAd_soyad(rs.getString("ad_soyad"));
                kullanici.setCep_telefonu(rs.getString("cep_telefonu"));
                kullanici.setEmail(rs.getString("email"));
                kullanici.setCinsiyet(rs.getString("cinsiyet"));
                kullanici.setYas(rs.getInt("yas"));
                kullanici.setKullaniciYetkileri(this.getYdao().getKullaniciYetkileri(kullanici.getKullanici_id()));

            }

        } catch (SQLException ex) {
            System.out.println(" Kullan??c?? DAO Hata(Kullan??c?? Var m???) :" + ex.getMessage());
        }

        return kullanici;
    }

    public List<Kullanici> getYetkiKullanicilari(int yetki_id) {
        List<Kullanici> list = new ArrayList<>();

        try {
            PreparedStatement pst1 = this.getConnection().prepareStatement("select * from kullanici_yetki where yetki_id=?");
            pst1.setInt(1, yetki_id);
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {

                list.add(this.find(rs1.getInt("kullanici_id")));

            }
        } catch (SQLException ex) {
            System.out.println("KullaniciDAO HATA(getYetkiKullanicilari): " + ex.getMessage());
        }
        return list;
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
