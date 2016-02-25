// This class is both used to resolve the dynamic variable names and
// to provide their values. These two functions can also be
// implemented by two different classes.
public class DVResolverProvider extends gnu.jel.DVMap {

    private java.util.HashMap<String,Object> properties=
	new java.util.HashMap<String,Object>();

    // adds a new property
    protected void addProperty(String name,Object value) {
	properties.put(name,value);
    };

    // implements the method of DVResolver interface,
    // used by the compiler to query about available dynamic
    // variables
    public String getTypeName(String name) {
	Object val=properties.get(name);
	if (val==null) return null; // dynamic variable does not exist
	if (val instanceof Data) return "Data";
	if (val instanceof String) return "String";
	// the type is not supported we say the variable is not defined
	return null;
    };
    
    // Next we have those YYY getXXXProperty(String) methods described in
    // the manual

    public Data getDataProperty(String name) {
	return (Data)properties.get(name);
    };

    public String getStringProperty(String name) {
	return (String)properties.get(name);
    };

};
