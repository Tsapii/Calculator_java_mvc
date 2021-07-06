package hu.alkfejl.model;

import hu.alkfejl.model.bean.Muvelet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MuveletDAODBImp implements MuveletDAO {
    private static final String DB_STRING = "jdbc:sqlite:sample.db";

    private static final String INIT_MUVELET = "CREATE TABLE IF NOT EXISTS  Muvelet (" +
            " id INTEGER  PRIMARY  KEY AUTOINCREMENT," +  "number1 DOUBLE NOT NULL ," +
            "number2 DOUBLE NOT NULL," +"operator TEXT NOT NULL," + "result DOUBLE NOT NULL" + ") ;" ;

    private static final String SELECT_MUVELET = "select * from Muvelet";
    private static final String DELETE_MUVELET = "delete from Muvelet";
    private static final String ID_MUVELET = "select * from Muvelet WHERE id = ?";
    private static final String ADD_MUVELET =
            "Insert into Muvelet(number1, number2, operator, result)"+
                    " values (?,?,?,?);";

    public MuveletDAODBImp(){
        constructTables();
    }
    private  void constructTables() {
        try (Connection conn = DriverManager.getConnection(DB_STRING);
             Statement st = conn.createStatement()
        ) {
            st.execute(INIT_MUVELET);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDB(){
        try(Connection conn = DriverManager.getConnection(DB_STRING);
            PreparedStatement ps = conn.prepareStatement(DELETE_MUVELET);
        ){
            ps.execute();   // Torles futtatasa
            try(PreparedStatement ps2 = conn.prepareStatement("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='Muvelet';");
            ){
                ps2.execute();  // Torles utan resetelni kell az Autoincrement erteket, es ez erre szolgal
            } catch (SQLException e){
                e.printStackTrace();
            }
            System.out.println("The Database has been CLEARED");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Muvelet loadByID(int id) {
        Muvelet m = new Muvelet();

        try(Connection conn = DriverManager.getConnection(DB_STRING);
            PreparedStatement ps = conn.prepareStatement("select max(id) from Muvelet;")
        ){
            ResultSet rs = ps.executeQuery();
            System.out.println("Max id:" +rs.getInt("max(id)"));    // Eloszor meghatarozzuk mennyi ID-nk van mar
            if(id <= rs.getInt("max(id)") && id > 0){
                try(Connection conn2 = DriverManager.getConnection(DB_STRING);
                        PreparedStatement ps2 = conn2.prepareStatement(ID_MUVELET)
                ){
                    ps2.setInt(1,id);
                    ResultSet rs2 = ps2.executeQuery();

                    m.setId(rs2.getInt("id"));
                    m.setNumber1(rs2.getDouble("number1"));
                    m.setNumber2(rs2.getDouble("number2"));
                    m.setOperator(rs2.getString("operator"));
                    m.setResult(rs2.getDouble("result"));
                } catch (SQLException e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("Az Adatbazis nem tartalmaz ilyen ID-t");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return m;
    }

    @Override
    public boolean addMuvelet(Muvelet muvelet) {
        try (Connection conn = DriverManager.getConnection(DB_STRING);
             PreparedStatement st = conn.prepareStatement(ADD_MUVELET))
        {
            // parameterek beallitasa
            st.setDouble(1, muvelet.getNumber1());
            st.setDouble(2, muvelet.getNumber2());
            st.setString(3, muvelet.getOperator());
            st.setDouble(4, muvelet.getResult());

            // vegrehajtas es az eredmeny ellenorzese
            return st.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Muvelet> listMuvelet() {
        List<Muvelet> result =new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(DB_STRING);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_MUVELET)
        ){
            while(rs.next()){
                Muvelet m = new Muvelet();
                m.setId(rs.getInt("id"));
                m.setNumber1(rs.getDouble("number1")); //adatb neveit hasznaljuk
                m.setNumber2(rs.getDouble("number2"));
                m.setOperator(rs.getString("operator"));
                m.setResult(rs.getDouble("result"));

                result.add(m);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

}
