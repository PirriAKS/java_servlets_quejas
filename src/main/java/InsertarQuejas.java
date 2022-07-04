import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author Alejandro Alba
 */
public class InsertarQuejas  extends HttpServlet {
    String pagina;
    String driver;
    static Connection con;
    static HttpSession sesion;

    public void init(ServletConfig conf) throws ServletException {
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
        int codigo_distrito;
        String sexo;
        String tipo;
        String descripcion;
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost/madrid_centro","root","");
            sesion=peticion.getSession(true);
            codigo_distrito= Integer.parseInt(peticion.getParameter("codigo_distrito"));
            sexo=peticion.getParameter("sexo");
            tipo=peticion.getParameter("tipo");
            descripcion=peticion.getParameter("descripcion");
            Queja q=new Queja(codigo_distrito,sexo,tipo,descripcion);
            crearQueja(q);
            pagina= "<HTML>" +
                    "              <BODY>" +
                    "                   <center>queja insertada con exito</center>"+
                    "                   <meta http-equiv=\"refresh\" content=\"2\" url=\"/examples/servlets/servlet/Distritos\">"+
                    "              </BODY>" +
                    "</HTML>";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        respuesta.setContentType("text/html");
        PrintWriter pw=respuesta.getWriter();
        pw.println(pagina);
    }
    protected static void crearQueja(Queja queja){
        try {
            PreparedStatement ps=con.prepareStatement("INSERT INTO queja VALUES (?,?,?,?,?)");
            ps.setString(1,null);
            ps.setInt(2, queja.getCodigo_distrito());
            ps.setString(3, queja.getSexo());
            ps.setString(4,queja.getTipo());
            ps.setString(5,queja.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
