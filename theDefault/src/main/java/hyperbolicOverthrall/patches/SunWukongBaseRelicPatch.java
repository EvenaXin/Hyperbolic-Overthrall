package hyperbolicOverthrall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class SunWukongBaseRelicPatch {
    @SpirePatch(
            clz = DiscardAtEndOfTurnAction.class,
            method = "update"
    )
    public static class DiscardAtEndOfTurnActionPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("hasPower")) {
                        m.replace("$_ = $proceed($$) || " + PatchMethods.class.getName() + ".getRunicRelics();");
                    }
                }
            };
        }
    }
}