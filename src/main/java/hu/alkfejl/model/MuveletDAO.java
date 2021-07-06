package hu.alkfejl.model;


import hu.alkfejl.model.bean.Muvelet;

import java.util.List;

public interface MuveletDAO {
    void deleteDB();
    Muvelet loadByID(int id);
    boolean addMuvelet(Muvelet muvelet);
    List<Muvelet> listMuvelet();

}
