package pt.ulusofona.lp2.deisiGreatGame;

import kotlin.Pair;
import kotlin.contracts.Returns;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.*;
import java.util.List;



public class GameManager {

    List<Programmer> programmers;
    Programmer[] programmersTurn;
    int programmerTurnPos;

    ArrayList<String> colors;
    int boardSize;
    boolean playing;
    int nrTurnos;

    List<Abismo> abismos;
    List<Ferramenta> ferramentas;

    HashMap<Integer,Abismo> abismosHashMap= new HashMap<Integer,Abismo>();
    HashMap<Integer,Ferramenta> ferramentasHashMap = new HashMap<Integer,Ferramenta>();

    int programersInPlay;

    HashMap<Integer,Integer> numPisadasCasas;


    public GameManager() {
        programmers = new ArrayList<Programmer>();
        colors = new ArrayList<String>();
        this.playing = true;
        abismos = new ArrayList<Abismo>();
        ferramentas = new ArrayList<Ferramenta>();
        programersInPlay = 0;
        this.numPisadasCasas = new HashMap<>();
    }


    public void createInitialBoard(String[][] playerInfo, int worldSize) throws InvalidInitialBoardException {
        this.programmers = new ArrayList<Programmer>();
        this.colors = new ArrayList<String>();
        int programmerTurnPos = 0;
        this.boardSize = 0;
        this.nrTurnos = 1;
        this.playing = true;

        boolean correct = true;
        int size = playerInfo.length;
        if((size > 4 || size < 2) || worldSize < size * 2 ){
            throw new InvalidInitialBoardException("Board demasiado pequeno para o n de jogadores");

        }
        for(String[] x : playerInfo){
            boolean insert = true;
            HashMap<String,Integer> mapProgrammerToColor = new HashMap<String,Integer>();
            Programmer pNew = new Programmer();
            for(int i = 0; i < 4 ; i++) {
                switch (i) {
                    case 0: {
                        if (!programmers.isEmpty()) {
                            for (Programmer p : programmers) {
                                if (p.id == Integer.parseInt(x[i])) {
                                    throw new InvalidInitialBoardException("Board demasiado pequeno para o n de jogadores");

                                }
                            }
                        }
                        if(Integer.parseInt(x[i]) < 0){
                            throw new InvalidInitialBoardException("Board demasiado pequeno para o n de jogadores");
                        }
                        pNew.id = Integer.parseInt(x[i]);
                        break;

                    }
                    case 1: {
                        if (x[i] == null || x[i].isEmpty()) {
                            correct = false;

                            insert = false;
                        }
                        pNew.name = x[i];
                        break;
                    }
                    case 2: {
                        String[] result = x[i].split(";");
                        ArrayList<String> listaResult = new ArrayList<String>();
                        for(String s : result)
                        {
                            listaResult.add(s);
                        }
                        Collections.sort(listaResult);
                        pNew.linguages = listaResult;
                        break;

                    }
                    case 3: {

                        if (colors.contains(x[i])) {
                            throw new InvalidInitialBoardException("Board demasiado pequeno para o n de jogadores");


                        } else {
                            colors.add(x[i]);
                        }
                        boolean z = false;
                        switch (x[i]) {
                            case "Purple" -> pNew.setColor(ProgrammerColor.PURPLE);
                            case "Green" -> pNew.setColor(ProgrammerColor.GREEN);
                            case "Brown" -> pNew.setColor(ProgrammerColor.BROWN);
                            case "Blue" -> pNew.setColor(ProgrammerColor.BLUE);
                            default -> z = true;
                        }
                        if(z){
                            throw new InvalidInitialBoardException("Board demasiado pequeno para o n de jogadores");
                        }
                        break;
                    }
                }
            }

            programmers.add(pNew);

        }
        this.boardSize = worldSize;
        programmersTurn = new Programmer[programmers.size()];
        programersInPlay = programmers.size();
        orderPlayers();

        //return correct;
    }


    public void createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools)  throws InvalidInitialBoardException{
        this.programmers = new ArrayList<Programmer>();
        this.colors = new ArrayList<String>();
        int programmerTurnPos = 0;
        this.boardSize = worldSize;
        this.nrTurnos = 1;
        this.playing = true;

        boolean correct = true;
        int size = playerInfo.length;
        if((size > 4 || size < 2) || boardSize < size * 2 ){
            throw new InvalidInitialBoardException("Board demasiado pequeno para o n de jogadores");

        }
        for(String[] x : playerInfo) {

            boolean insert = true;
            HashMap<String, Integer> mapProgrammerToColor = new HashMap<String, Integer>();
            Programmer pNew = new Programmer();
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0: {
                        if (!programmers.isEmpty()) {
                            for (Programmer p : programmers) {
                                if (p.getId() == Integer.parseInt(x[i])) {
                                    throw new InvalidInitialBoardException("Ja existia este id");

                                }
                            }
                        }
                        if (Integer.parseInt(x[i]) < 0) {
                            throw new InvalidInitialBoardException("O id do programador era negativa");

                        }
                        pNew.setId(Integer.parseInt(x[i]));
                        break;

                    }
                    case 1: {
                        if (x[i] == null || x[i].isEmpty()) {
                            correct = false;

                            insert = false;
                        }
                        pNew.setName(x[i]);
                        break;
                    }
                    case 2: {
                        String[] result = x[i].split(";");
                        ArrayList<String> listaResult = new ArrayList<String>();
                        for (String s : result) {
                            listaResult.add(s);
                        }
                        Collections.sort(listaResult);
                        pNew.setLinguages(listaResult);
                        break;

                    }
                    case 3: {

                        if (colors.contains(x[i])) {
                            throw new InvalidInitialBoardException("Cor ja estava a ser utilizada");


                        } else {
                            colors.add(x[i]);
                        }
                        boolean z = false;
                        switch (x[i]) {
                            case "Purple" -> pNew.setColor(ProgrammerColor.PURPLE);
                            case "Green" -> pNew.setColor(ProgrammerColor.GREEN);
                            case "Brown" -> pNew.setColor(ProgrammerColor.BROWN);
                            case "Blue" -> pNew.setColor(ProgrammerColor.BLUE);
                            default -> z = true;
                        }
                        if (z) {
                            throw new InvalidInitialBoardException("Cor invalida");
                        }
                        break;
                    }
                }
            }

            programmers.add(pNew);
        }
        programmersTurn = new Programmer[programmers.size()];
        orderPlayers();


        boolean isAbismo = true;
        for (String[] info : abyssesAndTools) {
            boolean verified = true;
            Abismo a = new Abismo();
            Ferramenta f = new Ferramenta();
            for (int i = 0; i < 3; i++) {
                switch (i) {
                    case 0: {

                        if (info[i].equals("0")) {
                            isAbismo = true;
                        } else if (info[i].equals("1")) {
                            isAbismo = false;
                        } else {
                            //verified = false;
                            throw new InvalidInitialBoardException("Id de ferramenta incorrecto");
                        }
                        break;
                    }
                    case 1: {
                        if (isAbismo) {
                            a.setId(Integer.parseInt(info[i]));
                            a.setTitulo(getTituloAbismo(a.getId()));
                            if (a.getTitulo().equals("404")) {
                                throw new InvalidInitialBoardException("Abismo inexistente", info[i], true, false );
                            }
                        } else {
                            f.setId(Integer.parseInt(info[i]));
                            f.setTitulo(getTituloFerramenta(f.getId()));
                            if (f.getTitulo().equals("404")) {
                                throw new InvalidInitialBoardException("Ferramenta inexistente", info[i], false, true);
                            }
                        }
                        break;
                    }
                    case 2: {
                        if(Integer.parseInt(info[i]) <= 0 || Integer.parseInt(info[i]) >= worldSize){
                            if(info[0].equals("0")){
                                throw new InvalidInitialBoardException("Abismo fora do mapa", info[2], true, false );
                            }else{
                                throw new InvalidInitialBoardException("Ferramenta forma do mapa", info[2], false, true);
                            }

                        }
                        if (isAbismo) {
                            a.setPos(Integer.parseInt(info[i]));
                        } else {
                            f.setPos(Integer.parseInt(info[i]));
                        }
                        break;
                    }
                }
            }
            if(verified){
                if(isAbismo){
                    this.abismos.add(a);
                }else{
                    this.ferramentas.add(f);
                }
            }
        }


        setAbismosHashMap();
        setFerramentasHashMap();
        setFerramentasDescricacao();
        programersInPlay = programmers.size();
        //return correct;
        for(int x = 1; x < worldSize ; x++){
            this.numPisadasCasas.put(x,0);
        }

    }

    public void setFerramentasDescricacao(){
        for(Ferramenta f : this.ferramentasHashMap.values()){
            f.setDescricao();
        }
    }

    public void setAbismosHashMap(){
        for(Abismo a : this.abismos) {
            abismosHashMap.put(a.getPos(), a);
        }
    }

    public void setFerramentasHashMap(){
        for(Ferramenta ferramenta: this.ferramentas){
            ferramentasHashMap.put(ferramenta.getPos(),ferramenta);
        }
    }


    public String getTituloFerramenta(int id){
        switch (id) {
            case 0 -> {
                return "Herança";
            }
            case 1 -> {
                return "Programação funcional";
            }
            case 2 -> {
                return "Testes unitários";
            }
            case 3 -> {
                return "Tratamento de Excepções";
            }
            case 4 -> {
                return "IDE";
            }
            case 5 -> {
                return "Ajuda Do Professor";
            }
        }
        return "404";
    }


    public String getTituloAbismo(int id){
        switch (id) {
            case 0 -> {
                return "Erro de sintaxe";
            }
            case 1 -> {
                return "Erro de lógica";
            }
            case 2 -> {
                return "Exception";
            }
            case 3 -> {
                return "File Not Found Exception";
            }
            case 4 -> {
                return "Crash (aka Rebentanço)";
            }
            case 5 -> {
                return "Duplicated Code";
            }
            case 6 -> {
                return "Efeitos secundários";
            }
            case 7 -> {
                return "Blue Screen of Death";
            }
            case 8 -> {
                return "Ciclo infinito";
            }
            case 9 -> {
                return "Segmentation Fault";
            }
        }
        return "404";
    }


    public void orderPlayers(){
        int pos = 0;
        for(int pos1 = 0 ; pos1 < programmers.size() ; pos1++){
            for(int pos2 = 0 ; pos2 < programmers.size() ; pos2++){
                if(pos1 != pos2){
                    if(programmers.get(pos1).getId() >= programmers.get(pos2).getId()){
                        pos++;
                    }
                }
            }
            this.programmersTurn[pos] = programmers.get(pos1);
            pos = 0;
        }
        this.programmerTurnPos = 0;
    }

    public List<Programmer> getProgrammers(){ //TODO
        return this.programmers;
    }

    public List<Programmer> getProgrammers(boolean includeDefeated){
        List<Programmer> result = new ArrayList<Programmer>();
        if(includeDefeated){
            for(Programmer p : this.programmers){
                result.add(p);
            }
        }else{
            for(Programmer p : this.programmers){
                if(p.isEstado()){
                    result.add(p);
                }
            }
        }
        return result;
    }

    public List<Programmer> getProgrammers(int position){

        List<Programmer> result = new ArrayList<Programmer>();
        if(position <= 0){
            return null;
        }
        for(Programmer p : programmers){
            if(p.getPos() == position)
            {
                result.add(p);
            }
        }
        if(result.isEmpty()){
            return null;
        }
        return result;
    }

    public boolean moveCurrentPlayer(int nrPositions){
        //TO STRING
        if(programmersTurn[programmerTurnPos].isStuck() || !programmersTurn[programmerTurnPos].isEstado()) {
            return false;
        }
        if(nrPositions < 1 || nrPositions > 6){
            return false;
        }
        Programmer currentPlayer = programmersTurn[programmerTurnPos];
        if(currentPlayer.getPos() + nrPositions == this.boardSize) {
            this.playing = false;
            currentPlayer.move(nrPositions);
        }
        else if(currentPlayer.getPos() + nrPositions > this.boardSize)
        {
            currentPlayer.move((-(nrPositions - (this.boardSize - currentPlayer.getPos()) ) + this.boardSize ) - currentPlayer.getPos());

        }else{
            currentPlayer.move(nrPositions);
        }

        return true;
    }


    public String reactToAbyssOrTool(){
        String result = null;
        Programmer currentPlayer = programmersTurn[programmerTurnPos];
        //if(!currentPlayer.isStuck()) {


            int currentPlayerPos = currentPlayer.getPos();
            if (abismosHashMap.containsKey(currentPlayer.getPos())) {
                Abismo a = abismosHashMap.get(currentPlayer.getPos());
                Ferramenta f = hasTool(currentPlayer, a);
                if (f == null) {
                    switch (a.getTitulo()) {
                        case "Erro de sintaxe" -> {
                            currentPlayer.move(-1);
                            result = "O programador recua 1 casa.";
                            break;
                        }
                        case "Erro de lógica" -> {
                            int temp = currentPlayer.getPosicoesHistoria().get(currentPlayer.getPosicoesHistoria().size() - 1);
                            currentPlayer.move(-((currentPlayer.getPos() - temp) / 2)); //saber a posicao anterior
                            result = "ee";
                            break;
                        }
                        case "Exception" -> {
                            currentPlayer.move(-2);
                            result = "O programador recua 2 casas.";
                            break;
                        }
                        case "File Not Found Exception" -> {
                            currentPlayer.move(-3);
                            result = "O programador recua 3 casas.";
                            break;
                        }
                        case "Crash (aka Rebentanço)" -> {
                            currentPlayer.pos = 1;
                            result = "O programador volta à primeira casa do jogo.";
                            break;
                        }
                        case "Duplicated Code" -> {
                            currentPlayer.pos = currentPlayer.getPosicoesHistoria().get(currentPlayer.getPosicoesHistoria().size() - 1);
                            result = "O programador recua até à casa onde estava antes de chegar a esta casa.";
                            break;
                        }
                        case "Efeitos secundários" -> {
                            currentPlayer.pos = currentPlayer.getPosicoesHistoria().get(currentPlayer.getPosicoesHistoria().size() - 2);
                            result = "O programador recua para a posição onde estava há 2 movimentos atrás.";
                            break;
                        }
                        case "Blue Screen of Death" -> {
                            currentPlayer.perdeu();
                            this.programmerTurnPos--;
                            this.programersInPlay--;
                            if(programersInPlay == 1){
                                this.playing = false;
                            }
                            Programmer[] newProgrammersTurn  = new Programmer[this.programersInPlay];
                            int count = 0;
                            for(Programmer p : programmersTurn){
                                if(p != currentPlayer){
                                    newProgrammersTurn[count] = p;
                                    count++;
                                }
                            }
                            this.programmersTurn = newProgrammersTurn;
                            result = "O programador perde imediatamente o jogo.";
                            break;
                        }
                        case "Segmentation Fault" -> {
                            List<Programmer> ps = getProgrammers(currentPlayer.getPos());
                            if(ps.size() >= 2 ){
                                for(Programmer pro : ps){
                                    pro.move(-3);
                                }
                            }
                            result = "Segmentation Fault";
                            break;
                        }
                        case "Ciclo infinito" -> {
                            if(getProgrammers(currentPlayerPos).size() > 1){
                                for(Programmer unstuckP : getProgrammers(currentPlayerPos)){
                                    if(unstuckP !=currentPlayer){
                                        unstuckP.unStuck();
                                    }
                                }
                            }
                            currentPlayer.getStuck();
                            result = "Caiu no ciclo infinito";
                            break;
                        }
                    }
                } else {

                    if(a.getTitulo().equals("Ciclo infinito")){
                        for(Programmer unstuckP : getProgrammers(currentPlayerPos)){
                            if(unstuckP !=currentPlayer){
                                unstuckP.unStuck();
                            }
                        }
                    }
                    //APAGAR FERRAMENTA
                    result = "Usou Ferramenta";
                    currentPlayer.removeFerramenta(f);
                }
            }
            if (ferramentasHashMap.containsKey(currentPlayer.getPos())) {
                Ferramenta ferramenta = ferramentasHashMap.get(currentPlayer.getPos());
                switch (ferramenta.getTitulo()) {
                    case "Herança" -> {
                        currentPlayer.addFerramenta(ferramenta);
                        result = "Herança";
                        break;
                    }
                    case "Programação funcional" -> {
                        currentPlayer.addFerramenta(ferramenta);
                        result = "Programa Funcional";
                        break;
                    }
                    case "Testes unitários" -> {
                        currentPlayer.addFerramenta(ferramenta);
                        result = "Testes unitários";
                        break;
                    }
                    case "Tratamento de Excepções" -> {
                        currentPlayer.addFerramenta(ferramenta);
                        result = "Tratamento de Excepções";
                        break;
                    }
                    case "IDE" -> {
                        currentPlayer.addFerramenta(ferramenta);
                        result = "IDE";
                        break;
                    }
                    case "Ajuda Do Professor" -> {
                        currentPlayer.addFerramenta(ferramenta);
                        result = "Ajuda Do Professor";
                        break;
                    }
                }
            }
        this.nrTurnos++;
        setNextPlayer();
            return result;
        /*}else{
            setNextPlayer();
            return null;
        }*/
    }

    public Ferramenta hasTool(Programmer currentPlayer, Abismo a) {
        HashMap<String,Ferramenta> fs =  currentPlayer.getFerramentas();
        switch (a.getTitulo()) {
            case "Erro de sintaxe" -> {
                if(fs.containsKey("Ajuda Do Professor")){
                    return fs.get("Ajuda do Professor");
                }else if(fs.containsKey("IDE")){
                    return fs.get("IDE");
                }
            }
            case "Erro de lógica" -> {
                if(fs.containsKey("Ajuda Do Professor")){
                    return fs.get("Ajuda do Professor");
                }else if(fs.containsKey("Testes unitários")) {
                    return fs.get("Testes unitários");
                }/*else if(fs.containsKey("Programação Funcional")){
                    return fs.get("Programação Funcional");
                }*/
            }
            case "Exception" -> {
                if(fs.containsKey("Ajuda Do Professor")){
                    return fs.get("Ajuda do Professor");
                }else if(fs.containsKey("Tratamento de Excepções")){
                    return fs.get("Tratamento de Excepções");
                }
            }
            case "File Not Found Exception" -> {
                if(fs.containsKey("Ajuda Do Professor")){
                    return fs.get("Ajuda do Professor");
                }else if (fs.containsKey("Tratamento de Excepções")){
                    return fs.get("Tratamento de Excepções");
                }
            }
            case "Crash (aka Rebentanço)" -> {
                if(fs.containsKey("Testes unitários")) {
                    return fs.get("Testes unitários");
                }
            }
            case "Duplicated Code" -> {
                if(fs.containsKey("Herança")){
                    return fs.get("Herança");
                }
            }
            case "Efeitos secundários" -> {
                if(fs.containsKey("Programação funcional")){
                    return fs.get("Programação funcional");
                }
            }
            case "Blue Screen of Death" -> {

            }
            case "Segmentation Fault" -> {

            }
            case "Ciclo infinito" -> {
                if(fs.containsKey("Programação funcional")){
                    return fs.get("Programação funcional");
                }
            }
        }
        return null;
    }

    public void setNextPlayer(){
        if(programmerTurnPos + 1 >= this.programersInPlay){
            programmerTurnPos = 0;

        }else{
            programmerTurnPos++;
        }
    }

    public int getCurrentPlayerID(){

        return programmersTurn[programmerTurnPos].id;
    }

    public Programmer getCurrentPlayerByID(int id){
        for(Programmer p : programmers){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public boolean gameIsOver(){
        return !this.playing;
    }


    public String getImagePng(int position){

        if(position > this.boardSize || position <= 0){
            return null;
        }
        if(position == this.boardSize){
            return "glory.png";
        }



        List<Programmer> programmersInPos = getProgrammers(position);


        if(programmersInPos != null){

            ProgrammerColor c = programmersInPos.get(0).getColor();
            if(c != null){
                switch (c) {
                    case BLUE -> {
                        return "playerBlue.png";
                    }
                    case GREEN -> {
                        return "playerGreen.png";
                    }
                    case BROWN -> {
                        return "playerBrown.png";
                    }
                    case PURPLE-> {
                        return "playerPurple.png";
                    }
                }
            }
        }

        if(!abismosHashMap.containsKey(position) && !ferramentasHashMap.containsKey(position)){
            return null;
        }
        if(abismosHashMap.containsKey(position)){
            String t = abismosHashMap.get(position).getTitulo();
            switch(t){
                case "Erro de sintaxe" ->{
                    return "syntax.png";
                }
                case "Erro de lógica" -> {
                    return "logic.png";
                }
                case "Exception" -> {
                    return "exception.png";
                }
                case "File Not Found Exception" -> {
                    return "file-not-found-exception.png";
                }
                case "Crash (aka Rebentanço)" -> {
                    return "crash.png";
                }
                case "Duplicated Code" -> {
                    return "duplicated-code.png";
                }
                case "Efeitos secundários" -> {
                    return "secondary-effects.png";
                }
                case "Blue Screen of Death" -> {
                    return "bsod.png";
                }
                case "Ciclo infinito" -> {
                    return "infinite-loop.png";
                }
                case "Segmentation Fault" -> {
                    return "core-dumped.png";
                }
            }
        }else if(ferramentasHashMap.containsKey(position)){
            String ferramentasTitulo = ferramentasHashMap.get(position).getTitulo();
            switch (ferramentasTitulo){
                case "Herança" -> {
                    return "inheritance.png";
                }
                case "Programação funcional" -> {
                    return "functional.png";
                }
                case "Testes unitários" -> {
                    return "unit-tests.png";
                }
                case "Tratamento de Excepções" -> {
                    return "exception.png";
                }
                case "IDE" -> {
                    return "IDE.png";
                }
                case "Ajuda Do Professor" -> {
                    return "ajuda-professor.png";
                }
            }
        }


        return "blank.png";
    }



    public List<String> getGameResults(){
        ArrayList<String> result = new ArrayList<>();
        result.add("O GRANDE JOGO DO DEISI");
        result.add("");
        result.add("NR. DE TURNOS");
        result.add(""+this.nrTurnos);
        result.add("");


        Programmer[] resultOrder = new Programmer[getProgrammers(true).size()];


        int pos = 0;

        for(Programmer  p : this.programmers){
            for(Programmer pCompare : this.programmers){
                if(p.getPos() >= pCompare.getPos()){

                }else{
                    pos++;
                }
            }
            //resultOrder[pos] = p;
            if(resultOrder[pos] == null)
            {
                resultOrder[pos] = p;
            }
            else if(pos-1 >= 0 && resultOrder[pos-1] == null)
            {
                resultOrder[pos-1] = p;
            }else if(pos+1 < resultOrder.length && resultOrder[pos+1] == null){
                resultOrder[pos+1] = p;
            }else if(resultOrder[pos] != null){
                while(resultOrder[pos] != null){
                    pos++;
                }
                resultOrder[pos] = p;
            }else{
                resultOrder[pos] = p;
            }
            pos = 0;
        }

        Boolean checker = true;
        while(checker){
            int i = 1;
            while(i + 1 < resultOrder.length ){
                if(resultOrder[i].getPos() == resultOrder[i +1].getPos()){
                    int size =  resultOrder[i + 1 ].getName().length() < resultOrder[i].getName().length() ? resultOrder[i + 1 ].getName().length() : resultOrder[i].getName().length();
                    String nome = resultOrder[i].getName();
                    String nome2 = resultOrder[i+1].getName();

                    boolean done = true;
                    for(int ii = 0; ii < size && done ; ii++){
                        if((int)nome.charAt(ii) < (int)nome2.charAt(ii)){
                            done = false;
                        } else if ((int)nome.charAt(ii) > (int)nome2.charAt(ii)) {
                            done = false;
                            //switch
                            Programmer temp = resultOrder[i];
                            resultOrder[i] = resultOrder[i+1];
                            resultOrder[i+1] = temp;
                        }else if(ii == size - 1){
                            if( size == nome2.length() ){
                                //SWITCH
                                Programmer temp = resultOrder[i];
                                resultOrder[i] = resultOrder[i+1];
                                resultOrder[i+1] = temp;
                                done = false;
                            }
                        }
                    }
                }
                i++;
            }
            int s = 1;
            checker = false;
            while( s + 1 < resultOrder.length ){
                if(resultOrder[s].getPos() == resultOrder[s +1 ].getPos() && (int) resultOrder[s].getName().charAt(0) == (int) resultOrder[s +1].getName().charAt(0)){
                    int size = resultOrder[s + 1 ].getName().length() < resultOrder[s].getName().length() ? resultOrder[s + 1 ].getName().length() : resultOrder[s].getName().length();
                    String nome = resultOrder[s].getName();
                    String nome2 = resultOrder[s+1].getName();

                    boolean done = true;
                    for(int ii = 0; ii < size && done; ii++){
                        if((int)nome.charAt(ii) < (int)nome2.charAt(ii)){
                            done = false;
                        } else if ((int)nome.charAt(ii) > (int)nome2.charAt(ii)) {
                            checker = true;
                            done = false;
                        }else if(ii == size - 1){
                            done = false;
                        }
                    }
                }
                s++;
            }
        }

        for(int i = 0; i < resultOrder.length ; i++){
            Programmer p = resultOrder[i];
            switch(i){
                case 0:{
                    result.add("VENCEDOR");
                    result.add(p.getName());
                    result.add("");
                    result.add("RESTANTES");
                    break;
                }
                default:{
                    if (p != null){
                        result.add(p.getName() + " " + p.pos);
                    }
                    //result.add("\n"); ver se é diferente de null
                }
            }
        }
        return result;
    }

    public void addAbismo(Integer id , Integer pos){
        Abismo a = new Abismo();
        a.setPos(pos);
        a.setId(id);
        a.setTitulo(getTituloAbismo(id));
        abismos.add(a);
        setAbismosHashMap();
    }

    public JPanel getAuthorsPanel(){
        //v1
        JPanel panel = new JPanel();
        JLabel jlabel = new JLabel("Pedro brito a21903302");
        JLabel jlabel2 = new JLabel("Joao luis a21903149");
        jlabel.setFont(new Font("Verdana",1,20));
        jlabel2.setFont(new Font("Verdana",1,20));
        panel.add(jlabel);
        panel.add(jlabel2);
        panel.setSize(300,300);
        return panel;

    }


    public String getProgrammersInfo(){
        String r = "";
        for(Programmer p : getProgrammers(false)){

            r += p.getName() + " : ";
            if(!p.getFerramentas().isEmpty()){
                int count = 1;
                for(Ferramenta f : p.getFerramentas().values()){
                    if(count !=  p.getFerramentas().values().size() || p != this.programmers.get(this.programmers.size() - 1) ){
                        r += f.getDescricao();
                        r += " | ";
                    }else{
                        r += f.getDescricao();
                    }
                }
            }else{
                r += "No tools";
                if(p != this.programmers.get(this.programmers.size() - 1)) {
                    r += " | ";
                }
            }
        }

        return r;
    }

    public boolean saveGame(File file){
        try {




            if (file.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file.getName(), true));

                writer.write(this.boardSize + "\n");
                writer.write(this.nrTurnos + "\n");
                writer.write(this.playing + "\n");


                writer.write(this.abismos.size() + "\n");
                for (Abismo a : this.abismos) {
                    writer.write(a.getId() + ";" + a.getPos() + ";" + a.getTitulo() + "\n");
                }

                writer.write(this.ferramentas.size() + "\n");
                for (Ferramenta f : this.ferramentas) {
                    writer.write(f.getId() + ";" + f.getPos() + ";" + f.getTitulo() + "\n");
                }

                writer.write(this.programmers.size() + "\n");
                for (Programmer p : this.programmers) {
                    writer.write(p.getColor() + " | " + p.toString() + "\n");
                }


                writer.write(this.programmerTurnPos + "\n");
                writer.write(this.programersInPlay + "\n");

                writer.write(this.programmersTurn.length + "\n");
                for (Programmer p : this.programmersTurn) {
                    writer.write(p.getColor() + " | " + p.toString() + "\n");
                }

                writer.write(this.programmersTurn.length + "\n");
                for (Programmer p : this.programmersTurn) {
                    writer.write(p.getId() + "\n");
                }

                writer.close();


            } else {

                return false;
            }


        }catch(IOException e){
            return false;
        }
        return true;
    }

    public boolean loadGame(File file) {
        try {
            this.programmers = new ArrayList<>();
            this.abismos = new ArrayList<>();
            this.ferramentas = new ArrayList<>();
            this.abismosHashMap = new HashMap<>();
            this.ferramentasHashMap = new HashMap<>();
            Scanner reader = new Scanner(file);


                //
                // LER TAMANHO DA GRID
                //

                this.boardSize = Integer.parseInt(reader.nextLine());
                this.nrTurnos = Integer.parseInt(reader.nextLine());
                this.playing = Boolean.getBoolean(reader.nextLine());

                int size;
                String s;
                String[] filter;
                size = Integer.parseInt(reader.nextLine());
                for (int i = 0; i < size; i++) {
                    s = reader.nextLine();
                    filter = s.split(";");
                    Abismo a = new Abismo();
                    a.setId(Integer.parseInt(filter[0]));
                    a.setPos(Integer.parseInt(filter[1]));
                    a.setTitulo(filter[2]);
                    this.abismos.add(a);
                }

                size = Integer.parseInt(reader.nextLine());
                for (int i = 0; i < size; i++) {
                    s = reader.nextLine();
                    filter = s.split(";");
                    Ferramenta f = new Ferramenta();
                    f.setId(Integer.parseInt(filter[0]));
                    f.setPos(Integer.parseInt(filter[1]));
                    f.setTitulo(filter[2]);
                    this.ferramentas.add(f);
                }

                setAbismosHashMap();
                setFerramentasHashMap();
                setFerramentasDescricacao();


                size = Integer.parseInt(reader.nextLine());
                for (int i = 0; i < size; i++) {
                    s = reader.nextLine();
                    s = s.replace(" | ", "|");
                    filter = s.split("\\|");
                    Programmer p = new Programmer();

                    switch (filter[0]) {
                        case "BLUE" -> {
                            p.setColor(ProgrammerColor.BLUE);
                        }
                        case "GREEN" -> {
                            p.setColor(ProgrammerColor.GREEN);
                        }
                        case "PURPLE" -> {
                            p.setColor(ProgrammerColor.PURPLE);
                        }
                        case "BROWN" -> {
                            p.setColor(ProgrammerColor.BROWN);
                        }
                    }

                    p.setId(Integer.parseInt(filter[1]));
                    p.setName(filter[2]);
                    p.setPos(Integer.parseInt(filter[3]));

                    if (!filter[4].equals("No tools")) {
                        String[] fil2 = filter[4].split(";");
                        for (String fil2Reader : filter) {
                            for (Ferramenta fs : this.ferramentas) {
                                if (fs.getDescricao().equals(fil2Reader)) {
                                    p.addFerramenta(fs);
                                    break;
                                }
                            }
                        }
                    }

                    if (filter[5].equals("Em jogo")) {

                    } else if (filter[5].equals("Derrotado")) {
                        p.perdeu();
                    } else {
                        // TEM LINGUAGES
                        String[] fil2 = filter[5].split(";");
                        ArrayList<String> temp = new ArrayList<>();
                        for (String fil2Reader : fil2) {
                            temp.add(fil2Reader);
                        }
                        p.setLinguages(temp);
                    }

                    if (filter.length == 7 && filter[6].equals("Derrotado")) {
                        p.perdeu();
                    }
                    this.programmers.add(p);

                }

                this.programmerTurnPos = Integer.parseInt(reader.nextLine());
                this.programersInPlay = Integer.parseInt(reader.nextLine());

                size = Integer.parseInt(reader.nextLine());
            this.programmersTurn = new Programmer[size];

                for (int i = 0; i < size; i++) {
                    s = reader.nextLine();
                    s = s.replace(" | ", "|");
                    filter = s.split("\\|");
                    Programmer p = new Programmer();

                    switch (filter[0]) {
                        case "BLUE" -> {
                            p.setColor(ProgrammerColor.BLUE);
                        }
                        case "GREEN" -> {
                            p.setColor(ProgrammerColor.GREEN);
                        }
                        case "PURPLE" -> {
                            p.setColor(ProgrammerColor.PURPLE);
                        }
                        case "BROWN" -> {
                            p.setColor(ProgrammerColor.BROWN);
                        }
                    }

                    p.setId(Integer.parseInt(filter[1]));
                    p.setName(filter[2]);
                    p.setPos(Integer.parseInt(filter[3]));

                    if (!filter[4].equals("No tools")) {
                        String[] fil2 = filter[4].split(";");
                        for (String fil2Reader : filter) {
                            for (Ferramenta fs : this.ferramentas) {
                                if (fs.getDescricao().equals(fil2Reader)) {
                                    p.addFerramenta(fs);
                                    break;
                                }
                            }
                        }
                    }

                    if (filter[5].equals("Em jogo")) {

                    } else if (filter[5].equals("Derrotado")) {
                        p.perdeu();
                    } else {
                        // TEM LINGUAGES
                        String[] fil2 = filter[5].split(";");
                        ArrayList<String> temp = new ArrayList<>();
                        for (String fil2Reader : fil2) {
                            temp.add(fil2Reader);
                        }
                        p.setLinguages(temp);
                    }

                    if (filter.length == 7 && filter[6].equals("Derrotado")) {
                        p.perdeu();
                    }
                    this.programmersTurn[i] = p;
                }

            size = Integer.parseInt(reader.nextLine());
            this.programmersTurn = new Programmer[size];

            for (int i = 0; i < size; i++) {
                int id = Integer.parseInt(reader.nextLine());
                for(Programmer p : this.programmers){
                    if(p.id == id){
                        this.programmersTurn[i] = p;
                    }
                }
            }


            reader.close();
            System.out.println(this.programmerTurnPos);
            this.playing = true;
            System.out.println("------");
            for(Programmer p : this.programmers){
                System.out.println(p.toString());
            }

            System.out.println("------");
            for(Programmer p : this.programmersTurn){
                System.out.println(p.toString());
            }

        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }



    public String getTitle(int position){
        if(abismosHashMap.containsKey(position)){
            return abismosHashMap.get(position).getTitulo();
        }
        if(ferramentasHashMap.containsKey(position)){
            return ferramentasHashMap.get(position).getTitulo();
        }
        return null;
    }
}
