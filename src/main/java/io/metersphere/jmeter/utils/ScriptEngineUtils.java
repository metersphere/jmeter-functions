package io.metersphere.jmeter.utils;


import io.metersphere.jmeter.functions.MockFunction;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.javascript.engine.RhinoScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;

public class ScriptEngineUtils {
    private static ScriptEngine engine;

    static {
        ScriptEngineUtils.init();
    }

    public static String buildFunctionCallString(String input) {
        if (!StringUtils.startsWith(input, "@")) {
            return input;
        }
        return "${" + MockFunction.KEY +
                "(" +
                RegExUtils.replaceAll(input, ",", "\\\\,") +
                ")" +
                "}";
    }

    public static String calculate(String input) {
        try {
            return engine.eval("calculate('" + input + "')").toString();
        } catch (ScriptException e) {
            e.printStackTrace();
            return input;
        }
    }

    public static void init() {
        try {
            RhinoScriptEngineFactory rhinoScriptEngineFactory = new RhinoScriptEngineFactory();
            engine = rhinoScriptEngineFactory.getScriptEngine();

            String scriptString = IOUtils.toString(ScriptEngineUtils.class.getResource("/javascript/func.js"), StandardCharsets.UTF_8);
            engine.eval(scriptString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
