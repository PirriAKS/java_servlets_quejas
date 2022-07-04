import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Alejandro Alba
 */

public class Quejas extends HttpServlet {
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
        String opcion;
        String nombre_distrito;
        String codigo_distrito;
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost/madrid_centro","root","");
            sesion=peticion.getSession(true);
            opcion=peticion.getParameter("opcion");
            nombre_distrito=peticion.getParameter("nombre_distrito");
            codigo_distrito=peticion.getParameter("codigo_distrito");
            if (opcion.equals("crear")){
                pagina= "<HTML>" +
                        "              <BODY>" +
                        "                     <H1><center>Crear queja en "+nombre_distrito+"</center></H1>"+
                        "                           <form action='http://localhost:8080/examples/servlets/servlet/InsertarQuejas?codigo_distrito="+codigo_distrito+" ' method=\"POST\">"+
                        "                               <center>" +
                        "                                   <h5>Introduzca sexo</h5>" +
                        "                                   <label>Masculino<input type=\"radio\" id=\"sexo\" name=\"sexo\" value=\"M\"><br><br>" +
                        "                                   <label>Femenino<input type=\"radio\" id=\"sexo\" name=\"sexo\" value=\"H\"><br><br>" +
                        "                                   </label>" +
                        "                                   <h5>Tipo de queja</h5>" +
                        "                                           <label>Ambiental<input type=\"radio\" id=\"tipo\" name=\"tipo\" value=\"Ambiental\"></label><br>" +
                        "                                           <label>Social<input type=\"radio\" id=\"tipo\" name=\"tipo\" value=\"Social\"></label><br>" +
                        "                                           <label>Conflictiva<input type=\"radio\" id=\"tipo\" name=\"tipo\" value=\"Conflictiva\"></label><br>" +
                        "                                   </label>" +
                        "                                   <h5>Descripcion de la incidencia</h5>" +
                        "                                       <textarea name=\"descripcion\" id=\"descripcion\" rows=\"10\" cols=\"50\"></textarea><br>" +
                        "                                   <input type=\"submit\" value=\"Enviar\">" +
                        "                               </center>" +
                        "                           </form>" +
                        "              </BODY>" +
                        "</HTML>";

            }
            if (opcion.equals("consultar")){
                ArrayList<Queja> quejas=loadQuejas(codigo_distrito);
                pagina= "<HTML>" +
                        "              <BODY>" +
                                            buildQuejas(quejas)+
                        "              </BODY>" +
                        "</HTML>";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        respuesta.setContentType("text/html");
        PrintWriter pw=respuesta.getWriter();
        pw.println(pagina);
    }
    protected static ArrayList loadQuejas(String codigo) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs;
        ArrayList arrayList=new ArrayList();
        rs = st.executeQuery("SELECT * FROM queja WHERE codigo_distrito="+codigo);
        while (rs.next()) {
            Queja q = new Queja(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
            arrayList.add(q);
        }
        return arrayList;
    }
    protected static String buildQuejas(ArrayList<Queja> array){
        String text="";
        for (int i = 0; i < array.size(); i++) {
            Queja queja=array.get(i);
            text=text+"<h4> Queja de tipo "+queja.getTipo()+"</h4><br><h5>Descripcion: "+queja.getDescripcion()+"</h5>";
        }
        return text;
    }
}
