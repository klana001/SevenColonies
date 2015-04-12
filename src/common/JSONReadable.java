package common;

import org.json.simple.JSONObject;

public interface JSONReadable
{
	public void readFromJSON(JSONObject json) throws Exception;
}
