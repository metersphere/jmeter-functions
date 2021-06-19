package io.metersphere.jmeter.functions;

import io.metersphere.jmeter.utils.ScriptEngineUtils;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MockFunction extends AbstractFunction {
    public static final String KEY = "__Mock";
    private static final List<String> desc = new LinkedList<>();

    private CompoundVariable varName;

    static {
        desc.add("String to calculate Mock");
    }

    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler) {
        String value = "";
        if (varName != null) {
            JMeterVariables vars = getVariables();
            final String varTrim = varName.execute().trim();
            if (vars != null && varTrim.length() > 0) {
                // vars will be null
                // on TestPlan
                value = ScriptEngineUtils.calculate(varTrim);
                vars.put(varTrim, value);
            }
        }
        return value;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 1, 1);
        //将值存入变量中
        Object[] values = parameters.toArray();
        varName = (CompoundVariable) values[0];
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

}
