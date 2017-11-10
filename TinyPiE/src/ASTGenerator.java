import org.antlr.v4.runtime.tree.ParseTree;

import parser.TinyPiEParser.BitOrExprContext;
import parser.TinyPiEParser.BitAndExprContext;
import parser.TinyPiEParser.AddExprContext;
import parser.TinyPiEParser.ExprContext;
import parser.TinyPiEParser.LiteralExprContext;
import parser.TinyPiEParser.MulExprContext;
import parser.TinyPiEParser.ParenExprContext;
import parser.TinyPiEParser.VarExprContext;

public class ASTGenerator {	
	ASTNode translateExpr(ParseTree ctxx) {
		if (ctxx instanceof ExprContext) {
			ExprContext ctx = (ExprContext) ctxx;
			return translateExpr(ctx.bitOrExpr());
		} else if (ctxx instanceof BitOrExprContext) {
			BitOrExprContext ctx = (BitOrExprContext) ctxx;
			if (ctx.bitOrExpr() == null)
				return translateExpr(ctx.bitAndExpr());
			ASTNode lhs = translateExpr(ctx.bitOrExpr());
			ASTNode rhs = translateExpr(ctx.bitAndExpr());
			return new ASTBinaryExprNode(ctx.BITOR().getText(), lhs, rhs);
		}else if (ctxx instanceof BitAndExprContext) {
			BitAndExprContext ctx = (BitAndExprContext) ctxx;
			if (ctx.bitAndExpr() == null)
				return translateExpr(ctx.addExpr());
			ASTNode lhs = translateExpr(ctx.bitAndExpr());
			ASTNode rhs = translateExpr(ctx.addExpr());
			return new ASTBinaryExprNode(ctx.BITAND().getText(), lhs, rhs);
		} else if (ctxx instanceof AddExprContext) {
			AddExprContext ctx = (AddExprContext) ctxx;
			if (ctx.addExpr() == null)
				return translateExpr(ctx.mulExpr());
			ASTNode lhs = translateExpr(ctx.addExpr());
			ASTNode rhs = translateExpr(ctx.mulExpr());
			return new ASTBinaryExprNode(ctx.ADDOP().getText(), lhs, rhs);
		} else if (ctxx instanceof MulExprContext) {
			MulExprContext ctx = (MulExprContext) ctxx;
			if (ctx.mulExpr() == null)
				return translateExpr(ctx.unaryExpr());
			ASTNode lhs = translateExpr(ctx.mulExpr());
			ASTNode rhs = translateExpr(ctx.unaryExpr());
			return new ASTBinaryExprNode(ctx.MULOP().getText(), lhs, rhs);
		} else if (ctxx instanceof LiteralExprContext) {
			LiteralExprContext ctx = (LiteralExprContext) ctxx;
			int value = Integer.parseInt(ctx.VALUE().getText());
			return new ASTNumberNode(value);
		} else if (ctxx instanceof VarExprContext) {
			VarExprContext ctx = (VarExprContext) ctxx;
			String varName = ctx.IDENTIFIER().getText();
			return new ASTVarRefNode(varName);
		} else if (ctxx instanceof ParenExprContext) {
			ParenExprContext ctx = (ParenExprContext) ctxx;
			return translateExpr(ctx.expr());
		}
		throw new Error("Unknown parse tree node: "+ctxx.getText());		
	}
	ASTNode translate(ParseTree ctxx) {
		return translateExpr(ctxx);
	}
}
