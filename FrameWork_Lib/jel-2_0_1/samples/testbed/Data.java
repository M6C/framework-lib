public class Data implements gnu.jel.reflect.Double {
    public double value=10.0;
    public double squared_value() {
	return value*value;
    };

    public Data(double value) {
	this.value=value;
    };

    // implements gnu.jel.reflect.Double interface
    public double getValue() {
	return value;
    };
};
