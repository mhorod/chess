package app.chess;

/**
 * Shakin' Stevens - Cry just a little bit <br> <br>
 *
 * Every night we have to whisper goodbye <br>
 * I cry just a little bit (cry just a little bit) <br>
 * I know it's crazy and I don't know why <br>
 * But I died just a little bit (die just a little bit) <br> <br>
 *
 * Yes, you will cry a little bit when you realise what this class does. <br>
 * But then you will realize that because of that, we don't violate the ISP, so clean coders can be happy. <br>
 * I hope <br>
 *
 * @author Dominik
 */
public class ChessPieceConverter {
    protected ChessPieceConverter(){
    }
    protected static AbstractChessPiece fakeConvert(ChessPiece a){
        //It's protected, so we can extend it inside rules package and be able to unwrap stuff there
        //Without violating the ISP
        return a.unwrap();
    }
}
