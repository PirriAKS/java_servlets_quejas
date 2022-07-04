import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Alejandro Alba
 */

public class Distritos extends HttpServlet {
    String pagina;
    String driver;
    static Connection con;
    static HttpSession sesion;

    public void init(ServletConfig conf) throws ServletException{
        super.init(conf);
        driver=conf.getInitParameter("driver");
        try {
            Class.forName(driver);
            System.out.println("Esta ok");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void service(HttpServletRequest peticion, HttpServletResponse respuesta) throws ServletException, IOException {
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost/madrid_centro","root","");
            sesion=peticion.getSession(true);
            ArrayList<Distrito> distritos = new ArrayList<Distrito>(loadDistritos());
            pagina= "<HTML>" +
                    "              <BODY>" +
                    "                     <H1><center>Quejas Comunidad de Madrid</center></H1>"+
                    "                     <img></img>"+
                    "                           <form action='http://localhost:8080/examples/servlets/servlet/Opcion' method=\"POST\">"+
                    "                               <center><select name=\"distrito\" id=\"distrito\">" +
                                                        buildComboBox(distritos) +
                    "                               </select>" +
                    "                               <input type=\"submit\" value=\"Enviar\"></center>" +
                    "                           </form>" +
                    "              </BODY>" +
                    "</HTML>";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        respuesta.setContentType("text/html");
        PrintWriter pw=respuesta.getWriter();
        pw.println(pagina);
    }
    protected static ArrayList loadDistritos() throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs;
        ArrayList arrayList=new ArrayList();
        rs = st.executeQuery("SELECT * FROM distrito");
        while (rs.next()) {
            Distrito d = new Distrito(rs.getInt(1), rs.getString(2));
            arrayList.add(d);
        }
        return arrayList;
    }
    protected static String buildComboBox(ArrayList<Distrito> array){
        String text="";
        for (int i = 0; i < array.size(); i++) {
            Distrito dis=array.get(i);
            text=text+"<option value="+dis.getNombre()+">"+dis.getNombre()+"</option>";
        }
        return text;
    }
}


