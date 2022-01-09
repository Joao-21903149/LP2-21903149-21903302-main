package pt.ulusofona.lp2.deisiGreatGame;

public class Abismo extends Items{



    public Abismo(int id, String titulo) {
        super(id,titulo);
    }

    public Abismo(){
        super();
    }

    @Override
    public String toString() {
        return "Abismo{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", pos=" + pos +
                '}';
    }
}
