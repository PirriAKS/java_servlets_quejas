import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Alejandro Alba
 */

public class Opcion extends HttpServlet {
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
            String nombre_distrito=peticion.getParameter("distrito");
            Distrito distrito=loadDistrito(nombre_distrito);
            pagina= "<HTML>" +
                    "              <BODY>" +
                    "                     <H1><center>"+distrito.getNombre()+"</center></H1>"+
                    "                     <img></img>"+
                    "                           <form action='http://localhost:8080/examples/servlets/servlet/Quejas?nombre_distrito="+distrito.getNombre()+"&codigo_distrito="+distrito.getCodigo()+" ' method=\"POST\">"+
                    "                               <center>"+
                    "                                  <label>Crear queja<input type=\"radio\" id=\"opcion\" name=\"opcion\" value=\"crear\"></label><br><br>"+
                    "                                  <label>Consultar quejas<input type=\"radio\" id=\"opcion\" name=\"opcion\" value=\"consultar\"></label><br><br>"+
                    "                                  <input type=\"submit\" value=\"Enviar\">"+
                    "                               </center>" +
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
    protected static Distrito loadDistrito(String distrito) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs;
        rs = st.executeQuery("SELECT * FROM distrito WHERE nombre='"+distrito+"'");
        Distrito d = null;
        while (rs.next()) {
            d = new Distrito(rs.getInt(1), rs.getString(2));
        }
        return d;
    }
}
