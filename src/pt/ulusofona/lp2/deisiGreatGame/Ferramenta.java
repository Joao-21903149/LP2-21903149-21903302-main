package pt.ulusofona.lp2.deisiGreatGame;

public class Ferramenta extends Items {
    private String descricao;


    public Ferramenta() {

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao() {
        switch(super.titulo){
            case "Herança" -> {
                this.descricao = "Herança";
            }
            case "Programação funcional" ->{
                this.descricao = "Programação Funcional";
            }
            case "Testes unitários" -> {
                this.descricao = "Testes unitários";
            }
            case "Tratamento de Excepções" -> {
                this.descricao = "Tratamento de Excepções";
            }
            case "IDE" -> {
                this.descricao =  "IDE";
            }
            case "Ajuda Do Professor" -> {
                this.descricao = "Ajuda Do Professor";
            }
        }
    }

    @Override
    public String toString() {
        return "Ferramenta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", pos=" + pos +
                '}';
    }
}
