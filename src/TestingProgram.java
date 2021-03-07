import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class TestingProgram {
    // chessPiece
    @Test
    public void chessPieceClassTest() {
        boolean check = Modifier.isAbstract(ChessPiece.class.getModifiers()) && ChessPiece.class.getConstructors().length > 0;

        Field[] fields = ChessPiece.class.getDeclaredFields();
        Method[] methods = ChessPiece.class.getDeclaredMethods();

        Map<String, String> fDict = new HashMap<>();
        Map<String, String> mDict = new HashMap<>();

        for (Field field : fields) {
            fDict.put(field.getName(), field.getType().getName());
        }

        for (Method method : methods) {
            if (method.getName().equals("getColor") || method.getName().equals("canMoveToPosition") ||
                    method.getName().equals("getSymbol")) {
                if (!Modifier.isAbstract(method.getModifiers())) check = false;
            }
            mDict.put(method.getName(), method.getReturnType().getName());
        }

        check = check && fDict.containsKey("color") && fDict.containsKey("check") && mDict.containsKey("getColor") &&
                mDict.containsKey("canMoveToPosition") && mDict.containsKey("getSymbol");

        if (check) check = fDict.get("color").equals("java.lang.String") && fDict.get("check").equals("boolean") &&
                mDict.get("getColor").equals("java.lang.String") && mDict.get("getSymbol").equals("java.lang.String") &&
                mDict.get("canMoveToPosition").equals("boolean");


        Assert.assertTrue("The ChessPiece class is incorrect", check);
    }

    // horse
    @Test
    public void horseClassTest() {
        boolean check = Horse.class.getSuperclass().getSimpleName().equals("ChessPiece");

        ChessBoard board = new ChessBoard("White");
        board.board[3][3] = new Horse("White");

        for (int i = 7; i > -1; i--) {
            for (int j = 0; j < 8; j++) {
                boolean b = (i == 2 && j == 1) || (i == 4 && j == 1) ||
                        (i == 1 && j == 2) || (i == 5 && j == 2) ||
                        (i == 1 && j == 4) || (i == 5 && j == 4) ||
                        (i == 2 && j == 5) || (i == 4 && j == 5);
                if (board.board[3][3].canMoveToPosition(board, 3, 3, i, j)) {

                } else {
                    if (b) {
                        check = false;
                    }
                }
            }
        }

        board.board[0][0] = new Horse("White");
        check = check && !board.board[0][0].canMoveToPosition(board, 0, 0, -2 , -1);

        Assert.assertTrue("The Horse moved is incorrect", check);
    }


    // pawn
    @Test
    public void pawnClassTest() {
        boolean check = Pawn.class.getSuperclass().getSimpleName().equals("ChessPiece");
        ChessBoard board = new ChessBoard("White");
        board.board[1][0] = new Pawn("White");
        board.board[6][1] = new Pawn("Black");

        check = check && board.board[1][0].canMoveToPosition(board, 1, 0, 2, 0) &&
                board.board[1][0].canMoveToPosition(board, 1, 0, 3, 0) &&
                !board.board[1][0].canMoveToPosition(board, 1, 0, 4, 0) &&
                !board.board[1][0].canMoveToPosition(board, 1, 0, 5, 0) &&
                !board.board[1][0].canMoveToPosition(board, 1, 0, 6, 0) &&
                !board.board[1][0].canMoveToPosition(board, 1, 0, 7, 0) &&
                !board.board[1][0].canMoveToPosition(board, 1, 0, 2, 1) &&
                !board.board[1][0].canMoveToPosition(board, 1, 0, -1, 1) &&

                board.board[6][1].canMoveToPosition(board, 6, 1, 5, 1) &&
                board.board[6][1].canMoveToPosition(board, 6, 1, 4, 1) &&
                !board.board[6][1].canMoveToPosition(board, 6, 1, 3, 1) &&
                !board.board[6][1].canMoveToPosition(board, 6, 1, 2, 1) &&
                !board.board[6][1].canMoveToPosition(board, 6, 1, 1, 1) &&
                !board.board[6][1].canMoveToPosition(board, 6, 1, 0, 1) &&
                !board.board[6][1].canMoveToPosition(board, 6, 1, 5, 2) &&
                !board.board[6][1].canMoveToPosition(board, 6, 1, 8, 1) &&
                board.board[1][0].getColor().equals("White") && board.board[6][1].getColor().equals("Black") &&
                board.board[1][0].getSymbol().equals("P") && board.board[6][1].getSymbol().equals("P");

        Assert.assertTrue("The Pawn moved is incorrect", check);
    }


    //bishop
    @Test
    public void bishopClassTest() {
        boolean check = Bishop.class.getSuperclass().getSimpleName().equals("ChessPiece");
        ChessBoard board = new ChessBoard("Black");
        board.board[3][3] = new Bishop("Black");
        ChessPiece bishop = board.board[3][3];

        for (int i = 7; i > -1; i--) {
            for (int j = 0; j < 8; j++) {
                boolean b = (i == 2 && j == 2) || (i == 1 && j == 1) ||
                        (i == 0 && j == 0) || (i == 4 && j == 4) ||
                        (i == 5 && j == 5) || (i == 6 && j == 6) ||
                        (i == 7 && j == 7) ||
                        (i == 4 && j == 2) || (i == 5 && j == 1) ||
                        (i == 6 && j == 0) || (i == 2 && j == 4) ||
                        (i == 1 && j == 5) || (i == 0 && j == 6);
                if (bishop.canMoveToPosition(board, 3, 3, i, j)) {
                    if (!b) {
                        check = false;
                    }
                } else {
                    if (b) {
                        check = false;
                    }
                }
            }
        }

        Assert.assertTrue("The Bishop moved is incorrect", check && bishop.getSymbol().equals("B") && bishop.getColor().equals("Black"));
    }


    // rook
    @Test
    public void rookClassTest() {
        boolean check = Rook.class.getSuperclass().getSimpleName().equals("ChessPiece");
        ChessBoard board = new ChessBoard("White");
        board.board[3][3] = new Rook("White");
        ChessPiece rook = board.board[3][3];

        for (int i = 7; i > -1; i--) {
            for (int j = 0; j < 8; j++) {
                boolean b = (i == 3 && j == 2) || (i == 3 && j == 1) ||
                        (i == 3 && j == 0) || (i == 3 && j == 4) ||
                        (i == 3 && j == 5) || (i == 3 && j == 6) ||
                        (i == 3 && j == 7) ||
                        (i == 2 && j == 3) || (i == 1 && j == 3) ||
                        (i == 0 && j == 3) || (i == 4 && j == 3) ||
                        (i == 5 && j == 3) || (i == 6 && j == 3) ||
                        (i == 7 && j == 3);
                if (rook.canMoveToPosition(board, 3, 3, i, j)) {
                    if (!b) {
                        check = false;
                    }
                } else {
                    if (b) {
                        check = false;
                    }
                }
            }
        }

        Assert.assertTrue("The Rook moved is incorrect", check && rook.getSymbol().equals("R") && rook.getColor().equals("White"));
    }

    // Queen and King
    @Test
    public void queenClassTest() {
        boolean check = Queen.class.getSuperclass().getSimpleName().equals("ChessPiece");
        ChessBoard board = new ChessBoard("White");
        board.board[3][3] = new Queen("White");
        ChessPiece queen = board.board[3][3];

        for (int i = 7; i > -1; i--) {
            for (int j = 0; j < 8; j++) {
                boolean b = (i == 2 && j == 2) || (i == 1 && j == 1) ||
                        (i == 0 && j == 0) || (i == 4 && j == 4) ||
                        (i == 5 && j == 5) || (i == 6 && j == 6) ||
                        (i == 7 && j == 7) ||
                        (i == 4 && j == 2) || (i == 5 && j == 1) ||
                        (i == 6 && j == 0) || (i == 2 && j == 4) ||
                        (i == 1 && j == 5) || (i == 0 && j == 6) ||
                        (i == 3 && j == 2) || (i == 3 && j == 1) ||
                        (i == 3 && j == 0) || (i == 3 && j == 4) ||
                        (i == 3 && j == 5) || (i == 3 && j == 6) ||
                        (i == 3 && j == 7) ||
                        (i == 2 && j == 3) || (i == 1 && j == 3) ||
                        (i == 0 && j == 3) || (i == 4 && j == 3) ||
                        (i == 5 && j == 3) || (i == 6 && j == 3) ||
                        (i == 7 && j == 3);
                if (queen.canMoveToPosition(board, 3, 3, i, j)) {
                    if (!b) {
                        check = false;
                    }
                } else {
                    if (b) {
                        check = false;
                    }
                }
            }
        }

        Assert.assertTrue("The Queen moved is incorrect", check && queen.getSymbol().equals("Q") && queen.getColor().equals("White"));
    }

    @Test
    public void kingClassTest() {
        boolean check = King.class.getSuperclass().getSimpleName().equals("ChessPiece");
        Method[] methods = King.class.getDeclaredMethods();
        Map<String, String> d = new HashMap<>();
        for (Method method: methods) {
            d.put(method.getName(), method.getReturnType().getName());
        }

        check = d.containsKey("isUnderAttack");
        if (check) check = d.get("isUnderAttack").equals("boolean");

        ChessBoard board = new ChessBoard("White");
        board.board[3][3] = new King("White");
        ChessPiece king = board.board[3][3];

        for (int i = 7; i > -1; i--) {
            for (int j = 0; j < 8; j++) {
                boolean b = (i == 3 && j == 2) || (i == 3 && j == 4) ||
                        (i == 4 && j == 3) || (i == 2 && j == 3) ||
                        (i == 4 && j == 4) || (i == 2 && j == 2) ||
                        (i == 4 && j == 2) ||
                        (i == 2 && j == 4);
                if (king.canMoveToPosition(board, 3, 3, i, j)) {
                    if (!b) {
                        check = false;
                    }
                } else {
                    if (b) {
                        check = false;
                    }
                }
            }
        }

        Assert.assertTrue("The King moved is incorrect", check && king.getSymbol().equals("K") && king.getColor().equals("White"));
    }


    //castling
    @Test
    public void castlingTest() {
        boolean check;
        ChessBoard board = new ChessBoard("White");
        board.board[0][7] = new Rook("White");
        board.board[0][4] = new King("White");
        board.board[7][7] = new Rook("Black");
        board.board[7][4] = new King("Black");
        board.castling7();
        check = board.board[0][7] == null && board.board[0][4] == null && board.board[7][7] != null &&
                board.board[7][4] != null && board.board[0][6].getSymbol().equals("K") &&
                board.board[0][5].getSymbol().equals("R");
        board.castling7();
        check = check && board.board[7][4] == null && board.board[7][7] == null &&
                board.board[7][6].getSymbol().equals("K") && board.board[7][5].getSymbol().equals("R");

        check = check && board.board[0][6].getColor().equals("White") && board.board[0][5].getColor().equals("White") &&
                board.board[7][6].getColor().equals("Black") && board.board[7][5].getColor().equals("Black");

        ChessBoard board1 = new ChessBoard("White");
        board1.board[0][4] = new King("White");
        board1.board[0][7] = new Horse("White");
        check = check && !board1.castling7();

        board1.board[0][7] = new Rook("White");
        board1.moveToPosition(0, 7, 0, 6);
        board1.nowPlayer = "White";
        board1.moveToPosition(0, 6, 0, 7);
        board1.nowPlayer = "White";
        check = check && !board1.castling7();

        Assert.assertTrue("The castling7() is incorrect", check);
    }
}
