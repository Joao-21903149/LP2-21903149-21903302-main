package pt.ulusofona.lp2.deisiGreatGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Programmer {


    int id;
    int pos = 1;
    String name;
    ProgrammerColor color;
    boolean estado;
    ArrayList<String> linguages;
    HashMap<String, Ferramenta> ferramentas;
    ArrayList<Integer> posicoesHistoria;
    boolean stuck;

    public Programmer() {
        estado = true;
        ArrayList<String> linguages = new ArrayList<String>();
        this.ferramentas = new  HashMap<String, Ferramenta>();
        this.posicoesHistoria = new ArrayList<Integer>();
        this.stuck = false;
    }

    public void getStuck(){
        this.stuck = true;
    }

    public boolean isStuck(){
        return this.stuck;
    }

    public void unStuck(){
        this.stuck = false;
    }

    public  HashMap<String, Ferramenta> getFerramentas() {
        return this.ferramentas;
    }

    public void addFerramenta(Ferramenta f){
        this.ferramentas.put(f.getTitulo(),f);
    }
    public void removeFerramenta(Ferramenta f){
        this.ferramentas.remove(f.getTitulo());
    }

    public boolean isEstado() {
        return estado;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setName(String name){
        this.name = name;
    }

    public ProgrammerColor getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(ProgrammerColor c){
        this.color = c;
    }

    public void move(int pos){
        posicoesHistoria.add(this.pos);
        if(pos < 0){
            if(this.pos + pos <= 0){
                this.pos = 1;
            }else{
                this.pos += pos;
            }
        }else{
            this.pos += pos;
        }

    }

    public void perdeu(){
        this.estado = false;
    }

    public ArrayList<Integer> getPosicoesHistoria(){
        return this.posicoesHistoria;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getPos()
    {
        return this.pos;
    }

    public void setLinguages(ArrayList<String> linguages){
        this.linguages = linguages;
    }

    public ArrayList<String> getLinguages(){
        return this.linguages;
    }

    @Override
    public String toString() {
        String result = this.id + " | " + this.name + " | "  + this.pos + " | ";
        if(this.ferramentas.isEmpty()){
            result += "No tools | ";
        }else {
            int count = 1;
            int size = this.ferramentas.values().size();
            for (Ferramenta f : this.ferramentas.values()){
                if(count != size){
                    result += f.getDescricao() + ';';
                }else{
                    result += f.getDescricao();
                }
                count++;
            }
            result += " | ";
        }
        if(linguages != null && !linguages.isEmpty()){
            for(int i = 0; i < linguages.size(); i++){
                if(i == linguages.size()-1){
                    result += linguages.get(i) + " | ";
                }else{
                    result += linguages.get(i) + "; ";
                }
            }
        }

        if(estado){
            result += "Em Jogo";
        }else{
            result += "Derrotado";
        }
        return result;
    }
}
