// Since ChessPiece is just a wrapper we want to baseUnwrap it to get the actual piece.
// This is true for every sub-package in chess, but in java those are separate packages,
// and therefore they cannot access ChessPiece.unwrap method
// This class provides a workaround - it lives in the same package as ChessPiece, so it can call unwrap
// This way we can unwrap ChessPiece from anywhere and we are happy that ChessPiece does not expose unwrapping publicly
package app.chess;

/**
 * Shakin' Stevens - Cry just a little bit <br> <br>
 * <p>
 * Every night we have to whisper goodbye <br> I cry just a little bit (cry just a little bit) <br> I know it's crazy
 * and I don't know why <br> But I died just a little bit (die just a little bit) <br> <br>
 * <p>
 * Yes, you will cry a little bit when you realise what this class does. <br> But then you will realize that because of
 * that, we don't violate the ISP, so clean coders can be happy. <br> I hope <br>
 */
public class ChessPieceUnwrapper {
    protected ChessPieceUnwrapper() {
    }

    public static AbstractChessPiece unwrap(ChessPiece a) {
        //It's protected, so we can extend it inside rules package and be able to baseUnwrap stuff there
        //Without violating the ISP
        if (a == null) {
            return null;
        }
        return a.unwrap();
    }
}
