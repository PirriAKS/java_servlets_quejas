/**
 * @author Alejandro Alba
 */
public class Queja {
    int codigo;
    int codigo_distrito;

    String sexo;
    String tipo;
    String descripcion;

    public Queja() {
    }

    public Queja(int codigo_distrito, String sexo, String tipo, String descripcion) {
        this.codigo_distrito = codigo_distrito;
        this.sexo = sexo;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Queja(int codigo, int codigo_distrito, String sexo, String tipo, String descripcion) {
        this.codigo = codigo;
        this.codigo_distrito = codigo_distrito;
        this.sexo = sexo;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo_distrito() {
        return codigo_distrito;
    }

    public void setCodigo_distrito(int codigo_distrito) {
        this.codigo_distrito = codigo_distrito;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Queja{" +
                "codigo=" + codigo +
                ", codigo_distrito=" + codigo_distrito +
                ", sexo='" + sexo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
