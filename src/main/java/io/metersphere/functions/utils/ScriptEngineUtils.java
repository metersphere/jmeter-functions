package io.metersphere.functions.utils;

import com.oracle.truffle.js.scriptengine.GraalJSEngineFactory;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;

public class ScriptEngineUtils {
    private static ScriptEngine engine;

    static {
        try {
            System.out.println(Thread.currentThread().getContextClassLoader());
            engine = new GraalJSEngineFactory().getScriptEngine();
            String script = IOUtils.toString(ScriptEngineUtils.class.getResource("/javascript/func.js"), StandardCharsets.UTF_8);
            engine.eval(script);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String calculate(String input) {
        try {
            return engine.eval("calculate('" + input + "')").toString();
        } catch (ScriptException e) {
            e.printStackTrace();
            return input;
        }
    }
}
