package hu.alkfejl.model;

import hu.alkfejl.model.bean.Muvelet;

import java.util.ArrayList;
import java.util.List;

public class MuveletDAOMemImp implements MuveletDAO {
    List<Muvelet> muveletek = new ArrayList<Muvelet>();

    @Override
    public void deleteDB(){
        System.out.println("Data from the Memory has been DELETED");
        muveletek.clear();
    }

    @Override
    public Muvelet loadByID(int id) {
        return null;
    }

    @Override
    public boolean addMuvelet(Muvelet muvelet) {
        return muveletek.add(muvelet);
    }

    @Override
    public List<Muvelet> listMuvelet() {
        return muveletek;
    }
}
