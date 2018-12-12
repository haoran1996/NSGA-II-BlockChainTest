package NSGA.objectivefunction;

import NSGA.Interface.IObjectiveFunction;
import NSGA.datastructure.Chromosome;
import NSGA.datastructure.ParetoObject;
import blockchain.CMDRedirect.Main;
import blockchain.JDBC.MysqlUtil;
import org.moeaframework.problem.DTLZ.BCdata;

import static blockchain.CMDRedirect.Main.TableName;

public class BC_gas implements IObjectiveFunction {
    private static final String AXIS_TITLE = "Gas";

    @Override
    public double objectiveFunction(final ParetoObject paretoObject) {
        return objectiveFunction(paretoObject.getChromosome());
    }

    @Override
    public double objectiveFunction(final Chromosome chromosome) {
        return objectiveFunction(chromosome.getID());
    }

    @Override
    public double objectiveFunction(int id) {
        BCdata bcdata = MysqlUtil.GetBCdataByID(id);
        return (double)bcdata.getGas();
    }

    @Override
    public String getAxisTitle() {
        return AXIS_TITLE;
    }
}
