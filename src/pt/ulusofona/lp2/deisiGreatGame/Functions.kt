package pt.ulusofona.lp2.deisiGreatGame

enum class CommandType {
    GET , POST
}

fun command(ct : CommandType) : (GameManager, List<String>) -> String? {
    if(ct.equals(CommandType.GET)){
        return ::commandGet
    }
    if(ct.equals(CommandType.POST)){
        return ::commandPost
    }
    return ::commandPost // MUDAR
}

fun router() : (CommandType) -> (GameManager, List<String>) -> String?{
    return ::command;
}


fun getPlayer(Gm : GameManager , args : List<String>) : String?{
    val r = Gm.programmers.filter { p -> p.getName().split(" ")[0] == args[1]  }
    if(r.size == 0){
        return "Inexistent player";
    }else{
        return r[0].toString();
    }
}

fun getPlayersByLanguage (Gm : GameManager , args : List<String>) : String?{
    val r = Gm.programmers.filter { p -> p.getLinguages().contains(args[1])   }
    if(r.size == 0){
        return "";
    }else{
        var stringResponse =""
        r.forEach{
            stringResponse += "," + it.getName()

        }
        return stringResponse.substring(1);
    }
}

fun getPOLYGLOTS (Gm : GameManager , args : List<String>) : String?{
    val r = Gm.programmers.filter { p -> p.getLinguages().size > 1  }
    if(r.size == 0){
        return "";
    }else{
        var stringResponse =""
        r.sortedByDescending { lp -> lp.getLinguages().size }
                .reversed()
        .forEach(){
            stringResponse += it.getName() + ":" + it.getLinguages().size + "\n"
        }
        return stringResponse.substring(0,stringResponse.length-1);
    }
}



fun getMostUsedPositions (Gm : GameManager , args : List<String>) : String?{
    /*
    val r = Gm.numPisadasCasas.filterValues {  }
    val i = r.take( args[1].toInt())


    if(r.isEmpty()){
        return "";
    }else{

    }
    */
    return null
}

fun commandGet(Gm : GameManager , args : List<String> ) : String?{
    when(args[0]){
        "PLAYER" -> return getPlayer(Gm, args);
        "PLAYERS_BY_LANGUAGE" -> return getPlayersByLanguage(Gm, args);
        "POLYGLOTS" -> return getPOLYGLOTS(Gm, args);
        "MOST_USED_POSITIONS" -> return getMostUsedPositions(Gm, args);
        else -> return null;
    }
}

fun postMove(Gm : GameManager , args : List<String>) : String?{
    val r = Gm.moveCurrentPlayer(args[1].toInt());
    val f = Gm.reactToAbyssOrTool();
    if(f == null){
        return "OK";
    }else{
        return f;
    }
}

fun postAbyss(Gm : GameManager , args : List<String>) : String?{
    val r = Gm.abismosHashMap.filterKeys { k -> k == args[2].toInt() }
    if(r.size == 0){
        Gm.addAbismo(args[1].toInt(), args[2].toInt());
        return "OK"
    }else{
        return "Position is occupied"
    }
}


fun commandPost(Gm : GameManager , args : List<String> ) : String?{
    when(args[0]){
        "MOVE" -> return postMove(Gm, args);
        "ABYSS" -> return postAbyss(Gm, args);
        else -> return null;
    }
}

class Functions {

    enum class CommandType {
        GET , POST
    }


}