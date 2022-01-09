package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception{
    String message;
    Boolean invalidAbyss;
    Boolean invalidTool;
    String idFerramenta;

    InvalidInitialBoardException(String message){
        this.message = message;
        this.invalidAbyss = false;
        this.invalidTool = false;
    }
    InvalidInitialBoardException(String message, String idFerramenta, Boolean cameFromAbyss, Boolean cameFromTool) {
        this.message = message;
        this.invalidAbyss = cameFromAbyss;
        this.invalidTool = cameFromTool;
        this.idFerramenta = idFerramenta;
    }
    public String getMessage() {
        return message;
    }
    public boolean isInvalidAbyss(){
        return this.invalidAbyss;
    }
    public boolean isInvalidTool(){
        return this.invalidTool;
    }
    public String getTypeId(){
        return this.idFerramenta;
    }
}
