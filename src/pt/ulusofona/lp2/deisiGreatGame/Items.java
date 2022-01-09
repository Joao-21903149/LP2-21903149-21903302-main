package pt.ulusofona.lp2.deisiGreatGame;

public class Items {
    protected int id;
    protected String titulo;
    protected int pos;

    public Items(){}

    public Items(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPos() {
        return pos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

}
