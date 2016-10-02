package net.dusterthefirst.TEA.variables.subsets;

import net.dusterthefirst.TEA.variables.TEAVariable;

public class Test extends TEAVariable{

	@Override
	public boolean onReload() {
		variables.clear();
		variables.put("hello", 21);
		variables.put("server", server.getOnlinePlayers());
		return false;
	}

	@Override
	public Object get(String value) {
		return variables.getOrDefault(value, "null");
	}

}
